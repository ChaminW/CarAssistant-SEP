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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chart1Fragment extends Fragment {
    View view;
    FuelFillUpDAO fuelFillUpDAO;

    public static final String PREFS_NAME = "MyPrefsFile";
    private String relevantRegNo;

    public Chart1Fragment() {
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
        view = inflater.inflate(R.layout.fragment_chart1, container, false);
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


        LineChart lineChart = (LineChart) view.findViewById(R.id.chart1);
        // creating list of entry
        ArrayList<Entry> entries = new ArrayList<>();
        // creating labels
        ArrayList<String> labels = new ArrayList<String>();

        int i=0;
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");

        for (FuelFillUp fillUp : list){

            entries.add(new Entry((float) fillUp.getCostPerLiter(),i));
            labels.add(dateFormater.format(fillUp.getDate()));

            i++;
        }





        LineDataSet dataset = new LineDataSet(entries, "Fuel price ($/Liter)");
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);


        LineData data = new LineData(labels, dataset);
        lineChart.setData(data); // set the data and list of lables into chart


        lineChart.setDescription("Fuel price VS Time");  // set the description
        lineChart.animateXY(1000, 2000);
        lineChart.invalidate();




        return  true;
    }

}
