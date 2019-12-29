package Web_Services;

import com.google.gson.JsonObject;

public class MetroStation {

    private Double[] coordinates = new Double[2];
    private int line_code;
    private int station_code;
    private String line_name;


    public MetroStation(JsonObject metroStation){
        this.coordinates[0] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();

    }

    public Double[] getCoordinates() {
        return coordinates;
    }

}
