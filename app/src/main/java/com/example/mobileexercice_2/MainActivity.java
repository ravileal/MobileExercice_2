package com.example.mobileexercice_2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Controller.ControllerPaciente;
import Model.Paciente;


public class MainActivity extends AppCompatActivity {

    private ListView listview_paciente;
    private ArrayList<String> listIdPaciente = new ArrayList<>();
    public static final String IDPACIENTE = "Paciente ID";
    public static final String ACTION = "action";
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureItemListView();
        configureButtonAdicionar();
    }

    private void configureButtonAdicionar(){
        findViewById(R.id.button_main_add).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAndEditActivity.class);
            intent.putExtra(MainActivity.ACTION, ADICIONAR);
            startActivityForResult(intent, ADICIONAR);
        });
    }

    private void configureItemListView(){
        listview_paciente = findViewById(R.id.list_viewer_pacientes);
        listview_paciente.setAdapter(null);
        listview_paciente.setAdapter(configureAdapterListView());
        listview_paciente.setOnItemClickListener((parent, view, position, id) -> {
            ControllerPaciente.readId(listIdPaciente.get(position), list -> {
                openPopUp(list.get(0));
            });
        });
    }

    private ArrayAdapter<String> configureAdapterListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);

        ControllerPaciente.readAll(list -> {
            for(Paciente p : list) {
                adapter.add(p.getNome());
                listIdPaciente.add(p.getId());
            }
        });

        return adapter;
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
            Intent intent = new Intent(this, AddAndEditActivity.class);
            intent.putExtra( IDPACIENTE, paciente.getId() );
            intent.putExtra(MainActivity.ACTION, EDITAR);
            startActivityForResult(intent, EDITAR);
        });
    }

}