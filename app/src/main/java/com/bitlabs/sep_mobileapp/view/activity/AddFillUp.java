package com.bitlabs.sep_mobileapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.controller.VehicleDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.view.viewController.Utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddFillUp extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    FuelFillUpDAO fuelFillUpDAO;
    VehicleDAO vehicleDAO;

    DatePicker DPdate;
    TextView TVodoCount;
    TextView TVcost;
    TextView TVamount;
    TextView TVcostPerLiter;

    CheckBox CboxIsFullTank;
    CheckBox CboxIsLocationOn;
    TextView TVnote;


    Spinner SpinVehicleSelect;

    private FuelFillUp relavantFuelFillUp;
    private String relevantRegNo;
    private String editType;
    private int relavantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fill_up);

        fuelFillUpDAO = new FuelFillUpDAO(this);
        vehicleDAO = new VehicleDAO(this);

        DPdate = (DatePicker) findViewById(R.id.addFillUp_date);
        TVodoCount = (TextView) findViewById(R.id.addFillUp_odoCount);
        TVcost = (TextView) findViewById(R.id.addFillUp_cost);
        TVamount = (TextView) findViewById(R.id.addFillUp_amount);
        TVcostPerLiter = (TextView) findViewById(R.id.addFillUp_costPerLiter);
        CboxIsFullTank = (CheckBox) findViewById(R.id.addFillUp_FullTank);
        CboxIsLocationOn = (CheckBox) findViewById(R.id.addFillUp_location);
        TVnote = (TextView) findViewById(R.id.addFillUp_note);

        //DPdate.setMaxDate(new Date().getTime());

        if (getIntent().hasExtra("type")) {
            if (getIntent().getExtras().getString("type").equals("edit")) {
                System.out.println("Fillup Edit mode");
                editType = "edit";
                relavantId = getIntent().getExtras().getInt("Id");
                setAddFillupView();
                getSupportActionBar().setTitle("Edit Fillup");
            }
        } else {
            System.out.println("Fill Up Add mode");
            editType = "add";
            relavantId = 0;
            getSupportActionBar().setTitle("Add Fillup");

        }

        restorePreferences();
    }

    private void setAddFillupView() {
        System.out.println("fillup setting");
        relavantFuelFillUp = fuelFillUpDAO.getFillUpAsId(relavantId);
        if (relavantFuelFillUp != null) {


            TVodoCount.setText(relavantFuelFillUp.getOdoMeter() + "");
            TVamount.setText(relavantFuelFillUp.getAmount() + "");
            TVcost.setText(relavantFuelFillUp.getCost() + "");
            TVcostPerLiter.setText(relavantFuelFillUp.getCostPerLiter() + "");

            Date date = relavantFuelFillUp.getDate(); // your date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DPdate.updateDate(year, month, day);


            CboxIsFullTank.setChecked(relavantFuelFillUp.getIsFullTank());
            TVnote.setText(relavantFuelFillUp.getNote() + "");
            System.out.println("fillup set");
        }
    }

    private void restorePreferences() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
    }

    @Override
    protected void onStop() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        List<String> lables = vehicleDAO.getAllVehicleRegNo();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
//// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (editType.equals("edit")) {

            getMenuInflater().inflate(R.menu.menu_edit_fill_up, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_add_fill_up, menu);
        }
        MenuItem item = menu.findItem(R.id.vehicleSelectSpinner);
        SpinVehicleSelect = (Spinner) MenuItemCompat.getActionView(item);
        SpinVehicleSelect.setAdapter(adapter); // set the adapter to provide layout of rows and content
        SpinVehicleSelect.setSelection(((ArrayAdapter<String>) SpinVehicleSelect.getAdapter()).getPosition(relevantRegNo));

        SpinVehicleSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                Object item = arg0.getItemAtPosition(arg2);
                if (item != null) {
                    //Toast.makeText(AddFillUp.this, "Vehicle " + item.toString() + " selected", Toast.LENGTH_SHORT).show();
                    relevantRegNo = item.toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addFillUp_save) {
            fillUpSave();

            return true;
        }
        else if (id == R.id.addFillUp_delete) {
            fillUpDelete();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillUpDelete() {
        fuelFillUpDAO.deleteFuelFillUp(relavantId);
        Toast.makeText(AddFillUp.this, "Fill up details successfully added!", Toast.LENGTH_SHORT).show();
        endActivity();

    }

    @Override
    public void onBackPressed() {
        endActivity();
    }

    public void fillUpSave() {
        int id;
        Date date;
        double cost;
        String note;
        int odoMeter;
        String regNo;
        double amount;
        double costPerLiter;
        Location location;
        boolean isFullTank;

        if (isFillUpValid()) {
            id = relavantId;
            date = Utils.getDateFromPicker(DPdate);
            odoMeter = Integer.parseInt(TVodoCount.getText().toString());
            try {
                cost = Double.parseDouble(TVcost.getText().toString());
            }
            catch (NumberFormatException e){
                cost=Double.parseDouble(TVamount.getText().toString())*Double.parseDouble(TVcostPerLiter.getText().toString());
                DecimalFormat df = new DecimalFormat("#.##");
                cost = Double.valueOf(df.format(cost));
            }
            try {
                amount = Double.parseDouble(TVamount.getText().toString());
            }
            catch (NumberFormatException e){
                amount =Double.parseDouble(TVcost.getText().toString())/Double.parseDouble(TVcostPerLiter.getText().toString());
                DecimalFormat df = new DecimalFormat("#.##");
                amount = Double.valueOf(df.format(amount));
            }
            try {
                costPerLiter = Double.parseDouble(TVcostPerLiter.getText().toString());
            }catch (NumberFormatException e){
                costPerLiter =  Double.parseDouble(TVcost.getText().toString())/Double.parseDouble(TVamount.getText().toString());
                DecimalFormat df = new DecimalFormat("#.##");
                costPerLiter = Double.valueOf(df.format(costPerLiter));

            }
            regNo = relevantRegNo;
            note = TVnote.getText().toString();
            isFullTank = CboxIsFullTank.isChecked();
            FuelFillUp fuelFillUp = new FuelFillUp(id, date, odoMeter, cost, amount, costPerLiter, regNo, isFullTank, Double.NaN, Double.NaN, note);
            if (CboxIsLocationOn.isChecked()) {
                location = getLocation();
                fuelFillUp.setLatitude(location.getLatitude());
                fuelFillUp.setLongitude(location.getLongitude());
            }

            if(editType.equals("edit")){
                fuelFillUpDAO.updateFillUp(fuelFillUp);
                Toast.makeText(AddFillUp.this, "Fill up details successfully updated!", Toast.LENGTH_LONG).show();

            }
            else {
                fuelFillUpDAO.setFillUp(fuelFillUp);
                Toast.makeText(AddFillUp.this, "Fill up details successfully added!", Toast.LENGTH_SHORT).show();
            }
            endActivity();
        }


    }

    private boolean isFillUpValid() {
        Boolean valid = true;
        String errorMsg = "";

        Date date = Utils.getDateFromPicker(DPdate);
        Date CurrentDate = new Date();

        int factor = 0;
        if (!TextUtils.isEmpty(TVcost.getText().toString())) {
            factor++;
        }
        if (!TextUtils.isEmpty(TVamount.getText().toString())) {
            factor++;
        }
        if (!TextUtils.isEmpty(TVcostPerLiter.getText().toString())) {
            factor++;
        }

        if (date.after(CurrentDate)) {
            valid = false;
            errorMsg += "Date can not be in future.\n";
        } else if (TextUtils.isEmpty(TVodoCount.getText().toString())) {
            valid = false;
            TVodoCount.setError("Must not be empty");
        } else if (factor < 2) {
            valid = false;
            errorMsg += "Two must be fill by cost,amount and costPer ";
        } else if (CboxIsLocationOn.isChecked()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                valid = false;
                errorMsg += "Location services should activated";
            }
        }

        if (!valid) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }


    private String getLocationName(Location location) {

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(location
                    .getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null && list.size() > 0) {
            Address address = list.get(0);
            String result = address.getLocality();
            return result;
        }
        return "Location name can't obtain";
    }

    private Location getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = null;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Activate GPS connection", Toast.LENGTH_SHORT).show();

        } else {
            location = locationManager.getLastKnownLocation(bestProvider);
        }

        try {
            //lat = location.getLatitude ();
            //lon = location.getLongitude ();
            Toast.makeText(this, "Got the location lat:" + location.getLatitude() + " Lan:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            return location;
        } catch (NullPointerException e) {
            Toast.makeText(this, "Can't obtain location, Try by activate Location and Internet services.", Toast.LENGTH_SHORT).show();
            return null;
        }


    }

    private void endActivity() {
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);

    }


}
