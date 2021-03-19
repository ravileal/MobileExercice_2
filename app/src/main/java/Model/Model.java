package Model;

public abstract class Model {
    protected String id = null;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
}
