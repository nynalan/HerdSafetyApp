package com.example.herdsafety;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlertFormPage extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_form_page);

        // Declaring variable for EditText widget (description input).
        EditText alertDescriptionWidget = findViewById(R.id.editTextTextMultiLine);

        // Declaring DBHandler instance and passing context to it.
        DBHandler dbHandler = new DBHandler(AlertFormPage.this);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openAlertFormMapsActivityPage();
            }
        });

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Pulling user input for alert description.
                String alertDescription = alertDescriptionWidget.getText().toString();

                // Ensuring a valid description is entered.
                if (((alertDescription.equals("")) || alertDescription.equals("      Enter your alert description here"))) {
                    Toast.makeText(AlertFormPage.this, "Please provide an alert description.", Toast.LENGTH_SHORT).show();
                }

                // If valid...
                else {
                    try {
                        // Construct new AlertModel, set class variables, insert into SQLite DB.
                        AlertModel new_alert = new AlertModel();
                        new_alert.setDescription(alertDescription);
                        boolean success = dbHandler.addNewAlert(new_alert);

                        // Test pop-up to verify insertion works correctly.
                        Toast.makeText(AlertFormPage.this, "Successfully added? " + success, Toast.LENGTH_SHORT).show();

                        // Proceeding if all works correctly.
                        openAlertFormReportConfirmationPage();
                    }
                    catch (Exception e) {
                        // Display error message if not working correctly.
                        Toast.makeText(AlertFormPage.this, "Oh no! An error occurred reporting the alert!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void openAlertFormMapsActivityPage(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openAlertFormReportConfirmationPage(){
        Intent intent = new Intent(this, ReportConfirmationPage.class);
        startActivity(intent);
    }
}