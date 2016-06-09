package com.bitlabs.sep_mobileapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.data.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chamin on 5/3/2016.
 */
public class ReminderDAO extends DBhelper {


    public ReminderDAO(Context context) {
        super(context);
    }

    public void setReminder(Reminder reminder) {

        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "INSERT INTO " + TABLE_REMINDER + " VALUES (?,?,?,?,?,?)";
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = dateFormater.format(reminder.getTime());


        values.put("time", strTime);
        values.put("title", reminder.getTitle());
        values.put("isAlarmOn", reminder.getAlarmOn() == true ? "true" : "false");
        values.put("notificationType", reminder.getNotificationType());
        values.put("reccurenceType", reminder.getReccurenceType());

        db.insert(TABLE_REMINDER, null, values);
        db.close();
        System.out.println("reminder added");


    }

    public List<Reminder> getAllReminder() {
        //give all other Expense details
        List<Reminder> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDER;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(convertToReminder(res));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

    public boolean deleteReminder(Reminder reminder) {
        // remove existing details . if it is not in db give error.
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_REMINDER, "id" + "=?", new String[]{String.valueOf(reminder.getId())});
            System.out.println("reminder deleted");
            return true;
        } catch (SQLiteException e) {

            return false;
            // should give a error message
        } finally {
            db.close();
        }
    }


    private Reminder convertToReminder(Cursor res) {

        int id;
        boolean alarmOn;
        String notificationType;
        String alarmTone;
        String title;
        String time;
        String reccurenceType;


        id = res.getInt(0);
        title = res.getString(res.getColumnIndex("title"));
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        Date srtTime = null;
        try {
            srtTime = dateF.parse(res.getString(res.getColumnIndex("time")));

        } catch (ParseException e) {
            //do nothing
        }
        alarmOn = (res.getString(res.getColumnIndex("isAlarmOn"))) == "true" ? true : false;
        reccurenceType = res.getString(res.getColumnIndex("reccurenceType"));
        notificationType = res.getString(res.getColumnIndex("notificationType"));
        alarmTone = "";

        Reminder tempReminder = new Reminder(id, alarmOn, notificationType, alarmTone, title, srtTime, reccurenceType);
        return tempReminder;
    }


}
