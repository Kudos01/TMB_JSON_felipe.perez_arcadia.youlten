package DataModel;

import java.util.Date;

/**
 * Represents a user's favourite location
 */

public class FavLocation{

    private String type;
    private Date date;
    private Location location;

    /**
     * Constructor method to generate a new favourite location with the specified parameters.
     *
     * @param location  The location to be set as favourite
     * @param date      The date it was set as favourite
     * @param type      The type of the location
     */

    public FavLocation(Location location, Date date, String type) {
        this.location = location;
        this.date = date;
        this.type = type;
    }

    public String getType() { return type; }

    public Date getDate() { return date; }

    public Location getLocation(){
        return location;
    }
}
