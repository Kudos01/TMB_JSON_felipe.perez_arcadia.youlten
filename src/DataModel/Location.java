package DataModel;

public abstract class Location {

    private String name;
    private Double[] coordinates;
    private String description;

    public Location(Generic g){
        this.name = g.getName();
        this.coordinates = g.getCoordinates();
        this.description = g.getDescription();
    }

    public Location(String name, Double[] coordinates, String description){
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates[0] = coordinates[0];
        this.coordinates[1] = coordinates[1];
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
