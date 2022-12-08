package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.WarningSimilarity;

import com.google.android.gms.maps.model.LatLng;

public class WarningAlert extends AAlert{

    public WarningAlert(int newAlertId, String description, Float latitude, Float longitude) {
        super(newAlertId, description, latitude, longitude, "Warning");
        sim_algorithm = new WarningSimilarity();
    }

}
