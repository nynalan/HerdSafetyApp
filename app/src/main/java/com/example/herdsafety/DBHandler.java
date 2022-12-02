package com.example.herdsafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.herdsafety.AppObjects.AAlert;

public class DBHandler {
    private final Context context;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBHandler(Context context) {
        // Declaring DBHandler instance and passing context to it.
        this.context = context;
    }

    public DBHandler open() throws SQLException {
        // Opening database, throwing error if unsuccessful.
        this.dbHelper = new DBHelper(this.context);
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        // Closing database.
        this.dbHelper.close();
    }

    public boolean insertAlert(AAlert alert) {
        // Creating variable for data.
        ContentValues values = new ContentValues();
        values.put(DBHelper.DESCRIPTION_COL, alert.getDescription());
        values.put(DBHelper.TYPE_COL, alert.getType());

        Log.d("database_insert", values.toString());

        // Passing data to DB.
        long insert = db.insert(DBHelper.ALERTS_NAME, null, values);
        close();
        return insert != -1;
    }

    public boolean retrieveNearbyAlerts() {
        // Pulling all alerts. (will add location filter in future)
        return true;
    }
}
