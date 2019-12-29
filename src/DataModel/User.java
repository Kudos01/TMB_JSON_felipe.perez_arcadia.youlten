package DataModel;

import java.sql.Date;
import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private Integer birthday;

    public static ArrayList<Location> userLocations = new ArrayList<>();
    public static ArrayList<FavLocation> favoriteLocation = new ArrayList<>();


    //getters

    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public Integer getBirthday() {return birthday;}

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }
}
