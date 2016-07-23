package com.bitlabs.sep_mobileapp.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.controller.OtherExpenseDAO;
import com.bitlabs.sep_mobileapp.controller.VehicleDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Chart3Fragment extends Fragment {
    public static final String PREFS_NAME = "MyPrefsFile";
    private String relevantRegNo;

    View view;

    OtherExpenseDAO otherExpenseDAO;
    FuelFillUpDAO fuelFillUpDAO;

    double service_cost = 0.0;
    double maintenance_cost = 0.0;
    double registration_cost = 0.0;
    double parking_cost = 0.0;
    double wash_cost = 0.0;
    double fine_cost = 0.0;
    double tunning_cost = 0.0;
    double insurance_cost = 0.0;
    double  tot_fuel=0.0;

    public Chart3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restorePreferences();
        view = inflater.inflate(R.layout.fragment_chart3, container, false);
        // Inflate the layout for this fragment

        otherExpenseDAO=new OtherExpenseDAO(getActivity());
        fuelFillUpDAO = new FuelFillUpDAO(getActivity());

        plot();

        return view;
    }

    private void restorePreferences() {
        // Restore preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
        System.out.println(relevantRegNo+" got the reg no");
    }

    private boolean plot() {

        expenseCalculate();
        PieChart pieChart = (PieChart) view.findViewById(R.id.chart3);
        // creating list of entry
        ArrayList<Entry> entries = new ArrayList<>();
        // creating labels

        Double d=1.0;
        entries.add(new Entry((float) service_cost,0));
        entries.add(new Entry((float) maintenance_cost,1));
        entries.add(new Entry((float) registration_cost,2));
        entries.add(new Entry((float) parking_cost,3));
        entries.add(new Entry((float) wash_cost,4));
        entries.add(new Entry((float) fine_cost,5));
        entries.add(new Entry((float) tunning_cost,6));
        entries.add(new Entry((float) insurance_cost,7));
        entries.add(new Entry((float) tot_fuel,8));





        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Service");
        labels.add("Maintenance");
        labels.add("Registration");
        labels.add("Parking");
        labels.add("Wash");
        labels.add("Fines");
        labels.add("Tuning");
        labels.add("Insurance");
        labels.add("Fuel");






        PieDataSet dataset = new PieDataSet(entries, "Cost of expense");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); // set the color

        PieData data = new PieData(labels, dataset); // initialize Piedata
        pieChart.setData(data); //set data into chart

        pieChart.setDescription("Expense Cost variation");  // set the description
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
        return true;
    }

    private void expenseCalculate() {
        ArrayList<OtherExpense> expenseArrayList=otherExpenseDAO.getOtherExpenseAsRegNo(relevantRegNo);
        ArrayList<FuelFillUp> fillUpArrayList= fuelFillUpDAO.getFillUpAsRegNo(relevantRegNo);
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

        for (FuelFillUp fillUp : fillUpArrayList) {
            tot_fuel += fillUp.getCost();
        }


    }
}
