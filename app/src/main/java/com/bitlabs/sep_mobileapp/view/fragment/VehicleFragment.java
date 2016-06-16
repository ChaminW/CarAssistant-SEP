package com.bitlabs.sep_mobileapp.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.VehicleDAO;
import com.bitlabs.sep_mobileapp.data.Vehicle;
import com.bitlabs.sep_mobileapp.view.activity.AddVehicle;
import com.bitlabs.sep_mobileapp.view.activity.Navigation;
import com.bitlabs.sep_mobileapp.view.viewModel.vehicleBaseAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehicleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleFragment extends Fragment {
    public static final String PREFS_NAME = "MyPrefsFile";
    private String relevantRegNo;

    private View view;

    private ListView listview;

    private ArrayList<Vehicle> vehicleList;
    private vehicleBaseAdapter baseAdapter;

    private VehicleDAO vehicleDAO;
    Runnable run;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehicleFragment newInstance(String param1, String param2) {
        VehicleFragment fragment = new VehicleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((Navigation) getActivity()).getSupportActionBar().setTitle(
                "Vehicles");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restorePreferences();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vehicle, container, false);


        vehicleDAO = new VehicleDAO(getActivity());
        vehicleList = vehicleDAO.getAllVehicle();

        listview = (ListView) view.findViewById(R.id.vehicle_list_view);

        baseAdapter = new vehicleBaseAdapter(getActivity(), vehicleList);

        listview.setAdapter(baseAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Vehicle entry = (Vehicle) parent.getAdapter().getItem(position);
//                Intent intent = new Intent(getActivity(), AddVehicle.class);
//                String regNo = entry.getRegNo();
//                intent.putExtra("type","edit");
//                intent.putExtra("regNo", regNo);
//                startActivity(intent);

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.vehiclelitem_popup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Activate":
                                vehicleActivate(entry);
                                break;
                            case "Edit":
                                vehicleEdit(entry);
                                break;
                            case "Delete":
                                vehicleDelete(entry);

                                break;

                        }


                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        run = new Runnable() {
            public void run() {
                //reload content
//                vehicleList.clear();
//                vehicleList = vehicleDAO.getAllVehicle();
//                baseAdapter.setList(vehicleList);
//                baseAdapter.setActiveRegNo(relevantRegNo);
//                baseAdapter.notifyDataSetChanged();
//                listview.invalidateViews();
//                listview.refreshDrawableState();
            }
        };


        return view;
    }
    private void restorePreferences() {
        // Restore preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
        System.out.println(relevantRegNo+" got the reg no");
    }

    private void vehicleDelete(final Vehicle entry) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                vehicleDAO.deleteVehicle(entry.getRegNo());
                getActivity().runOnUiThread(run);
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

    }

    private void vehicleEdit(Vehicle entry) {

        Intent intent = new Intent(getActivity(), AddVehicle.class);
        String regNo = entry.getRegNo();
        intent.putExtra("type", "edit");
        intent.putExtra("regNo", regNo);
        startActivity(intent);
    }


    private void vehicleActivate(Vehicle entry) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        relevantRegNo =entry.getRegNo();
        editor.putString("relevantRegNo",relevantRegNo);
        // Commit the edits!
        editor.commit();
        System.out.println(relevantRegNo+" is set in preference");
        Toast.makeText(getActivity(), "Vehicle " + relevantRegNo + " activated", Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(run);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        ((Navigation) getActivity()).getSupportActionBar().setTitle(
                "Car Assistant");



    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
