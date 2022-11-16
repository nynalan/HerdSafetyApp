package com.example.herdsafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;


public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "herdsafetydb";  // Constant database variable.
    private static final int DB_VERSION = 1;

    // Declaring table name and columns.
    private static final String USERS_NAME = "users";
    private static final String US_ID_COL = "id";
    private static final String US_NAME_COL = "username";
    private static final String EMAIL_COL = "email";
    private static final String PW_COL = "password";


    private static final String ALERTS_NAME = "alerts";
    private static final String AL_ID_COL = "id";
    private static final String AL_TITLE_COL = "title";
    private static final String DESCRIPTION_COL = "description";
    private static final String LATITUDE_COL = "latitude";
    private static final String LONGITUDE_COL = "longitude";
    private static final String REPORTEDBY_COL = "reportedby";
    private static final String VERIFICATIONS_COL = "verifications";
    private static final String RADIUS_COL = "radius";
    private static final String TYPE_COL = "type";
    private static final String NOTIFICATIONRADIUS_COL = "notificationradius";
    private static final String LASTUPDATED_COL = "lastupdated";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creates database by executing SQL queries.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Designing queries to create tables.
        String user_query = "CREATE TABLE " + USERS_NAME + " ("
                + US_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + US_NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + PW_COL + " TEXT)";

        String alert_query = "CREATE TABLE " + ALERTS_NAME + " ("
                + AL_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AL_TITLE_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + LATITUDE_COL + " FLOAT,"
                + LONGITUDE_COL + " FLOAT,"
                + REPORTEDBY_COL + " INTEGER,"
                + VERIFICATIONS_COL + " INTEGER,"
                + RADIUS_COL + " REAL,"
                + TYPE_COL + " TEXT,"
                + NOTIFICATIONRADIUS_COL + " TEXT,"
                + LASTUPDATED_COL + " TEXT)";

        // Executing queries.
        db.execSQL(user_query);
        db.execSQL(alert_query);
    }

    // WILL NEED TO REFACTOR TO INCORPORATE ALERTMODEL CLASS.
    // Adding new user to SQLite database.
    public void addNewUser(String username, String email, String password) {
        // Create variable for DB, calling writable method to write new data.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create variable for data.
        ContentValues values = new ContentValues();

        // Pass all key-value pairs to variable.
        values.put(US_NAME_COL, username);
        values.put(EMAIL_COL, email);
        values.put(PW_COL, password);

        // Pass variable to DB.
        db.insert(USERS_NAME, null, values);

        // Close database.
        db.close();
    }

    public boolean addNewAlert(AlertModel alert) {
        // Create variable for DB, calling writable method to write new data.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create variable for data.
        ContentValues values = new ContentValues();

        // Pass all key-value pairs to variable.
        values.put(DESCRIPTION_COL, alert.getDescription());

        Log.d("database_insert", values.toString());

        // Pass variable to DB.
        long insert = db.insert(ALERTS_NAME, null, values);
        db.close();  // Close database.
        return insert != -1;
    }

    // WILL NEED TO REFACTOR TO INCORPORATE ALERTMODEL CLASS.
    // Adding new alert to SQLite database.
    public void addNewAlertInFull(String alertName, String description, Float latitude, Float longitude, Integer reportedBy, Integer verifications, Float radius, String alertType, String notificationRadius, String lastUpdated) {
        // Create variable for DB, calling writable method to write new data.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create variable for data.
        ContentValues values = new ContentValues();

        // Pass all key-value pairs to variable.
        values.put(AL_TITLE_COL, alertName);
        values.put(DESCRIPTION_COL, description);
        values.put(LATITUDE_COL, latitude);
        values.put(LONGITUDE_COL, longitude);
        values.put(REPORTEDBY_COL, reportedBy);
        values.put(VERIFICATIONS_COL, verifications);
        values.put(RADIUS_COL, radius);
        values.put(TYPE_COL, alertType);
        values.put(NOTIFICATIONRADIUS_COL, notificationRadius);
        values.put(LASTUPDATED_COL, lastUpdated);

        // Pass variable to DB.
        db.insert(ALERTS_NAME, null, values);

        // Close database.
        db.close();
    }

    public void deleteAllAlerts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_query = "DELETE FROM Alerts;";
        db.execSQL(delete_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ALERTS_NAME);
        onCreate(db);
    }


    // public List<> outputAlertsInDB(SQLiteDatabase db) {
        // Query to pull alerts.
        // String alert_query = "SELECT * FROM " + ALERTS_NAME + "; ";

        // Executing queries.
        // db.execSQL(alert_query);
    // }
}