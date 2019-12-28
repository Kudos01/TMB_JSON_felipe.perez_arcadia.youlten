//package com.company;

import DataModel.*;
import GUI_and_Menu_logic.Menu;

import java.io.IOException;

// APP ID AND APP KEY
// app_id=41936f32&
// app_key=3c5639afc8280c17cb4f633b78de717b

public class Main {
    public static void main(String[] args) throws IOException {

        //TODO: goal for next couple sessions -> program various menu options
        //start with whatever you wish to start with, i've added some menu stuff, so it should be more or less straight
        //forward to try and implement stuff.
        //see the other files for the other todos;


        //love u <3 :kiss:

        //Parser parse = new Parser();
        //parse the json file localizations
        //parse.parseLocation();
        Menu menu = new Menu();

        menu.printIntro();

        do{
                menu.printMenu1();
                menu.askForOption();

                while(!menu.validOption1()){
                    System.out.println("Please Insert a valid option");
                    menu.printMenu1();
                    menu.askForOption();
                }

                menu.whichOptionM1(menu.getOption1());

            if(menu.getOption1() == 1) {
                do {
                    menu.printMenu2();
                    menu.askForOptionString();

                    while (!menu.validOption2()){
                        System.out.println("Please Insert a valid option");
                        menu.printMenu2();
                        menu.askForOptionString();
                    }

                    menu.whichOptionM2(menu.getOption2());
                }while (!menu.getOption2().equals("f"));
            }

        }while(!menu.exitMenu1());




        /*
        //https://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //change url -- needs to return json
                .url(  "https://api.tmb.cat/v1/transit/parades?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b")
                .build();
        try{

            Response response = client.newCall(request).execute();
            String jsonData = null;
            if(response.body() != null){

                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            Type metroLineType = new TypeToken<ArrayList<MetroLines>>(){}.getType();

            ArrayList<MetroLines> ml = gson.fromJson(jsonData, metroLineType);

            //System.out.println(ml);

        }catch (IOException e){
            e.printStackTrace();
        }
        */





        }

    }
