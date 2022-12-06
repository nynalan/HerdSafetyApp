package com.example.herdsafety.AppPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.example.herdsafety.MainAlertObjects.AAlert;
import com.example.herdsafety.Database.DBHandler;
import com.example.herdsafety.MainAlertObjects.AAlert;
import com.example.herdsafety.R;
import com.example.herdsafety.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.herdsafety.databinding.ActivityMapsBinding;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button buttonReport;
    ListView aPlaceHolder;
    String[] monthsPlaceHolder;
    public ArrayList<LatLng> coordinates = new ArrayList<>();
    public ArrayList<String> descriptions = new ArrayList<>();

    // Declaring database connection.
    DBHandler dbHandler;
    static boolean severityOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize severity switch and setOnClick
        SwitchCompat severitySwitch = findViewById(R.id.orderLayout);
        severitySwitch.setChecked(severityOn);
        severitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlertList(severitySwitch.isChecked());
                severityOn = severitySwitch.isChecked();
            }
        });

        dbHandler = new DBHandler(MapsActivity.this);

        // dbHandler.deleteAllAlerts();

        // Remove default title text
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        buttonReport = (Button) findViewById(R.id.buttonReport);
        buttonReport.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoAlertFormPage();
            }
        });

        AAlert.alertList = dbHandler.retrieveNearbyAlerts();

        // Getting location data for markers on map display.
        for (int i = 0; i < AAlert.alertList.size(); i++) {
            Log.d("location", String.valueOf(AAlert.alertList.get(i).getLatitude()));
            coordinates.add(new LatLng(AAlert.alertList.get(i).getLatitude(), AAlert.alertList.get(i).getLongitude()));
        }
        // Log.d("marker", "Locations: " + coordinates);

        // Getting names.
        for (int i = 0; i < AAlert.alertList.size(); i++) {
            descriptions.add(AAlert.alertList.get(i).getDescription());
        }

        aPlaceHolder = findViewById(R.id.alertPlaceholder);
        ArrayAdapter<String> adapterPlaceHolder = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descriptions);
        aPlaceHolder.setAdapter(adapterPlaceHolder);
        aPlaceHolder.setOnItemClickListener((adapterView, view, i, l) -> {
            String month = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getApplicationContext(),"Clicked: " + month, Toast.LENGTH_SHORT).show();
        });
        setAlertList(severityOn);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        LatLng cuBoulder = new LatLng(40.00894024526554, -105.2679755325988);
        mMap.addMarker(new MarkerOptions().position(cuBoulder).title("CU Boulder"));

        // Creating marker for each set of coordinates.
        Log.d("location_test", "Coordinates: " + coordinates);
        for (int i = 0; i < coordinates.size(); i++) {
            LatLng coords = new LatLng(coordinates.get(i).latitude, coordinates.get(i).longitude);
            MarkerOptions marker = new MarkerOptions().position(coords).title(descriptions.get(i));
            Log.d("location_test", "Coords returned: " + coords);
            Log.d("location_test", "Marker created: " + marker);
            mMap.addMarker(new MarkerOptions().position(coords).title(descriptions.get(i)));
        }

        // Test alert!
        LatLng test = new LatLng(40.012197, -105.263686);
        mMap.addMarker(new MarkerOptions().position(test).title("Marker #1"));

        double border = 0.01;

        LatLng one = new LatLng(cuBoulder.latitude - border, cuBoulder.longitude - border);
        LatLng two = new LatLng(cuBoulder.latitude + border, cuBoulder.longitude + border);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);
    }

    // Go to alert form page
    public void gotoAlertFormPage(){
        Intent intent = new Intent(this, AlertFormPage.class);
        startActivity(intent);
    }

    // Go to alert details page with the index of the alert specified in the AAlert.alertList
    public void gotoMoreAlertsDetailPage(int i){
        Intent intent = new Intent(this, AlertDetailsPage.class);
        intent.putExtra("Alert Index", i);
        startActivity(intent);
    }

    //Gets an array of strings with alert descriptions
    public void setAlertList(boolean severityOrder){
        AAlert.alertList = dbHandler.retrieveNearbyAlerts();

        // Set severity order of alerts based on their types: 1. Crime, 2. Warning, 3. Caution
        if(severityOrder) {
            Collections.sort(AAlert.alertList, new Comparator<AAlert>() {
                @Override
                public int compare(AAlert aAlert, AAlert otherAlert) {
                    if (aAlert.getType().equals("Crime") && (otherAlert.getType().equals("Warning") ||
                            otherAlert.getType().equals("Caution"))) {
                        return -1;
                    }
                    if (aAlert.getType().equals("Warning") && (otherAlert.getType().equals("Caution"))) {
                        return -1;
                    }
                    if (aAlert.getType().equals(otherAlert.getType())) {
                        return 0;
                    }
                    if (aAlert.getType().equals("Caution") && (otherAlert.getType().equals("Warning") ||
                            otherAlert.getType().equals("Crime"))) {
                        return 1;
                    }
                    if (aAlert.getType().equals("Warning") && (otherAlert.getType().equals("Crime"))) {
                        return 1;
                    }

                    return 0;
                }
            });
        }

        // Getting names.
        ArrayList<String> descriptions = new ArrayList<String>();
        for (int i = 0; i < AAlert.alertList.size(); i++) {
            String fullDescription = AAlert.alertList.get(i).getDescription();
            if(fullDescription.length() > 30){
                descriptions.add(fullDescription.substring(0, 30) + "...");
            }
            else {
                descriptions.add(fullDescription);
            }
        }
        Log.d("database_insert", "Descriptions: " + descriptions);

        // If severity is not on order them based on time of entry
        aPlaceHolder = findViewById(R.id.alertPlaceholder);
        ArrayAdapter<String> adapterPlaceHolder = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descriptions);
        aPlaceHolder.setAdapter(adapterPlaceHolder);
        aPlaceHolder.setOnItemClickListener((adapterView, view, i, l) -> {
            String month = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getApplicationContext(),"Clicked: " + month, Toast.LENGTH_SHORT).show();
            gotoMoreAlertsDetailPage(i);
        });


    }
}