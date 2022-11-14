package com.example.herdsafety;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


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
    private static final String LOCATION_COL = "location";
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
                + LOCATION_COL + " BLOB,"
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

    // Adding new user to SQLite database.

    // Adding new alert to SQLite database.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
