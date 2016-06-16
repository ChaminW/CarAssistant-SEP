package com.bitlabs.sep_mobileapp.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bitlabs.sep_mobileapp.R;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{

    private Button addFillBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFillBtn = (Button) findViewById(R.id.addFillBtn);

        ActionBar ab = getSupportActionBar();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Three tab to display in actionbar
        ab.addTab(ab.newTab().setText("Tab 1").setTabListener(this));
        ab.addTab(ab.newTab().setText("Tab 2").setTabListener(this));
        ab.addTab(ab.newTab().setText("Tab 3").setTabListener(this));




    }











    public void OnSettingsClicked(View view) {

        Intent getSettingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(getSettingsIntent);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        //Called when a tab is selected
        int nTabSelected = tab.getPosition();
        switch (nTabSelected) {
            case 0:
                setContentView(R.layout.tab_fuel_fillup);
                break;
            case 1:
                setContentView(R.layout.tab_other_expense);
                break;
            case 2:
                setContentView(R.layout.tab_calculator);
                break;
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Called when a tab unselected.
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Called when a tab is selected again.
    }
}
