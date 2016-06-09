package com.bitlabs.sep_mobileapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.data.FuelFillUp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chamin on 5/3/2016.
 */
public class FuelFillUpDAO extends DBhelper {


    public FuelFillUpDAO(Context context) {
        super(context);
    }

    public void setFillUp(FuelFillUp fuelFillUp) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //String query = "INSERT INTO " + TABLE_FILLUP + " VALUES (?,?,?,?,?,?,?,?,?)";

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(fuelFillUp.getDate());

        values.put("date", strDate);
        values.put("odoMeter", fuelFillUp.getOdoMeter());
        values.put("cost", fuelFillUp.getCost());
        values.put("amount", fuelFillUp.getAmount());
        values.put("costPerLiter", fuelFillUp.getCostPerLiter());
        values.put("regNo", fuelFillUp.getRegNo());
        values.put("isFullTank", fuelFillUp.getIsFullTank() == true ? "true" : "false");
        values.put("latitude", fuelFillUp.getLatitide());
        values.put("longitude ", fuelFillUp.getLongitude ());
        values.put("note", fuelFillUp.getNote());

        db.insert(TABLE_FILLUP, null, values);
        System.out.println("fuelFill up added successfully");

        db.close();


    }

    public List<FuelFillUp> getAllFillUps() {
        //give fill up details
        List<FuelFillUp> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_FILLUP;
        Cursor res = db.rawQuery(query, null);
        // looping through all rows and adding to list
        if (res.moveToFirst()) {
            do {
                array_list.add(convertToFuelFillUp(res));

            } while (res.moveToNext());
        }
// return quest list
        db.close();
        return array_list;
    }

    public boolean deleteFuelFillUp(FuelFillUp fuelFillUp) {
        // remove existing details . if it is not in db give error.
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_FILLUP, "id" + "=?", new String[]{String.valueOf(fuelFillUp.getId())});
            String msg = "FuelFillUp detail was deleted";
            System.out.println(msg);
            return true;
        } catch (SQLiteException e) {
            String msg = "FuelFillUp detail is invalid. It can be already deleted";
            System.out.println(msg);
            return false;
            // should give a error message
        } finally {
            db.close();
        }
    }

    private FuelFillUp convertToFuelFillUp(Cursor res) {
        int id;
        Date date;
        double cost;
        String note;
        int odoMeter;
        String regNo;
        double amount;
        double costPerLiter;
        double latitude;
        double longitude;
        boolean isFullTank;

        id = res.getInt(0);
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        date = null;
        try {
            date = dateF.parse(res.getString(res.getColumnIndex("date")));

        } catch (ParseException e) {
            //do nothing
        }
        odoMeter = res.getInt(res.getColumnIndex("odoMeter"));
        cost = res.getDouble(res.getColumnIndex("cost"));
        amount = res.getDouble(res.getColumnIndex("amount"));
        costPerLiter = res.getDouble(res.getColumnIndex("costPerLiter"));
        regNo = res.getString(res.getColumnIndex("regNo"));
        isFullTank = (res.getString(res.getColumnIndex("isFullTank"))) == "true" ? true : false;
        latitude = res.getDouble(res.getColumnIndex("latitude"));
        longitude  = res.getDouble(res.getColumnIndex("longitude"));
        note = res.getString(res.getColumnIndex("note"));


        FuelFillUp tempFuelFillUp = new FuelFillUp(id, date, odoMeter, cost, amount, costPerLiter, regNo, isFullTank, latitude,longitude, note);
        return tempFuelFillUp;
    }


}
