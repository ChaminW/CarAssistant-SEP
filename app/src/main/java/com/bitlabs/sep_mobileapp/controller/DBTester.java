package com.bitlabs.sep_mobileapp.controller;

import android.content.Context;

import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.data.Reminder;
import com.bitlabs.sep_mobileapp.data.Vehicle;

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
        this.context = context;


        fuelFillUpDAO = new FuelFillUpDAO(context);
        otherExpenseDAO = new OtherExpenseDAO(context);
        reminderDAO = new ReminderDAO(context);
        vehicleDAO = new VehicleDAO(context);


    }
    public void clearAll(){

        fuelFillUpDAO.clearAll();
    }

    public void test() {


        Date date = new Date();
        fuelFillUpDAO.setFillUp(new FuelFillUp(1, date, 1, 20100, 1, 1123, "Diesel", true, 6.7, 80.912, "none"));
        fuelFillUpDAO.setFillUp(new FuelFillUp(1, date, 1, 20100, 1, 1123, "Diesel", true, 6.31, 81.3123, "none"));

        List<FuelFillUp> list1 = fuelFillUpDAO.getAllFillUps();
        for (int i = 0; i < list1.size(); i++) {
            System.out.println(list1.get(i).getRegNo());
        }

        fuelFillUpDAO.updateFillUp(new FuelFillUp(2, date, 9, 99, 99, 9999, "ab-cdef", true, 6.9331, 81.93123, "update"));
        FuelFillUp fuelFillUp = fuelFillUpDAO.getFillUpAsId(2);
        System.out.println(fuelFillUp.getRegNo() + " id of get by id");

        List<FuelFillUp> list12 = fuelFillUpDAO.getFillUpAsRegNo("ab-cdef");
        for (int i = 0; i < list12.size(); i++) {
            System.out.println(list12.get(i).getRegNo() + " reg no get by reg no");
        }

        //fuelFillUpDAO.deleteFuelFillUp(new FuelFillUp(1,date,1,20100,1,1123,"Diesel",true,6.27,80.3912,"none"));
        //fuelFillUpDAO.deleteFuelFillUp(new FuelFillUp(1,date,1,123232,1,22313,"Diesel",true,6.47,80.2912,"none"));


        otherExpenseDAO.setOtherExpense(new OtherExpense(1, date, 123132923, "none", 13, "Service", "serrvoi", "One-time cost", "dg-1244", 6.12, 80.12));
        otherExpenseDAO.setOtherExpense(new OtherExpense(1, date, 23099912, "none", 22341, "Service", "xxxxxx", "One-time cost", "xd-1244", 6.8, 81.3));
        otherExpenseDAO.updateOtherExpense(new OtherExpense(2, date, 23099912, "none", 22341, "Service", "xxxxxx", "One-time cost", "xd-1244", 6.8, 81.3));

        List<OtherExpense> list2 = otherExpenseDAO.getAllOtherExpense();
        for (OtherExpense model : list2) {
            System.out.println(model.getRegNo());
        }
        otherExpenseDAO.getOtherExpenseAsId(2);
        otherExpenseDAO.getOtherExpenseAsRegNo("xd-1244");

        //otherExpenseDAO.deleteOtherExpense(new OtherExpense(1,date,23099912,"none",22341,"Service","xxxxxx","One-time cost","xd-1244",6.0,80.0));


        reminderDAO.setReminder(new Reminder(1, true, "Notification only", "", "service", date, "One-time"));
        reminderDAO.setReminder(new Reminder(1, true, "Notification only", "", "xxxxservice", date, "One-time"));
        reminderDAO.updateRemider(new Reminder(2, true, "Notification only", "", "xxxxsdservice", date, "One-time"));

        List<Reminder> list3 = reminderDAO.getAllReminder();
        for (Reminder model : list3) {
            System.out.println(model.getId());
        }
        //reminderDAO.getReminderAsId(2);

        //reminderDAO.deleteReminder(new Reminder(1, true, "Notification only", "", "xxxxservice", date, "One-time"));


        //vehicleDAO.setVehicle(new Vehicle("civic", "km", "Liter", "Diesel", "as-12334", "9121", ""));
        //vehicleDAO.setVehicle(new Vehicle("nissan","km","Liter","Diesel","qw-s12334","91211",""));
        //vehicleDAO.updateVehicle("as-12334", new Vehicle("civic", "km", "Liter", "Diesel", "xx-12334", "9121", ""));


        List<Vehicle> list4 = vehicleDAO.getAllVehicle();
        for (Vehicle model : list4) {
            System.out.println(model.getRegNo());
        }
        vehicleDAO.getVehicleAsRegNo("as-12334");
        vehicleDAO.deleteVehicle("");

    }


}
