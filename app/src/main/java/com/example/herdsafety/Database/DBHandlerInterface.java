package com.example.herdsafety.Database;

import android.database.sqlite.SQLiteDatabase;
import com.example.herdsafety.MainAlertObjects.AAlert;

import java.util.ArrayList;

interface DBHandlerInterface {
    //Data object access pattern, covers both the interface and the corresponding file (DBHandler.java)

    void onCreate(SQLiteDatabase db);
    void addNewUser(String username, String email, String password);  // Placeholder. Will be refactored.
    boolean addNewAlert(AAlert alert);
    void addNewAlertInFull(String alertName, String description, float latitude,
                           float longitude, int reportedBy, int verifications,
                           float radius, String alertType, float notificationRadius,
                           String lastUpdated);  // Placeholder. Will be refactored.
    void deleteAllAlerts();
    ArrayList<AAlert> retrieveNearbyAlerts();
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
