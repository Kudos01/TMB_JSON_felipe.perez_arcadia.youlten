import java.util.Scanner;

public class Menu {

    private static final int MIN = 1;
    private static final int MAX1 = 5;
    private static final int MAX2 = 4;

    private static final String option1menu2 = "1: Sort teams by winrate";
    private static final String option2menu2 = "2: Sort players by nationality";
    private static final String option3menu2 = "3: Sort players by their KDA and winrate";

    private static final String method1menu1 = "1: Sort using Quicksort";
    private static final String method2menu1 = "2: Sort using Mergesort";
    private static final String method3menu1 = "3: Sort using Bucketsort";
    private static final String method4menu1 = "4: Sort using Radixsort";

    private static final String exitmenu2 = "4: Go back";
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
        System.out.println(option1menu2);
        System.out.println(option2menu2);
        System.out.println(option3menu2);
        System.out.println(exitmenu2);
        System.out.println("");

    }

    //prints all of the info related to menu1
    public void printMenu1() {
        System.out.println("");
        System.out.println(method1menu1);
        System.out.println(method2menu1);
        System.out.println(method3menu1);
        System.out.println(method4menu1);
        System.out.println(exitmenu1);
        System.out.println("");
    }

    public boolean exitMenu1() { return option == MAX1; }
    public boolean exitMenu2() { return option == MAX2; }

    public void askForOption() {
        System.out.println(ask);
        option = scanner.nextInt();
    }

    public boolean validOption1() {
        return option >= MIN && option <= MAX1;
    }
    public boolean validOption2() {
        return option >= MIN && option <= MAX2;
    }

    public int getOption() {
        return option;
    }

}
