package Web_Services;

public class MetroStation {

    private Double[] coordinates;
    private int stop_code;
    private String stop_name;
    private String stop_description;
    private String address;
    private String date;
    private String street_name;

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getStop_code() {
        return stop_code;
    }

    public void setStop_code(int stop_code) {
        this.stop_code = stop_code;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStop_description() {
        return stop_description;
    }

    public void setStop_description(String stop_description) {
        this.stop_description = stop_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }
}
