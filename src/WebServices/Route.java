package WebServices;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.util.ArrayList;

public class Route {

    String origin;
    String destination;
    String date;
    String time;
    int maxWalkingDistance;
    int timeTaken;
    ArrayList<Leg> routeLegs;

    public Route (JsonObject fastestRoute, ArrayList<Leg> legs,String date, String time, int maxWalkDistance, String origin, String destination){
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.timeTaken = timeTaken;
        this.maxWalkingDistance = maxWalkDistance;
        this.routeLegs = legs;
    }

    public int getTimeTaken() {
        return timeTaken;
    }


    public String getOrigin() {
        return origin;
    }



    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    public int getMaxWalkingDistance() {
        return maxWalkingDistance;
    }

    public void setMaxWalkingDistance(int maxWalkingDistance) {
        this.maxWalkingDistance = maxWalkingDistance;
    }

    public ArrayList<Leg> getRouteLegs() {
        return routeLegs;
    }
}
