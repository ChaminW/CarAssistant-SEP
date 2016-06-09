package com.bitlabs.sep_mobileapp.view;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.BoolRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.view.viewController.dateFromPicker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddFillUp extends AppCompatActivity {

    FuelFillUpDAO fuelFillUpDAO = new FuelFillUpDAO(this);

    DatePicker DPdate;
    TextView TVodoCount;
    TextView TVcost;
    TextView TVamount;
    TextView TVcostPerLiter;
    ;
    //TextView TVfuelType;
    CheckBox CboxIsFullTank;
    CheckBox CboxIsLocationOn;
    TextView TVnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fill_up);

        DPdate = (DatePicker) findViewById(R.id.addFillUp_date);
        TVodoCount = (TextView) findViewById(R.id.addFillUp_odoCount);
        TVcost = (TextView) findViewById(R.id.addFillUp_cost);
        TVamount = (TextView) findViewById(R.id.addFillUp_amount);
        TVcostPerLiter = (TextView) findViewById(R.id.addFillUp_costPerLiter);
        CboxIsFullTank = (CheckBox) findViewById(R.id.addFillUp_FullTank);
        CboxIsLocationOn = (CheckBox) findViewById(R.id.addFillUp_location);
        TVnote = (TextView) findViewById(R.id.addFillUp_note);

        DPdate.setMaxDate(new Date().getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_fill_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_fillUp_save) {
            OnFillUpSave();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }

    public void OnFillUpSave() {
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

        if(isFillUpValid()){
            id=1;
            date = dateFromPicker.getDate(DPdate);
            odoMeter=Integer.parseInt(TVodoCount.getText().toString());
            cost=Double.parseDouble(TVcost.getText().toString());
            amount=Double.parseDouble(TVamount.getText().toString());
            costPerLiter=Double.parseDouble(TVcostPerLiter.getText().toString());
            regNo="sx-1234";                                                       //temporary
            note=TVnote.getText().toString();
            isFullTank=CboxIsFullTank.isChecked();
            FuelFillUp fuelFillUp = new FuelFillUp(id, date, odoMeter, cost, amount, costPerLiter, regNo, isFullTank,Double.NaN,Double.NaN, note);
            if(CboxIsLocationOn.isChecked()){
                location=getLocation();
                fuelFillUp.setLatitide(location.getLatitude());
                fuelFillUp.setLongitude(location.getLongitude());
            }
            fuelFillUpDAO.setFillUp(fuelFillUp);
            Toast.makeText(AddFillUp.this, "Fill up details successfully added!", Toast.LENGTH_LONG).show();


        }





    }

    private boolean isFillUpValid() {
        Boolean valid = true;
        String errorMsg = "";

        Date date = dateFromPicker.getDate(DPdate);
        Date CurrentDate = new Date();

        int factor=0;
        if (!TextUtils.isEmpty(TVcost.getText().toString())){
            factor++;
        }
        if (!TextUtils.isEmpty(TVamount.getText().toString())){
            factor++;
        }
        if (!TextUtils.isEmpty(TVcostPerLiter.getText().toString())){
            factor++;
        }

        if (date.after(CurrentDate)) {
            valid = false;
            errorMsg += "Date can not be in future.\n";
        }
        else if (TextUtils.isEmpty(TVodoCount.getText().toString())){
            valid = false;
            TVodoCount.setError("Must not be empty");
        }

        else if(factor<2){
            valid=false;
            errorMsg+="Two must be fill by cost,amount and costPer ";
        }
        else if(CboxIsLocationOn.isChecked()){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                valid=false;
                errorMsg+="Location services should activated";
            }
        }

        if(!valid){Toast.makeText(this,errorMsg,Toast.LENGTH_LONG).show();}
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

    private void endActivity(){
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);

    }


}
