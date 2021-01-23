package com.example.mobileexercice_2;

public class Paciente {

    private int id;
    private String nome;
    private Integer idade;
    private String tipoSangue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTipoSangue() {
        return tipoSangue;
    }

    public void setTipoSangue(String tipoSangue) {
        this.tipoSangue = tipoSangue;
    }

    @Override
    public String toString() {
        return  "" +
                "ID : " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Idade: " + idade + "\n" +
                "Tipo de sangue: " + tipoSangue +
                "";
    }
}
