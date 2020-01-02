package DataModel;

import com.google.gson.JsonArray;

/**
 * Represents a generic, unfiltered location recieved from the provided localizations file
 * Notice that it contains all the possible fields a location could have, all mixed in here.
 */

public class Generic {

    private String name;
    private double[] coordinates;
    private String description;
    private Integer stars;
    private JsonArray characteristics;
    private String architect;
    private Integer inauguration;

    public String getArchitect() { return architect; }

    public Integer getInauguration() { return inauguration; }

    public JsonArray getCharacteristics() { return characteristics; }

    public Integer getStars() {
        return stars;
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
