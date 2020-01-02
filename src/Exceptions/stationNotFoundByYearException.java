package Exceptions;

/**
 *  Exception notifying that the station was not found with the year specified
 */

public class stationNotFoundByYearException extends Exception {

    /**
     * Method to print display an error message stating the cause of the exception
     */

    public void printErrorMessage(){
        System.out.println("No subway station opened your birth year :(");
    }

}