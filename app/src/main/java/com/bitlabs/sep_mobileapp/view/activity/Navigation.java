package com.bitlabs.sep_mobileapp.view.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.DBTester;
import com.bitlabs.sep_mobileapp.controller.VehicleDAO;
import com.bitlabs.sep_mobileapp.view.fragment.ExpenseLogFragment;
import com.bitlabs.sep_mobileapp.view.fragment.FillUpLogFragment;
import com.bitlabs.sep_mobileapp.view.fragment.Home;
import com.bitlabs.sep_mobileapp.view.fragment.ReminderFragment;
import com.bitlabs.sep_mobileapp.view.fragment.Satatics;
import com.bitlabs.sep_mobileapp.view.fragment.VehicleFragment;

import java.util.List;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,Satatics.OnFragmentInteractionListener{
    public static final String PREFS_NAME = "MyPrefsFile";



    DBTester dbTester;
    Spinner SpinVehicleSelect;
    VehicleDAO vehicleDAO;
    String relevantRegNo;
    String selectedFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        /////////////////////////////////////////////////////////////////////////////////////////////
        //dbTester = new DBTester(this);
        //dbTester.clearAll();



        /////////////////////////////////////////////////////////////////////////////////////////////
        vehicleDAO =new VehicleDAO(this);
        restorePreferences();




        Fragment fragment = new Home();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
        selectedFrag="H";

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

    private void restorePreferences() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
    }

    @Override
    protected void onStop(){
        super.onStop();
//        System.out.println("onStop() called");
//        // We need an Editor object to make preference changes.
//        // All objects are from android.context.Context
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("relevantRegNo", relevantRegNo);
//        // Commit the edits!
//        editor.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else if(!selectedFrag.equals("H")){

            Fragment fragment = new Home();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        List<String> lables = vehicleDAO.getAllVehicleRegNo();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
//// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        getMenuInflater().inflate(R.menu.navigation, menu);
//        MenuItem item = menu.findItem(R.id.vehicleSelectSpinner);
//        SpinVehicleSelect = (Spinner) MenuItemCompat.getActionView(item);
//        SpinVehicleSelect.setAdapter(adapter); // set the adapter to provide layout of rows and content
//        SpinVehicleSelect.setSelection(((ArrayAdapter<String>) SpinVehicleSelect.getAdapter()).getPosition(relevantRegNo));
//        SpinVehicleSelect.setEnabled(false);
//        SpinVehicleSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                // TODO Auto-generated method stub
//                Object item = arg0.getItemAtPosition(arg2);
//                if (item != null) {
//                    Toast.makeText(Navigation.this, "Vehicle " + item.toString() + " selected", Toast.LENGTH_SHORT).show();
//                    relevantRegNo = item.toString();
//                }
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.vehicleSelectSpinner) {


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
            selectedFrag="V";

        } else if (id == R.id.nav_FuelLog) {
            fragment = new FillUpLogFragment();
            selectedFrag="F";

        } else if (id == R.id.nav_ExpenseLog) {
            fragment = new ExpenseLogFragment();
            selectedFrag="E";

        }else if (id == R.id.nav_Charts) {

            Intent getAddExpenseIntent = new Intent(this, ChartView.class);
            startActivity(getAddExpenseIntent);


        } else if (id == R.id.nav_Map) {

            Intent getAddExpenseIntent = new Intent(this, MapViewerActivity.class);
            startActivity(getAddExpenseIntent);

//        } else if (id == R.id.nav_Instruction) {
//
//
//
        } else if (id == R.id.nav_stat) {
            fragment = new Satatics();
            selectedFrag="S";

        } else if (id == R.id.nav_reminder) {
            fragment = new ReminderFragment();
            selectedFrag="R";

//        } else if (id == R.id.nav_Settings) {
//
        } else if (id == R.id.nav_GoogleDriveBackUp) {
            Intent getBackupIntent = new Intent(this, GoogleBackup.class);
            startActivity(getBackupIntent);
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

    public void OnAddReminderClick(View view) {
        Toast.makeText(this, "Enter a Reminder details", Toast.LENGTH_SHORT).show();
        Intent getAddReminderIntent = new Intent(this, AddReminder.class);
        startActivity(getAddReminderIntent);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}



