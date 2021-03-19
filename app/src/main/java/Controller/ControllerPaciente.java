package Controller;

import DAO.DAOPaciente;
import DAO.Response;
import Model.Paciente;

public class ControllerPaciente {

    private static DAOPaciente dao = new DAOPaciente();

    public static void create(Paciente paciente){
        dao.create(paciente);
    }

    public static void readAll(Response.Result<Paciente> result){
        dao.readAll(result);
    }

    public static void readId(String id, Response.Result<Paciente> result){
        dao.readId(id, result);
    }

    public static void delete(String id){
        dao.delete(id);
    }

    public static void update(Paciente paciente){
        dao.update(paciente);
    }
}
