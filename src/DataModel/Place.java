package DataModel;

/**
 * Class representing base location that can be instantiated. Contains the same fields as location
 */

public class Place extends Location {

    /**
     * Constructor method to build a new Place given a generic location
     *
     * @param g
     */

    public Place(Generic g) {
        super(g);
    }

    /**
     * Constructor method used to build a new Place given different parameters.
     *
     * @param name          The name of the location
     * @param coordinates   The coordinates of the location
     * @param description   The description of the location
     */

    public Place(String name, double[] coordinates, String description) {
        super(name, coordinates, description);
    }
}
