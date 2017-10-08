package starsoft.litrail_android.Model;

public class RouteTime {
    public final String departureTime;
    public final String arrivalTime;
    public final String duration;

    RouteTime(String departureTime, String arrivalTime, String duration) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }

}