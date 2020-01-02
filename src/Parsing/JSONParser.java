package Parsing;

import DataModel.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Represents the way we parse the locations JSON file and load the data into an ArrayList of type Location.
 * Implements the Parse interface, that defines the functionalities to parse the locations.
 */

public class JSONParser implements Parse {

    private final String FILENAME = "resources/localizations.json";

    /**
     * Parses the locations from the JSON file and returns an ArrayList of type Location with all the locations loaded.
     * If the parsing fails, an error is printed and the ArrayList is set to null
     *
     * @return The Locations that have been parsed, null if process failed.
     */

    @Override
    public ArrayList<Location> parseLocations() {

        ArrayList<Location> allLocations = new ArrayList<>();

        Gson gson = new Gson();
        try (Reader reader = new FileReader(FILENAME)) {

            //make a new type token of type teams, so that we can parse all the teams from the json to an arraylist of type Team
            Type tempListType = new TypeToken<ArrayList<Generic>>(){}.getType();

            ArrayList<Generic> temp;

            //get the json object
            JsonObject j = gson.fromJson(reader, JsonObject.class);

            temp = gson.fromJson(j.get("locations").getAsJsonArray(), tempListType);

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
            //e.printStackTrace();
            System.out.println("Error! Could not read JSON file.");
            allLocations = null;
        }
        return allLocations;
    }
}
