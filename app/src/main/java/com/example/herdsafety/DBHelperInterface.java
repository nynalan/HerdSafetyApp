package com.example.herdsafety;

import android.database.sqlite.SQLiteDatabase;
import com.example.herdsafety.AppObjects.AAlert;

// Declaring interface for expected general SQL database structure.
interface DBHelperInterface {
    void onCreate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}