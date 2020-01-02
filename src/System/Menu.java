package System;

import java.util.Scanner;

/**
 * Represents the methods to print out all the different menu options and if the inputs were correct or not
 */

public class Menu {

    //Different class fields for printing and showing the user different information

    private static final int MIN1 = 1;
    private static final int MAX1 = 5;

    private static final String select = "Select an option: ";

    public static final String intro = "Welcome to the TMBJson application! Please enter the requested information.";
    public static final String userinfo1 = "Username: ";
    public static final String userinfo2 = "E-mail: ";
    public static final String userinfo3 = "Birth Year: ";
    public static final String flagvalid = "The information has been successfully registered! ";

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

    /**
     * Creates a new menu object with a scanner and changes the default value for option 1
     */

    public Menu() {
        scanner = new Scanner(System.in);
        option1 = -1;
    }

    /**
     * Prints the different options available for menu 1
     */

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

    /**
     * Prints the different options available for menu 2
     */

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

    /**
     * Checks if we should exit menu 1
     * @return
     */

    public boolean exitMenu1() { return option1 == MAX1; }

    /**
     * Asks for an int option (for menu 1)
     */

    public void askForOption() {

        if(scanner.hasNextInt()){
            option1 = scanner.nextInt();
            scanner.nextLine();
        }

        else{
            scanner.nextLine();
            option1 = 0;
        }
    }

    /**
     * Asks for an string option (for menu 2)
     */

    public void askForOptionString() {
        option2 = scanner.nextLine();
    }

    /**
     * Checks if the option entered is valid for menu 1
     * @return
     */

    public boolean validOption1() {
        return option1 >= MIN1 && option1 <= MAX1;
    }

    /**
     * Checks if the option entered is valid for menu 2
     * @return
     */

    public boolean validOption2() {
        return (option2.equalsIgnoreCase("a") ||option2.equalsIgnoreCase("b")|| option2.equalsIgnoreCase("c")|| option2.equalsIgnoreCase("d")|| option2.equalsIgnoreCase("e")|| option2.equalsIgnoreCase("f"));
    }

    /**
     * getter for option 1
     * @return
     */

    public int getOption1() {
        return option1;
    }

    /**
     * getter for option 2
     * @return
     */

    public String getOption2() {
        return option2;
    }
}