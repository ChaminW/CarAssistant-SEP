package com.bitlabs.sep_mobileapp.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chamin on 5/3/2016.
 */
public abstract class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "130648V.db";
    public static final String TABLE_FILLUP_ = "fillUp";




    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}



