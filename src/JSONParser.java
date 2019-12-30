import DataModel.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class JSONParser implements Parse{

    @Override
    public ArrayList<Location> parseLocations() {

        ArrayList<Location> allLocations = new ArrayList<>();

        Gson gson = new Gson();
        //TODO: check if this is sufficient for checking and exiting program with error message
        try (Reader reader = new FileReader("resources/localizations.json")) {

            //make a new type token of type teams, so that we can parse all the teams from the json to an arraylist of type Team

            LocationObj locOb;
            Type tempListType = new TypeToken<ArrayList<Generic>>(){}.getType();

            ArrayList<Generic> temp;

            //get the json object
            locOb = gson.fromJson(reader, LocationObj.class);
            //if you run locOb.getLocations() you get the json list of locations
            temp = gson.fromJson(locOb.getLocations(), tempListType);

            System.out.println(Arrays.toString(temp.get(0).getCoordinates()));

            //the idea of this part is once you get here, there should be an arraylist of generics (see temp)
            for (int i = 0; i < temp.size(); i++) {

                if (temp.get(i).getStars() != null) {
                    Hotel tempH = new Hotel(temp.get(i));
                    tempH.setStars(temp.get(i).getStars());
                    allLocations.add(tempH);

                } else if (temp.get(i).getArchitect() != null) {
                    Monument tempM = new Monument(temp.get(i));
                    tempM.setArchitect(temp.get(i).getArchitect());
                    tempM.setInauguration(temp.get(i).getInauguration());
                    allLocations.add(tempM);

                } else if (temp.get(i).getCharacteristics() != null) {
                    Restaurant tempR = new Restaurant(temp.get(i));
                    //if characteristics is not null
                    tempR.setCharacteristics(temp.get(i).getCharacteristics());
                    allLocations.add(tempR);

                } else {
                    //add to location
                    Place tempP = new Place(temp.get(i));
                    allLocations.add(tempP);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allLocations;
    }

}
