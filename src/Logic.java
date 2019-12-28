import java.util.Date;

public class Logic {

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

            Menu menu = new Menu();
            menu.printMenu2();

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

    /*

    private void listLocations(){


    }

    private void createNewLocation(String name,  Double[] coordinates, String description){


    }

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

