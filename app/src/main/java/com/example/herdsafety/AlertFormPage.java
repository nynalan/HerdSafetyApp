package com.example.herdsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlertFormPage extends AppCompatActivity {

    private Button buttonCancel;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_form_page);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openAlertFormMapsActivityPage();
            }
        });

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openAlertFormReportConfirmationPage();
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