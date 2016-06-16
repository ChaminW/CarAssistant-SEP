package com.bitlabs.sep_mobileapp.view.activity;

import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;

public class GoogleBackup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_backup);
    }


    public void onBackupClick(View view)
    {
        System.out.println("Backupbtn clicked");
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
        Toast.makeText(this,"Backup will run in background",Toast.LENGTH_SHORT).show();
    }

    public void onRestoreClick(View view) {
        System.out.println("Restorebtn clicked");
        BackupManager bm = new BackupManager(this);
        int isRestore=bm.requestRestore(new RestoreObserver() {
            @Override
            public void restoreStarting(int numPackages) {
                super.restoreStarting(numPackages);
            }
        });

        if(isRestore==0){
            System.out.println("Restored successfully! ");
        }
        Toast.makeText(this,"Restore will run in background",Toast.LENGTH_SHORT).show();
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
