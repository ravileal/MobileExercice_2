package DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Model;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.FirestoreClient;


public abstract class DAO<T extends Model> {

    protected String tableName;
    protected Class classType;

    public void create(Model obj) {
        DatabaseReference table = Connection.configureTable(tableName);
        obj.setId(table.push().getKey());
        table.child(obj.getId()).setValue(obj);
    }

    public void readAll(Response.Result<T> response){
        Connection.configureTable(tableName).addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<T> objList = new ArrayList<T>();
                    for (DataSnapshot noteSnapshot: dataSnapshot.getChildren())
                        objList.add((T) noteSnapshot.getValue(classType));

                    response.result(objList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(tableName+" error data: ", databaseError.getMessage());
                }
            });
    }

    protected void readId(String id, Response.Result<T> response, Response.MapToObjectList<T> mapToObject){
        Connection.configureTable(tableName).child(id).get().addOnCompleteListener(
            new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        HashMap hashMap = (HashMap) task.getResult().getValue();

                        List<T> list = new ArrayList<T>();
                        list.add(mapToObject.result(hashMap));
                        response.result(list);
                    }
                }
            }
        );
    }

    protected DatabaseReference update(String id){
        return Connection.configureTable(tableName).child(id);
    }

    public void delete(String id){
        Connection.configureTable(tableName).child(id).removeValue();
    }

}
