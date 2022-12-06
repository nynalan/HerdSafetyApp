package com.example.herdsafety.AppObjects;

import com.google.android.gms.maps.model.LatLng;

public class CrimeAlert extends AAlert {

    public CrimeAlert(int newAlertId, String description, Float latitude, Float longitude) {
        super(newAlertId, description, "Crime");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
