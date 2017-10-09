package starsoft.litrail_android.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import starsoft.litrail_android.MainActivity;
import starsoft.litrail_android.Model.PinStation;
import starsoft.litrail_android.Model.RouteMap;
import starsoft.litrail_android.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public static final String TAG = "MapFragment";

    private MapView mapView;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Stotys ir maršrutai");
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        ((MainActivity)getActivity()).showHomeAsUp(false);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        else {
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //TODO: Ne visas spalvas paima/yra juodos spalvos maršutų
    //TODO: Stočių koordinatės JSON faile nėra tikslios

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Collection<PinStation> pinStations;
        Collection<RouteMap> routes;

        try {
            //Skaito stations JSON failą
            Gson gson = new Gson();
            JsonReader jsonReader = getReader(R.raw.stations);
            pinStations = gson.fromJson(jsonReader, new TypeToken<Collection<PinStation>>(){}.getType());
            jsonReader.close();


            //Skaito routes JSON failą
            Reader readerRoutes = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.routes)));
            jsonReader = getReader(R.raw.routes);
            routes = gson.fromJson(jsonReader, new TypeToken<Collection<RouteMap>>(){}.getType());
            jsonReader.close();

            //Iteracija

            for (PinStation pinStation : pinStations) {
                LatLng stationPosition = new LatLng(pinStation.latitude, pinStation.longitude);
                googleMap.addMarker(new MarkerOptions().position(stationPosition).title(pinStation.name)
                        .snippet(pinStation.description)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
            }

            List<Integer> colors = new ArrayList<>();

            colors.add(Color.parseColor("#F44336"));
            colors.add(Color.parseColor("#E91E63"));
            colors.add(Color.parseColor("#9C27B0"));
            colors.add(Color.parseColor("#3F51B5"));
            colors.add(Color.parseColor("#2196F3"));
            colors.add(Color.parseColor("#00BCD4"));
            colors.add(Color.parseColor("#009688"));
            colors.add(Color.parseColor("#4CAF50"));
            colors.add(Color.parseColor("#CDDC39"));
            colors.add(Color.parseColor("#FFC107"));
            colors.add(Color.parseColor("#795548"));
            colors.add(Color.parseColor("#9E9E9E"));
            colors.add(Color.parseColor("#FF5722"));

            int pos = 0;

            for (RouteMap routeMap : routes) {
                List<LatLng> latLngs = new ArrayList<>();
                PolylineOptions polylineOptions = new PolylineOptions().clickable(false);

                for (int i = 0; i < routeMap.routeStations.size(); i++) {
                    latLngs.add(new LatLng(routeMap.routeStations.get(i).latitude, routeMap.routeStations.get(i).longitude));
                }
                Polyline polyline = googleMap.addPolyline(polylineOptions);
                polyline.setWidth(routeMap.strokeWeight);
                polyline.setPoints(latLngs);

//                int color = Color.parseColor((routeMap.routeColor.isEmpty())?"#F44336" : routeMap.routeColor );
                polyline.setColor(colors.get(pos++));
                if (pos >= colors.size())
                        pos = 0;
            }

        } catch (Exception e) {
            Log.d(TAG, "File handling exception");
            e.printStackTrace();
        }

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        initMapStyle(googleMap);

        LatLng centerMapView = new LatLng(55.315231, 24.189048);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(centerMapView));
        googleMap.setMinZoomPreference(7);
    }

    private void initMapStyle(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.mapstyle));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    private JsonReader getReader(int resourceID) {
        Reader readerStations = new BufferedReader(new InputStreamReader(getResources().openRawResource(resourceID)));
        return new JsonReader(readerStations);
    }
}
