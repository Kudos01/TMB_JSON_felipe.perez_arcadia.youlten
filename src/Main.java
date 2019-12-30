//package com.company;

import WebServices.API;

import java.io.IOException;

// APP ID AND APP KEY
// app_id=41936f32&
// app_key=3c5639afc8280c17cb4f633b78de717b

public class Main {

    public static void main(String[] args) throws IOException {

        //TODO: goal for next couple sessions -> program various menu options
        //TODO: add exception prevention for
        //start with whatever you wish to start with, i've added some menu stuff, so it should be more or less straight
        //forward to try and implement stuff.
        //see the other files for the other todos;


        //love u <3 :kiss:
        //Love u too babe :KISSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS & Hug & CUUUUUDDDLEEEEE:

        //parse the json file localizations

        Logic logic = new Logic();
        Menu menu = new Menu();

        logic.loadData();


        logic.Intro();

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


        }

    }
