package DataModel;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private int birthyear;

    public ArrayList<Location> userLocations = new ArrayList<>();
    public ArrayList<FavLocation> favoriteLocations = new ArrayList<>();
    public ArrayList<Route> pastRoutes = new ArrayList<>();

    public User(String username, String email, int birthyear){
        this.username = username;
        this.email = email;
        this.birthyear = birthyear;
    }

    //getters

    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public Integer getBirthyear() {return birthyear;}

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(Integer birthday) {
        this.birthyear = birthday;
    }
}
