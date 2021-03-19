package DAO;

import com.google.firebase.database.DatabaseReference;

import Model.Paciente;

public class DAOPaciente extends DAO<Paciente> {

    public DAOPaciente() {
        tableName = Tables.Paciente;
        classType = Paciente.class;
    }

    public void readId(String id, Response.Result<Paciente> response){
        super.readId(id, response,
            hashMap -> {
                Paciente j = new Paciente();
                j.setId(String.valueOf(hashMap.get("id")));
                j.setNome(String.valueOf(hashMap.get("nome")));
                j.setIdade(String.valueOf(hashMap.get("idade")));
                j.setTipoSangue(String.valueOf(hashMap.get("tipoSangue")));
                return j;
            }
        );
    }

    public void update(Paciente paciente){
        DatabaseReference tagReference = super.update(paciente.getId());

        tagReference.child("nome").setValue(paciente.getNome());
        tagReference.child("idade").setValue(paciente.getIdade());
        tagReference.child("tipoSangue").setValue(paciente.getTipoSangue());
    }

}
