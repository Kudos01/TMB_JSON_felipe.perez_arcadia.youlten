package DataModel;

import com.google.gson.JsonArray;

public class Restaurant extends Location {

    private JsonArray characteristics;

    public Restaurant(Generic g) {
        super(g);
    }

    public JsonArray getCharacteristics() { return characteristics; }

    public void setCharacteristics(JsonArray characteristics) {
        this.characteristics = characteristics;
    }
}
