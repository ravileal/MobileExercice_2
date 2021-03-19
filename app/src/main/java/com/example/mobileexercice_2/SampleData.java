package com.example.mobileexercice_2;

import java.util.ArrayList;
import java.util.List;

import Model.Paciente;

public abstract class SampleData {

    public static List<Paciente> getSampleJournal() {

        List<Paciente> pacienteEnrties = new ArrayList<>();

        Paciente pacienteEntry1 = new Paciente();
        pacienteEntry1.setNome("Jose");
        pacienteEntry1.setIdade("20");
        pacienteEntry1.setTipoSangue("A+");

        pacienteEnrties.add(pacienteEntry1);

        return pacienteEnrties;
    }

}
