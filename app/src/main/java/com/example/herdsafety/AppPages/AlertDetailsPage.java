package com.example.herdsafety.AppPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

public class AlertDetailsPage extends AppCompatActivity implements OnMapReadyCallback {

    private Button buttonDone;
    TextView alertDescriptionBox;
    RadioGroup alertTypeRadio;
    private GoogleMap mMap;
    public ArrayList<LatLng> coordinates = new ArrayList<>();
    public ArrayList<String> descriptions = new ArrayList<>();
    AAlert specifiedAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details_page);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Declaring variable for EditText widget (description input).
        RadioGroup alertTypeWidget = findViewById(R.id.radioGroup_alertLevel);

        DBHandler dbHandler = new DBHandler(AlertDetailsPage.this);

        buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoMainPage();
            }
        });

        // Gets the unique alert object
        specifiedAlert = AAlert.alertList.get(getIntent().getExtras().getInt("Alert Index"));

        // Sets the description display
        alertDescriptionBox = findViewById(R.id.editTextTextMultiLine);
        alertDescriptionBox.setText(specifiedAlert.getDescription());

        // Sets the type display
        alertTypeRadio = findViewById(R.id.radioGroup_alertLevel);
        if(specifiedAlert.getType() == "Caution"){
            alertTypeRadio.check(R.id.radioButton_caution);
        }
        else if(specifiedAlert.getType() == "Warning"){
            alertTypeRadio.check(R.id.radioButton_warning);
        }
        else if(specifiedAlert.getType() == "Crime"){
            alertTypeRadio.check(R.id.radioButton_crime);
        }

    }

    public void gotoMainPage(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //Pulls in the location of the marker from the database
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        LatLng alertLocation = new LatLng(specifiedAlert.getLatitude(),specifiedAlert.getLongitude());
        String fullDescription = specifiedAlert.getDescription();

        // Arranges the length of the alert description that will appear on the marker
        String shortenDescription = fullDescription.length() > 15 ?
                fullDescription.substring(0,15) + "..." : fullDescription;

        // Indicates the location my putting a marker on the map
        mMap.addMarker(new MarkerOptions().position(alertLocation).title(shortenDescription));

        // Creating marker for each set of coordinates.
        Log.d("location_test", "Coordinates: " + coordinates);
        for (int i = 0; i < coordinates.size(); i++) {
            LatLng coords = new LatLng(coordinates.get(i).latitude, coordinates.get(i).longitude);
            MarkerOptions marker = new MarkerOptions().position(coords).title(descriptions.get(i));
            Log.d("location_test", String.valueOf(i));
            Log.d("location_test", "Coords returned: " + coords);
            Log.d("location_test", "Marker created: " + marker);
            mMap.addMarker(new MarkerOptions().position(coords).title(descriptions.get(i)));
        }

        double border = 0.01;

        // Focuses the map on the area where the alert has been reported
        LatLng one = new LatLng(alertLocation.latitude - border, alertLocation.longitude - border);
        LatLng two = new LatLng(alertLocation.latitude + border, alertLocation.longitude + border);

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
}