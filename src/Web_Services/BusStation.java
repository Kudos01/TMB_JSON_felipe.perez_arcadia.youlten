package Web_Services;

import com.google.gson.JsonObject;

public class BusStation {

    private Double[] coordinates = new Double[2];
    private int stop_id;
    private int stop_code;
    private String stop_name;
    private String stop_description;
    private String address;
    private String date;
    private String street_name;

    public BusStation(JsonObject busStation){
        this.coordinates[0] = busStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = busStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();
        this.stop_id = busStation.get("properties").getAsJsonObject().get("ID_PARADA").getAsInt();
        this.stop_code = busStation.get("properties").getAsJsonObject().get("CODI_PARADA").getAsInt();
        this.stop_name = busStation.get("properties").getAsJsonObject().get("NOM_PARADA").getAsString();
        this.stop_description = busStation.get("properties").getAsJsonObject().get("DESC_PARADA").getAsString();
        this.address = busStation.get("properties").getAsJsonObject().get("ADRECA").getAsString();
        this.date = busStation.get("properties").getAsJsonObject().get("DATA").getAsString();
        this.street_name = busStation.get("properties").getAsJsonObject().get("NOM_VIA").getAsString();
    }


    public Double[] getCoordinates() {
        return coordinates;
    }

    public int getStopCode() {
        return stop_id;
    }

    public String getStopName() {
        return stop_name;
    }

    public String getStopDescription() {
        return stop_description;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getStreetName() {
        return street_name;
    }
}
