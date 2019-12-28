package GUI_and_Menu_logic;

import DataModel.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Logic {

    private Scanner scanner;

    public Logic() {
        scanner = new Scanner(System.in);
    }



    private void listLocations(ArrayList<Location> userLocations){

        String yesorno;

        if(userLocations.isEmpty() == false){

            for (int i = 0; i < userLocations.size(); i++) {

                System.out.println();

            }

        }
        else{
                System.out.println("You don't have any locations created");
                System.out.println("");
                System.out.println("Want to create a new location? (yes/no)");
                yesorno = scanner.nextLine();
                if(yesorno.equalsIgnoreCase("yes")){

                    Menu menu = new Menu();
                    menu.userCreateLocation();

                }
                else if(yesorno.equalsIgnoreCase("no")){


                }
                else{


                }


        }

    }


     Location createNewLocation(String name, Double[] coordinates, String description){

        Place newPlace = new Place();
        newPlace.setName(name);
        newPlace.setCoordinates(coordinates);
        newPlace.setDescription(description);

       return newPlace;

    }

    /*

    private void editLocation(Location newLocation){


    }

   private Boolean searchLocation(Location location){
        return true;
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

