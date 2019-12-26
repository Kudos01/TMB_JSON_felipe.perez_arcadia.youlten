package DataModel;

public class Place extends Location {

        private String name;
        private Double[] coordinates;
        private String description;

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
