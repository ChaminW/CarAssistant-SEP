package com.bitlabs.sep_mobileapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chamin on 5/3/2016.
 */
public class OtherExpenseDAO extends DBhelper {


    public OtherExpenseDAO(Context context) {
        super(context);
    }

    public void setOtherExpense(OtherExpense otherExpense) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(otherExpense.getDate());

        values.put("regNo", otherExpense.getRegNo());
        values.put("title", otherExpense.getTitle());
        values.put("date", strDate);
        values.put("odoMeter", otherExpense.getOdoMeter());
        values.put("cost", otherExpense.getCost());
        values.put("category", otherExpense.getCategory());
        values.put("reccurenceType", otherExpense.getReccurenceType());
        values.put("latitude", otherExpense.getLatitude());
        values.put("longitude", otherExpense.getLongitude());
        values.put("note", otherExpense.getNote());

        db.insert(TABLE_OTHEREXPENSE, null, values);
        db.close();
        System.out.println("other expense is added added.");

    }

    public void updateOtherExpense(OtherExpense otherExpense) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(otherExpense.getDate());

        values.put("regNo", otherExpense.getRegNo());
        values.put("title", otherExpense.getTitle());
        values.put("date", strDate);
        values.put("odoMeter", otherExpense.getOdoMeter());
        values.put("cost", otherExpense.getCost());
        values.put("category", otherExpense.getCategory());
        values.put("reccurenceType", otherExpense.getReccurenceType());
        values.put("latitude", otherExpense.getLatitude());
        values.put("longitude", otherExpense.getLongitude());
        values.put("note", otherExpense.getNote());

        db.update(TABLE_OTHEREXPENSE, values, "Id = ? ", new String[]{Integer.toString(otherExpense.getId())});
        System.out.println("Other expense updated successfully");

        db.close();


    }

    public List<OtherExpense> getAllOtherExpense() {
        //give all other Expense details
        List<OtherExpense> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_OTHEREXPENSE;
        Cursor res = db.rawQuery(query, null);

        if (res.moveToFirst()) {
            do {
                array_list.add(convertToOtherExpense(res));

            } while (res.moveToNext());
        }

        db.close();
        return array_list;
    }

    public boolean deleteOtherExpense(OtherExpense otherExpense) {
        // remove existing details . if it is not in db give error.
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_OTHEREXPENSE, "Id" + "=?", new String[]{String.valueOf(otherExpense.getId())});

            return true;
        } catch (SQLiteException e) {

            return false;
            // should give a error message
        } finally {
            db.close();
        }
    }

    public ArrayList<OtherExpense> getOtherExpenseAsRegNo(String regNo) {
        SQLiteDatabase db;
        ArrayList<OtherExpense> array_list = new ArrayList<OtherExpense>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_OTHEREXPENSE + " WHERE regNo = '" + regNo + "' ORDER BY date DESC";
        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                array_list.add(convertToOtherExpense(cursor));
            } while (cursor.moveToNext());
        }
// return quest list

        return array_list;
    }


    public OtherExpense getOtherExpenseAsId(int Id) {
        SQLiteDatabase db;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_OTHEREXPENSE + " WHERE Id = '" + Id + "'";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        OtherExpense otherExpense;
        if (cursor.moveToFirst()) {
            otherExpense = convertToOtherExpense(cursor);
        } else {
            otherExpense = null;
        }
        return otherExpense;
    }


    private OtherExpense convertToOtherExpense(Cursor res) {

        int id;
        String regNo;
        Date date;
        double cost;
        String note;
        int odoMeter;
        String category;
        String title;
        String reccurenceType;
        Double latitude;
        Double longitude;


        id = res.getInt(0);
        regNo = res.getString(res.getColumnIndex("regNo"));
        title = res.getString(res.getColumnIndex("title"));
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        date = null;
        try {
            date = dateF.parse(res.getString(res.getColumnIndex("date")));

        } catch (ParseException e) {
            //do nothing
        }
        odoMeter = res.getInt(res.getColumnIndex("odoMeter"));
        cost = res.getDouble(res.getColumnIndex("cost"));
        category = res.getString(res.getColumnIndex("category"));
        reccurenceType = res.getString(res.getColumnIndex("reccurenceType"));
        latitude = res.getDouble(res.getColumnIndex("latitude"));
        longitude = res.getDouble(res.getColumnIndex("longitude"));
        note = res.getString(res.getColumnIndex("note"));


        OtherExpense tempOtherExpense = new OtherExpense(id, date, cost, note, odoMeter, category, title, reccurenceType, regNo, latitude, longitude);
        return tempOtherExpense;
    }

}
