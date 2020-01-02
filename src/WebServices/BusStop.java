package WebServices;

import com.google.gson.JsonObject;

/**
 * Represents a bus stop, with the different fields that are needed to identify it and its information
 */

public class BusStop {

    private double[] coordinates = new double[2];
    private int stop_code;
    private String stop_name;
    private String stop_description;

    /**
     * Constructor method to build a bus stop. Takes the JSONObject from the API response and extracts the different fields
     * that are necessary to make a bus stop station object.
     *
     * @param busStation    The API JSONObject representing a bus stop
     */

    BusStop(JsonObject busStation){
        this.coordinates[0] = busStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = busStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();
        this.stop_code = busStation.get("properties").getAsJsonObject().get("CODI_PARADA").getAsInt();
        this.stop_name = busStation.get("properties").getAsJsonObject().get("NOM_PARADA").getAsString();
        this.stop_description = busStation.get("properties").getAsJsonObject().get("DESC_PARADA").getAsString();
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public int getStopCode() { return stop_code; }

    public String getStopName() {
        return stop_name;
    }

    public String getStopDescription() {
        return stop_description;
    }

}
