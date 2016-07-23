package com.bitlabs.sep_mobileapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitlabs.sep_mobileapp.data.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Chamin on 5/3/2016.
 */
public class ReminderDAO extends DBhelper {


    public ReminderDAO(Context context) {
        super(context);
    }

    public boolean setReminder(Reminder reminder) {

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

        return true;
    }

    public boolean updateRemider(Reminder reminder) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = dateFormater.format(reminder.getTime());

        values.put("time", strTime);
        values.put("title", reminder.getTitle());
        values.put("isAlarmOn", reminder.getAlarmOn() == true ? "true" : "false");
        values.put("notificationType", reminder.getNotificationType());
        values.put("reccurenceType", reminder.getReccurenceType());

        db.update(TABLE_REMINDER, values, "Id = ? ", new String[]{Integer.toString(reminder.getId())});
        System.out.println("reminder updated successfully");

        db.close();

        return true;
    }


    public ArrayList<Reminder> getAllReminder() {
        //give all other Expense details
        ArrayList<Reminder> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDER+" ORDER BY time DESC";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(convertToReminder(res));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

    public boolean deleteReminder(int Id) {
        // remove existing details . if it is not in db give error.
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_REMINDER, "id" + "=?", new String[]{String.valueOf(Id)});
            System.out.println("reminder deleted");
            return true;
        } catch (SQLiteException e) {

            return false;
            // should give a error message
        } finally {
            db.close();
        }
    }


    public Reminder getReminderAsId(int Id) {
        SQLiteDatabase db;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_REMINDER + " WHERE Id = '" + Id + "'";

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        Reminder reminder = convertToReminder(cursor);

        return reminder;
    }


    private Reminder convertToReminder(Cursor res) {

        int id;
        boolean alarmOn;
        String notificationType;
        String alarmTone;
        String title;
        Date srtTime = null;
        String reccurenceType;


        id = res.getInt(0);
        title = res.getString(res.getColumnIndex("title"));
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");

        try {
            srtTime = dateF.parse(res.getString(res.getColumnIndex("time")));

        } catch (ParseException e) {
            //do nothing
        }
        alarmOn = (res.getString(res.getColumnIndex("isAlarmOn"))).equals("true") ? true : false;
        reccurenceType = res.getString(res.getColumnIndex("reccurenceType"));
        notificationType = res.getString(res.getColumnIndex("notificationType"));
        alarmTone = "";

        Reminder tempReminder = new Reminder(id, alarmOn, notificationType, alarmTone, title, srtTime, reccurenceType);
        return tempReminder;
    }


}
