package starsoft.litrail_android.Controller;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import starsoft.litrail_android.Model.Route;
import starsoft.litrail_android.Model.Station;

public class RoutesReader extends ReaderJSON<Route> {
    private static final String TAG = "StationsReader";

    public RoutesReader(Context context, int sourceID) {
        super(context, sourceID);
    }

    @Override
    public Route readObject(JsonReader reader) throws IOException {
        Station startStation = null;
        Station endStation = null;
        List<Station> stations = null;
        String name = null;
        String routeColor = null;
        int strokeWeight = 0;
        Double strokeOpacity = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String identifier = reader.nextName();
            switch (identifier) {
                case "startStation":
                    startStation = readStation(reader);
                    break;
                case "endStation":
                    endStation = readStation(reader);
                    break;
                case "routeStations":
                    stations = readStations(reader);
                    break;
                case "routeColor":
                    routeColor = reader.nextString() == null ? reader.nextString() : "";
                    break;
                case "strokeWeight":
                    strokeWeight = reader.nextInt();
                    break;
                case "strokeOpacity":
                    strokeOpacity = reader.nextDouble();
                    break;
                default:
                    Log.d(TAG, "Unindentified key: " + identifier);
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Route(startStation, endStation, stations, routeColor, strokeWeight, strokeOpacity);
    }

    private List<Station> readStations(JsonReader reader) throws IOException {
        List<Station> stations = new ArrayList<Station>();

        // Gali būt nesklandumų su .beginObject()
        while (reader.hasNext())
            stations.add(readStation(reader));
        return stations;
    }

    private Station readStation(JsonReader reader) throws IOException {
        String name = null;
        Double longitude = null;
        Double latitude = null;

        reader.beginObject();
        String identifier = reader.nextName();
        switch (identifier) {
            case "name":
                name = reader.nextString();
                break;
            case "longitude":
                longitude = reader.nextDouble();
                break;
            case "latitude":
                latitude = reader.nextDouble();
                break;
            default:
                Log.d(TAG, "Unindentified key: " + identifier);
                reader.skipValue();
                break;
        }
        reader.endObject();
        return new Station(name, "", longitude, latitude);
    }
}
