package WebServices;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class containing all the API functionalities and loading of WebServices data
 */

public class API {

    private OkHttpClient client = new OkHttpClient();

    /**
     * Method that loads all of the bus stops from the API call to the TMB web service.
     * If it fails, an error message will be printed and the ArrayList set to null.
     *
     * @return the ArrayList of the Bus stops loaded from the webservices call
     */

    public ArrayList<BusStop> loadBusStops(){

        //request information from tmb with the following url
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/parades?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b").build();
        ArrayList<BusStop> busStops = new ArrayList<>();

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
                BusStop bus = new BusStop(busStationTemp.get("features").getAsJsonArray().get(i).getAsJsonObject());
                busStops.add(bus);
            }

        }catch (IOException e){
            e.printStackTrace();
            busStops = null;
            System.out.println("Error, unable to get bus stops");
        }
        //return all buses
        return busStops;
    }

    /**
     * Method that loads all of the metro stations from the API call to the TMB web service.
     * If it fails, an error message will be printed and the ArrayList set to null.
     *
     * @return the ArrayList of the Metro stations loaded from the webservices call.
     */

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
            metroStations = null;
            System.out.println("Error, unable to get metro stations");
        }
        return metroStations;
    }

    /**
     * Method to get the wait time of the buses of a particular stop.
     *
     * @param stopCode  Code of the stop from which we will get the buses passing through it
     * @return          ArrayList of buses that will arrive to the stop. Empty if no buses are arriving to the stop.
     */

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

    /**
     * Requests the route info from the webservice based on what parameters are passed to it.
     * If the building of the route was successful, returns the constructed route.
     * If there was a problem building the route, the return Route is returned as null and an error is printed
     *
     * @param origin            String with the coordinates of the origin location
     * @param destination       String with the coordinates of the destination location
     * @param date              date of the trip
     * @param time              time of arrival or departure
     * @param dep_or_arrival    specifying if it is a departure or an arrival
     * @param maxWalkDistance   Maximum walking distance of the trip
     * @return                  Constructed Route
     */

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
                System.out.println(routePlans.get("error").getAsString());
                fastest = null;
            }
            else {
                //get the json array with the information that we need
                JsonArray plan = gson.fromJson(routePlans.get("plan").getAsJsonObject().get("itineraries").getAsJsonArray(), JsonArray.class);

                int shortest = plan.get(0).getAsJsonObject().get("duration").getAsInt();
                int pos = 0;
                ArrayList<Section> sections = new ArrayList<>();
                int flag=0;

                for (int i = 0; i < plan.size(); i++) {

                    if (plan.get(i).getAsJsonObject().get("walkDistance").getAsDouble() <= maxWalkDistance ) {
                        flag =1;
                        if (shortest > plan.get(i).getAsJsonObject().get("duration").getAsInt()) {
                            shortest = plan.get(i).getAsJsonObject().get("duration").getAsInt();
                            pos = i;
                        }

                    }

                }
                if(flag == 0){
                    System.out.println("Error! all routes exceed max walk distance. Saving shortest time instead...");
                }
                for (int i = 0; i < plan.size(); i++) {
                    if (shortest > plan.get(i).getAsJsonObject().get("duration").getAsInt()) {
                        shortest = plan.get(i).getAsJsonObject().get("duration").getAsInt();
                        pos = i;
                    }
                }


                for (int i = 0; i < plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().size(); i++) {

                    //if the method of transit is 'walk
                    if ("WALK".equalsIgnoreCase(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject().get("mode").getAsString())) {

                        //create a new walking object
                        Walk walk = new Walk(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                        //add this part of the journey to steps
                        sections.add(walk);

                    } else {
                        //create a new transit object
                        Transit transit = new Transit(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                        sections.add(transit);
                    }

                }
                //get the time taken as it's own information
                int timeTaken = plan.get(pos).getAsJsonObject().get("duration").getAsInt();

                //store it in the route
                fastest = new Route(sections, date, time, maxWalkDistance, origin, destination, timeTaken);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            return fastest;
        }
    }
}
