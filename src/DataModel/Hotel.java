package DataModel;

public class Hotel extends Location {

    private Integer stars;

    public Hotel(Generic g) {
        super(g);
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
