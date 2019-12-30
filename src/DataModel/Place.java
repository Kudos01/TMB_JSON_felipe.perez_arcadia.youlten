package DataModel;

public class Place extends Location {

    public Place(Generic g) {
        super(g);
    }

    public Place(String name, double[] coordinates, String description) {
        super(name, coordinates, description);
    }
}
