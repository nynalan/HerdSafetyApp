package com.example.herdsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.herdsafety.AppObjects.AAlert;

import java.util.ArrayList;

public class AlertDetailsPage extends AppCompatActivity {

    private Button buttonDone;
    TextView alertDescriptionBox;
    RadioGroup alertTypeRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details_page);

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
        AAlert specifiedAlert = AAlert.alertList.get(getIntent().getExtras().getInt("Alert Index"));

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
}