package com.example.herdsafety.AppObjects;

import com.google.android.gms.maps.model.LatLng;

public class WarningAlert extends AAlert{

    public WarningAlert(int newAlertId, String description, Float latitude, Float longitude) {
        super(newAlertId, description, "Warning");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
