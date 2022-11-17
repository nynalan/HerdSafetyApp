package com.example.herdsafety;

import android.database.sqlite.SQLiteDatabase;
import com.example.herdsafety.AppObjects.AAlert;

interface DBHandlerInterface {
    void onCreate(SQLiteDatabase db);
    void addNewUser(String username, String email, String password);  // Placeholder. Will be refactored.
    boolean addNewAlert(AAlert alert);
    void addNewAlertInFull(String alertName, String description, Float latitude,
                           Float longitude, Integer reportedBy, Integer verifications,
                           Float radius, String alertType, String notificationRadius,
                           String lastUpdated);  // Placeholder. Will be refactored.
    void deleteAllAlerts();
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}