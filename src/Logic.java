import DataModel.*;
import com.sun.tools.javac.Main;

import java.io.IOException;
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
    private Scanner scanner = new Scanner(System.in);

    public ArrayList<Location> allLocations;
    private ArrayList<Location> searchedLocations;
    //private static ArrayList<Location> searchedLocations;

    public void loadLocationData(){
        JSONParser parser = new JSONParser();
        allLocations = parser.parseLocations();
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

        System.out.println(Menu.flagvalid);
        System.out.println("");

        user = new User(username, email, birthyear);
    }


    public void whichOptionM1(int option){

        if(option == 2){
            searchLocation();
        }
        else if(option == 3){

        }
        else if(option == 4){

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

        }
        else if(option.equalsIgnoreCase("e")){

        }
        else if(option.equalsIgnoreCase("f")){

            System.out.println();

        }
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
        //**********************************************************************************************************

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
        scanner.nextLine();
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

        Place newPlace = new Place();
        newPlace.setName(name);
        newPlace.setCoordinates(coordinates);
        newPlace.setDescription(description);

        return newPlace;
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
        Integer pos = null;

        System.out.println("");
        System.out.println("Enter the name of a location");
        name = scanner.nextLine();

        try {
            pos = checkLocationExist(name);
            searchedLocations.add(allLocations.get(pos));
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

    private Integer checkLocationExist(String name) throws locationNotFoundException{

        Integer pos = null;

        for (int i = 0; i < allLocations.size(); i++) {

            if(name.equalsIgnoreCase(allLocations.get(i).getName())){
                pos = i;
            }
        }
        
        if (pos == null){
            throw new locationNotFoundException();
        }
        
        return pos;
    }

    private void userSetFaveLocation(Location location){

        System.out.println("");
        System.out.println("Type (home / work / studies / leisure / culture):");
        String type = scanner.nextLine();
        //**********************************************************************************************************

        while(!(type.equalsIgnoreCase("home") || type.equalsIgnoreCase("work") || type.equalsIgnoreCase("studies") || type.equalsIgnoreCase("leisure") ||type.equalsIgnoreCase("culture")  )){
            System.out.println("");
            System.out.println("Error! You have to enter \"home\", \"work\", \"studies\", \"leisure\" or \"culture\".");
            System.out.println("Type (home / work / studies / leisure / culture):");
            type = scanner.nextLine();
        }

        Date date = new Date();

        if(location instanceof Restaurant){

            FavLocation tempR = new FavLocation();
            tempR.setLocation(location);
            tempR.setDate(date);
            tempR.setType(type);

            user.favoriteLocation.add(tempR);

        }
        else if(location instanceof Hotel){
            FavLocation tempH = new FavLocation();
            tempH.setLocation(location);
            tempH.setDate(date);
            tempH.setType(type);

            user.favoriteLocation.add(tempH);


        }
        else if(location instanceof Monument){
            FavLocation tempM = new FavLocation();
            tempM.setLocation(location);
            tempM.setDate(date);
            tempM.setType(type);

            user.favoriteLocation.add(tempM);
        }
        else{
            FavLocation tempP = new FavLocation();

            tempP.setLocation(location);
            tempP.setDate(date);
            tempP.setType(type);

            user.favoriteLocation.add(tempP);


        }

        System.out.println("");
        System.out.println(location.getName()+" had been added as a new favorite location");
    }
    
    
    private void locationHistory(){

        if(searchedLocations.isEmpty()){

            for (int i = 0; i < searchedLocations.size(); i++) {
                System.out.println("");
                System.out.println("Searched Locations:");
                System.out.println("/t"+"-"+searchedLocations.get(i));
            }

        }
        else{

            System.out.println("");
            System.out.println("You have not searched for any locations!");
            System.out.println("To search for one, access option 2 in the principal menu.");

        }

    }






   /*
    private void editLocation(Location newLocation){


    }


    private Route planRoute(Location origin, Location destination, String date, String hour, int max_dist_walking){

    return null;
    }

    private Route planRoute(String origin, String, char dest_or_arrival, Date date, String hour, int max_dist_walking){

    return null;
    }

    private void busWaitTime(int stop_code){


    }

    private void checkUserData(String username,String email,int birthday){


    }
    private void markAsFavourite(Location location){


    }




    */

}

