package DataModel;

import WebServices.Route;

import java.util.ArrayList;

/**
 * Represents a user in our system. Contains not only the information of the user but also the locations they have searched,
 * the locations they have created, the locations they have set as favourite and the routes they have made.
 */

public class User {

    private String username;
    private String email;
    private int birthyear;

    public ArrayList<Location> userLocations = new ArrayList<>();
    public ArrayList<FavLocation> favoriteLocations = new ArrayList<>();
    public ArrayList<Route> pastRoutes = new ArrayList<>();

    //store all the searched locations from the user
    public ArrayList<Location> searchedLocations = new ArrayList<>();

    /**
     * Constructor method for creating a new user, assigning their different attributes,
     * except the location ArrayLists, these will be modyfied as the user creates, adds and sets locations and creates new routes
     *
     * @param username
     * @param email
     * @param birthyear
     */

    public User(String username, String email, int birthyear){
        this.username = username;
        this.email = email;
        this.birthyear = birthyear;
    }

    //getters
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public Integer getBirthyear() {return birthyear;}

}
