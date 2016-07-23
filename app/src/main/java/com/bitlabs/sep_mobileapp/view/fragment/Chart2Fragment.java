package com.bitlabs.sep_mobileapp.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Chart2Fragment extends Fragment {

    View view;
    FuelFillUpDAO fuelFillUpDAO;

    public static final String PREFS_NAME = "MyPrefsFile";
    private String relevantRegNo;


    public Chart2Fragment() {
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
        view = inflater.inflate(R.layout.fragment_chart2, container, false);
        // Inflate the layout for this fragment
        fuelFillUpDAO =new FuelFillUpDAO(getActivity());

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

        ArrayList<FuelFillUp> list=fuelFillUpDAO.getFillUpAsRegNo(relevantRegNo);


        BarChart barChart = (BarChart) view.findViewById(R.id.chart2);
        // creating list of entry
        ArrayList<BarEntry> entries = new ArrayList<>();
        // creating labels
        ArrayList<String> labels = new ArrayList<String>();

        int i=0;
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");

        for (FuelFillUp fillUp : list){

            entries.add(new BarEntry((float) fillUp.getCost(),i));
            labels.add(dateFormater.format(fillUp.getDate()));

            i++;
        }



        BarDataSet dataset = new BarDataSet(entries, "Fill-up cost($)");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData  data = new BarData(labels, dataset);
        barChart.setData(data); // set the data and list of lables into chart


        barChart.setDescription("Fill-up Cost VS Time");  // set the description
        barChart.animateXY(1000, 2000);
        barChart.invalidate();




        return  true;
    }
}
