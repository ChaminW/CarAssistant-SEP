package com.bitlabs.sep_mobileapp.view.viewController;

import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Chamin on 6/8/2016.
 */
public class dateFromPicker {

    public static java.util.Date getDate(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
