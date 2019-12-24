package DataModel;

public abstract class Location {

    private String name;
    private Double[] coords;
    private String description;

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
