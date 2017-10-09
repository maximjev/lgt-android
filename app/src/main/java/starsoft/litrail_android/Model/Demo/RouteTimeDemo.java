package starsoft.litrail_android.Model.Demo;

import java.util.ArrayList;
import java.util.List;

import starsoft.litrail_android.Model.RouteTime;

public class RouteTimeDemo {

    public static final List<RouteTime> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new RouteTime("11:00", "13:00", "2:00"));
        ITEMS.add(new RouteTime("13:00", "14:45", "1:45"));
        ITEMS.add(new RouteTime("17:15", "18:20", "1:05"));
        ITEMS.add(new RouteTime("20:45", "22:05", "1:20"));
        ITEMS.add(new RouteTime("11:00", "13:00", "2:00"));
        ITEMS.add(new RouteTime("13:00", "14:45", "1:45"));
        ITEMS.add(new RouteTime("17:15", "18:20", "1:05"));
        ITEMS.add(new RouteTime("20:45", "22:05", "1:20"));
    }

}
