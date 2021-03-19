package Model;

public class Paciente extends Model {

    private String nome;
    private String idade;
    private String tipoSangue;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
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
        return "nome : " + nome + '\n' +
                "idade : " + idade + '\n' +
                "tipoSangue : " + tipoSangue + '\n';
    }
}
