package DataModel;

import java.util.ArrayList;

public class Route {

    String origin;
    String destination;
    String date;
    String time;
    int maxWalkingDistance;
    int timeTaken;

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    ArrayList<String> routeInformation = new ArrayList<>();

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMaxWalkingDistance() {
        return maxWalkingDistance;
    }

    public void setMaxWalkingDistance(int maxWalkingDistance) {
        this.maxWalkingDistance = maxWalkingDistance;
    }

    public ArrayList<String> getRouteInformation() {
        return routeInformation;
    }

    public void setRouteInformation(ArrayList<String> routeInformation) {
        this.routeInformation = routeInformation;
    }
}
