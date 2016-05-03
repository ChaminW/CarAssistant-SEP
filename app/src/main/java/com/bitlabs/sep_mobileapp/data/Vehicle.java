package com.bitlabs.sep_mobileapp.data;

/**
 * Created by Chamin on 5/3/2016.
 */
public class Vehicle {

    private String model;
    private String distanceUnit;
    private String fuelUnit;
    private String licenseNo;
    private String year;

    public Vehicle(String model, String distanceUnit, String fuelUnit, String licenseNo, String year) {
        this.model = model;
        this.distanceUnit = distanceUnit;
        this.fuelUnit = fuelUnit;
        this.licenseNo = licenseNo;
        this.year = year;
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

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
