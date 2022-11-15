package com.example.herdsafety;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.herdsafety.databinding.ActivityMapsBinding;

import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button buttonReport;

    // TODO: Declare variables for EditText widgets (for user input).
    // Ex: private EditText courseNameEdt;
    //     private Button addCourseBtn;

    // Creating variable for DBHandler.
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: Declare variables for alert data (from user input).
        // Ex: courseNameEdt = findViewById(R.id.idEdtCoursename);

        // Declaring database connection.
        dbHandler = new DBHandler(MapsActivity.this);

        // Remove default title text
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        // TODO: Add on-click listener for add alert button.
        // Ex: String courseName = courseNameEdt.getText().toString();

        buttonReport = (Button) findViewById(R.id.buttonReport);
        buttonReport.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openAlertFormAlertPage();
            }
        });
        
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng cuBoulder = new LatLng(40.00894024526554, -105.2679755325988);
        mMap.addMarker(new MarkerOptions().position(cuBoulder).title("CU Boulder"));

        //get latlong for corners for specified city

        double border = 0.005;

        LatLng one = new LatLng(cuBoulder.latitude - border, cuBoulder.longitude - border);
        LatLng two = new LatLng(cuBoulder.latitude + border, cuBoulder.longitude + border);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
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

    public void openAlertFormAlertPage(){
        Intent intent = new Intent(this, AlertFormPage.class);
        startActivity(intent);
    }

}