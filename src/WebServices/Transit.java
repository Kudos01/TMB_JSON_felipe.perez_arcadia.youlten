package WebServices;

import com.google.gson.JsonObject;

/**
 * Represents a Transit type of section (going by bus, metro, rail, etc.)
 */

public class Transit extends Section {

    private String line_name;
    private int from_stopcode;
    private int to_stopcode;
    private String from_name;
    private String to_name;

    /**
     * Constructor method for a section of type Transit. First sets the base fields using the super class constructor,
     * and then adds the fields unique to a transit type of section
     *
     * @param json_section API JSONObject response with the different section fields
     */

    public Transit(JsonObject json_section){
        super(json_section);

        this.line_name= json_section.get("route").getAsString();
        this.from_name=json_section.get("from").getAsJsonObject().get("name").getAsString();
        this.from_stopcode=json_section.get("from").getAsJsonObject().get("stopCode").getAsInt();
        this.to_name = json_section.get("to").getAsJsonObject().get("name").getAsString() ;
        this.to_stopcode=json_section.get("to").getAsJsonObject().get("stopCode").getAsInt();

    }

    public String getLine_name() {
        return line_name;
    }

    public int getFrom_stopcode() {
        return from_stopcode;
    }

    public int getTo_stopcode() {
        return to_stopcode;
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getTo_name() {
        return to_name;
    }
}
