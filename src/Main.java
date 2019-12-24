//package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

// APP ID AND APP KEY
// app_id=41936f32&
// app_key=3c5639afc8280c17cb4f633b78de717b

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

                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            Type metroLineType = new TypeToken<ArrayList<MetroLines>>(){}.getType();

            ArrayList<MetroLines> ml = gson.fromJson(jsonData, metroLineType);

            //System.out.println(ml);

        }catch (IOException e){
            e.printStackTrace();
        }





    }
}
