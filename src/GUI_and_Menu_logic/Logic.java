package GUI_and_Menu_logic;

import DataModel.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Logic {

    private Scanner scanner;

    public Logic() {
        scanner = new Scanner(System.in);
    }

    public void whichOptionM1(int option){

        if(option ==1){

            Menu menu = new Menu();
            menu.printMenu2();

        }
        else if(option == 2){

            //searchLocation();

        }
        else if(option == 3){

        }
        else if(option == 4){

        }
        else if(option == 5){

            //System.out.println();

        }
    }

    public void whichOptionM2(String option){

        if(option.equalsIgnoreCase("a")){
            //listLocations();
        }
        else if(option.equalsIgnoreCase("b")){

            //searchLocation();

        }
        else if(option.equalsIgnoreCase("c")){

        }
        else if(option.equalsIgnoreCase("d")){

        }
        else if(option.equalsIgnoreCase("e")){

        }
        else if(option.equalsIgnoreCase("f")){

            //System.out.println();

        }
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



    void createNewLocation(String name, Double[] coordinates, String description){


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

