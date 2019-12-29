package Web_Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

 /*
        //https://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html
        &filter=DATA_INAUGURACIO=1999
        System.out.println(metroLines.get("features").getAsJsonArray().get(0).getAsJsonObject().get("properties").getAsJsonObject().get("ID_PARADA"));
        */

public class API {

    private OkHttpClient client = new OkHttpClient();

    public ArrayList<BusStation> loadBusStations(){

        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/parades?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();
        ArrayList<BusStation> busStations = new ArrayList<>();

        try{
            Response response = client.newCall(request).execute();
            String jsonData = null;
            if(response.body() != null){
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            //Type metroLineType = new TypeToken<ArrayList<MetroLine>>(){}.getType();

            JsonObject busStationTemp = gson.fromJson(jsonData, JsonObject.class);

            //System.out.println(metroStation.get("features").getAsJsonArray().get(0).getAsJsonObject().get("properties").getAsJsonObject().get("ID_PARADA"));

            for (int i = 0; i < busStationTemp.get("features").getAsJsonArray().size(); i++) {
                BusStation bus = new BusStation(busStationTemp.get("features").getAsJsonArray().get(i).getAsJsonObject());
                busStations.add(bus);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return busStations;
    }


    public ArrayList<MetroStation> loadMetroStations() {
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/linies/metro/estacions?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();
        ArrayList<MetroStation> metroStations = new ArrayList<>();

        try{
            Response response = client.newCall(request).execute();
            String jsonData = null;
            if(response.body() != null){
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            //Type metroLineType = new TypeToken<ArrayList<MetroLine>>(){}.getType();

            JsonObject metroStationTemp = gson.fromJson(jsonData, JsonObject.class);

            //System.out.println(metroStation.get("features").getAsJsonArray().get(0).getAsJsonObject().get("properties").getAsJsonObject().get("ID_PARADA"));

            for (int i = 0; i < metroStationTemp.get("features").getAsJsonArray().size(); i++) {
                MetroStation ms = new MetroStation(metroStationTemp.get("features").getAsJsonArray().get(i).getAsJsonObject());
                metroStations.add(ms);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return metroStations;
    }

    public void testAPI(){

        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/parades?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();

        try{
            Response response = client.newCall(request).execute();
            String jsonData = null;
            if(response.body() != null){

                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            //Type metroLineType = new TypeToken<ArrayList<MetroLine>>(){}.getType();

            ArrayList<MetroStation> metroStations = new ArrayList<>();
            JsonObject metroStationTemp = gson.fromJson(jsonData, JsonObject.class);

            //System.out.println(metroStation.get("features").getAsJsonArray().get(0).getAsJsonObject().get("properties").getAsJsonObject().get("ID_PARADA"));

            for (int i = 0; i < metroStationTemp.get("features").getAsJsonArray().size(); i++) {
                MetroStation ml = new MetroStation(metroStationTemp.get("features").getAsJsonArray().get(i).getAsJsonObject());
                metroStations.add(ml);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<BusLine> getStops(Integer stopCode){

        StringBuilder sb = new StringBuilder();
        sb.append("https://api.tmb.cat/v1/ibus/stops/");
        sb.append(stopCode);
        sb.append("?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b");
        String url = sb.toString();

        Request request = new Request.Builder().url(url).build();
        ArrayList<BusLine> closeBuses = new ArrayList<>();

        //check bus lines + api

        /*



         */

        return closeBuses;

    }


}
