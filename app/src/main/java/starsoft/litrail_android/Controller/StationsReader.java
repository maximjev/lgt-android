package starsoft.litrail_android.Controller;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import starsoft.litrail_android.Model.Station;

public class StationsReader extends ReaderJSON<Station> {

    private static final String TAG = "StationsReader";

    public StationsReader(Context context, int sourceID) {
        super(context, sourceID);
    }

    @Override
    public Station readObject(JsonReader reader) throws IOException {
        String name = null;
        String description = null;
        Double longitude = null;
        Double latitude = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String identifier = reader.nextName();
            switch (identifier) {
                case "name":
                    name = reader.nextString();
                    break;
                case "description":
                    description = reader.nextString();
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
        }
        reader.endObject();
        return new Station(name, description, longitude, latitude);
    }
}
