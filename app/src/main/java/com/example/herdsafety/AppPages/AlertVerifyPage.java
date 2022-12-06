package com.example.herdsafety.AppPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.herdsafety.Database.DBHandler;
import com.example.herdsafety.MainAlertObjects.AAlert;
import com.example.herdsafety.MainAlertObjects.AlertFactory;
import com.example.herdsafety.R;

public class AlertVerifyPage extends AppCompatActivity {

    private Button buttonVerifyOld;
    private Button buttonSubmitNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_verify_page);

        DBHandler dbHandler = new DBHandler(this);

        //If verified, new alert is not added instead the verified alert is kept in the database
        buttonVerifyOld = (Button) findViewById(R.id.buttonVerifyOld);
        buttonVerifyOld.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoReportConfirmationPage();
            }
        });

        //If submitted a new one, alert description and type gets passed from the alertFormPage
        //and than added to the database
        buttonSubmitNew = (Button) findViewById(R.id.buttonSubmitNew);
        buttonSubmitNew.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoReportConfirmationPage();
                AAlert alertPassed = AlertFactory.singletonFactory.createAlert(
                        getIntent().getExtras().getString("Alert Description"),
                        null,
                        getIntent().getExtras().getString("Alert Type"));
                boolean success = dbHandler.addNewAlert(alertPassed);

                // Test pop-up to verify insertion works correctly.
                Toast.makeText(AlertVerifyPage.this, "Successfully added? " + success, Toast.LENGTH_SHORT).show();
            }
        });

        //Description of the similar alert pops up in this box
        TextView alertDescriptionBox = findViewById(R.id.editTextTextMultiLine);

        //Get the details for the similar alert
        for (AAlert alert: AAlert.alertList) {
            if(alert.getId() == getIntent().getExtras().getInt("Alert id")){
                alertDescriptionBox.setText(alert.getDescription());

                RadioGroup alertTypeRadio = findViewById(R.id.radioGroup_alertLevel);
                if(alert.getType() == "Caution"){
                    alertTypeRadio.check(R.id.radioButton_caution);
                }
                else if(alert.getType() == "Warning"){
                    alertTypeRadio.check(R.id.radioButton_warning);
                }
                else if(alert.getType() == "Crime"){
                    alertTypeRadio.check(R.id.radioButton_crime);
                }
            }
        }


    }

    public void gotoReportConfirmationPage(){
        Intent intent = new Intent(this, ReportConfirmationPage.class);
        startActivity(intent);
    }

}