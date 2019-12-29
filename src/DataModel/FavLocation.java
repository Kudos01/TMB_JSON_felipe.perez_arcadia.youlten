package DataModel;

import java.sql.Date;

public class FavLocation extends Location{

    private String type;
    private Date date;

    public String getType() { return type; }

    public Date getDate() { return date; }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
