package Exceptions;

public class stopCodeInvalidException extends Exception {
    public void printErrorMessage(){
        System.out.println("Error, stop code not valid!");
    }
}
