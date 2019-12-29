package GUI_and_Menu_logic;

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
private static final double maxlat = ;
private static final double minlong = ;
private static final double maxlong = ;

 */

public class Logic {

    private Scanner scanner;

    public static ArrayList<Location> allLocations;

    public Logic() {
        scanner = new Scanner(System.in);
    }

    void listLocations(ArrayList<Location> userLocations){

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

        else if(yesorno.equalsIgnoreCase("no")){

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

        User.userLocations.add(createNewLocation(name, coordinates, description));

        System.out.println("");
        System.out.println("This information has been successfully registered!");
        System.out.println("");
        for (int i = 0; i < User.userLocations.size(); i++) {
            System.out.println("-" + User.userLocations.get(i).getName());
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

    void searchLocation(){

        String name;
        Integer pos;

        System.out.println("");
        System.out.println("Enter the name of a location");
        name = scanner.nextLine();

        pos = checkLocationExist(name);

        if(pos != null){

            System.out.println("Coordinates:"+""+ Arrays.toString(allLocations.get(pos).getCoordinates()));
            System.out.println("Description:");
            System.out.println(allLocations.get(pos).getDescription());

            if(allLocations.get(pos) instanceof Restaurant){

                for (int i = 0; i < ((Restaurant) allLocations.get(pos)).getCharacteristics().size(); i++) {

                    System.out.println("Characteristics: ");
                    //TODO: how to get all characteristics
                    //System.out.print((Restaurant) allLocations.get(pos).getCharacteristics());
                }

            }
            else if(allLocations.get(pos) instanceof Hotel){

                System.out.println("Stars: " + ((Hotel) allLocations.get(pos)).getStars());

            }
            else if(allLocations.get(pos) instanceof Monument){

                System.out.println("Architect: " + ((Monument) allLocations.get(pos)).getArchitect());
                System.out.println("Inauguration: " + ((Monument) allLocations.get(pos)).getInauguration());

            }

        }
        else{


        }


    }

    private Integer checkLocationExist(String name){

        Integer pos = null;

        for (int i = 0; i < allLocations.size(); i++) {

            if(name.equalsIgnoreCase(allLocations.get(i).getName())){

                pos = i;
            }
        }
        return pos;
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

