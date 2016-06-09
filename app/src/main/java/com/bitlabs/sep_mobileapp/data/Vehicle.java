package com.bitlabs.sep_mobileapp.data;

/**
 * Created by Chamin on 5/3/2016.
 */
public class Vehicle {

    private String model;
    private String distanceUnit;
    private String fuelUnit;
    private String fuelType;
    private String regNo;
    private String year;

    public Vehicle(String model, String distanceUnit, String fuelUnit, String fuelType, String regNo, String year) {
        this.model = model;
        this.distanceUnit = distanceUnit;
        this.fuelUnit = fuelUnit;
        this.fuelType = fuelType;
        this.regNo = regNo;
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String fuelUnit) {
        this.fuelUnit = fuelUnit;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
