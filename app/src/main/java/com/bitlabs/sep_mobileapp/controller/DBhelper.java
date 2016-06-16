package com.bitlabs.sep_mobileapp.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Chamin on 5/3/2016.
 */
public abstract class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "carAssistant_4.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_FILLUP = "fuelFillUp";
    public static final String TABLE_OTHEREXPENSE = "otherExpense";
    public static final String TABLE_VEHICLE = "vehicle";
    public static final String TABLE_REMINDER = "reminder";


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE_FILLUP + "(" +
                "Id" + " integer primary key autoincrement, " +
                "date" + " text not null, " +
                "odoMeter" + " integer, " +
                "cost" + " real, " +
                "amount" + " real, " +
                "costPerLiter" +" real, " +
                "regNo" + " text not null, " +
                "isFullTank" + " numeric not null," +
                "latitude" + " real, " +
                "longitude " + " real, " +
                "note" + " text " +
                ");");


        db.execSQL("create table "
                + TABLE_OTHEREXPENSE + "(" +
                "Id" + " integer primary key autoincrement, " +
                "regNo"+ " text not null, "+
                "title" + " text not null, " +
                "date" + " text, " +
                "odoMeter" + " integer, " +
                "cost" + " real, " +
                "category" + " text not null, " +
                "reccurenceType" + " text, " +
                "latitude" + " real, " +
                "longitude " + " real, " +
                "note" + " text " +
                ");");



        db.execSQL("create table "
                + TABLE_REMINDER + "(" +
                "Id" + " integer primary key autoincrement, " +
                "time" + " text, " +
                "title" + " text, " +
                "isAlarmOn" + " numeric not null," +
                "notificationType" + " text, " +
                "reccurenceType" + " text " +
                ");");
        db.execSQL("create table "
                + TABLE_VEHICLE + "(" +
                "regNo" + " text primary key , " +
                "model" + " text, " +
                "year" + " text, " +
                "fuelType"+" text," +
                "fuelUnit" + " text, " +
                "distanceUnit" + " text, " +
                "image" + " text " +
                ");");

        Log.d(DBhelper.class.getName(), " data base is created");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBhelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILLUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHEREXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE);

        onCreate(db);


    }

    public  void clearAll(){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("drop TABLE IF EXISTS" + TABLE_FILLUP);
        db.execSQL("drop TABLE IF EXISTS" + TABLE_OTHEREXPENSE);
        db.execSQL("drop TABLE IF EXISTS" + TABLE_REMINDER);
        db.execSQL("drop TABLE IF EXISTS" + TABLE_VEHICLE);



    }



}



