import DataModel.*;
import Exceptions.stopCodeInvalidException;
import Exceptions.locationNotFoundException;
import Exceptions.stationNotFoundByYearException;
import WebServices.*;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;


public class Logic {

    private static final double minlat = 2.046171;
    private static final double maxlat =  2.244097;
    private static final double minlong = 41.287565;
    private static final double maxlong = 41.5;

    private User user;
    private API api = new API();
    private JSONParser parser = new JSONParser();
    private final Scanner scanner = new Scanner(System.in);

    public ArrayList<Location> allLocations;
    private ArrayList<Location> searchedLocations = new ArrayList<>();

    public ArrayList<BusStation> busStations = new ArrayList<>();
    public ArrayList<MetroStation> metroStations = new ArrayList<>();


    public void loadData(){
        //Loading locations from the JSON file
        allLocations = parser.parseLocations();

        //Loading all of the bus lines from the API
        busStations = api.loadBusStations();

        //loading all of the metro lines from the API
        metroStations = api.loadMetroStations();
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

        while(!checkYesOrNo(yesorno)){
            System.out.println("Want to create a new location? (yes/no)");
            yesorno = scanner.nextLine();
        }

        if(yesorno.equalsIgnoreCase("yes")){
            userCreateLocation();
        }
    }

    private void userCreateLocation(){

        String name;
        double[] coordinates = new double[2];
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

    private Location createNewLocation(String name, double[] coordinates, String description){
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

    private boolean askForLongitude(double[] coordinates){
        if(scanner.hasNextDouble()&& minlong > coordinates[0] && maxlong < coordinates[0] ){
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

    private boolean askForLatitude(double[] coordinates){
        if(scanner.hasNextDouble() && minlat > coordinates[1] && maxlat < coordinates[1] ){
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

            while(!checkYesOrNo(yesorno)){
                System.out.println("Do you want to set the found location as your favorite? (yes/no)");
                yesorno = scanner.nextLine();
            }

            if(yesorno.equalsIgnoreCase("yes")){
                userSetFaveLocation(allLocations.get(pos));
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
                StringBuilder sb = new StringBuilder(birthyear_stations.get(i).getStationName());
                sb.append(" ");
                sb.append(birthyear_stations.get(i).getLineName());
                System.out.println(sb);
            }

        } catch (stationNotFoundByYearException e) {
            e.printErrorMessage();
        }
    }


    public void getBusWaitTime(){
        int stopCode=0;
        boolean tof;
        boolean exists = false;

        //check if it exists
        while(!exists){
            System.out.println("");
            System.out.println("Enter the stop code:");

            tof = checkNextInt(stopCode);
            while(!tof){
                System.out.println("");
                System.out.println("Enter the stop code:");
                tof = checkNextInt(stopCode);
            }
            try {
                exists = checkStopCodeIfExists(stopCode, busStations);
            } catch (stopCodeInvalidException e) {
                e.printErrorMessage();
            }finally {
                if(exists){
                    ArrayList<iBus> closeBus = new ArrayList<>(api.getBusWaitTime(stopCode));

                    for (int i = 0; i < closeBus.size() ; i++) {
                        StringBuilder sb = new StringBuilder();
                        checkIfStopIsFavorite(stopCode);
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

    public boolean checkStopCodeIfExists(int stopcode, ArrayList<BusStation> stations) throws stopCodeInvalidException {

        boolean exists = false;

        for (int i = 0; i < stations.size(); i++) {
            if(stopcode == stations.get(i).getStopCode()){
                exists = true;
            }
        }

        if(!exists){
            throw new stopCodeInvalidException();
        }
        return exists;
    }

    private void checkIfStopIsFavorite(int stop_code){

        double distanceToFaveLocation=0;

        int index =0;

        for (int i = 0; i < busStations.size(); i++) {
            if(stop_code == busStations.get(i).getStopCode()){
                index = i;
                break;
            }
        }

        for (int j = 0; j < user.favoriteLocations.size() ; j++) {

            distanceToFaveLocation =  distanceInKmBetweenEarthCoordinates(busStations.get(index).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates());

            if(distanceToFaveLocation <= 500){
                System.out.println("Favourite stop!");
            }

        }

    }

    private void showCloseStations() {
        if (user.favoriteLocations.isEmpty()) {
            System.out.println("In order to have favourite stops and stations it is necessary to create a favourite location previously.");
        } else {

            boolean flag = false;

            for (int j = 0; j < user.favoriteLocations.size(); j++) {

                System.out.println("-" + user.favoriteLocations.get(j).getLocation().getName());
                int counter = 0;

                for (int i = 0; i < busStations.size(); i++) {

                    if (500 >= distanceInKmBetweenEarthCoordinates(busStations.get(i).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates())) {

                        StringBuilder sb = new StringBuilder();

                        sb.append(++counter);
                        sb.append(")");
                        sb.append(" ");
                        sb.append(busStations.get(i).getStopName());
                        sb.append(" (");
                        sb.append(busStations.get(i).getStopCode());
                        sb.append(") ");
                        sb.append("BUS");

                        System.out.println(sb);

                        flag = true;
                    }
                }

                for (int i = 0; i < metroStations.size(); i++) {

                    if (500 >= distanceInKmBetweenEarthCoordinates(metroStations.get(i).getCoordinates(), user.favoriteLocations.get(j).getLocation().getCoordinates())) {

                        StringBuilder sb = new StringBuilder();

                        sb.append(++counter);
                        sb.append(")");
                        sb.append(" ");
                        sb.append(metroStations.get(i).getStationName());
                        sb.append(" (");
                        sb.append(metroStations.get(i).getStationCode());
                        sb.append(") ");
                        sb.append(" METRO");

                        System.out.println(sb);

                        flag = true;
                    }
                }
            }

            if (!flag) {
                System.out.println("TMB is doing its best to make the bus and subway arrive here.");
            }
        }
    }

    public void planRoute(){

        String origin = null;
        String destination = null;
        String depOrArrival = null;
        boolean boolDepOrA = false;
        boolean tof = false;
        String day = null;
        String hour = null;
        int maxWalkingDist = 0;


        System.out.println("");
        System.out.println("Origin? (lat,long / destination)");
        origin = scanner.nextLine();

        /*
        tof = checkIfOriginOrDestValid(origin);
        while(!tof){
            tof = checkIfOriginOrDestValid(origin);
        }

         */

        System.out.println("");
        System.out.println("Destination? (lat,lon / location name)");
        destination = scanner.nextLine();
        /*
        TODO: IF LOCATION NAME INPUT, GET COORDINATES
        tof = checkIfOriginOrDestValid(destination);
        while(!tof){
            tof = checkIfOriginOrDestValid(destination);
        }

         */

        System.out.println("");
        System.out.println("Departure or arrival? (d/a)");
        /*
        tof = checkifDepOrArrivalValid(depOrArrival);
        while(!tof){
            tof = checkifDepOrArrivalValid(depOrArrival);
        }

         */
        depOrArrival = scanner.nextLine();

        if(depOrArrival.equalsIgnoreCase("a")){
            boolDepOrA = true;
        }
        else{
            boolDepOrA = false;
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


        Route possibleRoutes = api.plannerAPI(origin,destination, day,  hour, boolDepOrA, maxWalkingDist);
        if (possibleRoutes != null){

        user.pastRoutes.add(possibleRoutes);

        System.out.println("");
        System.out.println("Fastest Combination:");
        System.out.println("\tTime taken: " + possibleRoutes.getTimeTaken()/60 + " min");
        System.out.println("\tOrigin");
        System.out.println("\t|");
        for (int i = 0; i <possibleRoutes.getRouteLegs().size() ; i++) {
            if(possibleRoutes.getRouteLegs().get(i) instanceof Transit){

                int timeMin = calculateTimeInMinutes(possibleRoutes.getRouteLegs().get(i));
                System.out.println("\t"+ ((Transit) possibleRoutes.getRouteLegs().get(i)).getLine_name()
                        +" " + ((Transit)possibleRoutes.getRouteLegs().get(i)).getFrom_name()+" "+ "("+
                        ((Transit) possibleRoutes.getRouteLegs().get(i)).getFrom_stopcode()+")"+"->"
                        +" " + ((Transit) possibleRoutes.getRouteLegs().get(i)).getTo_name()+" "+ "("+
                        ((Transit) possibleRoutes.getRouteLegs().get(i)).getTo_stopcode()+")"+" "
                        +timeMin+" min");

                System.out.println("\t|");

            }
            else{

                int timeMin = calculateTimeInMinutes(possibleRoutes.getRouteLegs().get(i));
                System.out.println("\t"+possibleRoutes.getRouteLegs().get(i).getMode()+" "+timeMin+" min");
                System.out.println("\t|");
            }
        }

        System.out.println("\tDestination");
        System.out.println("");
        }

    }

    public void userRoutes(){

        if(user.pastRoutes.isEmpty()){
            System.out.println("You have not made any route :(");
            System.out.println("To search for one, access option 3 on the principal menu.");
        }
        else{

            for (int i = 0; i < user.pastRoutes.size(); i++) {
                int num = i+1;
                System.out.println("");
                System.out.println("->Route "+num+":");
                System.out.println("\t-Origin: "+user.pastRoutes.get(i).getOrigin());
                System.out.println("\t-Destination: "+user.pastRoutes.get(i).getDestination());
                System.out.println("\t-Date and Time: "+user.pastRoutes.get(i).getDate()+" at "+user.pastRoutes.get(i).getTime());
                System.out.println("\t-Max Walking Distance: "+user.pastRoutes.get(i).getMaxWalkingDistance());
                System.out.println("\t-Fastest Combination: ");
                System.out.println("\t\tTime Taken: " + user.pastRoutes.get(i).getTimeTaken()/60 + " min");
                System.out.println("\t\tOrigin");
                System.out.println("\t\t|");

                for (int j = 0; j <user.pastRoutes.get(i).getRouteLegs().size(); j++) {
                    if(user.pastRoutes.get(i).getRouteLegs().get(j) instanceof Transit){

                        int timeMin = calculateTimeInMinutes(user.pastRoutes.get(i).getRouteLegs().get(j));
                        System.out.println("\t\t"+ ((Transit) user.pastRoutes.get(i).getRouteLegs().get(j)).getLine_name()
                                +" " + ((Transit) user.pastRoutes.get(i).getRouteLegs().get(j)).getFrom_name()+" "+ "("+
                                ((Transit) user.pastRoutes.get(i).getRouteLegs().get(j)).getFrom_stopcode()+")"+"->"
                                +" " + ((Transit) user.pastRoutes.get(i).getRouteLegs().get(j)).getTo_name()+" "+ "("+
                                ((Transit) user.pastRoutes.get(i).getRouteLegs().get(j)).getTo_stopcode()+")"+" "
                                +timeMin+" min");

                        System.out.println("\t\t|");

                    }
                    else{

                        int timeMin = calculateTimeInMinutes(user.pastRoutes.get(i).getRouteLegs().get(j));
                        System.out.println("\t\t"+user.pastRoutes.get(i).getRouteLegs().get(j).getMode()+" "+timeMin+" min");
                        System.out.println("\t\t|");

                    }


                }

                System.out.println("\t\tDestination");
                System.out.println("");

            }

        }
    }

    private int calculateTimeInMinutes(Leg leg){
        int total_time=0;

        total_time = (int) (((leg.getEnd_time() -leg.getStart_time())/1000)/60);

        return total_time;
    }

    private boolean checkIfOriginOrDestValid(String name){
        if(scanner.hasNextLine() && (validLocationName(name, allLocations))){
            name = scanner.nextLine();
            scanner.nextLine();
            return true;
        }

        else{
            scanner.nextLine();
            System.out.println("");
            System.out.println("Sorry, this location is not valid :(");
            System.out.println("");
            System.out.println("Origin? (lat,long / destination)");
            return false;
        }
    }

    private boolean checkNextInt(int nextInt){
        if(scanner.hasNextInt()){
            nextInt = scanner.nextInt();
            scanner.nextLine();
            return true;
        }

        else{
            scanner.nextLine();
            System.out.println("");
            System.out.println("Error! Please enter an int");
            return false;
        }
    }

    private boolean checkYesOrNo(String yesorno){

        if(!yesorno.equalsIgnoreCase("no") && !yesorno.equalsIgnoreCase("yes")){
            System.out.println("");
            System.out.println("Error! you must enter yes or no!");
            return false;
        }

        else{
            return true;
        }
    }


    private double distanceInKmBetweenEarthCoordinates(double[] long_lat1, double[] long_lat2) {
        int earthRadiusM = 6371000;

        double latDistance = degreesToRadians(long_lat2[1]-long_lat1[1]);
        double lonDistance = degreesToRadians(long_lat2[0]-long_lat1[0]);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(degreesToRadians(long_lat1[1])) * Math.cos(degreesToRadians(long_lat2[1])) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadiusM * c;
    }

    private double degreesToRadians(double degrees) {
        return degrees * (Math.PI/180);
    }


    public void whichOptionM1(int option){

        if(option == 2){
            searchLocation(); //error handling ok
        }

        else if(option == 3){
            planRoute(); //somewhat okay --> checking if return error is okay, but not individually input information

        }
        else if(option == 4){

            getBusWaitTime();//error handling ok


        }
        else if(option == 5){
            System.out.println("Thanks for using our program!");
        }
    }

    public void whichOptionM2(String option){

        if(option.equalsIgnoreCase("a")){
            listLocations(); //error handling ok
        }

        else if(option.equalsIgnoreCase("b")){

            locationHistory(); //error handling ok

        }
        else if(option.equalsIgnoreCase("c")){

            userRoutes(); //error handling ok

        }

        else if(option.equalsIgnoreCase("d")){
            showCloseStations(); //??
        }

        else if(option.equalsIgnoreCase("e")){
            stationsInauguratedByBirthYear(); //?
        }

        else if(option.equalsIgnoreCase("f")){
            System.out.println();
        }
    }

}