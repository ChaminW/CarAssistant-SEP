package com.bitlabs.sep_mobileapp.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.view.viewController.StatCalculate;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Satatics.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Satatics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Satatics extends Fragment {

    public static final String PREFS_NAME = "MyPrefsFile";
    private String relevantRegNo;
    View view;

    TextView stat_tv_1_vehicleReg;

    TextView stat_tv_1_1;
    TextView stat_tv_1_2;
    TextView stat_tv_1_3;
    TextView stat_tv_1_4;
    TextView stat_tv_1_5;

    TextView stat_tv_2_1;
    TextView stat_tv_2_2;
    TextView stat_tv_2_3;
    TextView stat_tv_2_4;
    TextView stat_tv_2_5;
    TextView stat_tv_2_6;
    TextView stat_tv_2_7;
    TextView stat_tv_2_8;

    TextView stat_tv_3_1;
    TextView stat_tv_3_2;
    TextView stat_tv_3_3;
    TextView stat_tv_3_4;
    TextView stat_tv_3_5;
    TextView stat_tv_3_6;
    TextView stat_tv_3_7;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Satatics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Satatics.
     */
    // TODO: Rename and change types and number of parameters
    public static Satatics newInstance(String param1, String param2) {
        Satatics fragment = new Satatics();
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
        view=inflater.inflate(R.layout.fragment_satatics, container, false);

        stat_tv_1_vehicleReg= (TextView) view.findViewById(R.id.stat_tv_vehicle);

        stat_tv_1_1= (TextView) view.findViewById(R.id.stat_tv_1_1);
        stat_tv_1_2= (TextView) view.findViewById(R.id.stat_tv_1_2);
        stat_tv_1_3= (TextView) view.findViewById(R.id.stat_tv_1_3);
        stat_tv_1_4= (TextView) view.findViewById(R.id.stat_tv_1_4);
        stat_tv_1_5= (TextView) view.findViewById(R.id.stat_tv_1_5);

        stat_tv_2_1= (TextView) view.findViewById(R.id.stat_tv_2_1);
        stat_tv_2_2= (TextView) view.findViewById(R.id.stat_tv_2_2);
        stat_tv_2_3= (TextView) view.findViewById(R.id.stat_tv_2_3);
        stat_tv_2_4= (TextView) view.findViewById(R.id.stat_tv_2_4);
        stat_tv_2_5= (TextView) view.findViewById(R.id.stat_tv_2_5);
        stat_tv_2_6= (TextView) view.findViewById(R.id.stat_tv_2_6);
        stat_tv_2_7= (TextView) view.findViewById(R.id.stat_tv_2_7);
        stat_tv_2_8= (TextView) view.findViewById(R.id.stat_tv_2_8);

        stat_tv_3_1= (TextView) view.findViewById(R.id.stat_tv_3_1);
        stat_tv_3_2= (TextView) view.findViewById(R.id.stat_tv_3_2);
        stat_tv_3_3= (TextView) view.findViewById(R.id.stat_tv_3_3);
        stat_tv_3_4= (TextView) view.findViewById(R.id.stat_tv_3_4);
        stat_tv_3_5= (TextView) view.findViewById(R.id.stat_tv_3_5);
        stat_tv_3_6= (TextView) view.findViewById(R.id.stat_tv_3_6);
        stat_tv_3_7= (TextView) view.findViewById(R.id.stat_tv_3_7);

        StatCalculate stat=new StatCalculate(getActivity(),relevantRegNo);

        stat_tv_1_vehicleReg.setText("Vehicle : "+relevantRegNo);

        stat_tv_1_1.setText(String.valueOf(stat.getLast_odo()));
        stat_tv_1_2.setText(String.valueOf(stat.getTot_distansce())+" km");
        stat_tv_1_3.setText(String.valueOf(stat.getTot_fuel())+" L");
        stat_tv_1_4.setText(String.valueOf(stat.getTot_cost())+" $");
        stat_tv_1_5.setText(String.valueOf(stat.getTot_fill_ups()));

        stat_tv_2_1.setText(String.valueOf(stat.getService_cost())+" $");
        stat_tv_2_2.setText(String.valueOf(stat.getMaintenance_cost())+" $");
        stat_tv_2_3.setText(String.valueOf(stat.getRegistration_cost())+" $");
        stat_tv_2_4.setText(String.valueOf(stat.getParking_cost())+" $");
        stat_tv_2_5.setText(String.valueOf(stat.getWash_cost())+" $");
        stat_tv_2_6.setText(String.valueOf(stat.getFine_cost())+" $");
        stat_tv_2_7.setText(String.valueOf(stat.getTunning_cost())+" $");
        stat_tv_2_8.setText(String.valueOf(stat.getInsurance_cost())+" $");

        stat_tv_3_1.setText(String.valueOf(stat.getAvg_fill_up()));
        stat_tv_3_2.setText(String.valueOf(stat.getAvg_fill_up_bill())+" $");
        stat_tv_3_3.setText(String.valueOf(stat.getAvg_expense_per_month())+" $");
        stat_tv_3_4.setText(String.valueOf(stat.getAvg_expense_per_year())+" $");
        stat_tv_3_5.setText(String.valueOf(stat.getAvg_mileage_per_month())+" km");
        stat_tv_3_6.setText(String.valueOf(stat.getAvg_expense_per_year())+" km");
        stat_tv_3_7.setText(String.valueOf(stat.getAvg_fuel_consumption())+" $/km");


        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void restorePreferences() {
        // Restore preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        relevantRegNo = settings.getString("relevantRegNo", "XX-XXXX");
        System.out.println(relevantRegNo+" got the reg no");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
