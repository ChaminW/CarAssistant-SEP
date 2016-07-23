package com.bitlabs.sep_mobileapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.animation.AlphaAnimation;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.view.fragment.CalculatorFragment;
import com.bitlabs.sep_mobileapp.view.fragment.Chart1Fragment;
import com.bitlabs.sep_mobileapp.view.fragment.Chart2Fragment;
import com.bitlabs.sep_mobileapp.view.fragment.Chart3Fragment;
import com.bitlabs.sep_mobileapp.view.fragment.Satatics;
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
import java.util.List;

public class ChartView extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);

//        toolbar = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Charts");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Chart1Fragment(), "Fuel price");
        adapter.addFragment(new Chart2Fragment(), "Fill-up cost");
        adapter.addFragment(new Chart3Fragment(), "Expense cost variation");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
