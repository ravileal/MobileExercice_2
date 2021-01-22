package com.example.mobileexercice_2;

import java.util.ArrayList;

public class RepositoryPaciente {

    private static ArrayList<Paciente> list_paciente = new ArrayList<>();

    public static ArrayList<Paciente> getList_paciente() {
        return list_paciente;
    }

    public static void setList_paciente(ArrayList<Paciente> list_paciente) {
        RepositoryPaciente.list_paciente = list_paciente;
    }

    public static void addPaciente(Paciente paciene) {
        RepositoryPaciente.list_paciente.add(paciene);
    }

    public static void setPaciente(Paciente paciene){
        RepositoryPaciente.list_paciente.set(paciene.getId(), paciene);
    }

    public static Paciente getPaciente(int position){
        return RepositoryPaciente.list_paciente.get(position);
    }

    public static int getSize(){
        return RepositoryPaciente.list_paciente.size();
    }

}
