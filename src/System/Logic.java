package System;

import DataModel.*;
import Exceptions.stopCodeInvalidException;
import Exceptions.locationNotFoundException;
import Exceptions.stationNotFoundByYearException;
import Parsing.JSONParser;
import WebServices.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Represents our system. Holds all of the information regarding the user, the locations, stops and stations.
 */

public class Logic {

    //constants for the estimated long and lat values.
    private static final double minlat = 2.046171;
    private static final double maxlat =  2.244097;
    private static final double minlong = 41.287565;
    private static final double maxlong = 41.5;

    //create a new user -> singular user for all program
    private User user;
    //create a new api -> handles api requests and other functions
    private API api = new API();
    //create new json parser -> handles json parsing
    private JSONParser parser = new JSONParser();
    //create new scanner -> allows for user inputs
    private final Scanner scanner = new Scanner(System.in);

    //store all locations in the json file
    public ArrayList<Location> allLocations;

    //store all busStations from API - used to check information related to buses
    public ArrayList<BusStop> busStops = new ArrayList<>();
    //store all metro Stations from API - used to check information related to metro
    public ArrayList<MetroStation> metroStations = new ArrayList<>();

    /**
     * Method to load the location data from the JSON file provided and load the bus stops and metro stations from the API
     * @return  Indicates if the data has been loaded correctly. Returns true if the data has been loaded correctly
     *          and false if there was an error loading the data.
     */
    public boolean loadData(){
        //Loading locations from the JSON file
        allLocations = parser.parseLocations();

        //Loading all of the bus lines from the API
        busStops = api.loadBusStops();

        //loading all of the metro lines from the API
        metroStations = api.loadMetroStations();

        return !(busStops == null || metroStations == null || allLocations == null);
    }

    /**
     * Method for asking the user for their information (username, email and birthyear) and saving it.
     */

    public void Intro(){
        String username;
        String email;
        int birthyear;

        System.out.println("");
        //display all printed information from the menu
        System.out.println(Menu.intro);
        //ask user for the first piece of information we need
        System.out.println(Menu.userinfo1);
        username = scanner.nextLine();

        System.out.println(Menu.userinfo2);
        email = scanner.nextLine();

        System.out.println(Menu.userinfo3);
        birthyear = scanner.nextInt();
        scanner.nextLine();

        //print out a message saying the data has been saved correclty
        System.out.println(Menu.flagvalid);
        System.out.println("");

        //create a new user with the given information
        user = new User(username, email, birthyear);
    }

    /**
     * Method for listing the location a user has created. If no locations have been created, displays a message saying that in
     * order to list the locations, they need to be created first. Will also ask the user if they want to create a new location.
     * If they respond yes, we call the userCreateLocationMethod. Otherwise, exit this option.
     */

    public void listLocations(){

        //create an arrayist of locations based off of the locations the user has already created
        ArrayList<Location> userLocations = new ArrayList<>(user.userLocations);
        String yesorno;

        //if the user has created some locations
        if(!userLocations.isEmpty()){
            for (int i = 0; i < userLocations.size(); i++) {
                //Print out all of the user's locations
                System.out.println(userLocations.get(i).getName());
            }
        }
        //if the user has not created locations
        if(userLocations.isEmpty()){
            System.out.println("You don't have any locations created");
            System.out.println("");
        }

        System.out.println("Want to create a new location? (yes/no)");

        yesorno = scanner.nextLine();

        //if the user does not enter yes or no, repeat until they enter a valid location
        while(!checkYesOrNo(yesorno)){
            //ask if they want to create a new location
            System.out.println("Want to create a new location? (yes/no)");
            yesorno = scanner.nextLine();
        }

        //if the user enters yes
        if(yesorno.equalsIgnoreCase("yes")){
            //create a new location
            userCreateLocation();
        }
    }

    /**
     * Method that asks the user for different parameters (Location name, Longitude, Latitude and description) to create a new location.
     * Object creation handled by the {@link #createNewLocation(String, double[], String)} method.
     */

    private void userCreateLocation(){

        String name;
        double[] coordinates = new double[2];
        String description;

        //ask the user for the location name
        System.out.println("Location Name:");
        name = scanner.nextLine();


        //check if the name does not match that of an already existing location
        while(validLocationName(name, allLocations) ){
            //if so, print out an error
            System.out.println("");
            System.out.println("Error: This location already exists.");
            System.out.println("");

            //and ask again
            System.out.println("Location Name:");
            name = scanner.nextLine();
        }

        System.out.println("");
        //ask user for longitude
        System.out.println("Longitude: ");
        //check if the coordinates are valid
        boolean b = askForLongitude(coordinates);

        //if not, keep asking until they are
        while(!b){
            b = askForLongitude(coordinates);
        }

        System.out.println("");
        //ask the user for the latitude
        System.out.println("Latitude: ");
        //check if coordinates are valid
        b = askForLatitude(coordinates);

        //if not, keep asking until they are
        while(!b){
            b = askForLatitude(coordinates);
        }

        System.out.println("");
        //ask user for description
        System.out.println("Description: ");
        description = scanner.nextLine();

        //create a new location with the user input information
        Location newLoc = createNewLocation(name, coordinates, description);

        //add the location to the arraylist of userlocations
        user.userLocations.add(newLoc);
        //add the location to the list of all locations
        allLocations.add(newLoc);

        System.out.println("");
        //tell the user the information has been successfully registered
        System.out.println("This information has been successfully registered!");
        System.out.println("");
        //print out all of the user implemented locations
        for (int i = 0; i < user.userLocations.size(); i++) {
            System.out.println("-" + user.userLocations.get(i).getName());
        }
    }

    /**
     * Method to generate a new location object based on the input parameters. Uses the Constructor from class PLace
     *
     * @param name          name of the location
     * @param coordinates   coordinates of the location
     * @param description   description of the location
     *
     * @return {@code Place(name, coordinates, description)} Place object constructed using the input parameters
     */

    private Location createNewLocation(String name, double[] coordinates, String description){
        //create a new generic location with information the user input
        return new Place(name, coordinates, description);
    }

    /**
     * Method to check if a given location exists in the provided ArrayList of locations
     *
     * @param name          name of the location to be searched
     * @param allLocations  Location ArrayList that contains all of the locations to be compared with
     * @return              {@code tof} returns the result (true if location found, false if not)
     */

    private boolean validLocationName(String name, ArrayList<Location> allLocations){

        boolean tof = false;

        //for all of the locations
        for (int i = 0; i < allLocations.size(); i++) {
            //check if the name is the same for any locations already saved
            if(name.equalsIgnoreCase(allLocations.get(i).getName())){
                tof = true;
            }
        }
        return tof;
    }

    /**
     * Method to ask the user for the Longitude of a Location. Checks if the input is a double.
     * If input is wrong, prints message and returns false.
     *
     * @param coordinates   coordinates of a location.
     * @return              result of checking the input. True if input is a double, false otherwise.
     */

    private boolean askForLongitude(double[] coordinates){
        if(scanner.hasNextDouble()){
            coordinates[0] = scanner.nextDouble();
            scanner.nextLine();

            if(!checkIfLongitudeCorrect(coordinates[0])){
                System.out.println("");
                System.out.println("Error! The longitude is not valid (Outside range of allowed values)");
                System.out.println("");
                System.out.println("Longitude: ");
                return false;
            }else {
                return true;
            }
        }

        else{
            scanner.nextLine();
            System.out.println("");
            System.out.println("Error! The longitude is not valid");
            System.out.println("");
            System.out.println("Longitude: ");
            return false;
        }
    }

    /**
     * Method to check if the Longitude is within the range of values {@code minlong} {@code maxlong}
     *
     * @param longitude longitude of a location
     * @return          true if longitude is within the range of values. false otherwise
     */

    private boolean checkIfLongitudeCorrect(double longitude){
        return longitude >= minlong && longitude <= maxlong;
    }

    /**
     * Method to ask the user for the latitude of a Location. Checks if the input is a double.
     * If input is wrong, prints message and returns false.
     *
     * @param coordinates   coordinates of a location.
     * @return              result of checking the input. True if input is a double, false otherwise.
     */

    private boolean askForLatitude(double[] coordinates){
        if(scanner.hasNextDouble()) {
            coordinates[1] = scanner.nextDouble();
            scanner.nextLine();

            if (!checkIfLatitudeCorrect(coordinates[1])) {
                System.out.println("");
                System.out.println("Error! The latitude is not valid (Outside range of allowed values)");
                System.out.println("");
                System.out.println("Latitude: ");
                return false;
            } else {
                return true;
            }
        }
        else{
            scanner.nextLine();
            System.out.println("");
            System.out.println("Error! The latitude is not valid");
            System.out.println("");
            System.out.println("Latitude: ");
            return false;
        }
    }

    /**
     * Method to check if the latitude is within the range of values {@code minlat} {@code maxlat}
     *
     * @param latitude latitude of a location
     * @return         true if latitude is within the range of values. false otherwise
     */

    private boolean checkIfLatitudeCorrect(double latitude){
        return latitude >= minlat && latitude <= maxlat;
    }

    /**
     * Method to search for a specific Location. Asks the user for the name of the Location to be searched inside the method.
     */

    public void searchLocation(){

        String name;
        int pos = -1;

        System.out.println("");
        System.out.println("Enter the name of a location");

        name = scanner.nextLine();

        try {
            //get the position of the location that exists
            pos = checkLocationExist(name, allLocations);

            boolean alreadySearched = false;

            //for all of the locations that the user searched, we check if this location has already been searched
            //if this is the case, we remove it from the searched locations, and re-add it as the most recent one
            for (int i = 0; i < user.searchedLocations.size(); i++) {
                if(user.searchedLocations.get(i) == allLocations.get(pos)){
                    alreadySearched = true;
                    user.searchedLocations.remove(i);
                    user.searchedLocations.add(allLocations.get(pos));
                }
            }

            //if the user has not already searched this location
            if(!alreadySearched){
                //add the location to the list of searched locations
                user.searchedLocations.add(allLocations.get(pos));
            }

            //show the coordinates of the searched location
            System.out.println("Coordinates:"+""+ Arrays.toString(allLocations.get(pos).getCoordinates()));
            //show the description of the searched location
            System.out.println("Description:");
            System.out.println(allLocations.get(pos).getDescription());

            //if the searched location is a restaurant
            if(allLocations.get(pos) instanceof Restaurant){

                //also display the characteristics
                for (int i = 0; i < ((Restaurant) allLocations.get(pos)).getCharacteristics().size(); i++) {
                    System.out.println("Characteristics: ");
                    System.out.print(((Restaurant) allLocations.get(pos)).getCharacteristics());
                }
            }

            //if the searched location is a hotel
            else if(allLocations.get(pos) instanceof Hotel){

                //display the number of stars from the hotel
                System.out.println("Stars: " + ((Hotel) allLocations.get(pos)).getStars());

            }
            //if the searched location is a monument
            else if(allLocations.get(pos) instanceof Monument){

                //print out the architect
                System.out.println("Architect: " + ((Monument) allLocations.get(pos)).getArchitect());
                //print out the inaugruation
                System.out.println("Inauguration: " + ((Monument) allLocations.get(pos)).getInauguration());

            }

            System.out.println("");
            //ask the user if they want to set the location as their favorite
            System.out.println("Do you want to set the found location as your favorite? (yes/no)");
            String yesorno = scanner.nextLine();

            //check if the user input a yes or a no
            while(!checkYesOrNo(yesorno)){
                //if not, ask again until it is a yes or a no
                System.out.println("Do you want to set the found location as your favorite? (yes/no)");
                yesorno = scanner.nextLine();
            }

            //if yes, set the location as a favorite for the user
            if(yesorno.equalsIgnoreCase("yes")){
                userSetFaveLocation(allLocations.get(pos));
            }


        } catch (locationNotFoundException e) {
            e.printErrorMessage();
        }
    }

    /**
     * Method to check if a location exists given its name and an ArrayList of Locations containing all of them.
     *
     * @param name      the name of the location to be searched for
     * @param locations the locations to be searched from
     * @return          returns the location of the found location in the ArrayList. If it is not found, this value becomes -1.
     * @throws locationNotFoundException Thrown if the location is not found
     */

    private int checkLocationExist(String name, ArrayList<Location> locations) throws locationNotFoundException{
        int pos = -1;

        //for all of the locations
        for (int i = 0; i < locations.size(); i++) {

            //if the name matches the name of a location in the location list
            if(name.equalsIgnoreCase(locations.get(i).getName())){
                //get the position of the named location in the location array
                pos = i;
            }
        }

        //if the name is not in the system
        if (pos == -1){
            //throw exception
            throw new locationNotFoundException();
        }
        //return the position
        return pos;
    }

    /**
     * Method to set a location as favourite. Asks the user what type of location they want it to be set as and gets the current date.
     * Adds it to the favouriteLocations ArrayList.
     *
     * @param location Location to be set as favourite
     */

    private void userSetFaveLocation(Location location){

        System.out.println("");
        //ask the user what type of location they want to set it as
        System.out.println("Type (home / work / studies / leisure / culture):");
        String type = scanner.nextLine();

        //if they do not set it to one of the options, keep asking until they do
        while(!(type.equalsIgnoreCase("home") || type.equalsIgnoreCase("work") || type.equalsIgnoreCase("studies") || type.equalsIgnoreCase("leisure") ||type.equalsIgnoreCase("culture")  )){
            System.out.println("");
            System.out.println("Error! You have to enter \"home\", \"work\", \"studies\", \"leisure\" or \"culture\".");
            System.out.println("Type (home / work / studies / leisure / culture):");
            type = scanner.nextLine();
        }

        //get the date of when they set it as a favorite location
        Date date = new Date();

        //create a favorite location object with the user input information
        FavLocation temp = new FavLocation(location, date, type);

        //add the location to the arraylist of user's fav locations
        user.favoriteLocations.add(temp);

        System.out.println("");
        System.out.println(location.getName() + " has been added as a new favorite location");
    }

    /**
     * Method to print out all of the locations the user has searched for before.
     * Prints a message if there have been no locations searched for yet
     */

    private void locationHistory() {

        //if the user has searched previous locations
        if (!user.searchedLocations.isEmpty()) {

            System.out.println("Searched Locations:");

            //display all of the names of the locations searched by the user
            for (int i = user.searchedLocations.size() -1; i >= 0; i--) {
                System.out.println("");
                System.out.println("\t" + "-" + user.searchedLocations.get(i).getName());
            }
        }
        else{
            //show the error message
            System.out.println("");
            System.out.println("You have not searched for any locations!");
            System.out.println("To search for one, access option 2 in the principal menu.");
        }
    }

    /**
     * Method to find the stations given an inauguration year. If no station is found,
     * a stationNotFoundByYearException will be thrown and an error message printed.
     *
     * @param year  year of inauguration to compare with
     * @return      the locations that have been found. I none have been found, it will be empty.
     * @throws stationNotFoundByYearException
     */

    private ArrayList<MetroStation> findStationsByYear(int year) throws stationNotFoundByYearException {
        //create an arraylist of stations inaugurated in that year
        ArrayList<MetroStation> birthyear_stations = new ArrayList<>();

        //convert the user's birth year into a string (this is done as inaguration date is a string when taken from the api
        String yearStr = Integer.toString(year);

        //for all of the metro stations
        for (int i = 0; i < metroStations.size() ; i++) {
            //the date is given as YYYY-MM-DD, so we only compare the first four characters
            if(metroStations.get(i).getDateInaugurated().charAt(0) == yearStr.charAt(0) && metroStations.get(i).getDateInaugurated().charAt(1) == yearStr.charAt(1)
                && metroStations.get(i).getDateInaugurated().charAt(2) == yearStr.charAt(2) && metroStations.get(i).getDateInaugurated().charAt(3) == yearStr.charAt(3)){
                birthyear_stations.add(metroStations.get(i));
            }
        }

        //if there are no stations inaugurated on that year
        if(birthyear_stations.isEmpty()){
            //throw an exception
            throw new stationNotFoundByYearException();
        }
        //return the arraylist
        return birthyear_stations;
    }

    /**
     * Method to print out the stations inaugurated in the user's birth year. If there are none, will print a message stating so
     */

    public void stationsInauguratedByBirthYear(){

        ArrayList<MetroStation> birthyear_stations = new ArrayList<>();
        try{
            //find the birth stations for the user
            birthyear_stations = findStationsByYear(user.getBirthyear());

            //print out all of the information in the arraylist
            for (int i = 0; i < birthyear_stations.size(); i++) {
                StringBuilder sb = new StringBuilder(birthyear_stations.get(i).getStationName());
                sb.append(" ");
                sb.append(birthyear_stations.get(i).getLineName());
                System.out.println(sb);
            }

        } catch (stationNotFoundByYearException e) {
            //if exception thrown, print message
            e.printErrorMessage();
        }
    }

    /**
     * Asks the user for a stop code, checks if the input is an integer (asks the user for the input until it is),
     * checks if it exists, and prints out the corresponding information.
     * (if the input correct, if the station existed, if and what buses go through that stop and in how much time)
     */

    public void getBusWaitTime(){
        int stopCode=0;
        boolean exists = false;

        //run the loop while the return from the checkStopCodeExists method is false
        while(!exists){
            System.out.println("");
            //ask the user for the stop code they want to check
            System.out.println("Enter the stop code:");

            boolean ok = false;

            while(!ok){
                if(scanner.hasNextInt()){
                    stopCode = scanner.nextInt();
                    scanner.nextLine();
                    ok = true;
                }else{
                    scanner.nextLine();
                    System.out.println("Error, please enter an integer");
                }
            }

            try {
                //check if the stop code correlates to another station
                exists = checkStopCodeIfExists(stopCode, busStops);
            } catch (stopCodeInvalidException e) {
                //if not, print an error message
                e.printErrorMessage();
            }finally {
                if(exists){
                    //if the stop code exists
                    //make api request with stopcode to find buses that are nearby
                    ArrayList<iBus> closeBus = new ArrayList<>(api.getBusWaitTime(stopCode));

                    if(closeBus.size() == 0){
                        System.out.println("There are no busses circulating through this stop at this moment");
                    }else {

                        //check if the stop is a fave stop
                        checkIfStopIsFavorite(stopCode, busStops);

                        //for all close buses
                        for (int i = 0; i < closeBus.size(); i++) {
                            StringBuilder sb = new StringBuilder();
                            //get the line name
                            sb.append(closeBus.get(i).getLine());
                            sb.append(" - ");
                            //get the destination
                            sb.append(closeBus.get(i).getDestination());
                            sb.append(" - ");

                            //if the time until arrival is 0
                            if (closeBus.get(i).getTime_in_min() == 0) {
                                sb.append("Imminient");
                            }

                            //else
                            else {
                                //display the time in minutes
                                sb.append(closeBus.get(i).getTime_in_min());
                                sb.append(" min");
                            }

                            //print total string
                            System.out.println(sb);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if a particular stop code exists from the given stop code and the ArrayList of stops.
     * If the stop is not fund, throws stopCodeInvalidException
     *
     * @param stopcode  the stop code to be searched for
     * @param stops     the stops to be searched from
     * @return          returns if found or not
     * @throws stopCodeInvalidException
     */

    public boolean checkStopCodeIfExists(int stopcode, ArrayList<BusStop> stops) throws stopCodeInvalidException {

        boolean exists = false;

        //compare stopcode input to stop code of all stations
        for (int i = 0; i < stops.size(); i++) {
            if(stopcode == stops.get(i).getStopCode()){
                exists = true;
            }
        }

        //if it does not exist, throw exception
        if(!exists){
            throw new stopCodeInvalidException();
        }
        return exists;
    }

    /**
     * Checks if a stop is favourite given a stop code and an ArrayList of bus stops.
     * Finds the stop and then checks if it is close to a favourite location.
     *
     * @param stop_code Stop code to be checked
     * @param busStops  Stops to find the index of the stop we want, and be able to compare all of the information in them
     */

    private void checkIfStopIsFavorite(int stop_code, ArrayList<BusStop> busStops){

        double distanceToFaveLocation=0;

        int index =0;

        //get the index of the stop. (we assume it exists, since we check before using this method)
        for (int i = 0; i < busStops.size(); i++) {
            if(stop_code == busStops.get(i).getStopCode()){
                //if it does, get the index and then break
                index = i;
                break;
            }
        }

        //for all fave locations
        for (int j = 0; j < user.favoriteLocations.size() ; j++) {

            //check the distance by comparing the bus stop to the location
            distanceToFaveLocation =  distanceInMBetweenEarthCoordinates(busStops.get(index).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates());

            if(distanceToFaveLocation <= 500){
                //if it is within 500m, display message
                System.out.println("Favourite stop!");
            }
        }
    }

    /**
     * Shows the stations and stops close to the favourite locations the user has set. Prints a message if there is no favourite locations.
     */

    private void showCloseStations() {
        //if the user does not have any fave locations
        if (user.favoriteLocations.isEmpty()) {
            System.out.println("In order to have favourite stops and stations it is necessary to create a favourite location previously.");
        } else {

            //flag to know if there are any stops near the favourite location
            boolean flag;

            //for all of the user's fave locations
            for (int j = 0; j < user.favoriteLocations.size(); j++) {
                //reset the flag for every user favourite location
                flag = false;

                //get the name of their favorite location
                System.out.println("-" + user.favoriteLocations.get(j).getLocation().getName());
                int counter = 0;

                //display the bus stations nearby
                for (int i = 0; i < busStops.size(); i++) {

                    if (500 >= distanceInMBetweenEarthCoordinates(busStops.get(i).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates())) {

                        StringBuilder sb = new StringBuilder();

                        //display the stop code and name with a counter
                        sb.append("\t");
                        sb.append(++counter);
                        sb.append(")");
                        sb.append(" ");
                        sb.append(busStops.get(i).getStopName());
                        sb.append(" (");
                        sb.append(busStops.get(i).getStopCode());
                        sb.append(") ");
                        sb.append("BUS");

                        System.out.println(sb);

                        //set to true if we found a stop near the selected location
                        flag = true;
                    }
                }

                //for all metro stations
                for (int i = 0; i < metroStations.size(); i++) {

                    //if the station is within 500m of the favorite location
                    if (500 >= distanceInMBetweenEarthCoordinates(metroStations.get(i).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates())) {

                        StringBuilder sb = new StringBuilder();

                        //display the station name, station code, and a counter number
                        sb.append("\t");
                        sb.append(++counter);
                        sb.append(")");
                        sb.append(" ");
                        sb.append(metroStations.get(i).getStationName());
                        sb.append(" (");
                        sb.append(metroStations.get(i).getStationCode());
                        sb.append(") ");
                        sb.append("METRO");

                        System.out.println(sb);

                        flag = true;
                    }
                }
                if (!flag) {
                    System.out.println("TMB is doing its best to make the bus and subway arrive here.");
                }
            }
        }
    }

    /**
     * Plans a route. Asks the user for the different parameters to plan the route with (Origin location, destination location,
     * departure or arrival, date, time of day and maximum walking distance) Once the parameters have been entered,
     * the route will be saved in the user (if nothing went wrong {@link WebServices.API#plannerAPI(String origin, String destination, String date, String time, boolean dep_or_arrival, int maxWalkDistance)})
     * and the route displayed.
     */

    public void planRoute(){

        String origin = null;
        double[] coordsOrigin = new double[2];
        double[] coordsDestination = new double[2];

        String destination = null;
        String depOrArrival = null;
        boolean boolDepOrA = false;
        boolean tof = false;
        String day = null;
        String hour = null;
        int maxWalkingDist = 0;

        boolean flag = false;


        System.out.println("");
        System.out.println("Origin? (lat,long / location name)");

        while(!flag){

            if(scanner.hasNextInt()){
                scanner.nextInt();
                scanner.nextLine();
                flag = false;
                System.out.println("Sorry, this location is not valid :(");
                System.out.println("");
                System.out.println("Origin? (lat,long / location name)");
            }

            else {
                origin = scanner.nextLine();

                if (validLocationName(origin, allLocations)) {
                    int pos=0;
                    for (int i = 0; i < allLocations.size(); i++) {
                        if(origin.equalsIgnoreCase(allLocations.get(i).getName())){
                            pos = i;
                            break;
                        }
                    }
                    flag = true;
                    double[] cords= allLocations.get(pos).getCoordinates();
                    origin = cords[1]+ "," + cords[0];

                } else if (origin.contains(",")) {
                    String tempLat = origin.substring(origin.indexOf(",") + 1, origin.length());
                    String tempLong = origin.substring(0, origin.indexOf(","));

                    coordsOrigin[0] = Double.parseDouble(tempLat);
                    coordsOrigin[1] = Double.parseDouble(tempLong);

                    if (checkIfLatitudeCorrect(coordsOrigin[0]) && checkIfLongitudeCorrect(coordsOrigin[1])) {
                        flag = true;
                    }
                }

                if (!flag) {
                    System.out.println("Sorry, this location is not valid :(");
                    System.out.println("");
                    System.out.println("Origin? (lat,long / location name)");
                }
            }
        }

        System.out.println("");
        System.out.println("Destination? (lat,lon / location name)");

        flag = false;

        while(!flag){
                destination = scanner.nextLine();

                if (validLocationName(destination, allLocations)) {
                    int pos=0;
                    for (int i = 0; i < allLocations.size(); i++) {
                        if(destination.equalsIgnoreCase(allLocations.get(i).getName())){
                            pos = i;
                            break;
                        }
                    }
                    flag = true;
                    double[] cords= allLocations.get(pos).getCoordinates();
                    destination = cords[1] + "," + cords[0];
                } else if (destination.contains(",")) {
                    String tempLat = destination.substring(destination.indexOf(",") + 1, destination.length());
                    String tempLong = destination.substring(0, destination.indexOf(","));

                    coordsDestination[0] = Double.parseDouble(tempLat);
                    coordsDestination[1] = Double.parseDouble(tempLong);

                    if (checkIfLatitudeCorrect(coordsDestination[0]) && checkIfLongitudeCorrect(coordsDestination[1])) {
                        flag = true;
                    }
                }

                if (!flag) {
                    System.out.println("Sorry, this location is not valid :(");
                    System.out.println("");
                    System.out.println("Destination? (lat,long / location name)");
                }

        }

        System.out.println("");
        System.out.println("Departure or arrival? (d/a)");

        depOrArrival = scanner.nextLine();

        while (!(depOrArrival.equalsIgnoreCase("a") || depOrArrival.equalsIgnoreCase("d"))){
            System.out.println("Error, please enter a or d");
            System.out.println("");
            System.out.println("Departure or arrival? (d/a)");

            depOrArrival = scanner.nextLine();
        }

        if(depOrArrival.equalsIgnoreCase("a")){
            boolDepOrA = true;
        }

        System.out.println("");
        System.out.println("Day? (MM-DD-YYYY)");
        day = scanner.nextLine();

        System.out.println("");
        System.out.println("Hour? (HH:MMam/HH:MMpm)");
        hour = scanner.nextLine();

        System.out.println("");
        System.out.println("Maximum walking distance in meters?");
        maxWalkingDist = scanner.nextInt();
        scanner.nextLine();

        Route possibleRoutes = api.plannerAPI(origin,destination, day,  hour, boolDepOrA, maxWalkingDist);
        if (possibleRoutes != null){

        user.pastRoutes.add(possibleRoutes);

        System.out.println("");
        System.out.println("Fastest Combination:");
        System.out.println("\tTime taken: " + possibleRoutes.getTimeTaken()/60 + " min");
        System.out.println("\tOrigin");
        System.out.println("\t|");
        for (int i = 0; i <possibleRoutes.getRouteSections().size() ; i++) {
            if(possibleRoutes.getRouteSections().get(i) instanceof Transit){

                int timeMin = calculateTimeInMinutes(possibleRoutes.getRouteSections().get(i));
                System.out.println("\t"+ ((Transit) possibleRoutes.getRouteSections().get(i)).getLine_name()
                        +" " + ((Transit)possibleRoutes.getRouteSections().get(i)).getFrom_name()+" "+ "("+
                        ((Transit) possibleRoutes.getRouteSections().get(i)).getFrom_stopcode()+")"+"->"
                        +" " + ((Transit) possibleRoutes.getRouteSections().get(i)).getTo_name()+" "+ "("+
                        ((Transit) possibleRoutes.getRouteSections().get(i)).getTo_stopcode()+")"+" "
                        +timeMin+" min");

                System.out.println("\t|");

            }
            else{

                int timeMin = calculateTimeInMinutes(possibleRoutes.getRouteSections().get(i));
                System.out.println("\t"+possibleRoutes.getRouteSections().get(i).getMode()+" "+timeMin+" min");
                System.out.println("\t|");
            }
        }

        System.out.println("\tDestination");
        System.out.println("");
        }
    }

    /**
     * Prints the routes the user has saved. If there are none, prints a message stating so.
     */

    public void userRoutes(){

        //if the user has not made any routes
        if(user.pastRoutes.isEmpty()){
            System.out.println("You have not made any route :(");
            System.out.println("To search for one, access option 3 on the principal menu.");
        }
        else{

            //if the user has created a route
            for (int i = 0; i < user.pastRoutes.size(); i++) {
                int num = i+1;
                System.out.println("");
                //display route order with all information seen when asking for route
                System.out.println("->Route "+num+":");
                System.out.println("\t-Origin: "+user.pastRoutes.get(i).getOrigin());
                System.out.println("\t-Destination: "+user.pastRoutes.get(i).getDestination());
                System.out.println("\t-Date and Time: "+user.pastRoutes.get(i).getDate()+" at "+user.pastRoutes.get(i).getTime());
                System.out.println("\t-Max Walking Distance: "+user.pastRoutes.get(i).getMaxWalkingDistance());
                System.out.println("\t-Fastest Combination: ");
                System.out.println("\t\tTime Taken: " + user.pastRoutes.get(i).getTimeTaken()/60 + " min");
                System.out.println("\t\tOrigin");
                System.out.println("\t\t|");

                //get step-by-step guidance of the route for each part of the route
                for (int j = 0; j <user.pastRoutes.get(i).getRouteSections().size(); j++) {

                    //if this part of the route takes place on some form of transit
                    if(user.pastRoutes.get(i).getRouteSections().get(j) instanceof Transit){

                        //display the information (line name, stop name, and stop code) for origin and destination, and the total time taken
                        int timeMin = calculateTimeInMinutes(user.pastRoutes.get(i).getRouteSections().get(j));
                        System.out.println("\t\t"+ ((Transit) user.pastRoutes.get(i).getRouteSections().get(j)).getLine_name()
                                +" " + ((Transit) user.pastRoutes.get(i).getRouteSections().get(j)).getFrom_name()+" "+ "("+
                                ((Transit) user.pastRoutes.get(i).getRouteSections().get(j)).getFrom_stopcode()+")"+"->"
                                +" " + ((Transit) user.pastRoutes.get(i).getRouteSections().get(j)).getTo_name()+" "+ "("+
                                ((Transit) user.pastRoutes.get(i).getRouteSections().get(j)).getTo_stopcode()+")"+" "
                                +timeMin+" min");

                        System.out.println("\t\t|");

                    }
                    else{

                        //if the mode is walk
                        int timeMin = calculateTimeInMinutes(user.pastRoutes.get(i).getRouteSections().get(j));
                        //display how long it takes to walk
                        System.out.println("\t\t"+user.pastRoutes.get(i).getRouteSections().get(j).getMode()+" "+timeMin+" min");
                        System.out.println("\t\t|");

                    }
                }

                System.out.println("\t\tDestination");
                System.out.println("");

            }
        }
    }

    /**
     * Calculates the time it takes for a section to be completed (to go from one place to another in a route)
     *
     * @param section
     * @return time in integer minutes
     */

    private int calculateTimeInMinutes(Section section){
        int total_time=0;
        //calculate the total time for each leg of the journey
        //since the end time and start time are in epoch time, divide by 10000, and then 60 to get mintues
        //cast to an int to get approx time
        total_time = (int) (((section.getEnd_time() - section.getStart_time())/1000)/60);

        return total_time;
    }

    /**
     * Checks if the given string is a yes or a no. returns the result (true if it is a yes OR a no, false otherwise)
     *
     * @param yesorno string to be checked
     * @return result of the operation
     */

    private boolean checkYesOrNo(String yesorno){
        //check if the string input is yes or no

        //if not, print an error
        if(!yesorno.equalsIgnoreCase("no") && !yesorno.equalsIgnoreCase("yes")){
            System.out.println("");
            System.out.println("Error! you must enter yes or no!");
            return false;
        }

        else{
            return true;
        }
    }

    /**
     * Calculates the distance between two points on the earths surface, given their latitudes and longitudes.
     *
     * @param long_lat1
     * @param long_lat2
     * @return the distance between the two points given (in metres)
     */

    private double distanceInMBetweenEarthCoordinates(double[] long_lat1, double[] long_lat2) {
        int earthRadiusM = 6371000;

        //get the difference between the longitudes and latitudes
        double latDistance = degreesToRadians(long_lat2[1]-long_lat1[1]);
        double lonDistance = degreesToRadians(long_lat2[0]-long_lat1[0]);

        //Havesine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(degreesToRadians(long_lat1[1])) * Math.cos(degreesToRadians(long_lat2[1])) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        //return the distance between the 2 points
        return earthRadiusM * c;
    }

    /**
     * Converts to degrees to radians
     * @param degrees
     * @return
     */

    private double degreesToRadians(double degrees) {
        return degrees * (Math.PI/180);
    }


    /**
     * method to run the different options for menu 1 depending on the user input.
     * @param option integer to select the different options
     */

    public void whichOptionM1(int option){

        if(option == 2){
            searchLocation();
        }

        else if(option == 3){
            planRoute();
        }
        else if(option == 4){
            getBusWaitTime();
        }
        else if(option == 5){
            System.out.println("Thanks for using our program!");
        }
    }

    /**
     * method to run the different options for menu 2 depending on the user input.
     * @param option string to select the different options
     */

    public void whichOptionM2(String option){

        if(option.equalsIgnoreCase("a")){
            listLocations();
        }

        else if(option.equalsIgnoreCase("b")){
            locationHistory();
        }
        else if(option.equalsIgnoreCase("c")){
            userRoutes();
        }

        else if(option.equalsIgnoreCase("d")){
            showCloseStations();
        }

        else if(option.equalsIgnoreCase("e")){
            stationsInauguratedByBirthYear();
        }

        else if(option.equalsIgnoreCase("f")){
            System.out.println();
        }
    }

}