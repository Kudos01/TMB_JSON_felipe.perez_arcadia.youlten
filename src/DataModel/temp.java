package DataModel;

import com.google.gson.JsonArray;

public class temp{

    private String name;
    private Double[] coordinates;
    private String description;
    private Integer stars;
    private JsonArray characteristics;
    private String architect;
    private Integer inauguration;

    public String getArchitect() { return architect; }

    public Integer getInauguration() { return inauguration; }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public void setInauguration(Integer inauguration) {
        this.inauguration = inauguration;
    }

    public JsonArray getCharacteristics() { return characteristics; }

    public void setCharacteristics(JsonArray characteristics) {
        this.characteristics = characteristics;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
