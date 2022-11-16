
package com.example.herdsafety.AppObjects;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class AlertFactory {

    public static AlertFactory singletonFactory = new AlertFactory();

    public AAlert createAlert(String description, LatLng location, String type) {
        int newAlertId = 0;
        for(AAlert alert: AAlert.alertList) {
            if (alert.getId() > newAlertId) {
                newAlertId = alert.getId() + 1;
            }
        }

        if (type.equals("Caution")) {
            return new CautionAlert(newAlertId, description);
        }
        else if (type.equals("Warning")) {
            return new WarningAlert(newAlertId, description);
        }
        else if (type.equals("Crime")) {
            return new CrimeAlert(newAlertId, description);
        }
        // create the specific type of alert necessary
        return null;
    }

}
