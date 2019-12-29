package Exceptions;

public class invalidStopCodeException extends Exception {
    public void printErrorMessage(){
        System.out.println("Error, stop code not valid!");
    }
}
