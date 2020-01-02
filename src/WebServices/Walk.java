package WebServices;

import com.google.gson.JsonObject;

/**
 * Represents a walk type of section (walking from point A to point B)
 */

public class Walk extends Section {

    /**
     * Constructor method for a walk section object. Since walk does not have any extra attributes that we need,
     * we can simply use the constructor defined in the super class
     * @param json_section API JSONObject response with the different section fields
     */

    public Walk(JsonObject json_section) {
        super(json_section);
    }
}
