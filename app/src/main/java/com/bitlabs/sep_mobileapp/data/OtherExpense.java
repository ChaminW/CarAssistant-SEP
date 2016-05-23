package com.bitlabs.sep_mobileapp.data;

import java.util.Date;

/**
 * Created by Chamin on 5/3/2016.
 */
public class OtherExpense extends Expense{

    private String category;
    private String title;
    private String reccurenceType;

    public OtherExpense(int id, Date date, double cost, String note, int odoMeter, String category, String title, String reccurenceType) {
        super(id, date, cost, note, odoMeter);
        this.category = category;
        this.title = title;
        this.reccurenceType = reccurenceType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReccurenceType() {
        return reccurenceType;
    }

    public void setReccurenceType(String reccurenceType) {
        this.reccurenceType = reccurenceType;
    }
}
