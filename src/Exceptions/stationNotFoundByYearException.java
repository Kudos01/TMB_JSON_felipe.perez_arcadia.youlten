package Exceptions;

public class stationNotFoundByYearException extends Exception {

    public void printErrorMessage(){
        System.out.println("No subway station opened your birth year :(");
    }

}