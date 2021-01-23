package com.example.mobileexercice_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Paciente paciente;
    private int position;
    private Spinner spinner;
    private String[] tipoSangue = {
            "A+",
            "A-",
            "B+",
            "B-",
            "AB+",
            "AB-",
            "O+",
            "O-"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onCreateSpinner();

        position = getIntent().getIntExtra(MainActivity.position, 0);

        if(position != MainActivity.ADICIONAR) {
            showPaciente();
            ((Button)findViewById(R.id.button_upd_editar)).setText("Editar");
        }else{
            paciente = new Paciente();
            ((Button)findViewById(R.id.button_upd_editar)).setText("Adicionar");
        }

        onClickListener();
    }

    private void onClickListener(){
        Button button = findViewById(R.id.button_upd_editar);

        button.setOnClickListener(v -> {
            if(position == MainActivity.ADICIONAR) {
                if (!addPaciente())
                    return;
                setResult(MainActivity.ADICIONAR, new Intent());
            }else{
                if (!edtPaciente())
                    return;
                setResult(MainActivity.EDITAR, new Intent());
            }
            finish();
        });

        findViewById(R.id.button_upd_voltar).setOnClickListener(v -> finish() );
    }

    private void showPaciente(){
        paciente = RepositoryPaciente.getPaciente(position);
        ( (TextInputEditText) findViewById(R.id.text_input_nomePaciente)).setText(paciente.getNome());
        ( (TextInputEditText) findViewById(R.id.text_input_idade)).setText(String.valueOf(paciente.getIdade()));
        for (int i = 0; i < tipoSangue.length; i++)
            if(tipoSangue[i].equals(paciente.getTipoSangue()))
                spinner.setSelection(i);
    }

    private boolean edtPaciente(){
        paciente = RepositoryPaciente.getPaciente(position);

        if(!getData())
            return false;

        RepositoryPaciente.setPaciente(paciente);
        return true;
    }

    private boolean addPaciente(){
        paciente.setId(RepositoryPaciente.getSize());

        if(!getData())
            return false;

        RepositoryPaciente.addPaciente(paciente);
        return true;
    }

    private boolean getData(){
        TextInputEditText inputText;
        String text;

        inputText = findViewById(R.id.text_input_nomePaciente);
        text = inputText.getText().toString();
        if(text.isEmpty()) return false;
        paciente.setNome(text);

        inputText = findViewById(R.id.text_input_idade);
        text = inputText.getText().toString();
        if(text.isEmpty()) return false;
        paciente.setIdade(Integer.parseInt(text));

        return true;
    }

    private void onCreateSpinner(){
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipoSangue, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tipoSangue = parent.getItemAtPosition(position).toString();
        paciente.setTipoSangue(tipoSangue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}