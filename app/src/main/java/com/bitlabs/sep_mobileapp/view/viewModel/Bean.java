package com.bitlabs.sep_mobileapp.view.viewModel;

/**
 * Created by apple on 15/03/16.
 */
public class Bean {

    private String image;
    private String model;
    private String regNo;
    private String year;
    private String fuelType;
    private String fuelUnit;




    public Bean(String image, String model, String regNo, String year, String fuelType, String fuelUnit) {
        this.image = image;
        this.model = model;
        this.regNo = regNo;
        this.year = year;
        this.fuelType = fuelType;
        this.fuelUnit = fuelUnit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String fuelUnit) {
        this.fuelUnit = fuelUnit;
    }


}
