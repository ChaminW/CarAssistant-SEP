package com.bitlabs.sep_mobileapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
public class OtherExpenseDAO extends DBhelper{


    public OtherExpenseDAO(Context context) {
        super(context);
    }
    public void setOtherExpense(OtherExpense otherExpense) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_OTHEREXPENSE + " VALUES (?,?,?,?,?,?,?,?,?,?)";

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(otherExpense.getDate());

        db.execSQL(query, new String[]{
                String.valueOf(otherExpense.getId()),
                otherExpense.getTitle(),
                strDate,
                String.valueOf(otherExpense.getOdoMeter()),
                String.valueOf(otherExpense.getCost()),
                otherExpense.getCategory(),
                otherExpense.getReccurenceType(),
                otherExpense.getNote()
        });
        db.close();


    }

    public List<OtherExpense> getAllOtherExpense() {
        //give all other Expense details
        List<OtherExpense> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM "+TABLE_OTHEREXPENSE;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(convertToOtherExpense(res));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }



    private OtherExpense convertToOtherExpense(Cursor res) {

        int id;
        Date date;
        double cost;
        String note;
        int odoMeter;
        String category;
        String title;
        String reccurenceType;


        id =res.getInt(res.getColumnIndex("id"));
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
        category=res.getString(res.getColumnIndex("category"));
        reccurenceType=res.getString(res.getColumnIndex("reccurenceType"));
        note=res.getString(res.getColumnIndex("note"));


        OtherExpense tempOtherExpense = new OtherExpense( id,  date,  cost,  note,  odoMeter,  category,  title,  reccurenceType);
        return tempOtherExpense;
    }

}
