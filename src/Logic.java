import DataModel.*;
import Exceptions.invalidStopCodeException;
import Exceptions.locationNotFoundException;
import Exceptions.stationNotFoundByYearException;
import WebServices.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

//TODO: get min max values for latitude somehow
//TODO: find a way to maintain the values in arraylist for user input locations
//TODO: find out a way to maintain allLocations arraylist

/*
private static final double minlat = ;
private static final double maxlat = 50.712093;
private static final double minlong = ;
private static final double maxlong = 4.384272;

 */

public class Logic {

    private User user;
    private API api = new API();
    private JSONParser parser = new JSONParser();
    private final Scanner scanner = new Scanner(System.in);

    public ArrayList<Location> allLocations;
    private ArrayList<Location> searchedLocations = new ArrayList<>();
    //private static ArrayList<Location> searchedLocations;

    public ArrayList<BusStation> busStations = new ArrayList<>();
    public ArrayList<MetroStation> metroStations = new ArrayList<>();


    public void loadData(){
        //Loading locations from the JSON file
        allLocations = parser.parseLocations();

        //Loading all of the bus lines from the API
        busStations = api.loadBusStations();

        /*
        for (int i = 0; i < busStations.size(); i++) {
            System.out.println(busStations.get(i).getCoordinates()[0]);
            System.out.println(busStations.get(i).getCoordinates()[1]);
        }

         */

        //loading all of the metro lines from the API
        //metroStations = api.loadMetroStations();
    }

    public void Intro(){
        String username;
        String email;
        int birthyear;

        System.out.println("");
        System.out.println(Menu.intro);
        System.out.println(Menu.userinfo1);
        username = scanner.nextLine();

        System.out.println(Menu.userinfo2);
        email = scanner.nextLine();

        System.out.println(Menu.userinfo3);
        birthyear = scanner.nextInt();
        scanner.nextLine();

        System.out.println(Menu.flagvalid);
        System.out.println("");

        user = new User(username, email, birthyear);
    }

    public void listLocations(){

        ArrayList<Location> userLocations = new ArrayList<>(user.userLocations);

        String yesorno;

        if(!userLocations.isEmpty()){
            for (int i = 0; i < userLocations.size(); i++) {
                //Print out all of the user's locations
                System.out.println(userLocations.get(i).getName());
            }
        }

        if(userLocations.isEmpty()){
            System.out.println("You don't have any locations created");
            System.out.println("");
        }

        System.out.println("Want to create a new location? (yes/no)");
        yesorno = scanner.nextLine();

        while(!yesorno.equalsIgnoreCase("no") && !yesorno.equalsIgnoreCase("yes")){
            System.out.println("");
            System.out.println("Error! you must enter yes or no!");
            System.out.println("");
            System.out.println("Want to create a new location? (yes/no)");
            yesorno = scanner.nextLine();
        }

        if(yesorno.equalsIgnoreCase("yes")){
            userCreateLocation();
        }

        else if(yesorno.equalsIgnoreCase("no")) {

        }
    }

    void userCreateLocation(){

        String name;
        Double[] coordinates = new Double[2];
        String description;

        System.out.println("Location Name:");
        name = scanner.nextLine();


        while(validLocationName(name, allLocations) ){
            System.out.println("");
            System.out.println("Error: This location already exists.");
            System.out.println("");

            System.out.println("Location Name:");
            name = scanner.nextLine();
        }

        System.out.println("");
        System.out.println("Longitude: ");
        boolean b = askForLongitude(coordinates);

        while(!b){
            b = askForLongitude(coordinates);
        }

        System.out.println("");
        System.out.println("Latitude: ");
        b = askForLatitude(coordinates);

        while(!b){
            b = askForLatitude(coordinates);
        }

        System.out.println("");
        System.out.println("Description: ");
        description = scanner.nextLine();

        Location newLoc = createNewLocation(name, coordinates, description);

        user.userLocations.add(newLoc);
        allLocations.add(newLoc);

        System.out.println("");
        System.out.println("This information has been successfully registered!");
        System.out.println("");
        for (int i = 0; i < user.userLocations.size(); i++) {
            System.out.println("-" + user.userLocations.get(i).getName());
        }
    }

    private Location createNewLocation(String name, Double[] coordinates, String description){
        return new Place(name, coordinates, description);
    }

    private boolean validLocationName(String name, ArrayList<Location> allLocations){

        boolean tof = false;

        for (int i = 0; i < allLocations.size(); i++) {
            if(name.equalsIgnoreCase(allLocations.get(i).getName())){
                tof = true;
            }

        }
        return tof;
    }

    private boolean askForLongitude(Double[] coordinates){
        if(scanner.hasNextDouble()){
            coordinates[0] = scanner.nextDouble();
            scanner.nextLine();
            return true;
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

    private boolean askForLatitude(Double[] coordinates){
        if(scanner.hasNextDouble()){
            coordinates[1] = scanner.nextDouble();
            scanner.nextLine();
            return true;
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

    public void searchLocation(){

        String name;
        int pos = -1;

        System.out.println("");
        System.out.println("Enter the name of a location");

        name = scanner.nextLine();

        try {
            pos = checkLocationExist(name, allLocations);

            boolean alreadySearched = false;

            for (int i = 0; i < searchedLocations.size(); i++) {
                if(searchedLocations.get(i) == allLocations.get(pos)){
                    alreadySearched = true;
                    searchedLocations.remove(i);
                    searchedLocations.add(allLocations.get(pos));
                }
            }

            if(!alreadySearched){
                searchedLocations.add(allLocations.get(pos));
            }

            System.out.println("Coordinates:"+""+ Arrays.toString(allLocations.get(pos).getCoordinates()));
            System.out.println("Description:");
            System.out.println(allLocations.get(pos).getDescription());

            if(allLocations.get(pos) instanceof Restaurant){

                for (int i = 0; i < ((Restaurant) allLocations.get(pos)).getCharacteristics().size(); i++) {
                    System.out.println("Characteristics: ");
                    System.out.print(((Restaurant) allLocations.get(pos)).getCharacteristics());
                }

            }
            else if(allLocations.get(pos) instanceof Hotel){

                System.out.println("Stars: " + ((Hotel) allLocations.get(pos)).getStars());

            }
            else if(allLocations.get(pos) instanceof Monument){

                System.out.println("Architect: " + ((Monument) allLocations.get(pos)).getArchitect());
                System.out.println("Inauguration: " + ((Monument) allLocations.get(pos)).getInauguration());

            }

            System.out.println("");
            System.out.println("Do you want to set the found location as your favorite? (yes/no)");
            String yesorno = scanner.nextLine();

            while(!yesorno.equalsIgnoreCase("no") && !yesorno.equalsIgnoreCase("yes")){
                System.out.println("");
                System.out.println("Error! you must enter yes or no!");
                System.out.println("");
                System.out.println("Do you want to set the found location as your favorite? (yes/no)");
                yesorno = scanner.nextLine();
            }

            if(yesorno.equalsIgnoreCase("yes")){
                userSetFaveLocation(allLocations.get(pos));
            }

            else if(yesorno.equalsIgnoreCase("no")) {

            }

        } catch (locationNotFoundException e) {
            e.printErrorMessage();
        }


    }

    private int checkLocationExist(String name, ArrayList<Location> locations) throws locationNotFoundException{

        int pos = -1;

        for (int i = 0; i < locations.size(); i++) {

            if(name.equalsIgnoreCase(locations.get(i).getName())){
                pos = i;
            }
        }

        if (pos == -1){
            throw new locationNotFoundException();
        }

        return pos;
    }

    private void userSetFaveLocation(Location location){

        System.out.println("");
        System.out.println("Type (home / work / studies / leisure / culture):");
        String type = scanner.nextLine();

        while(!(type.equalsIgnoreCase("home") || type.equalsIgnoreCase("work") || type.equalsIgnoreCase("studies") || type.equalsIgnoreCase("leisure") ||type.equalsIgnoreCase("culture")  )){
            System.out.println("");
            System.out.println("Error! You have to enter \"home\", \"work\", \"studies\", \"leisure\" or \"culture\".");
            System.out.println("Type (home / work / studies / leisure / culture):");
            type = scanner.nextLine();
        }

        Date date = new Date();

        FavLocation temp = new FavLocation(location, date, type);

        user.favoriteLocations.add(temp);

        System.out.println("");
        System.out.println(location.getName() + " has been added as a new favorite location");
    }

    private void locationHistory() {

        if (!searchedLocations.isEmpty()) {

            System.out.println("Searched Locations:");

            for (int i = searchedLocations.size() -1; i >= 0; i--) {
                System.out.println("");
                System.out.println("\t" + "-" + searchedLocations.get(i).getName());
            }
        }
        else{
            System.out.println("");
            System.out.println("You have not searched for any locations!");
            System.out.println("To search for one, access option 2 in the principal menu.");

        }

    }

    private ArrayList<MetroStation> findStationsByYear(int year) throws stationNotFoundByYearException {
        ArrayList<MetroStation> birthyear_stations = new ArrayList<>();

        String yearStr = Integer.toString(year);

        for (int i = 0; i < metroStations.size() ; i++) {
            if(metroStations.get(i).getDateInaugurated().charAt(0) == yearStr.charAt(0) && metroStations.get(i).getDateInaugurated().charAt(1) == yearStr.charAt(1)
                && metroStations.get(i).getDateInaugurated().charAt(2) == yearStr.charAt(2) && metroStations.get(i).getDateInaugurated().charAt(3) == yearStr.charAt(3)){
                birthyear_stations.add(metroStations.get(i));
            }
        }

        if(birthyear_stations.isEmpty()){
            throw new stationNotFoundByYearException();
        }
        return birthyear_stations;
    }

    public void stationsInauguratedByBirthYear(){
        ArrayList<MetroStation> birthyear_stations = new ArrayList<>();
        try{
            birthyear_stations = findStationsByYear(user.getBirthyear());

            for (int i = 0; i < birthyear_stations.size(); i++) {
                StringBuilder sb = new StringBuilder(birthyear_stations.get(i).getName());
                sb.append(" ");
                sb.append(birthyear_stations.get(i).getLineName());
                System.out.println(sb);
            }

        } catch (stationNotFoundByYearException e) {
            e.printErrorMessage();
        }
    }


    public void getBusWaitTime(){
        int stopCode;
        boolean exists = false;

        //check if it exists HERE
        while(!exists){
            System.out.println("");
            System.out.println("Enter the stop code:");
            stopCode = scanner.nextInt();
            scanner.nextLine();
            try {
                exists = checkStopCodeIfExists(stopCode, busStations);
            } catch (invalidStopCodeException e) {
                e.printErrorMessage();
            }finally {
                if(exists){
                    ArrayList<iBus> closeBus = new ArrayList<>(api.getBusWaitTime(stopCode));
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < closeBus.size() ; i++) {
                        //System.out.println(closeBus.get(i).getLine() + "" + "-" + "" + closeBus.get(i).getDestination() + "" + "-" + "" + closeBus.get(i).getTime_in_min() + " min");
                        sb.append(closeBus.get(i).getLine());
                        sb.append(" - ");
                        sb.append(closeBus.get(i).getDestination());
                        sb.append(" - ");

                        if(closeBus.get(i).getTime_in_min() == 0){
                            sb.append("Imminient");
                        }

                        else{
                            sb.append(closeBus.get(i).getTime_in_min());
                            sb.append(" min");
                        }

                        System.out.println(sb);

                    }
                }
            }
        }
    }

    public boolean checkStopCodeIfExists(int stopcode, ArrayList<BusStation> stations) throws invalidStopCodeException {

        boolean exists = false;

        for (int i = 0; i < stations.size(); i++) {
            if(stopcode == stations.get(i).getStopCode()){
                exists = true;
            }
        }

        if(!exists){
            throw new invalidStopCodeException();
        }
        return exists;
    }

    private void showCloseStations() {
        if(user.favoriteLocations.isEmpty()){
            System.out.println("In order to have favourite stops and stations it is necessary to create a favourite location previously.");
        }

        else{
            ArrayList<BusStation> bs = new ArrayList<>(busStations);
            ArrayList<MetroStation> ms = new ArrayList<>(metroStations);

            int counter = 0;

            boolean flag = false;

            for (int i = 0; i < bs.size(); i++) {
                for (int j = 0; j < user.favoriteLocations.size(); j++) {

                    if(500 >= distanceInKmBetweenEarthCoordinates(bs.get(i).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates())){

                        System.out.println(distanceInKmBetweenEarthCoordinates(bs.get(i).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates()));

                        StringBuilder sb = new StringBuilder();

                        sb.append("(");
                        sb.append(++counter);
                        sb.append(" ");
                        sb.append(bs.get(i).getStopName());
                        sb.append(" (");
                        sb.append(bs.get(i).getStopCode());
                        sb.append(") ");
                        sb.append("BUS");

                        System.out.println(sb);

                        flag = true;
                    }
                }
            }

            if(!flag){
                System.out.println("TMB is doing its best to make the bus and subway arrive here.");
            }
        }
    }

    private double distanceInKmBetweenEarthCoordinates(Double[] lat_long1, Double[] lat_long2) {
        int earthRadiusM = 6371000;

        double dLat = degreesToRadians(lat_long2[1]-lat_long1[1]);
        double dLon = degreesToRadians(lat_long2[0]-lat_long1[0]);

        double lat1_deg = degreesToRadians(lat_long1[1]);
        double lat2_deg = degreesToRadians(lat_long2[1]);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1_deg) * Math.cos(lat2_deg);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c * earthRadiusM;
    }

    private double degreesToRadians(double degrees) {
        return degrees * 0.0174532925199433;
    }


    public void whichOptionM1(int option){

        if(option == 2){
            searchLocation();
        }

        else if(option == 3){

        }
        else if(option == 4){

            getBusWaitTime();

        }
        else if(option == 5){
            System.out.println("Thanks for using our program!");
        }
    }

    public void whichOptionM2(String option){

        if(option.equalsIgnoreCase("a")){
            listLocations();
        }

        else if(option.equalsIgnoreCase("b")){

            locationHistory();

        }
        else if(option.equalsIgnoreCase("c")){

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

   /*

    private Route planRoute(Location origin, Location destination, String date, String hour, int max_dist_walking){

    return null;
    }

    private Route planRoute(String origin, String, char dest_or_arrival, Date date, String hour, int max_dist_walking){

    return null;
    }

    private void checkUserData(String username,String email,int birthday){


    }

    */
}