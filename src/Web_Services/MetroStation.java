package Web_Services;

import com.google.gson.JsonObject;

public class MetroStation {

    private Double[] coordinates = new Double[2];
    private int stop_code;
    private String stop_name;
    private String stop_description;
    private String address;
    private String date;
    private String street_name;

    public MetroStation(JsonObject metroStation){
        this.coordinates[0] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = metroStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();
        this.stop_code = metroStation.get("properties").getAsJsonObject().get("ID_PARADA").getAsInt();
        this.stop_name = metroStation.get("properties").getAsJsonObject().get("NOM_PARADA").getAsString();
        this.stop_description = metroStation.get("properties").getAsJsonObject().get("DESC_PARADA").getAsString();
        this.address = metroStation.get("properties").getAsJsonObject().get("ADRECA").getAsString();
        this.date = metroStation.get("properties").getAsJsonObject().get("DATA").getAsString();
        this.street_name = metroStation.get("properties").getAsJsonObject().get("NOM_VIA").getAsString();
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getStop_code() {
        return stop_code;
    }

    public void setStop_code(int stop_code) {
        this.stop_code = stop_code;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStop_description() {
        return stop_description;
    }

    public void setStop_description(String stop_description) {
        this.stop_description = stop_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }
}
