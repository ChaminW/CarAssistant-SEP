package com.bitlabs.sep_mobileapp.view;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFillUp extends AppCompatActivity {

    FuelFillUpDAO fuelFillUpDAO = new FuelFillUpDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fill_up);


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnFillUpSave(View view) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        FuelFillUp fuelFillUp= new FuelFillUp(1,date,12324412,2000,12,123,"Diesel",true,"colombo","none");
        fuelFillUpDAO.setFillUp(fuelFillUp);
        Toast.makeText(AddFillUp.this, "Fill up details successfully added!", Toast.LENGTH_LONG).show();


    }
}
