package DataModel;

import java.sql.Date;

public class FavLocation {

    private String type;
    private Date date;

    public String getType() { return type; }

    public Date getDate() { return date; }

    public void setType(String type) {//f
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
