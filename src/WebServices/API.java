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

    public void testAPI(String origin, String destination, String date, String time, boolean dep_or_arrival, int maxWalkDistance){


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

        //String aaaa = https://api.tmb.cat/v1/planner/plan?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b&fromPlace=41.403475,2.174400&toPlace=41.386878,2.159704&date=12-31-2019&time=03:45pm&arriveBy=false&mode=TRANSIT,WALK&maxWalkDistance=500&showIntermediateStops=true
        String url = sb.toString();
        System.out.println(url);

        Request request = new Request.Builder().url(url).build();

        try{
            Response response = client.newCall(request).execute();
            String jsonData = null;
            if(response.body() != null){
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            JsonObject routePlans = gson.fromJson(jsonData, JsonObject.class);
            JsonArray plan = gson.fromJson(routePlans.get("plan").getAsJsonObject().get("itineraries").getAsJsonArray(), JsonArray.class);

            int shortest = plan.get(0).getAsJsonObject().get("duration").getAsInt();
            int pos = 0;
            ArrayList<Leg> legs = new ArrayList<>();


            for (int i = 0; i <plan.size() ; i++) {

                if(maxWalkDistance <= plan.get(i).getAsJsonObject().get("duration").getAsDouble()){

                    if(shortest > plan.get(i).getAsJsonObject().get("duration").getAsInt() ){
                        shortest = plan.get(i).getAsJsonObject().get("duration").getAsInt();
                        pos = i;
                    }

                }

            }

            for (int i = 0; i <plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().size() ; i++) {

                if("WALK".equalsIgnoreCase(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject().get("mode").getAsString())){

                    Walk walk = new Walk(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                    legs.add(walk);

                }
                else{
                    Transit transit = new Transit(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                    legs.add(transit);
                }

            }

            Route fastest = new Route(plan.get(pos).getAsJsonObject(),legs, date, time, maxWalkDistance,origin,destination);

            /*
            System.out.println(fastest);
            System.out.println(fastest.getDestination());
            System.out.println(fastest.getMaxWalkingDistance());
            System.out.println(fastest.getDate());
            System.out.println(fastest.getOrigin());
            System.out.println(fastest.getTime());
            for (int i = 0; i <fastest.getRouteLegs().size() ; i++) {

                if(fastest.getRouteLegs().get(i) instanceof Walk){
                    System.out.println(fastest.getRouteLegs().get(i).getMode());
                    System.out.println(fastest.getRouteLegs().get(i).getStart_time());
                    System.out.println(fastest.getRouteLegs().get(i).getEnd_time());
                }
                else if(fastest.getRouteLegs().get(i) instanceof Transit){
                    System.out.println(fastest.getRouteLegs().get(i).getMode());
                    System.out.println(fastest.getRouteLegs().get(i).getStart_time());
                    System.out.println(fastest.getRouteLegs().get(i).getEnd_time());
                    System.out.println((((Transit) fastest.getRouteLegs().get(i)).getFrom_name()));
                    System.out.println((((Transit) fastest.getRouteLegs().get(i)).getFrom_stopcode()));
                    System.out.println((((Transit) fastest.getRouteLegs().get(i)).getTo_name()));
                    System.out.println((((Transit) fastest.getRouteLegs().get(i)).getTo_stopcode()));
                    System.out.println((((Transit) fastest.getRouteLegs().get(i)).getLine_name()));

                }


            }

             */



        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<iBus> getBusWaitTime(int stopCode){


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
                closeBuses.add(ib);
            }

        }catch(IOException e){

            e.printStackTrace();

        }
        return closeBuses;
    }

    public Route plannerAPI(String origin, String destination, String date, String time, boolean dep_or_arrival, int maxWalkDistance){


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

        //String aaaa = https://api.tmb.cat/v1/planner/plan?app_id=41936f32&app_key=3c5639afc8280c17cb4f633b78de717b&fromPlace=41.403475,2.174400&toPlace=41.386878,2.159704&date=12-31-2019&time=03:45pm&arriveBy=false&mode=TRANSIT,WALK&maxWalkDistance=500&showIntermediateStops=true
        String url = sb.toString();
        System.out.println(url);

        Request request = new Request.Builder().url(url).build();

        try{
            Response response = client.newCall(request).execute();
            String jsonData = null;
            if(response.body() != null){
                jsonData = response.body().string();
            }

            Gson gson = new Gson();

            JsonObject routePlans = gson.fromJson(jsonData, JsonObject.class);
            JsonArray plan = gson.fromJson(routePlans.get("plan").getAsJsonObject().get("itineraries").getAsJsonArray(), JsonArray.class);

            int shortest = plan.get(0).getAsJsonObject().get("duration").getAsInt();
            int pos = 0;
            ArrayList<Leg> legs = new ArrayList<>();


            for (int i = 0; i <plan.size() ; i++) {

                if(maxWalkDistance <= plan.get(i).getAsJsonObject().get("duration").getAsDouble()){

                    if(shortest > plan.get(i).getAsJsonObject().get("duration").getAsInt() ){
                        shortest = plan.get(i).getAsJsonObject().get("duration").getAsInt();
                        pos = i;
                    }

                }

            }

            for (int i = 0; i <plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().size() ; i++) {

                if("WALK".equalsIgnoreCase(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject().get("mode").getAsString())){

                    Walk walk = new Walk(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                    legs.add(walk);

                }
                else{
                    Transit transit = new Transit(plan.get(pos).getAsJsonObject().get("legs").getAsJsonArray().get(i).getAsJsonObject());
                    legs.add(transit);
                }

            }

            Route fastest = new Route(plan.get(pos).getAsJsonObject(),legs, date, time, maxWalkDistance,origin,destination);

            System.out.println(fastest);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
