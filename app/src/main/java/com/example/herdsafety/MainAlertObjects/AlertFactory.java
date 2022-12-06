
package com.example.herdsafety.MainAlertObjects;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class AlertFactory {

    public static AlertFactory singletonFactory = new AlertFactory();

    public AAlert createAlert(String description, LatLng location, String type) {
        int newAlertId = 0;
        for(AAlert alert: AAlert.alertList) {
            if (alert.getId() > newAlertId) {
                newAlertId = alert.getId() + 1;
            }
        }

        // Log.d("location_test", "LatLng object: " + location);
        // Log.d("location_test", "Latitude: " + location.latitude);
        // Log.d("location_test", "Latitude: " + location.longitude);

        if (type.equals("Caution")) {
            return new CautionAlert(newAlertId, description, (float) location.latitude, (float) location.longitude);
        }
        else if (type.equals("Warning")) {
            return new WarningAlert(newAlertId, description, (float) location.latitude, (float) location.longitude);
        }
        else if (type.equals("Crime")) {
            return new CrimeAlert(newAlertId, description, (float) location.latitude, (float) location.longitude);
        }
        // create the specific type of alert necessary
        return null;
    }

}
