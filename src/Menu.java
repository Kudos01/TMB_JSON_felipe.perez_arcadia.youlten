import DataModel.User;

import java.util.Scanner;

public class Menu {

    private static final int MIN1 = 1;
    private static final String MIN2 = "a";
    private static final int MAX1 = 5;
    private static final String MAX2 = "f";

    private static final String select = "Select an option: ";

    private static final String intro = "Welcome to the TMBJson application! Please enter the requested information.";
    private static final String userinfo1 = "Username: ";
    private static final String userinfo2 = "E-mail: ";
    private static final String userinfo3 = "Birth Year: ";
    private static final String flagvalid = "The information has been successfully registered! ";

    private static final String option1menu1 = "1. User Management";
    private static final String option2menu1 = "2. Search Location";
    private static final String option3menu1 = "3. Plan a route";
    private static final String option4menu1 = "4. Bus wait time";

    private static final String optionamenu2 = "a) My Locations";
    private static final String optionbmenu2 = "b) Location History";
    private static final String optioncmenu2 = "c) My Routes";
    private static final String optiondmenu2 = "d) Favorite stops and stations";
    private static final String optionemenu2 = "e) Stations inaugurated by my birth year";


    private static final String exitmenu2 = "f) Back to the principal menu";
    private static final String exitmenu1 = "5: Exit";

    private int option1;
    private String option2;
    private Scanner scanner;
    private Logic logic = new Logic();

    public Menu() {
        scanner = new Scanner(System.in);
        option1 = -1;
    }


    public void printIntro(){

        String username;
        String email;
        Integer birthday;

        System.out.println("");
        System.out.println(intro);
        System.out.println(userinfo1);
        username = scanner.nextLine();

        System.out.println(userinfo2);
        email = scanner.nextLine();

        System.out.println(userinfo3);
        birthday = scanner.nextInt();

        System.out.println(flagvalid);
        System.out.println("");

        logic.userAssignInfo(username, email, birthday);

    }

    //prints all of the information related to menu2
    public void printMenu1() {
        System.out.println("");
        System.out.println(option1menu1);
        System.out.println(option2menu1);
        System.out.println(option3menu1);
        System.out.println(option4menu1);
        System.out.println(exitmenu1);
        System.out.println("");
        System.out.println(select);
    }

    //prints all of the info related to menu1
    public void printMenu2() {
        System.out.println("");
        System.out.println(optionamenu2);
        System.out.println(optionbmenu2);
        System.out.println(optioncmenu2);
        System.out.println(optiondmenu2);
        System.out.println(optionemenu2);
        System.out.println(exitmenu2);
        System.out.println("");
        System.out.println(select);
    }

    public boolean exitMenu1() { return option1 == MAX1; }
    public boolean exitMenu2() { return option2.equalsIgnoreCase(MAX2); }


    public void askForOption() {

        if(scanner.hasNextInt()){
            option1 = scanner.nextInt();
        }

        else{
            scanner.next();
            option1 = 0;
        }
    }

    public void askForOptionString() {
        option2 = scanner.next();
    }


    public void whichOptionM1(int option){

        if(option == 2){
            logic.searchLocation();
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
            logic.listLocations(User.userLocations);
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


    public boolean validOption1() {
        return option1 >= MIN1 && option1 <= MAX1;
    }
    public boolean validOption2() {
        return (option2.equalsIgnoreCase("a") ||option2.equalsIgnoreCase("b")|| option2.equalsIgnoreCase("c")|| option2.equalsIgnoreCase("d")|| option2.equalsIgnoreCase("e")|| option2.equalsIgnoreCase("f"));
    }

    public int getOption1() {
        return option1;
    }
    public String getOption2() {
        return option2;
    }

}
