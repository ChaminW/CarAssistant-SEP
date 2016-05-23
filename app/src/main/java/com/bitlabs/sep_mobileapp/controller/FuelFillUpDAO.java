package com.bitlabs.sep_mobileapp.controller;

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
        String query = "INSERT INTO " + TABLE_FILLUP + " VALUES (?,?,?,?,?,?,?,?,?,?)";

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(fuelFillUp.getDate());

        db.execSQL(query, new String[]{
                String.valueOf(fuelFillUp.getId()),
                strDate,
                String.valueOf(fuelFillUp.getOdoMeter()),
                String.valueOf(fuelFillUp.getCost()),
                String.valueOf(fuelFillUp.getAmount()),
                String.valueOf(fuelFillUp.getCostPerLiter()),
                fuelFillUp.getFuelType(),
                fuelFillUp.getIsFullTank() == true ? "true" : "false",
                fuelFillUp.getLocation(),
                fuelFillUp.getNote()
        });
        db.close();


    }

    public List<FuelFillUp> getAllFillUps() {
        //give fill up details
        List<FuelFillUp> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM fuelFillUp";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(convertToFuelFillUp(res));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

    public boolean deleteFuelFillUp(FuelFillUp fuelFillUp) {
        // remove existing details . if it is not in db give error.
        SQLiteDatabase db= this.getWritableDatabase();
        try {
            db.delete(TABLE_FILLUP,"id" + "=?", new String[]{String.valueOf(fuelFillUp.getId())});
            String msg ="FuelFillUp detail was deleted";
            // toasting
            return true;
        }
        catch (SQLiteException e) {
            String msg = "FuelFillUp detail is invalid. It can be already deleted";
            return false;
            // should give a error message
        }
        finally {
            db.close();
        }
    }

    private FuelFillUp convertToFuelFillUp(Cursor res) {
        int id;
        Date date;
        double cost;
        String note;
        int odoMeter;
        String fuelType;
        double amount;
        double costPerLiter;
        String location;
        boolean isFullTank;

        id =res.getInt(res.getColumnIndex("id"));
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
        fuelType=res.getString(res.getColumnIndex("fuelType"));
        isFullTank= (res.getString(res.getColumnIndex("isFullTank")))=="true"? true  : false;
        location=res.getString(res.getColumnIndex("location"));
        note=res.getString(res.getColumnIndex("note"));


        FuelFillUp tempFuelFillUp = new FuelFillUp(id,date,odoMeter,cost,amount,costPerLiter,fuelType,isFullTank,location,note);
        return tempFuelFillUp;
    }


}
