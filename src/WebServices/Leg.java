package WebServices;

import com.google.gson.JsonObject;

public abstract class Leg {

    double start_time;
    double end_time;
    String mode;


    public Leg(JsonObject partial_leg){

        this.start_time = start_time;
        this.end_time = end_time;
        this.mode = mode;

    }


    public double getStart_time() {
        return start_time;
    }

    public double getEnd_time() {
        return end_time;
    }

    public String getMode() {
        return mode;
    }

}
