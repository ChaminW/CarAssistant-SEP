package com.bitlabs.sep_mobileapp.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.DBTester;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DBTester dbTester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        /////////////////////////////////////////////////////////////////////////////////////////////
        dbTester = new DBTester(this);
        dbTester.test();


        /////////////////////////////////////////////////////////////////////////////////////////////


        Fragment fragment = new Home();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {
            fragment = new Home();
            //Handle the home action
        } else if (id == R.id.nav_AddFuel) {

            Toast.makeText(this, "Enter a Fill up details", Toast.LENGTH_SHORT).show();
            Intent getAddFillIntent = new Intent(this, AddFillUp.class);
            startActivity(getAddFillIntent);

        } else if (id == R.id.nav_AddExpense) {

            Toast.makeText(this, "Enter a Expense details", Toast.LENGTH_SHORT).show();
            Intent getAddExpenseIntent = new Intent(this, AddOtherExpense.class);
            startActivity(getAddExpenseIntent);

        } else if (id == R.id.nav_Vehicles) {
            fragment = new VehicleFragment();

        } else if (id == R.id.nav_FuelCharts) {

            Intent getAddExpenseIntent = new Intent(this, ChartView.class);
            startActivity(getAddExpenseIntent);


        } else if (id == R.id.nav_FuelMap) {

            Intent getAddExpenseIntent = new Intent(this, MapViewerActivity.class);
            startActivity(getAddExpenseIntent);

        } else if (id == R.id.nav_ExpenseCharts) {

            Intent getAddExpenseIntent = new Intent(this, ChartView.class);
            startActivity(getAddExpenseIntent);

        } else if (id == R.id.nav_ExpenseMap) {
            Intent getAddExpenseIntent = new Intent(this, MapViewerActivity.class);
            startActivity(getAddExpenseIntent);

        } else if (id == R.id.nav_Settings) {

        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void OnAddFillClick(View view) {
        Toast.makeText(this, "Enter a Fill up details", Toast.LENGTH_SHORT).show();
        Intent getAddFillIntent = new Intent(this, AddFillUp.class);
        startActivity(getAddFillIntent);


    }

    public void OnOtherExpenseClick(View view) {

        Toast.makeText(this, "Enter a Expense details", Toast.LENGTH_SHORT).show();
        Intent getAddExpenseIntent = new Intent(this, AddOtherExpense.class);
        startActivity(getAddExpenseIntent);

    }

    public void OnAddVehicleClick(View view) {
        Toast.makeText(this, "Enter a Vehicle details", Toast.LENGTH_SHORT).show();
        Intent getAddVehicleIntent = new Intent(this, AddVehicle.class);
        startActivity(getAddVehicleIntent);
    }
}



