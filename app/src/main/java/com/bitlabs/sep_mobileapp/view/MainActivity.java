package com.bitlabs.sep_mobileapp.view;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        switch (id){
            case R.id.action_settings:
                Intent getSettingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(getSettingsIntent);


                break;

            case R.id.action_Exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;


        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void OnAddFillClick(View view) {
        Toast.makeText(this, "Enter a Fill up details", Toast.LENGTH_LONG).show();
        Intent getAddFillIntent = new Intent(this, AddFillUp.class);
        startActivity(getAddFillIntent);


    }

    public void OnOtherExpenseClick(View view) {

        Toast.makeText(this, "Enter a Expense details", Toast.LENGTH_LONG).show();
        Intent getAddExpenseIntent = new Intent(this, AddOtherExpense.class);
        startActivity(getAddExpenseIntent);

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
