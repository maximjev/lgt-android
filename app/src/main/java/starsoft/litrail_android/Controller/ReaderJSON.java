package starsoft.litrail_android.Controller;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class ReaderJSON<T> {
    public InputStream targetStream;

    public ReaderJSON (Context context, int sourceId) {
        targetStream = context.getResources().openRawResource(sourceId);
    }

    public List<T> readJsonStream() throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(targetStream, "UTF-8"))) {
            return readObjectArray(reader);
        }
    }

    public List<T> readObjectArray(JsonReader reader) throws IOException {
        List<T> objects = new ArrayList<T>();

        reader.beginArray();
        while (reader.hasNext()) {
            objects.add(readObject(reader));
        }
        reader.endArray();
        return objects;
    }

    public abstract T readObject(JsonReader reader) throws IOException;



}
