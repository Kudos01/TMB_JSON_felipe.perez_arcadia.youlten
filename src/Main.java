//package com.company;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

// APP ID AND APP KEY
// app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b

public class Main {
    public static void main(String[] args) throws IOException {


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

                jsonData = responses.body().string();
            }

            Gson gson = new Gson();

            //TODO: TYPE TOKEN FOR MetroLines
            MetroLines ml = gson.fromJson(jsonData, MetroLines.class);

            System.out.println(ml);

        }catch (IOException e){
            e.printStackTrace();
        }



    }
}
