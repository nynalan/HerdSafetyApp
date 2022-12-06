package com.example.herdsafety;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.herdsafety.AppObjects.AAlert;
import com.example.herdsafety.AppObjects.AlertFactory;

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
                        boolean success = dbHandler.addNewAlert(newAlert);

                        // Test pop-up to verify insertion works correctly.
                        Toast.makeText(AlertFormPage.this, "Successfully added? " + success, Toast.LENGTH_SHORT).show();

                        // Proceeding if all works correctly.
                        gotoConfirmationPage();
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

    public void gotoConfirmationPage(){
        Intent intent = new Intent(this, ReportConfirmationPage.class);
        startActivity(intent);
    }
}