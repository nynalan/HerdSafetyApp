package com.example.herdsafety.AppPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.example.herdsafety.Database.DBHandler;
import com.example.herdsafety.MainAlertObjects.AAlert;
import com.example.herdsafety.MainAlertObjects.AlertFactory;
import com.example.herdsafety.R;
import com.google.android.gms.maps.model.LatLng;
import java.util.Hashtable;

public class AlertFormPage extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSubmit;
    // private Button testButton;

    // Declaring LatLng to store device location (if proper permissions given).
    // FusedLocationProviderClient to pull location data.
    private LatLng curr_location;
    FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_form_page);

        // Declaring variable for EditText widget (description input).
        EditText alertDescriptionWidget = findViewById(R.id.editTextTextMultiLine);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Declaring variable for EditText widget (description input).
        RadioGroup alertTypeWidget = findViewById(R.id.radioGroup_alertLevel);

        // Declaring DBHandler instance and passing context to it.
        DBHandler dbHandler = new DBHandler(AlertFormPage.this);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoMainPage();
            }
        });

        /*
        testButton = (Button) findViewById(R.id.buttonTest);
        testButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getDeviceLocation();
                } else {
                    showAlertMessageLocationDisabled();
                }
            }
        });
        */

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Pulling user input for alert description.
                String alertDescription = alertDescriptionWidget.getText().toString();
                Button alertLevelButton = (Button) findViewById(alertTypeWidget.getCheckedRadioButtonId());

                // Ensuring a valid description is entered.
                if (((alertDescription.equals("")) || alertDescription.equals("      Enter your alert description here"))) {
                    Toast.makeText(AlertFormPage.this, "Please provide an alert description.", Toast.LENGTH_SHORT).show();
                }

                // Ensuring a valid type is chosen.
                else if ((alertLevelButton == null)) {
                    Toast.makeText(AlertFormPage.this, "Please select an alert type.", Toast.LENGTH_SHORT).show();
                }

                // If valid...
                else {
                    try {
                        // Construct new AlertModel, set class variables, insert into SQLite DB.
                        String alertLevel = alertLevelButton.getText().toString();

                        // Checking that location permission was given.
                        // if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        LatLng location = getDeviceLocation();
                        // } else {
                            // If not, display message. Null-pointer exception in next statement will be caught.
                            // showAlertMessageLocationDisabled();
                        // }
                        Log.d("location", "AlertFormPage location read: " + location);
                        AAlert newAlert = AlertFactory.singletonFactory.createAlert(alertDescription, location, alertLevel);
                        // Log.d("location_test", "newAlert returned: " + newAlert);
                        // Log.d("location_test", "newAlert latitude: " + newAlert.getLatitude());
                        // Log.d("location_test", "newAlert longitude: " + newAlert.getLongitude());
                        boolean success = dbHandler.addNewAlert(newAlert);

                        // Test pop-up to verify insertion works correctly.
                        Toast.makeText(AlertFormPage.this, "Successfully added? " + success, Toast.LENGTH_SHORT).show();

                        Hashtable<Integer, String> hashtableAlert = new Hashtable<>();
                        for(AAlert alert: AAlert.alertList){
                            if(alert.getType().equals(newAlert.getType())) {
                                hashtableAlert.put(alert.getId(), alert.getDescription());
                            }
                        }
                        int bestMatchId = newAlert.sim_algorithm.similarPostId(newAlert.getDescription(), hashtableAlert);

                        // Proceeding if all works correctly.
                        gotoVerifyPage(bestMatchId, alertDescription, null, newAlert.getType());
                    }
                    catch (Exception e) {
                        // Display error message if not working correctly.
                        Log.d("database_insert", "Error: " + e);
                        Toast.makeText(AlertFormPage.this, "Oh no! An error occurred reporting the alert!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void gotoMainPage(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void gotoVerifyPage(int id, String alertDescription, LatLng latLng, String type){
        Intent intent = new Intent(this, AlertVerifyPage.class);
        intent.putExtra("Alert id", id);
        intent.putExtra("Alert Description", alertDescription);
        //intent.putExtra("Alert LatLng", latLng);
        intent.putExtra("Alert Type", type);
        startActivity(intent);
    }

    // Get device's current location.
    @SuppressLint("MissingPermission")
    private LatLng getDeviceLocation() {
        // Log.d("Location", String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED));

        // If permissions are given, pull current device location with cancellation token.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken()).addOnSuccessListener(this, location -> {
                Log.d("Location", String.valueOf(location));
                if (location != null) { curr_location = getLocation(location); }
            });
        }
        // Request permission if not yet given.
        else { requestPermission(); }
        return curr_location;
    }

    private void requestPermission() {
        // Requesting permission and sending request code to adjust settings.
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
    }

    // Pop-up message that displays when location privileges are not granted.
    private void showAlertMessageLocationDisabled() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Device location is turned off. Do you want to turn location on?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Handling result of permission request.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Listening for granted permissions.
        if (requestCode == 10) {
            // Pulling device location if allowed.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation();
            }
        } else {
            // Permission denied.
            Toast.makeText(AlertFormPage.this, "Location required. Please enable from Settings.", Toast.LENGTH_SHORT).show();
        }
    }

    private LatLng getLocation(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        LatLng latlng = new LatLng(latitude, longitude);
        return latlng;
    }
}