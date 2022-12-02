package com.example.herdsafety;

import com.example.herdsafety.AppObjects.AAlert;

// Declaring interface for expected database methods.
interface DBHandlerInterface {
    DBHandler open();
    void close();
    boolean insertAlert(AAlert alert);
    boolean retrieveNearbyAlerts();
}
