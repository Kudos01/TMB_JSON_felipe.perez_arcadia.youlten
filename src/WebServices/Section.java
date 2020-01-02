package WebServices;

import com.google.gson.JsonObject;

/**
 * Class representing the idea of a section. Sets the base of all the different sections.
 * Extended by other classes where they need a section as a base (Walk and Transit)
 */

public abstract class Section {

    private double start_time;
    private double end_time;
    private String mode;

    /**
     * Constructor method for setting the base parameters of a section
     *
     * @param json_section API JSONObject response with the different section fields
     */

    public Section(JsonObject json_section){
        this.start_time = json_section.get("startTime").getAsDouble();
        this.end_time = json_section.get("endTime").getAsDouble();
        this.mode = json_section.get("mode").getAsString();
    }

    public double getStart_time() {
        return start_time;
    }

    public double getEnd_time() {
        return end_time;
    }

    public String getMode() {
        return mode;
    }

}
