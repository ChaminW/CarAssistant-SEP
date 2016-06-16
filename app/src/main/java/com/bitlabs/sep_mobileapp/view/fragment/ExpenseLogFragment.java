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
import com.bitlabs.sep_mobileapp.controller.OtherExpenseDAO;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.view.activity.AddOtherExpense;
import com.bitlabs.sep_mobileapp.view.viewModel.expenseBaseAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseLogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenseLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseLogFragment extends Fragment {

    public static final String PREFS_NAME = "MyPrefsFile";

    private View view;
    private ListView listview;

    private ArrayList<OtherExpense>  expenseList;
    private expenseBaseAdapter baseAdapter;
    private OtherExpense otherExpense;

    private String relevantRegNo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ExpenseLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenseLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseLogFragment newInstance(String param1, String param2) {
        ExpenseLogFragment fragment = new ExpenseLogFragment();
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
        view = inflater.inflate(R.layout.fragment_expense_log, container, false);

        OtherExpenseDAO otherExpenseDAO = new OtherExpenseDAO(getActivity());
        expenseList = otherExpenseDAO.getOtherExpenseAsRegNo(relevantRegNo);

        listview = (ListView) view.findViewById(R.id.expense_listView);


        baseAdapter = new expenseBaseAdapter(getActivity(), expenseList) {
        };
        listview.setAdapter(baseAdapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                OtherExpense entry= (OtherExpense) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), AddOtherExpense.class);
                int expenseId = entry.getId();



                intent.putExtra("Id", expenseId);
                intent.putExtra("type", "edit");
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
//        System.out.println("onDetach() called");
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
