package com.bitlabs.sep_mobileapp.data;

import java.util.Date;

/**
 * Created by Chamin on 5/3/2016.
 */
public class FuelFillUp extends Expense {

    private String fuelType;
    private double amount;
    private double costPerLiter;
    private String location;
    private boolean isFullTank;


    public FuelFillUp(int id, Date date,int odoMeter, double cost, double amount, double costPerLiter, String fuelType,  boolean isFullTank, String location, String note) {
        super(id, date, cost, note, odoMeter);
        this.isFullTank = isFullTank;
        this.fuelType = fuelType;
        this.amount = amount;
        this.costPerLiter = costPerLiter;
        this.location = location;
    }


    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getIsFullTank() {
        return isFullTank;
    }

    public void setIsFullTank(boolean isFullTank) {
        this.isFullTank = isFullTank;
    }
}
