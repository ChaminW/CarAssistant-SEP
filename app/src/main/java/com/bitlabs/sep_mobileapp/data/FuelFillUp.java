package com.bitlabs.sep_mobileapp.data;

import java.util.Date;

/**
 * Created by Chamin on 5/3/2016.
 */
public class FuelFillUp extends Expense {


    private double amount;
    private double costPerLiter;

    private boolean isFullTank;


    public FuelFillUp(int id, Date date,int odoMeter, double cost, double amount, double costPerLiter, String regNo,  boolean isFullTank, double latitide, double longitude, String note) {
        super(id, date, cost, note, odoMeter,regNo,latitide,longitude);
        this.isFullTank = isFullTank;

        this.amount = amount;
        this.costPerLiter = costPerLiter;

    }



    public boolean isFullTank() {
        return isFullTank;
    }

    public void setFullTank(boolean fullTank) {
        isFullTank = fullTank;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCostPerLiter() {
        return costPerLiter;
    }

    public void setCostPerLiter(double costPerLiter) {
        this.costPerLiter = costPerLiter;
    }



    public boolean getIsFullTank() {
        return isFullTank;
    }

    public void setIsFullTank(boolean isFullTank) {
        this.isFullTank = isFullTank;
    }
}
