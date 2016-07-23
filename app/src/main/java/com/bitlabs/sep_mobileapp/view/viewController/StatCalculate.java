package com.bitlabs.sep_mobileapp.view.viewController;

import android.content.Context;

import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.controller.OtherExpenseDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Chamin on 6/16/2016.
 */
public class StatCalculate {

    FuelFillUpDAO fuelFillUpDAO;
    OtherExpenseDAO otherExpenseDAO;
    ArrayList<FuelFillUp> fillUpArrayList;
    ArrayList<OtherExpense> expenseArrayList;

    String relavantRegNo;

    int last_odo;
    int tot_distansce;
    Double tot_fuel;
    Double tot_cost;
    int tot_fill_ups;

    Double service_cost = 0.0;
    Double maintenance_cost = 0.0;
    Double registration_cost = 0.0;
    Double parking_cost = 0.0;
    Double wash_cost = 0.0;
    Double fine_cost = 0.0;
    Double tunning_cost = 0.0;
    Double insurance_cost = 0.0;

    int avg_fill_up;
    Double avg_fill_up_bill;
    Double avg_expense_per_month;
    Double avg_expense_per_year;
    Double avg_mileage_per_month;
    Double avg_mileage_per_year;
    Double avg_fuel_consumption;


    public StatCalculate(Context context, String relavantRegNo) {


        fuelFillUpDAO = new FuelFillUpDAO(context);
        otherExpenseDAO = new OtherExpenseDAO(context);
        this.relavantRegNo = relavantRegNo;

        fillUpArrayList = fuelFillUpDAO.getFillUpAsRegNo(relavantRegNo);
        expenseArrayList = otherExpenseDAO.getOtherExpenseAsRegNo(relavantRegNo);
        totalStaCalculate();
        expenseCalculate();
    }

    private void totalStaCalculate() {
        try {
            last_odo = fillUpArrayList.get(0).getOdoMeter();

        } catch (Exception e) {
            last_odo = 0;

        }

        try {
            tot_distansce = ((fillUpArrayList.get(0).getOdoMeter()) - (fillUpArrayList.get(fillUpArrayList.size() - 1).getOdoMeter()));
            tot_distansce= Math.abs(tot_distansce);

        } catch (Exception e) {
            tot_distansce = 0;
        }

        try {
            tot_fuel = 0.0;
            for (FuelFillUp fillUp : fillUpArrayList) {
                tot_fuel += fillUp.getAmount();
            }

        } catch (Exception e) {
            tot_fuel = 0.0;
        }

        try {
            tot_cost = 0.0;
            for (FuelFillUp fillUp : fillUpArrayList) {
                tot_cost += fillUp.getCost();
            }
            for (OtherExpense expense : expenseArrayList) {
                tot_cost += expense.getCost();
            }

        } catch (Exception e) {
            tot_cost = 0.0;
        }

        tot_fill_ups = fillUpArrayList.size();

    }


    private void expenseCalculate() {
        for (OtherExpense expense : expenseArrayList) {
            switch (expense.getCategory()) {

                case "Service":
                    service_cost += expense.getCost();
                    break;
                case "Maintenance":
                    maintenance_cost += expense.getCost();
                    break;
                case "Registration":
                    registration_cost += expense.getCost();
                    break;
                case "Parking":
                    parking_cost += expense.getCost();
                    break;
                case "Wash":
                    wash_cost += expense.getCost();
                    break;
                case "Fines":
                    fine_cost += expense.getCost();
                    break;
                case "Tuning":
                    tunning_cost += expense.getCost();
                    break;
                case "Insurance":
                    insurance_cost += expense.getCost();
                    break;

            }
        }


    }

    public int getLast_odo() {


        return last_odo;
    }

    public int getTot_distansce() {


        return tot_distansce;
    }

    public Double getTot_fuel() {

        return tot_fuel;
    }

    public Double getTot_cost() {


        return tot_cost;
    }

    public int getTot_fill_ups() {


        return tot_fill_ups;
    }


    public Double getService_cost() {
        return service_cost;
    }

    public Double getMaintenance_cost() {
        return maintenance_cost;
    }

    public Double getRegistration_cost() {
        return registration_cost;
    }

    public Double getParking_cost() {
        return parking_cost;
    }

    public Double getWash_cost() {
        return wash_cost;
    }

    public Double getFine_cost() {
        return fine_cost;
    }

    public Double getTunning_cost() {
        return tunning_cost;
    }

    public Double getInsurance_cost() {
        return insurance_cost;
    }

    public int getAvg_fill_up() { //per month
        try {
            avg_fill_up = getTot_fill_ups() / Utils.monthsBetween(fillUpArrayList.get(fillUpArrayList.size() - 1).getDate(), fillUpArrayList.get(0).getDate());
        }
        catch (Exception e){
            avg_fill_up=0;
        }
        return avg_fill_up;
    }

    public Double getAvg_fill_up_bill() {
        try {
            Double fillUp_cost = 0.0;
            for (FuelFillUp fillUp : fillUpArrayList) {
                fillUp_cost += fillUp.getCost();
            }
            avg_fill_up_bill = fillUp_cost / getTot_fill_ups();
        }catch (Exception e){
            avg_fill_up_bill=0.0;
        }
        return avg_fill_up_bill;
    }

    public Double getAvg_expense_per_month() {
        try {
            avg_expense_per_month = getTot_cost() / Utils.monthsBetween(fillUpArrayList.get(fillUpArrayList.size() - 1).getDate(), fillUpArrayList.get(0).getDate());
        }catch (Exception e) {
            avg_expense_per_month=0.0;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avg_expense_per_month = Double.valueOf(df.format(avg_expense_per_month));
            return avg_expense_per_month;

    }

    public Double getAvg_expense_per_year() {
        try {
            avg_expense_per_year = getTot_cost() / Utils.yearsBetween(fillUpArrayList.get(fillUpArrayList.size() - 1).getDate(), fillUpArrayList.get(0).getDate());
        }
        catch (Exception e){
            avg_expense_per_year=0.0;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avg_expense_per_year = Double.valueOf(df.format(avg_expense_per_year));
        return avg_expense_per_year;
    }

    public Double getAvg_mileage_per_month() {
        try {
            avg_mileage_per_month = Double.valueOf(getTot_distansce()) / Utils.monthsBetween(fillUpArrayList.get(fillUpArrayList.size() - 1).getDate(), fillUpArrayList.get(0).getDate());
        }catch (Exception e){
            avg_mileage_per_month=0.0;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avg_mileage_per_month = Double.valueOf(df.format(avg_mileage_per_month));
        return avg_mileage_per_month;
    }

    public Double getAvg_mileage_per_year() {
        try {
            avg_mileage_per_year = Double.valueOf(getTot_distansce()) / Utils.yearsBetween(fillUpArrayList.get(fillUpArrayList.size() - 1).getDate(), fillUpArrayList.get(0).getDate());
        }
        catch (Exception e){
            avg_mileage_per_year=0.0;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avg_mileage_per_year = Double.valueOf(df.format(avg_mileage_per_year));
        return avg_mileage_per_year;
    }

    public Double getAvg_fuel_consumption() {
        try {
            avg_fuel_consumption=getTot_distansce()/getTot_fuel();
        }
        catch (Exception e){

            avg_fuel_consumption=0.0;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avg_fuel_consumption = Double.valueOf(df.format(avg_fuel_consumption));
        return avg_fuel_consumption;
    }
}
