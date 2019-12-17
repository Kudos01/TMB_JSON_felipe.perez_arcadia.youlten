import java.util.Scanner;

public class Menu {

    private static final int MIN1 = 1;
    private static final char MIN2 = 'a';
    private static final int MAX1 = 5;
    private static final char MAX2 = 'f';

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
    private static final String ask = "Select an option: ";


    private int option;
    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
        option = -1;
    }

    //prints all of the information related to menu2
    public void printMenu2() {
        System.out.println("");
        System.out.println(option1menu1);
        System.out.println(option2menu1);
        System.out.println(option3menu1);
        System.out.println(option4menu1);
        System.out.println(exitmenu1);
        System.out.println("");

    }

    //prints all of the info related to menu1
    public void printMenu1() {
        System.out.println("");
        System.out.println(optionamenu2);
        System.out.println(optionbmenu2);
        System.out.println(optioncmenu2);
        System.out.println(optiondmenu2);
        System.out.println(optionemenu2);
        System.out.println(exitmenu2);
        System.out.println("");
    }

    public boolean exitMenu1() { return option == MAX1; }
    public boolean exitMenu2() { return option == MAX2; }

    public void askForOption() {
        System.out.println(ask);
        option = scanner.nextInt();
    }

    public boolean validOption1() {
        return option >= MIN1 && option <= MAX1;
    }
    public boolean validOption2() {

        return option >= MIN2 && option <= MAX2;
    }

    public int getOption() {
        return option;
    }

}
