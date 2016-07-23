package com.bitlabs.sep_mobileapp.view.viewController;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chamin on 6/8/2016.
 */
public class Utils {

    public static java.util.Date getDateFromPicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public static int monthsBetween(Date s1, Date s2) {
        final Calendar d1 = Calendar.getInstance();
        d1.setTime(s1);
        final Calendar d2 = Calendar.getInstance();
        d2.setTime(s2);
        int diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
        if(diff==0){diff=1;}
        return diff;
    }
    public static int yearsBetween(Date s1, Date s2) {
        final Calendar d1 = Calendar.getInstance();
        d1.setTime(s1);
        final Calendar d2 = Calendar.getInstance();
        d2.setTime(s2);
        int diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR));
        if(diff==0){diff=1;}
        System.out.println(s1.toString()+" "+s2.toString()+" "+diff);
        return diff;

    }

    public static int monthsBetweenDates(Date s1, Date s2) {
        int diff;
        if(s1.after(s2)) {

            final Calendar d1 = Calendar.getInstance();
            d1.setTime(s1);
            final Calendar d2 = Calendar.getInstance();
            d2.setTime(s2);

            diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);

            if (diff == 0) {
                diff = 1;
            }
            return diff;
        }
        else {
            return Integer.parseInt(null);
        }

    }

}
