package com.bitlabs.sep_mobileapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;

import com.bitlabs.sep_mobileapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;

public class ChartView extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);



        plot(this);


    }

    public void plot (Context context){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(4f, 0));
        entries2.add(new Entry(8f, 1));
        entries2.add(new Entry(6f, 2));
        entries2.add(new Entry(12f, 3));
        entries2.add(new Entry(18f, 4));
        entries2.add(new Entry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        LineDataSet dataSet2 =new LineDataSet(entries2,"xxxx");
        //dataSet2.setColor(Color.RED, AlphaAnimation.ZORDER_NORMAL);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");


//        BarChart chart = new BarChart(context);
//        LineChart chart2 = (LineChart) findViewById(R.id.chart);
//        //setContentView(chart2);
//
//        //BarData data = new BarData(labels, dataset);
//        LineData data2 =new LineData(labels, dataSet2);
//        //chart.setData(data);
//        chart2.setData(data2);
//
//        //chart.setDescription("# of times Alice called Bob");
//        //chart.animateY(5000);
//        chart2.animateXY(500,500);
//        chart.invalidate();

    }

    private void endActivity() {
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);

    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}
