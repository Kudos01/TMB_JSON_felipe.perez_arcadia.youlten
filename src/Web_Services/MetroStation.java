package Web_Services;

import com.google.gson.JsonObject;

public class MetroStation {

    private Double[] coordinates = new Double[2];
    private int stop_code_group;
    private String station_name;
    private String date;


    public MetroStation(JsonObject metroStation){
        this.coordinates[0] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();
        this.station_name = metroStation.get("properties").getAsJsonObject().get("NOM_ESTACIO").getAsString();
        this.date = metroStation.get("properties").getAsJsonObject().get("DATA").getAsString();
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public int getStop_code() {
        return stop_code_group;
    }

    public String getStop_name() {
        return station_name;
    }

    public String getDate() {
        return date;
    }

}
