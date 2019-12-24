package DataModel;

public class temp {

    private String name;
    private Double[] coords;
    private String description;
    private String architect;
    private Integer inauguration;
    private Integer stars;

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getArchitect() { return architect; }

    public Integer getInauguration() { return inauguration; }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public void setInauguration(Integer inauguration) {
        this.inauguration = inauguration;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double[] getCoords() {
        return coords;
    }

    public void setCoords(Double[] coords) {
        this.coords = coords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
