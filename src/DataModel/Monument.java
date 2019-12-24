package DataModel;

public class Monument extends Location{

    private String architect;
    private Integer inauguration;

    public String getArchitect() { return architect; }

    public Integer getInauguration() { return inauguration; }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public void setInauguration(Integer inauguration) {
        this.inauguration = inauguration;
    }
}
