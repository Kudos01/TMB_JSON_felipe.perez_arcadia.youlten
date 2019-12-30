package WebServices;

import com.google.gson.JsonObject;

public class MetroStation {

    private Double[] coordinates = new Double[2];
    private int station_id;
    private int station_code;
    private String station_name;
    private String line_name;
    private String date_inaugurated;



    public MetroStation(JsonObject metroStation){
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

    public String getName() {
        return station_name;
    }

    public String getLineName() {
        return line_name;
    }

    public String getDateInaugurated() {
        return date_inaugurated;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }
}
