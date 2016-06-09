package com.bitlabs.sep_mobileapp.controller;

import android.content.Context;

import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.data.Reminder;
import com.bitlabs.sep_mobileapp.data.Vehicle;
import com.bitlabs.sep_mobileapp.view.FillUpLogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Chamin on 6/7/2016.
 */
public class DBTester {

    Context context;
    FuelFillUpDAO fuelFillUpDAO;
    OtherExpenseDAO otherExpenseDAO;
    ReminderDAO reminderDAO;
    VehicleDAO vehicleDAO;

    public DBTester(Context context) {
        this.context =context;

        fuelFillUpDAO = new FuelFillUpDAO(context);
        otherExpenseDAO = new OtherExpenseDAO(context);
        reminderDAO = new ReminderDAO(context);
        vehicleDAO = new VehicleDAO(context);
    }

    public void test(){


        Date date = new Date();
        fuelFillUpDAO.setFillUp(new FuelFillUp(1,date,1,20100,1,1123,"Diesel",true,6.7,80.912,"none"));


        List<FuelFillUp> list1=fuelFillUpDAO.getAllFillUps();
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i).getId());
        }

        fuelFillUpDAO.deleteFuelFillUp(new FuelFillUp(1,date,1,20100,1,1123,"Diesel",true,6.27,80.3912,"none"));
        fuelFillUpDAO.deleteFuelFillUp(new FuelFillUp(1,date,1,123232,1,22313,"Diesel",true,6.47,80.2912,"none"));


        otherExpenseDAO.setOtherExpense(new OtherExpense(1,date,123132923,"none",13,"Service","serrvoi","One-time cost","dg-1244"));
        otherExpenseDAO.setOtherExpense(new OtherExpense(1,date,23099912,"none",22341,"Service","xxxxxx","One-time cost","xd-1244"));

        List<OtherExpense> list2 = otherExpenseDAO.getAllOtherExpense();
        for(OtherExpense model : list2) {
            System.out.println(model.getId());
        }

        otherExpenseDAO.deleteOtherExpense(new OtherExpense(1,date,23099912,"none",22341,"Service","xxxxxx","One-time cost","xd-1244"));



        reminderDAO.setReminder(new Reminder(1,true,"Notification only","","service",date,"One-time"));
        reminderDAO.setReminder(new Reminder(1,true,"Notification only","","xxxxservice",date,"One-time"));

        List<Reminder> list3 = reminderDAO.getAllReminder();
        for(Reminder model : list3) {
            System.out.println(model.getId());
        }
        reminderDAO.deleteReminder(new Reminder(1,true,"Notification only","","xxxxservice",date,"One-time"));






    }


}
