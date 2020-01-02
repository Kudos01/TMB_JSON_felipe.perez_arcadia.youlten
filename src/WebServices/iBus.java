package WebServices;

import com.google.gson.JsonObject;

/**
 * Represents the a bus line that circulates through a particular stop, including how long it will take to reach that stop.
 * Notice that the stop is not a field in the class,
 * because when we request this type of data from the API, we must specify the stop code there.
 */

public class iBus {

    private int route_id;
    private String line;
    private String time_in_text;
    private int time_in_sec;
    private String destination;
    private int time_in_min;

    /**
     * Constructor method to build the iBus object using the API call response.
     * The most important information we save are the line name and the time for a bus to reach this stop.
     *
     * @param ibus The API JSONObject with the iBus response data.
     */

    public iBus(JsonObject ibus){
        this.route_id = ibus.get("routeId").getAsInt();
        this.line = ibus.get("line").getAsString();
        this.time_in_text = ibus.get("text-ca").getAsString();
        this.time_in_sec = ibus.get("t-in-s").getAsInt();
        this.destination = ibus.get("destination").getAsString();
        this.time_in_min = ibus.get("t-in-min").getAsInt();
    }

    public int getRoute_id() {
        return route_id;
    }

    public String getLine() {
        return line;
    }

    public String getTime_in_text() {
        return time_in_text;
    }

    public int getTime_in_sec() {
        return time_in_sec;
    }

    public String getDestination() {
        return destination;
    }

    public int getTime_in_min() {
        return time_in_min;
    }

}
