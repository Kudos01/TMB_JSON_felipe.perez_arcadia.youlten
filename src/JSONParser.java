import DataModel.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.text.html.parser.DTD;
import javax.swing.text.html.parser.Parser;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JSONParser implements Parse{

    @Override
    public ArrayList<Location> parseLocations() {

        ArrayList<Location> allLocations = new ArrayList<>();

        Gson gson = new Gson();
        //TODO: check if this is sufficient for checking and exiting program with error message
        try (Reader reader = new FileReader("resources/localizations.json")) {

            //make a new type token of type teams, so that we can parse all the teams from the json to an arraylist of type Team

            LocationObj locOb;
            Type tempListType = new TypeToken<ArrayList<temp>>() {}.getType();
            //temp temps = new temp();
            ArrayList<temp> temps = new ArrayList<>();

            //get the json object
            locOb = gson.fromJson(reader, LocationObj.class);
            //if you run locOb.getLocations() you get the json list of locations
            temps = gson.fromJson(locOb.getLocations(), tempListType);

            //System.out.println(temps);
            //System.out.println(generics);

            //the idea of this part is once you get here, there should be an arraylist of generics (see temp)
            for (int i = 0; i < temps.size(); i++) {

                Hotel tempH = new Hotel();
                Monument tempM = new Monument();
                Restaurant tempR = new Restaurant();
                Place tempP = new Place();

                if (temps.get(i).getStars() != null) {

                    tempH.setName(temps.get(i).getName());
                    tempH.setCoordinates(temps.get(i).getCoordinates());
                    tempH.setDescription(temps.get(i).getDescription());
                    tempH.setStars(temps.get(i).getStars());

                    allLocations.add(tempH);
                } else if (temps.get(i).getArchitect() != null) {

                    tempM.setName(temps.get(i).getName());
                    tempM.setCoordinates(temps.get(i).getCoordinates());
                    tempM.setDescription(temps.get(i).getDescription());
                    tempM.setArchitect(temps.get(i).getArchitect());
                    tempM.setInauguration(temps.get(i).getInauguration());

                    allLocations.add(tempM);


                } else if (temps.get(i).getCharacteristics() != null) {

                    //if characteristics is not null
                    tempR.setName(temps.get(i).getName());
                    tempR.setCoordinates(temps.get(i).getCoordinates());
                    tempR.setDescription(temps.get(i).getDescription());
                    tempR.setCharacteristics(temps.get(i).getCharacteristics());

                    allLocations.add(tempR);

                } else {
                    //add to location
                    tempP.setName(temps.get(i).getName());
                    tempP.setCoordinates(temps.get(i).getCoordinates());
                    tempP.setDescription(temps.get(i).getDescription());

                    allLocations.add(tempP);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allLocations;
    }

}
