package WebServices;

import com.google.gson.JsonObject;

public class BusStation {

    private double[] coordinates = new double[2];
    private int stop_code;
    private String stop_name;
    private String stop_description;
    private String line_name;


    public BusStation(JsonObject busStation){
        this.coordinates[0] = busStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble();
        this.coordinates[1] = busStation.get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble();
        this.stop_code = busStation.get("properties").getAsJsonObject().get("CODI_PARADA").getAsInt();
        this.stop_name = busStation.get("properties").getAsJsonObject().get("NOM_PARADA").getAsString();
        this.stop_description = busStation.get("properties").getAsJsonObject().get("DESC_PARADA").getAsString();
        this.line_name = busStation.get("properties").getAsJsonObject().get("NOM_LINIA").getAsString();
    }

    public BusStation() {

    }


    public double[] getCoordinates() {
        return coordinates;
    }

    public int getStopCode() { return stop_code; }

    public String getStopName() {
        return stop_name;
    }

    public String getLine_name() {
        return line_name;
    }

    public String getStopDescription() {
        return stop_description;
    }

}
