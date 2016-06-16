package com.bitlabs.sep_mobileapp.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.controller.FuelFillUpDAO;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.view.activity.AddFillUp;
import com.bitlabs.sep_mobileapp.view.viewModel.fillUpBaseAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FillUpLogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FillUpLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillUpLogFragment extends Fragment {
    public static final String PREFS_NAME = "MyPrefsFile";


    private View view;
    private ListView listview;

    private ArrayList<FuelFillUp> fillUpList;
    private fillUpBaseAdapter baseAdapter;
    private FuelFillUp fuelFillUp;

    private String relevantRegNo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FillUpLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FillUpLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FillUpLogFragment newInstance(String param1, String param2) {
        FillUpLogFragment fragment = new FillUpLogFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        restorePreferences();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fill_up_log, container, false);

        FuelFillUpDAO fuelFillUpDAO = new FuelFillUpDAO(getActivity());
        fillUpList = fuelFillUpDAO.getFillUpAsRegNo(relevantRegNo);

        listview = (ListView) view.findViewById(R.id.fillUp_listView);


        baseAdapter = new fillUpBaseAdapter(getActivity(), fillUpList) {
        };
        listview.setAdapter(baseAdapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FuelFillUp entry= (FuelFillUp) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), AddFillUp.class);
                int fillUpId =entry.getId();

                System.out.println(fillUpId+"   selected fill up id");
                intent.putExtra("type", "edit");
                intent.putExtra("Id", fillUpId);
                startActivity(intent);
                return true;
            }


        });


        return view;
    }
    private void restorePreferences() {
        // Restore preferences

        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
        System.out.println("pre restored-"+relevantRegNo);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        System.out.println("onDetach() called");
//        // We need an Editor object to make preference changes.
//        // All objects are from android.context.Context
//        SharedPreferences settings = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("relevantRegNo", relevantRegNo);
//        // Commit the edits!
//        editor.commit();
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
