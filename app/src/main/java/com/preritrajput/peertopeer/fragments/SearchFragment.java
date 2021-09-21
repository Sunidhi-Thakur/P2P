package com.preritrajput.peertopeer.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.preritrajput.peertopeer.R;
import com.preritrajput.peertopeer.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    LatLng delhi = new LatLng(28.6330, 77.2194);
    LatLng Jantar = new LatLng(28.6271, 77.2166);
    LatLng Lalit = new LatLng(28.6317, 77.2272);
    LatLng Agrasen = new LatLng(28.6260, 77.2250);

    private FragmentSearchBinding binding;
    private ArrayList<LatLng> locationArrayList;
    private ArrayList<String> title;

    final LatLng somewhere = new LatLng(28.6330, 77.2194);

    private int markerclicked;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.mapView.getMapAsync(this);

        locationArrayList = new ArrayList<>();
        title = new ArrayList<>();

        binding.mapView.onCreate(savedInstanceState);
        locationArrayList.add(Jantar);
        locationArrayList.add(Lalit);
        locationArrayList.add(Agrasen);
        locationArrayList.add(delhi);

        title.add("Honda Civic\nRs. 100/- per hour\nRating - 4.5");
        title.add("Ford Figo\nRs. 135/- per hour\nRating - 4.1");
        title.add("Honda Amaze\nRs. 161/- per hour\nRating - 3.8");
        title.add("Swift Dzire\nRs. 180/- per hour\nRating - 3.9");
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        for (int i = 0; i < locationArrayList.size(); i++) {

            map.addMarker(new MarkerOptions()
                    .position(locationArrayList.get(i))
                    .title(title.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hatchback)));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationArrayList.get(i),15f));
        }

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker arg0) {

                View v = getLayoutInflater().inflate(R.layout.info_window, null);

                LatLng latLng = arg0.getPosition();

                TextView tv1 = (TextView) v.findViewById(R.id.carName);
                String title=arg0.getTitle();

                if(onMarkerClick(arg0)==true && markerclicked==1){

                }

                tv1.setText(title);

                return v;

            }
        });
    }

    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(somewhere))
        {
            markerclicked=1;
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        binding.mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}