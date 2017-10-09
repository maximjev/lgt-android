package starsoft.litrail_android.Model.Demo;

import java.util.ArrayList;
import java.util.List;

import starsoft.litrail_android.Model.SavedRoute;

public class SavedRoutesDemo {

    public static final List<SavedRoute> ITEMS = new ArrayList<SavedRoute>();

    static {
        ITEMS.add(new SavedRoute("Kaunas", "Šiauliai"));
        ITEMS.add(new SavedRoute("Vilnius", "Šiauliai"));
        ITEMS.add(new SavedRoute("Panevėžys", "Klaipėda"));
    }
}
