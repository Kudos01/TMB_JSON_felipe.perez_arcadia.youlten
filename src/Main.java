//package com.company;

import DataModel.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.source.tree.ArrayAccessTree;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

// APP ID AND APP KEY
// app_id=41936f32&
// app_key=3c5639afc8280c17cb4f633b78de717b

public class Main {
    public static void main(String[] args) throws IOException {

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


        Gson gson = new Gson();
        try(Reader reader = new FileReader("resources/localizations.json")) {

            //make a new type token of type teams, so that we can parse all the teams from the json to an arraylist of type Team
            //Type monumentListType = new TypeToken<ArrayList<Monument>>(){}.getType();
            //Type hotelListType = new TypeToken<ArrayList<Hotel>>(){}.getType();
            //Type restaurantListType = new TypeToken<ArrayList<Restaurant>>(){}.getType();
            //Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();

            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<Hotel> hotels = new ArrayList<>();
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            ArrayList<Monument> monuments = new ArrayList<>();

            Type genericListType = new TypeToken<ArrayList<temp>>(){}.getType();
            ArrayList<temp> generics = gson.fromJson(reader, genericListType);

            for(int i=0; i<generics.size(); i++){
            /*

            This section is commented as it's more of a guideline than what will actually be implemented
            I'm not sure 100% how to make it work, but the generics array should be full so


                if(){
                    //if stars is not null
                    //hotels.add(generics.get(i));
                }
                else if(){

                    //if architect is not null
                    ////monuments.add(generics.get(i));

                }
                else if(){

                    //if characteristics is not null
                    //restaurants.add(generics.get(i));

                }
                else{
                    //add to location
                    //locations.add(generics.get(i));
                }

             */

            }




        }catch (IOException e) {
            e.printStackTrace();
            }

        }

    }
