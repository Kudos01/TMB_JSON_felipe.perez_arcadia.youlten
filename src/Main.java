//package com.company;

import WebServices.API;
import WebServices.Route;

import java.io.IOException;

// APP ID AND APP KEY
// app_id=41936f32&
// app_key=3c5639afc8280c17cb4f633b78de717b

public class Main {

    public static void main(String[] args){

        boolean endProg;
        //create a new logic object so we can get the user information properly
        Logic logic = new Logic();
        //create a new menu object so we can display the correct options
        Menu menu = new Menu();

        //load the json information so we can use it later in the program
        endProg = logic.loadData();
        if(!endProg){

            //ask the user for their information so we can use it later
            logic.Intro();


            do{
                //print the first menu and ask the user what they want to do
                menu.printMenu1();
                menu.askForOption();

                //if the user does not input a valid option
                while(!menu.validOption1()){
                    System.out.println("Please Insert a valid option");
                    //re-print the menu and ask again
                    menu.printMenu1();
                    menu.askForOption();
                }

                //choose which function to run depending on what option the user chose
                logic.whichOptionM1(menu.getOption1());

                //if the user chose option 1, display the second menu
                if(menu.getOption1() == 1) {
                    do {
                        //display menu 2 and ask the user for their option
                        menu.printMenu2();
                        menu.askForOptionString();

                        //if the user enters an invalid option
                        while (!menu.validOption2()){
                            //print an error message and ask for the option again
                            System.out.println("Please Insert a valid option");
                            menu.printMenu2();
                            menu.askForOptionString();
                        }

                        //choose which function to run depending on what the user inputs
                        logic.whichOptionM2(menu.getOption2());

                    }while (!menu.getOption2().equalsIgnoreCase("f"));
                }
            }while(!menu.exitMenu1());

        }
        }

    }
