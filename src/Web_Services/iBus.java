package Web_Services;

import com.google.gson.JsonObject;

public class iBus {

    private String route_id;
    private String line;
    private String text_ac;
    private int time_in_sec;
    private String destination;
    private int time_in_min;


    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getText_ac() {
        return text_ac;
    }

    public void setText_ac(String text_ac) {
        this.text_ac = text_ac;
    }

    public int getTime_in_sec() {
        return time_in_sec;
    }

    public void setTime_in_sec(int time_in_sec) {
        this.time_in_sec = time_in_sec;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTime_in_min() {
        return time_in_min;
    }

    public void setTime_in_min(int time_in_min) {
        this.time_in_min = time_in_min;
    }
}
