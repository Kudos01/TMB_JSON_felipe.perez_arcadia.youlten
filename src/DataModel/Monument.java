package DataModel;

public class Monument extends Location{

    private String architect;
    private int inauguration;

    /**
     * Constructor for building a Monument object using a generic location
     * (The architect and the inauguration date are set using the setters)
     *
     * @param g
     */

    public Monument(Generic g) {
        super(g);
    }

    public String getArchitect() { return architect; }

    public Integer getInauguration() { return inauguration; }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public void setInauguration(Integer inauguration) {
        this.inauguration = inauguration;
    }
}
