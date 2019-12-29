package Exceptions;

public class StationNotFoundByYearException extends Exception {

    public void printErrorMessage(){
        System.out.println("No subway station opened your birth year :(");
    }

}