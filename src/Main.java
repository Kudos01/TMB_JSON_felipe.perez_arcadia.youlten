//package com.company;

import DataModel.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

            int i=0;
            //make a new type token of type teams, so that we can parse all the teams from the json to an arraylist of type Team
            //Type monumentListType = new TypeToken<ArrayList<Monument>>(){}.getType();
            //Type hotelListType = new TypeToken<ArrayList<Hotel>>(){}.getType();
            //Type restaurantListType = new TypeToken<ArrayList<Restaurant>>(){}.getType();
            //Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();

            Type genericListType = new TypeToken<ArrayList<temp>>(){}.getType();
            ArrayList<temp> generics = gson.fromJson(reader, genericListType);




        }catch (IOException e) {
            e.printStackTrace();
            }

        }

    }
