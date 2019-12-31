package WebServices;

import com.google.gson.JsonObject;

public class Transit extends Leg {

    String transit_mode;
    String line_name;
    int from_stopcode;
    int to_stopcode;
    String from_name;
    String to_name;

    public Transit(JsonObject partial_leg){
        super(partial_leg);

        this.transit_mode= partial_leg.get("mode").getAsString();
        this.line_name= partial_leg.get("route").getAsString();
        this.from_name=partial_leg.get("from").getAsJsonObject().get("name").getAsString();
        this.from_stopcode=partial_leg.get("from").getAsJsonObject().get("stopCode").getAsInt();
        this.to_name = partial_leg.get("to").getAsJsonObject().get("name").getAsString() ;
        this.to_stopcode=partial_leg.get("to").getAsJsonObject().get("stopCode").getAsInt();

    }


    public String getTransit_mode() {
        return transit_mode;
    }

    public String getLine_name() {
        return line_name;
    }

    public int getFrom_stopcode() {
        return from_stopcode;
    }

    public int getTo_stopcode() {
        return to_stopcode;
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getTo_name() {
        return to_name;
    }
}
