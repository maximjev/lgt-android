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

import java.io.IOException;
import java.util.List;

import starsoft.litrail_android.Controller.RoutesReader;
import starsoft.litrail_android.MainActivity;
import starsoft.litrail_android.Model.Route;
import starsoft.litrail_android.Model.Station;
import starsoft.litrail_android.R;
import starsoft.litrail_android.Controller.StationsReader;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public static final String TAG = "MapFragment";

    private MapView mapView;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Station> stations;
        List<Route> routes;

        try {
            stations = (new StationsReader(getContext(), R.raw.stations)).readJsonStream();
            for (Station station : stations) {
                LatLng stationPosition = new LatLng(station.latitude, station.longitude);
                googleMap.addMarker(new MarkerOptions().position(stationPosition).title(station.name)
                        .snippet(station.description)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
            routes = (new RoutesReader(getContext(), R.raw.routes)).readJsonStream();

            }
        } catch (IOException e) {
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
        googleMap.addMarker(new MarkerOptions().position(centerMapView).title("Marker in center")
                .snippet("snippet snippet snippet snippet snippet...")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
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
}
