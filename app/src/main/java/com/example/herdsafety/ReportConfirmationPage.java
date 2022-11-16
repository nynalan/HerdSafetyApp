package com.example.herdsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportConfirmationPage extends AppCompatActivity {

    private Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_confirmation_page);
        buttonDone = (Button) findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoMainPage();
            }
        });
    }

    public void gotoMainPage(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}