package com.bitlabs.sep_mobileapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.data.Reminder;
import com.bitlabs.sep_mobileapp.data.Vehicle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chamin on 5/3/2016.
 */
public class VehicleDAO extends DBhelper{


    public VehicleDAO(Context context) {
        super(context);
    }




    public void setVehicle(Vehicle vehicle) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("regNo", vehicle.getRegNo());
        values.put("model", vehicle.getModel());
        values.put("year", vehicle.getYear());
        values.put("fuelType", vehicle.getFuelType());
        values.put("fuelUnit", vehicle.getFuelUnit());
        values.put("distanceUnit", vehicle.getDistanceUnit());
        values.put("image", vehicle.getImage());

        db.insert(TABLE_VEHICLE, null, values);
        db.close();
        System.out.println("Vehicle details are added.");

    }


    public void updateVehicle(String regNo,Vehicle vehicle) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("regNo", vehicle.getRegNo());
        values.put("model", vehicle.getModel());
        values.put("year", vehicle.getYear());
        values.put("fuelType", vehicle.getFuelType());
        values.put("fuelUnit", vehicle.getFuelUnit());
        values.put("distanceUnit", vehicle.getDistanceUnit());
        values.put("image", vehicle.getImage());

        db.update(TABLE_VEHICLE,values,"regNo = ? ", new String[] { regNo} );
        System.out.println("vehicle updated successfully");

        db.close();


    }

    public ArrayList<Vehicle> getAllVehicle() {
        //give all vehicle details
        ArrayList<Vehicle> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_VEHICLE;
        Cursor res = db.rawQuery(query, null);

        if (res.moveToFirst()) {
            do {
                array_list.add(convertToVehicle(res));

            } while (res.moveToNext());
        }

        db.close();
        System.out.println("Got all vehicle");
        return array_list;
    }

    public List<String> getAllVehicleRegNo() {
        //give all vehicle details
        List<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_VEHICLE;
        Cursor res = db.rawQuery(query, null);

        if (res.moveToFirst()) {
            do {
                array_list.add(res.getString(res.getColumnIndex("regNo")));

            } while (res.moveToNext());
        }

        db.close();
        System.out.println("Got all vehicle");
        return array_list;
    }


    public boolean deleteVehicle(String regNo) {
        // remove existing details . if it is not in db give error.
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_VEHICLE, "regNo" + "=?", new String[]{String.valueOf(regNo)});
            System.out.println("Vehicle deleted");
            return true;
        } catch (SQLiteException e) {

            return false;
            // should give a error message
        } finally {
            db.close();
        }
    }
    public Vehicle getVehicleAsRegNo(String regNo) {
        SQLiteDatabase db;

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_VEHICLE + " WHERE regNo = '" + regNo + "'";
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Vehicle vehicle;
        if (cursor.moveToFirst()) {
            vehicle=convertToVehicle(cursor);
        }
        else {
            vehicle=null;
        }

        return vehicle;
    }


    private Vehicle convertToVehicle(Cursor res) {

        String regNo;
        String model;
        String year;
        String fuelType;
        String fuelUnit;
        String distanceUnit;
        String image;



        regNo = res.getString(res.getColumnIndex("regNo"));
        model = res.getString(res.getColumnIndex("model"));
        year = res.getString(res.getColumnIndex("year"));
        fuelType = res.getString(res.getColumnIndex("fuelType"));
        fuelUnit = res.getString(res.getColumnIndex("fuelUnit"));
        distanceUnit=res.getString(res.getColumnIndex("distanceUnit"));
        image = res.getString(res.getColumnIndex("image"));


        Vehicle tempVehicle = new Vehicle(model,distanceUnit,fuelUnit,fuelType,regNo,year,image);
        return tempVehicle;
    }





}
