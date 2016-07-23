package com.bitlabs.sep_mobileapp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.VehicleDAO;
import com.bitlabs.sep_mobileapp.data.Expense;
import com.bitlabs.sep_mobileapp.data.Vehicle;

import java.util.ArrayList;

public class AddVehicle extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    private String editType;
    private String relavantRegNo;
    private Vehicle relavantVehicle;

    private CoordinatorLayout coordinatorLayout;

    private String uriStr;

    ImageView imgView;
    TextView TVregNo;
    TextView TVmodel;
    TextView TVyear;
    Spinner SpinfuelUnit;
    Spinner SpinfuelType;
    Spinner SpinDistanceUnit;

    VehicleDAO vehicleDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        imgView = (ImageView) findViewById(R.id.imgView);
        TVregNo = (TextView) findViewById(R.id.addVehicle_RegNo);
        TVmodel = (TextView) findViewById(R.id.addVehicle_Model);
        TVyear = (TextView) findViewById(R.id.addVehicle_Year);
        SpinfuelType = (Spinner) findViewById(R.id.addVehicle_fuelTypeSpinner);
        SpinfuelUnit = (Spinner) findViewById(R.id.addVehicle_fuelUnitSpinner);
        SpinDistanceUnit = (Spinner) findViewById(R.id.addVehicle_distanceUnitSpinner);

        vehicleDAO = new VehicleDAO(this);

        if (getIntent().hasExtra("type")) {
            if (getIntent().getExtras().getString("type").equals("edit")) {
                System.out.println("Vehicle Edit mode");
                editType = "edit";
                relavantRegNo = getIntent().getExtras().getString("regNo");
                setVehicleView();
                getSupportActionBar().setTitle("Edit Vehicle");
            }

        }else {
                System.out.println( "Vehicle Add mode");
                editType="add";
                relavantRegNo="";
                getSupportActionBar().setTitle("Add Vehicle");

            }

            //System.out.println(regNo+ "selected reg No");




    }

    private void setVehicleView() {
        relavantVehicle=vehicleDAO.getVehicleAsRegNo(relavantRegNo);

        try {

            imgView.setImageURI(Uri.parse(relavantVehicle.getImage()));
            uriStr=relavantVehicle.getImage();
            imgView.setBackground(null);
        }catch (Exception e){
            imgView.setImageResource(R.drawable.car_image2);
        }


        TVregNo.setText(relavantVehicle.getRegNo()+"");
        TVmodel.setText(relavantVehicle.getModel()+"");
        TVyear.setText(relavantVehicle.getYear()+"");

        SpinfuelType.setSelection(((ArrayAdapter<String>)SpinfuelType.getAdapter()).getPosition(relavantVehicle.getFuelType()));
        SpinfuelUnit.setSelection(((ArrayAdapter<String>)SpinfuelUnit.getAdapter()).getPosition(relavantVehicle.getFuelUnit()));
        SpinDistanceUnit.setSelection(((ArrayAdapter<String>)SpinDistanceUnit.getAdapter()).getPosition(relavantVehicle.getDistanceUnit()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(editType=="edit") {
            System.out.println("Edit menu set");
            getMenuInflater().inflate(R.menu.menu_edit_vehicle, menu);
        }
        else  {
            System.out.println("Add menu set");
            getMenuInflater().inflate(R.menu.menu_add_vehicle, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addVehicle_save) {
            onclickSave();
            return true;
        }
        else if (id == R.id.editVehicle_save) {
            onclickSave();
            return true;
        }
        if (id == R.id.editVehicle_delete) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    vehicleDAO.deleteVehicle(relavantRegNo);

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

    private void onclickSave() {


        String regNo;
        String model;
        String year;
        String fuelType;
        String fuelUnit;
        String distanceUnit;
        String image;

        if (isVehicleValid()) {

            regNo = TVregNo.getText().toString();
            model = TVmodel.getText().toString();
            year = TVyear.getText().toString();
            fuelType = SpinfuelType.getSelectedItem().toString();
            fuelUnit = SpinfuelUnit.getSelectedItem().toString();
            distanceUnit = SpinDistanceUnit.getSelectedItem().toString();
            image = uriStr;    //temporary

            Vehicle tempVehicle = new Vehicle(model, distanceUnit, fuelUnit, fuelType, regNo, year, image);
            if(editType=="edit"){
                vehicleDAO.updateVehicle(relavantRegNo,tempVehicle);
                Toast.makeText(this, "Vehicle details successfully updated!", Toast.LENGTH_SHORT).show();
            }else {
                vehicleDAO.setVehicle(tempVehicle);
                Toast.makeText(this, "Vehicle details successfully added!", Toast.LENGTH_SHORT).show();
            }

            endActivity();
        }


    }

    private void endActivity() {
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);
    }

    private boolean isVehicleValid() {
        boolean valid = true;


        if (TextUtils.isEmpty(TVregNo.getText().toString())) {
            valid = false;
            TVregNo.setError("Must not be empty");
        } else if (TextUtils.isEmpty(TVmodel.getText().toString())) {
            valid = false;
            TVmodel.setError("Must not be empty");
        }


        return valid;
    }


    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imgView.setBackground(null);
                    imgView.setImageURI(selectedImageUri);
                    uriStr=selectedImageUri.toString();
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void onAddPicClick(View view) {
        openImageChooser();
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}
