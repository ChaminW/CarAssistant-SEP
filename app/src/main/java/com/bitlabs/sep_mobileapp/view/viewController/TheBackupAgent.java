package com.bitlabs.sep_mobileapp.view.viewController;

/**
 * Created by Chamin on 6/15/2016.
 */
import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.controller.DBhelper;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;

import java.io.IOException;


public class TheBackupAgent extends BackupAgentHelper {
    Context context;
    DBhelper dBhelper;
    FuelFillUpDAO fuelFillUpDAO;
    // The name of the SharedPreferences file
    static String FILENAME ;

    // A key to uniquely identify the set of backup data
    static final String FILES_BACKUP_KEY = "myDB";

    // Allocate a helper and add it to the backup agent


    public TheBackupAgent(Context context) {
        this.context=context;
        fuelFillUpDAO =new FuelFillUpDAO(context);
        FILENAME=context.getDatabasePath(fuelFillUpDAO.getDatabaseName()).getPath();
    }

    @Override
    public void onCreate() {


        FileBackupHelper helper = new FileBackupHelper(this, FILENAME);
        addHelper(FILES_BACKUP_KEY, helper);
    }
    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) {
        Log.d("Backup","backup onbackup called");

        try {
            //class is the lock since we are using static synchronized methods to read/write

                super.onBackup(oldState, data, newState);
                Log.d("Backedup","Backup successful");
                Toast.makeText(context,"Backup successful",Toast.LENGTH_SHORT);

        } catch (IOException e) {
            Log.d("error","Backup error, Unable to write to file: " + e);
            Toast.makeText(context,"Backup error, Check internet connection.",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState){

        Log.d("restore","Backup onrestore called");
        try {
            //class is the lock since we are using static synchronized methods to read/write

                super.onRestore(data, appVersionCode, newState);
                Toast.makeText(context,"Restore successful",Toast.LENGTH_SHORT);

        } catch (IOException e) {
            Log.d("Error","Backup error, Unable to read from file: " + e);
            Toast.makeText(context,"Restore error, Check internet connection.",Toast.LENGTH_SHORT);
        }
    }

}