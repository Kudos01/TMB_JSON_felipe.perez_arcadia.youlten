package WebServices;

import java.util.ArrayList;

/**
 * Represents a route the user can plan and save.
 */

public class Route {

    private String origin;
    private String destination;
    private String date;
    private String time;
    private int maxWalkingDistance;
    private int timeTaken;
    private ArrayList<Section> routeSections;

    public Route(){};

    /**
     * Constructor for a Route. Takes all the different paramters and sets them to create a route.
     * @param sections          The different sections of the trip
     * @param date              The date of the trip
     * @param time              The time of the trip
     * @param maxWalkDistance   The max walking distance of the trip
     * @param origin            The origin of the trip
     * @param destination       The destination of the trip
     * @param timeTaken         The total time to complete the trip
     */

    public Route (ArrayList<Section> sections, String date, String time, int maxWalkDistance, String origin, String destination, int timeTaken){
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.maxWalkingDistance = maxWalkDistance;
        this.routeSections = sections;
        this.timeTaken = timeTaken;
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

    public ArrayList<Section> getRouteSections() {
        return routeSections;
    }
}
