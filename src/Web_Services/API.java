package Web_Services;

import Exceptions.noStationsBirthdayException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

 /*
        //https://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html
        &filter=DATA_INAUGURACIO=1999
        */

public class API {



        public void testAPI(){

            OkHttpClient client = new OkHttpClient();
            //
            Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/parades?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();

            try{

                Response response = client.newCall(request).execute();
                String jsonData = null;
                if(response.body() != null){

                    jsonData = response.body().string();
                }

                Gson gson = new Gson();

                //Type metroLineType = new TypeToken<ArrayList<MetroLines>>(){}.getType();

                //ArrayList<MetroLines> ml = gson.fromJson(jsonData, metroLineType);
                JsonObject metroLines = gson.fromJson(jsonData, JsonObject.class);

                System.out.println(metroLines);

            }catch (IOException e){
                e.printStackTrace();
            }

        }

        public ArrayList<MetroStation> apiGetStationInauguration(int birth_year) throws noStationsBirthdayException {

            ArrayList<MetroStation> inauguratedStations = new ArrayList<>();

            /*
            for (int i = 0; i < ; i++) {

                if( birth_year == ){

                inauguratedStations.add();

                }

                //check all stations on all lines if they were inaugurated in year
            }

            return inauguratedStations;

             */
            if(inauguratedStations.isEmpty()){

                throw new noStationsBirthdayException();
            }

            return inauguratedStations;

        }


}
