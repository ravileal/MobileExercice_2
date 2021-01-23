package com.example.mobileexercice_2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ListView listview_paciente;
    private ArrayAdapter<String> adapter;
    private static final int ATUALIZAR = 1;
    public static final String position = "Paciente ID";
    public static final int ADICIONAR = -1;
    public static final int EDITAR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview_paciente = findViewById(R.id.list_viewer_pacientes);
        showListViewPaciene();

        onClickButtonListern();
        onClickItemListern();
    }

    private void onClickButtonListern(){
        findViewById(R.id.button_main_add).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra( position, ADICIONAR);
            startActivityForResult(intent, ATUALIZAR);
        });
    }

    private void onClickItemListern(){
        listview_paciente.setOnItemClickListener((parent, view, position, id) -> {
            openPopUp(RepositoryPaciente.getPaciente(position));
        });
    }

    private void openPopUp(Paciente paciente){
        Dialog dialog = new Dialog(this, R.style.PopupDialog );
        dialog.setContentView(R.layout.fragment_popup);
        dialog.show();

        TextView textView = dialog.findViewById(R.id.textView_info_paciente);
        textView.setText(paciente.toString());

        dialog.findViewById(R.id.button_popup_voltar).setOnClickListener( v -> {
            dialog.dismiss();
        });

        dialog.findViewById(R.id.button_popup_editar).setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra( position, paciente.getId());
            startActivityForResult(intent, ATUALIZAR);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ATUALIZAR)
            showListViewPaciene();
    }

    private void showListViewPaciene(){
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);

        for(Paciente p : RepositoryPaciente.getList_paciente())
            adapter.add(p.getNome());

        listview_paciente.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings)? true : super.onOptionsItemSelected(item);
    }

}