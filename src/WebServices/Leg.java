package WebServices;

import com.google.gson.JsonObject;

public abstract class Leg {

    double start_time;
    double end_time;
    String mode;


    public Leg(JsonObject partial_leg){

        this.start_time = partial_leg.get("startTime").getAsDouble();
        this.end_time = partial_leg.get("endTime").getAsDouble();
        this.mode =partial_leg.get("mode").getAsString();

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
