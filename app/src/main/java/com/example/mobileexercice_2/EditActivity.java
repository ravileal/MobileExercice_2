package com.example.mobileexercice_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;


public class EditActivity extends AppCompatActivity {

    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Integer a = getIntent().getIntExtra(MainActivity.position, 0);
        paciente = RepositoryPaciente.getPaciente(a);

        showPaciente();
        onCreateButtonListern();
    }

    private void onCreateButtonListern(){
        findViewById(R.id.button_upd_editar).setOnClickListener(v -> {
            updPaciente();
            finish();
        });
        findViewById(R.id.button_main_add).setOnClickListener(v -> finish() );
    }

    private void showPaciente(){
        ( (TextInputEditText) findViewById(R.id.text_input_edt_nomePaciente)).setText(paciente.getNome());
        ( (TextInputEditText) findViewById(R.id.text_input_edt_idade)).setText(String.valueOf(paciente.getIdade()));
        ( (TextInputEditText) findViewById(R.id.text_input_edt_tipoSanguePaciente)).setText(paciente.getTipoSangue());
    }

    private void updPaciente(){
        String text;
        TextInputEditText inputText;

        inputText = findViewById(R.id.text_input_edt_nomePaciente);
        text = inputText.getText().toString();
        if(text.isEmpty()) return;
        paciente.setNome(text);

        inputText = findViewById(R.id.text_input_edt_idade);
        text = inputText.getText().toString();
        if(text.isEmpty()) return;
        paciente.setIdade(Integer.parseInt(text));

        inputText = findViewById(R.id.text_input_edt_tipoSanguePaciente);
        text = inputText.getText().toString();
        if(text.isEmpty()) return;
        paciente.setTipoSangue(text);

        RepositoryPaciente.setPaciente(paciente);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}