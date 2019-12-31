//package com.company;

import WebServices.API;
import WebServices.Route;

import java.io.IOException;

// APP ID AND APP KEY
// app_id=41936f32&
// app_key=3c5639afc8280c17cb4f633b78de717b

public class Main {

    public static void main(String[] args){

        //TODO: EXCEPTION PREVENTION

        Logic logic = new Logic();
        Menu menu = new Menu();

        logic.loadData();
        logic.Intro();

        logic.testapi();

        /*

        do{
            menu.printMenu1();
            menu.askForOption();

            while(!menu.validOption1()){
                System.out.println("Please Insert a valid option");
                menu.printMenu1();
                menu.askForOption();
            }

            logic.whichOptionM1(menu.getOption1());

            if(menu.getOption1() == 1) {
                do {
                    menu.printMenu2();
                    menu.askForOptionString();

                    while (!menu.validOption2()){
                        System.out.println("Please Insert a valid option");
                        menu.printMenu2();
                        menu.askForOptionString();
                    }

                    logic.whichOptionM2(menu.getOption2());
                }while (!menu.getOption2().equalsIgnoreCase("f"));
            }
        }while(!menu.exitMenu1());


         */
        }


    }
