package com.example.musadkhan.internshiptask_vyorius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap mMap;
    int counter= 0,zoom=10;
    String[] items = {"Dark","Night","Aubergine","Retro","Silver"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    Polyline polyline = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        autoCompleteTextView = findViewById(R.id.auto_complete);
        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTextView.setAdapter(adapterItem);



        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedItem = adapterView.getItemAtPosition(i).toString();

                if (selectedItem =="Dark")
                {
                    try {
                        // Customize the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        MainActivity.this, R.raw.dark_json));

                        if (!success) {
                            Log.e(android.content.ContentValues.TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(android.content.ContentValues.TAG, "Can't find style. Error: ", e);
                    }
                }

                else if(selectedItem == "Night")
                {
                    try {
                        // Customize the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        MainActivity.this, R.raw.night_json));

                        if (!success) {
                            Log.e(android.content.ContentValues.TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(android.content.ContentValues.TAG, "Can't find style. Error: ", e);
                    }
                }

                else if(selectedItem == "Aubergine")
                {
                    try {
                        // Customize the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        MainActivity.this, R.raw.aubergine_json));

                        if (!success) {
                            Log.e(android.content.ContentValues.TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(android.content.ContentValues.TAG, "Can't find style. Error: ", e);
                    }
                }

                else if(selectedItem == "Retro")
                {
                    try {
                        // Customize the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        MainActivity.this, R.raw.retro_json));

                        if (!success) {
                            Log.e(android.content.ContentValues.TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(android.content.ContentValues.TAG, "Can't find style. Error: ", e);
                    }
                }

                else if(selectedItem == "Silver")
                {
                    try {
                        // Customize the styling of the base map using a JSON object defined
                        // in a raw resource file.
                        boolean success = mMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        MainActivity.this, R.raw.silver_json));

                        if (!success) {
                            Log.e(android.content.ContentValues.TAG, "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e(android.content.ContentValues.TAG, "Can't find style. Error: ", e);
                    }
                }

                mapFragment.getMapAsync(MainActivity.this);
            }
        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        mMap = googleMap;
        LatLng latLng = new LatLng(19.088037997047756, 72.8928457927014);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = mMap.addMarker(markerOptions);

                if(counter < 2) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                    latLngList.add(latLng);
                    markerList.add(marker);
                    counter = counter + 1;

                    if(counter==2)
                    {
                        if(polyline != null) polyline.remove();
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(latLngList).clickable(true);
                        polyline = mMap.addPolyline(polylineOptions);
                    }
                }
                else {
                    mMap.clear();
                    counter=0;
                    if(polyline != null) polyline.remove();
                    for(Marker marker1 : markerList) marker1.remove();
                    latLngList.clear();
                    markerList.clear();
                }

            }
        });

    }

}