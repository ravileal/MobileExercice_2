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

import Controller.ControllerPaciente;
import Model.Paciente;


public class AddAndEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Button btnConfirm;
    private Button btnVoltar;
    private TextInputEditText inputNomePacinete;
    private TextInputEditText inputIdadePaciente;
    private String idPaciente;

    private final String[] tipoSangue = {
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
        setContentView(R.layout.activity_add_and_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        btnConfirm = findViewById(R.id.button_confirm);
        btnVoltar = findViewById(R.id.button_upd_voltar);
        inputNomePacinete = findViewById(R.id.text_input_nomePaciente);
        inputIdadePaciente = findViewById(R.id.text_input_idade);

        onCreateSpinner();

        int action = getIntent().getIntExtra(MainActivity.ACTION, 0);

        if(action == MainActivity.ADICIONAR) {
            btnConfirm.setText("Adicionar");
        }else{
            idPaciente = getIntent().getStringExtra(MainActivity.IDPACIENTE);
            ControllerPaciente.readId(idPaciente, list -> showPaciente(list.get(0)) );
            btnConfirm.setText("Editar");
        }

        configureButtonConfirm(action);
        configureButtonVoltar();
    }

    private void showPaciente(Paciente paciente){
        inputNomePacinete.setText(paciente.getNome());
        inputIdadePaciente.setText(String.valueOf(paciente.getIdade()));
        for (int i = 0; i < tipoSangue.length; i++)
            if(tipoSangue[i].equals(paciente.getTipoSangue()))
                spinner.setSelection(i);
    }

    private void configureButtonVoltar(){
        btnVoltar.setOnClickListener(v -> finish() );
    }

    private void configureButtonConfirm(int action){
        if(action == MainActivity.ADICIONAR)
            btnConfirm.setText("Adicionar");
        else btnConfirm.setText("Editar");

        btnConfirm.setOnClickListener(v -> {
            if(action == MainActivity.ADICIONAR) {
                if (!addPaciente(getData(action))) return;
            } else {
                if (!edtPaciente(getData(action))) return;
            }
            finish();
        });
    }

    private boolean addPaciente(Paciente paciente){
        if(paciente == null) return false;
        ControllerPaciente.create(paciente);
        setResult(MainActivity.ADICIONAR, new Intent());
        return true;
    }

    private boolean edtPaciente(Paciente paciente){
        if(paciente == null) return false;
        ControllerPaciente.update(paciente);
        setResult(MainActivity.EDITAR, new Intent());
        return true;
    }

    private Paciente getData(int action){
        Paciente paciente = new Paciente();

        String text;

        if(action == MainActivity.EDITAR) {
            if (idPaciente.isEmpty()) return null;
            paciente.setId(idPaciente);
        }

        text = inputNomePacinete.getText().toString();
        if(text.isEmpty()) return null;
        paciente.setNome(text);

        text = inputIdadePaciente.getText().toString();
        if(text.isEmpty()) return null;
        paciente.setIdade(text);

        spinner = findViewById(R.id.spinner);
        int position = spinner.getSelectedItemPosition();
        paciente.setTipoSangue(tipoSangue[position]);

        return paciente;
    }

    private void onCreateSpinner(){
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
//        String tipoSangue = parent.getItemAtPosition(position).toString();
//        paciente.setTipoSangue(tipoSangue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
