package Exceptions;

public class noStationsBirthdayException extends Exception {

    public void printErrorMessage(){
        System.out.println("No subway station opened your birth year :(");
    }

}