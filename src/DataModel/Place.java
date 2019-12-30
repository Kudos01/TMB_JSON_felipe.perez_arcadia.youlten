package DataModel;

public class Place extends Location {

    public Place(Generic g) {
        super(g);
    }

    public Place(String name, Double[] coordinates, String description) {
        super(name, coordinates, description);
    }
}
