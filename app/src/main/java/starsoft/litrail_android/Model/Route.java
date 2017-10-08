package starsoft.litrail_android.Model;

import java.util.List;

public class Route {
    public final Station startStation;
    public final Station endStation;

    public final List<Station> routeStations;
    public final String routeColor;
    public final int strokeWeight;
    public final Double strokeOpacity;

    public Route(Station startStation, Station endStation, List<Station> routeStations, String routeColor, int strokeWeight, Double strokeOpacity) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.routeStations = routeStations;
        this.routeColor = routeColor;
        this.strokeWeight = strokeWeight;
        this.strokeOpacity = strokeOpacity;
    }
}
