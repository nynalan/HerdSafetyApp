package com.example.herdsafety.AppObjects;

import com.google.android.gms.maps.model.LatLng;

public class CautionAlert extends AAlert {

    public CautionAlert(int newAlertId, String description, Float latitude, Float longitude) {
        super(newAlertId, description, latitude, longitude,"Caution");
    }

    @Override
    public String getFormattedDisplay() {
        return null;
    }
}
