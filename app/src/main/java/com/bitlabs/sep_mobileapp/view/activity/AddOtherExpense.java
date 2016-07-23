package com.bitlabs.sep_mobileapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.bitlabs.sep_mobileapp.controller.OtherExpenseDAO;
import com.bitlabs.sep_mobileapp.controller.ReminderDAO;
import com.bitlabs.sep_mobileapp.controller.VehicleDAO;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.data.Reminder;
import com.bitlabs.sep_mobileapp.view.viewController.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddOtherExpense extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";

    OtherExpenseDAO otherExpenseDAO;
    VehicleDAO vehicleDAO;
    ReminderDAO reminderDAO;

    DatePicker DPdate;
    TextView TVodoCount;
    TextView TVcost;
    TextView TVtitle;
    Spinner SpinCategory;
    Spinner SpinReccurenceType;
    CheckBox CboxIsReminder;
    CheckBox CboxIsLocationOn;
    TextView TVnote;

    Spinner SpinVehicleSelect;

    private OtherExpense relavantOtherExpense;
    private String relevantRegNo;
    private String editType;
    private int relavantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other_expense);

        otherExpenseDAO = new OtherExpenseDAO(this);
        vehicleDAO = new VehicleDAO(this);
        reminderDAO = new ReminderDAO(this);

        TVtitle = (TextView) findViewById(R.id.addExpense_title);
        DPdate = (DatePicker) findViewById(R.id.addExpense_datePicker);
        TVodoCount = (TextView) findViewById(R.id.addExpense_odoMeter);
        TVcost = (TextView) findViewById(R.id.addExpense_cost);
        SpinCategory = (Spinner) findViewById(R.id.addExpense_CategorySpinner);
        SpinReccurenceType = (Spinner) findViewById(R.id.addExpense_recurrenceTypeSpinner);
        CboxIsReminder = (CheckBox) findViewById(R.id.addExpense_isReminder);
        CboxIsLocationOn = (CheckBox) findViewById(R.id.addExpense_isLocation);
        TVnote = (TextView) findViewById(R.id.addExpense_note);


        if (getIntent().hasExtra("type")) {
            if (getIntent().getExtras().getString("type").equals("edit")) {
                System.out.println("Expense Edit mode");
                editType = "edit";
                relavantId = getIntent().getExtras().getInt("Id");
                setReminderView();
                getSupportActionBar().setTitle("Edit Expense");
            }
        } else {
            System.out.println("Expense Add mode");
            editType = "add";
            relavantId = 0;
            getSupportActionBar().setTitle("Add Expense");

        }

//        Spinner categorySpinner = (Spinner) findViewById(R.id.addOtherExpense_spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.Expense_category_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        categorySpinner.setAdapter(adapter);

        restorePreferences();
    }

    private void restorePreferences() {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop() called");
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("relevantRegNo", relevantRegNo);
        // Commit the edits!
        editor.commit();
    }

    private void setReminderView() {
        relavantOtherExpense = otherExpenseDAO.getOtherExpenseAsId(relavantId);
        if (relavantOtherExpense != null) {

            SpinCategory.setSelection(((ArrayAdapter<String>) SpinCategory.getAdapter()).getPosition(relavantOtherExpense.getCategory()));

            TVtitle.setText(relavantOtherExpense.getTitle() + "");
            TVcost.setText(relavantOtherExpense.getCost() + "");

            Date date = relavantOtherExpense.getDate(); // your date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DPdate.updateDate(year, month, day);

            SpinReccurenceType.setSelection(((ArrayAdapter<String>) SpinReccurenceType.getAdapter()).getPosition(relavantOtherExpense.getReccurenceType()));
            TVodoCount.setText(relavantOtherExpense.getOdoMeter() + "");
            TVnote.setText(relavantOtherExpense.getNote() + "");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        List<String> lables = vehicleDAO.getAllVehicleRegNo();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
//// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (editType.equals("edit")) {

            getMenuInflater().inflate(R.menu.menu_edit_other_expense, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_add_other_expense, menu);
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
                    //Toast.makeText(AddOtherExpense.this, "Vehicle " + item.toString() + " selected", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.expense_save) {
            System.out.println("save clicked");
            otherExpenseSave();
            return true;
        }
        else if (id == R.id.expense_delete) {

            otherExpenseDelete();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void otherExpenseDelete() {
        otherExpenseDAO.deleteOtherExpense(relavantId);
        Toast.makeText(this, "Other expense details successfully deleted!", Toast.LENGTH_LONG).show();
        endActivity();
    }

    private void otherExpenseSave() {


        int id;
        String regNo;
        Date date;
        double cost;
        String note;
        int odoMeter;
        String category;
        String title;
        String reccurenceType;
        Location location;

        if (isOtherExpenseValid()) {
            id = relavantId;
            date = Utils.getDateFromPicker(DPdate);
            try {
                odoMeter = Integer.parseInt(TVodoCount.getText().toString());
            } catch (NumberFormatException e) {
                odoMeter = 0;
            }

            cost = Double.parseDouble(TVcost.getText().toString());
            title = TVtitle.getText().toString();
            category = SpinCategory.getSelectedItem().toString();
            reccurenceType = SpinReccurenceType.getSelectedItem().toString();
            regNo = relevantRegNo;                                                       //temporary
            note = TVnote.getText().toString();

            OtherExpense otherExpense = new OtherExpense(id, date, cost, note, odoMeter, category, title, reccurenceType, regNo, Double.NaN, Double.NaN);
            if (CboxIsLocationOn.isChecked()) {
                location = getLocation();
                otherExpense.setLatitude(location.getLatitude());
                otherExpense.setLongitude(location.getLongitude());
            }
            if (CboxIsReminder.isChecked()) {
                reminderDAO.setReminder(new Reminder(1, true, "Notification only", "", category + ":" + title, date, reccurenceType));
                Toast.makeText(this, "Reminder successfully added!", Toast.LENGTH_LONG).show();
                // save as a reminder
            }

            if (editType.equals("edit")) {
                otherExpenseDAO.updateOtherExpense(otherExpense);
                Toast.makeText(AddOtherExpense.this, "Expense details successfully updated!", Toast.LENGTH_LONG).show();

            } else {
                otherExpenseDAO.setOtherExpense(otherExpense);
                Toast.makeText(this, "Other expense details successfully added!", Toast.LENGTH_LONG).show();
            }


            endActivity();
        } else {
            Log.d("Error", "Inpur is not valid", null);
        }


    }

    private boolean isOtherExpenseValid() {
        boolean valid = true;
        String errorMsg = "";

        Date date = Utils.getDateFromPicker(DPdate);
        Date CurrentDate = new Date();

        if (TextUtils.isEmpty(TVtitle.getText().toString())) {
            valid = false;
            TVtitle.setError("Must not be empty");
        } else if (TextUtils.isEmpty(TVcost.getText().toString())) {
            valid = false;
            TVcost.setError("Must not be empty");
        } else if (date.after(CurrentDate)) {
            valid = false;
            errorMsg += "Date can not be in future.\n";
        } else if (CboxIsReminder.isChecked() && SpinReccurenceType.getSelectedItem().toString().equals("One-time")) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                valid = false;
                errorMsg += "Location services should activated";
            }
        } else if (CboxIsLocationOn.isChecked()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                valid = false;
                errorMsg += "Location services should activated";
            }
        }

        if (!valid) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }

        return valid;
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

    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void endActivity() {
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);

    }


}
