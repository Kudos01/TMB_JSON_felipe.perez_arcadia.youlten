package WebServices;

import com.google.gson.JsonObject;

public class iBus {

    private int route_id;
    private String line;
    private String time_in_text;
    private int time_in_sec;
    private String destination;
    private int time_in_min;

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

    public String getText_ac() {
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
