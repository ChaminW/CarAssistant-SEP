package com.bitlabs.sep_mobileapp.view;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.view.viewModel.Bean;
import com.bitlabs.sep_mobileapp.view.viewModel.JayBaseAdapter;

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

    private  View view;

    private ListView listview;

    Typeface fonts1,fonts2;

    private int[] IMAGE = {R.drawable.car_image1, R.drawable.car_image2, R.drawable.car_image3,
            R.drawable.car_image1, R.drawable.car_image2};

    private String[] TITLE = {"Honda Civiv", "Toyota Allion", "Nissan Blue Bird", "Woks Wagon", "Bumble Bee"};

    private String[] DESCRIPTION = {"One Size", "One Size", "Size L", "One Size", "One Size"};

    private String[] DATE = {"$ 220.00","$ 49.00","$ 320.00","$ 220.00","$ 49.00"};

    private ArrayList<com.bitlabs.sep_mobileapp.view.viewModel.Bean>Bean;
    private JayBaseAdapter baseAdapter;







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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vehicle, container, false);


        listview = (ListView)view.findViewById(R.id.vehicle_list_view);


        Bean = new ArrayList<Bean>();

        for (int i= 0; i< TITLE.length; i++){

            Bean bean = new Bean(IMAGE[i], TITLE[i], DESCRIPTION[i], DATE[i]);
            Bean.add(bean);

        }


        baseAdapter = new JayBaseAdapter(getActivity(), Bean) {
        };

        listview.setAdapter(baseAdapter);


        return view;
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
