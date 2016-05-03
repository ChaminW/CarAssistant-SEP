package com.bitlabs.sep_mobileapp.data;

import java.util.Date;

/**
 * Created by Chamin on 5/3/2016.
 */
public class Expense {

    private Date date;
    private double cost;
    private String note;
    private int odoMeter;

    public Expense(Date date, double cost, String note, int odoMeter) {
        this.date = date;
        this.cost = cost;
        this.note = note;
        this.odoMeter = odoMeter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOdoMeter() {
        return odoMeter;
    }

    public void setOdoMeter(int odoMeter) {
        this.odoMeter = odoMeter;
    }
}
