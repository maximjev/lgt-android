package starsoft.litrail_android.Model;

public class Station {
    public final String name;
    public final String description;
    public final Double longitude;
    public final Double latitude;

    public Station(String name, String description, Double longitude, Double latitude) {
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
