package DataModel;

import java.sql.Date;
import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private int birthday;

    public ArrayList<Location> userLocations = new ArrayList<>();
    public ArrayList<FavLocation> favoriteLocation = new ArrayList<>();

    public User(String username, String email, int birthday){
        this.username = username;
        this.email = email;
        this.birthday = birthday;
    }

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
