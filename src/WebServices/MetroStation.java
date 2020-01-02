package WebServices;

import com.google.gson.JsonObject;

/**
 * Represents a metro station, with the different fields that are needed to identify it and its information
 */

public class MetroStation {

    private double[] coordinates = new double[2];
    private int station_id;
    private int station_code;
    private String station_name;
    private String line_name;
    private String date_inaugurated;

    /**
     * Constructor for the metro station. Takes the JSONObject from the API response and extracts the different fields
     * that are necessary to make a new metro station object.
     * @param metroStation  The API JSONObject representing a metro station
     */

    MetroStation(JsonObject metroStation){
        this.coordinates[0] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();
        this.station_id = metroStation.get("properties").getAsJsonObject().get("ID_ESTACIO").getAsInt();
        this.station_code = metroStation.get("properties").getAsJsonObject().get("CODI_ESTACIO").getAsInt();
        this.station_name = metroStation.get("properties").getAsJsonObject().get("NOM_ESTACIO").getAsString();
        this.line_name = metroStation.get("properties").getAsJsonObject().get("NOM_LINIA").getAsString();
        this.date_inaugurated = metroStation.get("properties").getAsJsonObject().get("DATA_INAUGURACIO").getAsString();
    }

    public int getStation_id() {
        return station_id;
    }

    public int getStationCode() {
        return station_code;
    }

    public String getStationName() {
        return station_name;
    }

    public String getLineName() {
        return line_name;
    }

    public String getDateInaugurated() {
        return date_inaugurated;
    }

    public double[] getCoordinates() {
        return coordinates;
    }
}
