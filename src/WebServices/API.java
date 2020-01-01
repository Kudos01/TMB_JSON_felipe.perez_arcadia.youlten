package WebServices;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;


public class API {

    private OkHttpClient client = new OkHttpClient();

    public ArrayList<BusStation> loadBusStations(){

        //request information from tmb with the following url
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/parades?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();
        ArrayList<BusStation> busStations = new ArrayList<>();

        try{
            //get the response from the API
            Response response = client.newCall(request).execute();
            String jsonData = null;
            //if the information is not null
            if(response.body() != null){
                //store the information from the response in a string
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            //convert the JSON string to a json object
            JsonObject busStationTemp = gson.fromJson(jsonData, JsonObject.class);

            //for all of the features
            for (int i = 0; i < busStationTemp.get("features").getAsJsonArray().size(); i++) {
                //store all of the bus objects into an arraylist of objects
                BusStation bus = new BusStation(busStationTemp.get("features").getAsJsonArray().get(i).getAsJsonObject());
                busStations.add(bus);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        //return all buses
        return busStations;
    }


    public ArrayList<MetroStation> loadMetroStations() {
        //request information from tmb with the following url
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/linies/metro/estacions?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();
        ArrayList<MetroStation> metroStations = new ArrayList<>();

        try{
            //get the response from the API
            Response response = client.newCall(request).execute();
            String jsonData = null;
            //if the information is not null
            if(response.body() != null){
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            JsonObject metroStationTemp = gson.fromJson(jsonData, JsonObject.class);

            for (int i = 0; i < metroStationTemp.get("features").getAsJsonArray().size(); i++) {
                //add all metro stations to an arraylist
                MetroStation ms = new MetroStation(metroStationTemp.get("features").getAsJsonArray().get(i).getAsJsonObject());
                metroStations.add(ms);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return metroStations;
    }

    public ArrayList<iBus> getBusWaitTime(int stopCode){


        //build the url from the user request
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.tmb.cat/v1/ibus/stops/");
        sb.append(stopCode);
        sb.append("?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b");
        String url = sb.toString();
        ArrayList<iBus> closeBuses = new ArrayList<>();

        Request request = new Request.Builder().url(url).build();

        try {

            Response response = client.newCall(request).execute();
            String jsonData = null;
            if (response.body() != null) {
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            JsonObject ibus = gson.fromJson(jsonData, JsonObject.class);


            for (int i = 0; i < ibus.get("data").getAsJsonObject().get("ibus").getAsJsonArray().size(); i++) {
                iBus ib = new iBus(ibus.get("data").getAsJsonObject().get("ibus").getAsJsonArray().get(i).getAsJsonObject());
                //get all information about all close buses, and store it an arraylist of close busses
                closeBuses.add(ib);
            }

        }catch(IOException e){

            e.printStackTrace();

        }
        return closeBuses;
    }

    public Route plannerAPI(String origin, String destination, String date, String time, boolean dep_or_arrival, int maxWalkDistance){

            //build the url of the request with the user input information
            StringBuilder sb = new StringBuilder();
            sb.append("https://api.tmb.cat/v1/planner/plan?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b&");
            sb.append("fromPlace=").append(origin).append("&");
            sb.append("toPlace=").append(destination).append("&");
            sb.append("date=").append(date).append("&");
            sb.append("time=").append(time).append("&");
            sb.append("arriveBy=").append(dep_or_arrival).append("&");
            sb.append("mode=TRANSIT,WALK&");
            sb.append("maxWalkDistance=").append(maxWalkDistance).append("&");
            sb.append("showIntermediateStops=TRUE");
            String url = sb.toString();

            System.out.println(url);
            //request with url generated from user input
            Request request = new Request.Builder().url(url).build();

            Route fastest = new Route();

            try {
                Response response = client.newCall(request).execute();
                String jsonData = null;
                if (response.body() != null) {
                    jsonData = response.body().string();
                }

                Gson gson = new Gson();
                JsonObject routePlans = gson.fromJson(jsonData, JsonObject.class);

                //if the api returns a json object that has an error
                if(routePlans.has("error")){
                    //print an error
                    System.out.println("Error! one of the parameters is incorrect :(");
                    fastest = null;
                }
                else {
                    //get the json array with the information that we need
                    JsonArray plan = gson.fromJson(routePlans.get("plan").getAsJsonObject().get("itineraries").getAsJsonArray(), JsonArray.class);

                    int shortest = plan.get(0).getAsJsonObject().get("duration").getAsInt();
                    int pos = 0;
                    ArrayList<Leg> legs = new ArrayList<>();
                    int flag=0;

                    for (int i = 0; i < plan.size(); i++) {

                        if (maxWalkDistance <= plan.get(i).getAsJsonObject().get("walkDistance").getAsDouble()) {
                            flag =1;
                            if (shortest > plan.get(i).getAsJsonObject().get("duration").getAsInt()) {
                                shortest = plan.get(i).getAsJsonObject().get("duration").getAsInt();
                                pos = i;
                            }

                        }

                    }
                    /*
                    if(flag == 0){
                        System.out.println("Error! all routes exceed max walk distance. Saving shortest time instead...");
                    }
                    for (int i = 0; i < plan.size(); i++) {
                        if (shortest > plan.get(i).getAsJsonObject().get("duration").getAsInt()) {
                            shortest = plan.get(i).getAsJsonObject().get("duration").getAsInt();
                            pos = i;
                        }
                    }

                     */

                    for (int i = 0; i < plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().size(); i++) {

                        //if the method of transit is 'walk
                        if ("WALK".equalsIgnoreCase(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject().get("mode").getAsString())) {

                            //create a new walking object
                            Walk walk = new Walk(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                            //add this part of the journey to steps
                            legs.add(walk);

                        } else {
                            //create a new transit object
                            Transit transit = new Transit(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                            legs.add(transit);
                        }

                    }
                    //get the time taken as it's own information
                    int timeTaken = plan.get(pos).getAsJsonObject().get("duration").getAsInt();

                    //store it in the route
                    fastest = new Route(plan.get(pos).getAsJsonObject(), legs, date, time, maxWalkDistance, origin, destination, timeTaken);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                return fastest;
            }
    }
}
