package DataModel;

public class Hotel extends Location {

    private Integer stars;

    /**
     * Constructor for building a Hotel object using a generic location
     * (The stars are set using the setter)
     *
     * @param g
     */

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
