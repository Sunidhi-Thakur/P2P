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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.preritrajput.peertopeer.R;
import com.preritrajput.peertopeer.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;

    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.mapView.onCreate(savedInstanceState);

        binding.mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng delhi = new LatLng(28.6330, 77.2194);
        map.addMarker(new MarkerOptions().position(delhi).title("Marker in Delhi"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(delhi,15f));
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