package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.CautionSimilarity;

import com.google.android.gms.maps.model.LatLng;

public class CautionAlert extends AAlert {

    public CautionAlert(int newAlertId, String description, Float latitude, Float longitude) {
        super(newAlertId, description, latitude, longitude,"Caution");
        sim_algorithm = new CautionSimilarity();
    }

    public String getFormattedDisplay() {
        return null;
    }
}
