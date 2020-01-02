package Exceptions;

/**
 *  Exception notifying that the stop was not found with the stop code specified
 */

public class stopCodeInvalidException extends Exception {

    /**
     * Method to print display an error message stating the cause of the exception
     */

    public void printErrorMessage(){
        System.out.println("Error, stop code not valid!");
    }
}
