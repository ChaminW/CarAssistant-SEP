package com.bitlabs.sep_mobileapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.controller.OtherExpenseDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapViewerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FuelFillUpDAO fuelFillUpDAO;
    private OtherExpenseDAO otherExpenseDAO;
    private List<FuelFillUp> fillUpList;
    private List<OtherExpense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_viewer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fuelFillUpDAO=new FuelFillUpDAO(this);
        otherExpenseDAO =new OtherExpenseDAO(this);


        fillUpList =fuelFillUpDAO.getAllFillUps();
        expenseList=otherExpenseDAO.getAllOtherExpense();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Activate GPS and internet services", Toast.LENGTH_SHORT).show();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setContentDescription("axsaxasx");


//        final Handler handler = new Handler();
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    while(true) {
//
//                        for(FuelFillUp fillUp : fillUpList){
//                            addMarker(new LatLng(fillUp.getLatitude(),fillUp.getLongitude()),"Fuel");}
//                        for(OtherExpense expense : expenseList){
//                            addMarker(new LatLng(expense.getLatitude(),expense.getLongitude()),"Expense");}
//
//                        sleep(1000);
//                        handler.post(this);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();

        for (FuelFillUp fillUp : fillUpList) {
            addFillUpMarker(fillUp);
        }
        for (OtherExpense expense : expenseList) {
            addExpenseMarker(expense);
        }

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 8.0f));
        }
        catch (NullPointerException e){
            Log.d("Error",e.getMessage());

        }
    }

    private void addExpenseMarker(OtherExpense expense) {
        MarkerOptions markerOptions=new MarkerOptions().position(new LatLng(expense.getLatitude(),expense.getLongitude())).title("Expense place:"+expense.getCategory());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("expense_marker",100,100))).snippet("This is a expense due place.");
        mMap.addMarker(markerOptions);
    }

    private void addFillUpMarker(FuelFillUp fillUp) {
        MarkerOptions markerOptions=new MarkerOptions().position(new LatLng(fillUp.getLatitude(),fillUp.getLongitude())).title("Fuel Station");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("fuel_marker",100,100))).snippet("Cost of liter is:"+fillUp.getCostPerLiter());
        mMap.addMarker(markerOptions);
    }




    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void endActivity() {
        Intent getAddFillIntent = new Intent(this, Navigation.class);
        startActivity(getAddFillIntent);

    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}
