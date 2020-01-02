package DataModel;

import com.google.gson.JsonArray;

/**
 * Class representing a Restaurant. Contains the same fields as Location plus characteristics
 */

public class Restaurant extends Location {

    private JsonArray characteristics;

    /**
     * Constructor for building a Restaurant object using a generic location
     * (The characteristics are set using the setter)
     *
     * @param g
     */

    public Restaurant(Generic g) {
        super(g);
    }

    public JsonArray getCharacteristics() { return characteristics; }

    public void setCharacteristics(JsonArray characteristics) {
        this.characteristics = characteristics;
    }
}
