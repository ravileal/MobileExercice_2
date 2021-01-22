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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    ListView listview_paciente;
    TextInputEditText inputText;
    ArrayAdapter<String> adapter;
    public static final String position = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listview_paciente = findViewById(R.id.list_viewer_pacientes);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        for(Paciente p : RepositoryPaciente.getList_paciente())
            adapter.add(p.getNome());
        listview_paciente.setAdapter(adapter);

        onCreateButtonListern();
        onCreateListViewListern();
    }

    private void clearText(){
        ((TextInputEditText)findViewById(R.id.text_input_nomePaciente)).setText("");
        ((TextInputEditText)findViewById(R.id.text_input_idade)).setText("");
        ((TextInputEditText)findViewById(R.id.text_input_tipoSanguePaciente)).setText("");
    }

    private void onCreateButtonListern(){
        findViewById(R.id.button_main_add).setOnClickListener(v -> {

            Paciente p = addPaciente();
            if(p == null) return;
            clearText();

            adapter.add(p.getNome());
            listview_paciente.setAdapter(adapter);
        });
    }

    private void onCreateListViewListern(){
        listview_paciente.setOnItemClickListener((parent, view, position, id) -> {
            openPopUp(RepositoryPaciente.getPaciente(position));
        });
    }

    private Paciente addPaciente(){
        String text;
        Paciente paciente = new Paciente();

        paciente.setId(RepositoryPaciente.getSize());

        inputText = findViewById(R.id.text_input_nomePaciente);
        text = inputText.getText().toString();
        Log.d("aasddd : ", text);
        if(text.isEmpty()) return null;
        paciente.setNome(text);

        inputText = findViewById(R.id.text_input_idade);
        text = inputText.getText().toString();
        if(text.isEmpty()) return null;
        paciente.setIdade(Integer.parseInt(text));

        inputText = findViewById(R.id.text_input_tipoSanguePaciente);
        text = inputText.getText().toString();
        if(text.isEmpty()) return null;
        paciente.setTipoSangue(text);

        RepositoryPaciente.addPaciente(paciente);
        return paciente;
    }

    private void openPopUp(Paciente paciente){
        Dialog dialog = new Dialog(this, R.style.FullHeightDialog );
        dialog.setContentView(R.layout.fragment);
        dialog.show();

        String text = "" +
                "Id : " + paciente.getId() + "\n" +
                "Nome: " + paciente.getNome() + "\n" +
                "Idade: " + paciente.getIdade() + "\n" +
                "Tipo de sangue: " + paciente.getTipoSangue() +
                "";

        TextView textView = dialog.findViewById(R.id.textView_info_paciente);
        textView.setText(text);

        dialog.findViewById(R.id.button_popup_voltar).setOnClickListener( v -> {
            dialog.dismiss();
        });

        dialog.findViewById(R.id.button_popup_editar).setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra( position, paciente.getId());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void onClickItemListern(){
        listview_paciente = findViewById(R.id.list_viewer_pacientes);

        listview_paciente.setOnClickListener(v -> {
            String nome = listview_paciente.getSelectedItem().toString();
            Toast.makeText(this, nome, Toast.LENGTH_LONG);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return (id == R.id.action_settings)? true : super.onOptionsItemSelected(item);
    }
}