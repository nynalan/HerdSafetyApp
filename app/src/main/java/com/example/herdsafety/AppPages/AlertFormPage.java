package com.example.herdsafety.AppPages;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.herdsafety.Database.DBHandler;
import com.example.herdsafety.MainAlertObjects.AAlert;
import com.example.herdsafety.MainAlertObjects.AlertFactory;
import com.example.herdsafety.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Hashtable;

public class AlertFormPage extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_form_page);

        // Declaring variable for EditText widget (description input).
        EditText alertDescriptionWidget = findViewById(R.id.editTextTextMultiLine);

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
                        // TODO: fix location reporting
                        String alertLevel = alertLevelButton.getText().toString();
                        AAlert newAlert = AlertFactory.singletonFactory.createAlert(alertDescription, null, alertLevel);
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
}