//package com.company;

import DataModel.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

            LocationObj locOb = new LocationObj();
            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<Hotel> hotels = new ArrayList<>();
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            ArrayList<Monument> monuments = new ArrayList<>();
            Type tempListType = new TypeToken<ArrayList<temp>>(){}.getType();
            //temp temps = new temp();
            ArrayList<temp> temps = new ArrayList<>();

            //get the json object
            locOb = gson.fromJson(reader, LocationObj.class);
            //if you run locOb.getLocations() you get the json list of locations
            temps = gson.fromJson(locOb.getLocations(),tempListType);

            //System.out.println(temps);
            //System.out.println(generics);

            //the idea of this part is once you get here, there should be an arraylist of generics (see temp)
            for(int i=0; i< temps.size(); i++){

                Hotel tempH = new Hotel();
                Monument tempM = new Monument();
                Restaurant tempR = new Restaurant();
                Place tempP = new Place();

                if(temps.get(i).getStars() != null){

                    tempH.setName(temps.get(i).getName());
                    tempH.setCoordinates(temps.get(i).getCoordinates());
                    tempH.setDescription(temps.get(i).getDescription());
                    tempH.setStars(temps.get(i).getStars());

                    hotels.add(tempH);
                }
                else if(temps.get(i).getArchitect() != null){

                    tempM.setName(temps.get(i).getName());
                    tempM.setCoordinates(temps.get(i).getCoordinates());
                    tempM.setDescription(temps.get(i).getDescription());

                    monuments.add(tempM);


                }
                else if(temps.get(i).getCharacteristics() != null){

                    //if characteristics is not null
                    tempR.setName(temps.get(i).getName());
                    tempR.setCoordinates(temps.get(i).getCoordinates());
                    tempR.setDescription(temps.get(i).getDescription());

                     restaurants.add(tempR);

                }
                else{
                    //add to location
                    tempP.setName(temps.get(i).getName());
                    tempP.setCoordinates(temps.get(i).getCoordinates());
                    tempP.setDescription(temps.get(i).getDescription());

                    locations.add(tempP); //te

                }

            }

        }catch (IOException e) {
            e.printStackTrace();
            }

        }

    }
