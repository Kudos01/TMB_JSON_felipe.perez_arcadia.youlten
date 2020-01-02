package DataModel;

/**
 * Represents a Location. Locations can have more attributes, but these are the base ones.
 */

public abstract class Location {

    private String name;
    private double[] coordinates;
    private String description;

    /**
     * Constructor to build a Location based on a Generic object.
     *
     * @param g object containing all possible location fields
     */

    public Location(Generic g){
        this.name = g.getName();
        this.coordinates = g.getCoordinates();
        this.description = g.getDescription();
    }

    /**
     * Constructor to build a location based on input parameters
     *
     * @param name          The name of the location
     * @param coordinates   The coordinates of the location
     * @param description   The description of the location
     */

    public Location(String name, double[] coordinates, String description){
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public String getDescription() {
        return description;
    }
}
