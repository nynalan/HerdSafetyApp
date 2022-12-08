package com.example.herdsafety.MainAlertObjects;

import com.example.herdsafety.Similarity.CrimeSimilarity;

import com.google.android.gms.maps.model.LatLng;

public class CrimeAlert extends AAlert {

    public CrimeAlert(int newAlertId, String description, Float latitude, Float longitude) {
        super(newAlertId, description, latitude, longitude,"Crime");
        sim_algorithm = new CrimeSimilarity();
    }

}
