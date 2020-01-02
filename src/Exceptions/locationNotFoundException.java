package Exceptions;

/**
 *  Exception notifying that the location was not found
 */

public class locationNotFoundException extends Exception {

    /**
     * Method to print display an error message stating the cause of the exception
     */

    public void printErrorMessage(){
        System.out.println("Sorry, there is no location with this name");
    }
}
