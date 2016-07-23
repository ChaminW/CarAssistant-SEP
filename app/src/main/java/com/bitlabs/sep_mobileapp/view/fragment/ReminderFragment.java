package com.bitlabs.sep_mobileapp.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.ReminderDAO;
import com.bitlabs.sep_mobileapp.data.Reminder;
import com.bitlabs.sep_mobileapp.view.activity.AddReminder;
import com.bitlabs.sep_mobileapp.view.activity.Navigation;
import com.bitlabs.sep_mobileapp.view.viewModel.reminderBaseAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {

    private View view;

    private ListView listview;

    private ArrayList<Reminder> reminderList;
    private reminderBaseAdapter baseAdapter;

    private ReminderDAO reminderDAO;
    Runnable run;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReminderFragment() {
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
    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
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
                "Reminder");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reminder, container, false);


        reminderDAO = new ReminderDAO(getActivity());
        reminderList = reminderDAO.getAllReminder();


        listview = (ListView) view.findViewById(R.id.reminder_list_view);

        baseAdapter = new reminderBaseAdapter(getActivity(), reminderList);
        listview.setAdapter(baseAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Reminder entry = (Reminder) parent.getAdapter().getItem(position);


                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.reminder_item_popup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Deactivate":
                                reminderDeactivate(entry);
                                break;
                            case "Activate":
                                reminderActivate(entry);
                                break;
                            case "Edit":
                                reminderEdit(entry);
                                break;
                            case "Delete":
                                reminderDelete(entry);

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
                reminderList.clear();
                reminderList = reminderDAO.getAllReminder();
                baseAdapter.setList(reminderList);
                baseAdapter.notifyDataSetChanged();
                listview.invalidateViews();
                listview.refreshDrawableState();
            }
        };


        return view;
    }

    private void reminderDeactivate(Reminder entry) {

        Reminder reminder = entry;
        reminder.setAlarmOn(false);
        reminderDAO.updateRemider(reminder);
        getActivity().runOnUiThread(run);

    }

    private void reminderDelete(final Reminder entry) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reminderDAO.deleteReminder(entry.getId());
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

    private void reminderEdit(Reminder entry) {

        Intent intent = new Intent(getActivity(), AddReminder.class);
        int Id = entry.getId();
        intent.putExtra("type", "edit");
        intent.putExtra("Id", Id);
        startActivity(intent);
    }


    private void reminderActivate(Reminder entry) {
        Reminder reminder = entry;
        reminder.setAlarmOn(true);
        reminderDAO.updateRemider(reminder);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
