package com.bitlabs.sep_mobileapp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.ReminderDAO;
import com.bitlabs.sep_mobileapp.data.Reminder;
import com.bitlabs.sep_mobileapp.view.viewController.Utils;

import java.util.Calendar;
import java.util.Date;


public class AddReminder extends AppCompatActivity {

    ReminderDAO reminderDAO;

    DatePicker DPdate;
    TextView TVtitle;
    Spinner SpiNotificationType;
    Spinner SpinReccurenceType;
    Switch SwitchIsAlarmOn;
    TextView TVnote;

    private String editType;
    private int relavantId;
    private Reminder relavantReminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        reminderDAO = new ReminderDAO(this);

        TVtitle = (TextView) findViewById(R.id.addReminder_title);
        DPdate = (DatePicker) findViewById(R.id.addReminder_date);
        SpiNotificationType = (Spinner) findViewById(R.id.addReminder_notificationType);
        SpinReccurenceType = (Spinner) findViewById(R.id.addReminder_RecurrenceType);
        SwitchIsAlarmOn = (Switch) findViewById(R.id.addReminder_onSwitch);
        TVnote = (TextView) findViewById(R.id.addReminder_note);

        if (getIntent().hasExtra("type")) {
            if (getIntent().getExtras().getString("type").equals("edit")) {
                System.out.println("Reminder Edit mode");
                editType = "edit";
                relavantId = getIntent().getExtras().getInt("Id");
                setReminderView();
                getSupportActionBar().setTitle("Edit Reminder");
            }
        } else {
            System.out.println("Reminder Add mode");
            editType = "add";
            relavantId = 0;
            getSupportActionBar().setTitle("Add Reminder");

        }

        //System.out.println(regNo+ "selected reg No");


    }


    private void setReminderView() {
        relavantReminder = reminderDAO.getReminderAsId(relavantId);

        SwitchIsAlarmOn.setChecked(relavantReminder.getAlarmOn());
        TVtitle.setText(relavantReminder.getTitle() + "");

        Date date = relavantReminder.getTime(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DPdate.updateDate(year, month, day);


        SpiNotificationType.setSelection(((ArrayAdapter<String>) SpiNotificationType.getAdapter()).getPosition(relavantReminder.getNotificationType()));
        SpinReccurenceType.setSelection(((ArrayAdapter<String>) SpinReccurenceType.getAdapter()).getPosition(relavantReminder.getReccurenceType()));

        TVnote.setText("None");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (editType == "edit") {
            System.out.println("Edit menu set");
            getMenuInflater().inflate(R.menu.menu_edit_reminder, menu);
        } else {
            System.out.println("Add menu set");
            getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.editReminder_save) {
            onClickSave();
            return true;
        } else if (id == R.id.addReminder_save) {
            onClickSave();
            return true;
        } else if (id == R.id.editReminder_delete) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    reminderDAO.deleteReminder(relavantId);

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Set other dialog properties
            builder.setMessage("Do you want to delete?")
                    .setTitle("Delete");

            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave() {


        int id;
        boolean alarmOn;
        String notificationType;
        String alarmTone;
        String title;
        Date srtTime = null;
        String reccurenceType;
        String note;

        if (isOtherExpenseValid()) {
            id = 1;

            srtTime = Utils.getDateFromPicker(DPdate);
            title = TVtitle.getText().toString();
            notificationType = SpiNotificationType.getSelectedItem().toString();
            reccurenceType = SpinReccurenceType.getSelectedItem().toString();
            note = TVnote.getText().toString();
            alarmOn = SwitchIsAlarmOn.isChecked();

            alarmTone = "";            //temporary

            if (editType == "edit") {
                reminderDAO.updateRemider(new Reminder(id, alarmOn, notificationType, alarmTone, title, srtTime, reccurenceType));
                Toast.makeText(this, "Reminder details successfully updated!", Toast.LENGTH_SHORT).show();
            } else {
                reminderDAO.setReminder(new Reminder(id, alarmOn, notificationType, alarmTone, title, srtTime, reccurenceType));
                Toast.makeText(this, "Remider details successfully added!", Toast.LENGTH_SHORT).show();
            }


            endActivity();
        }


    }

    private void endActivity() {
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);

    }

    private boolean isOtherExpenseValid() {
        boolean valid = true;
        String errorMsg = "";

        // validations


        if (!valid) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }

        return valid;

    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}
