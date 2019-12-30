package DataModel;

import java.util.Date;

public class FavLocation{

    private String type;
    private Date date;
    private Location location;

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
