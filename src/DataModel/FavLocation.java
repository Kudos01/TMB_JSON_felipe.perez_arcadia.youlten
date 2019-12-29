package DataModel;

import java.util.Date;

public class FavLocation{

    private String type;
    private Date date;
    private Location location;

    public String getType() { return type; }

    public Date getDate() { return date; }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
