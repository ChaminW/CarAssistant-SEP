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
        values.put("note", otherExpense.getNote());

        db.insert(TABLE_OTHEREXPENSE, null, values);
        db.close();
        System.out.println("other expense is added added.");

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
            db.delete(TABLE_OTHEREXPENSE, "id" + "=?", new String[]{String.valueOf(otherExpense.getId())});

            return true;
        } catch (SQLiteException e) {

            return false;
            // should give a error message
        } finally {
            db.close();
        }
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
        note = res.getString(res.getColumnIndex("note"));


        OtherExpense tempOtherExpense = new OtherExpense(id, date, cost, note, odoMeter, category, title, reccurenceType,regNo);
        return tempOtherExpense;
    }

}
