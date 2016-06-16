package com.bitlabs.sep_mobileapp.view.viewModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.OtherExpense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Chamin on 6/13/2016.
 */
public class fillUpBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<FuelFillUp> fillUpList;
    public fillUpBaseAdapter(Context context, ArrayList<FuelFillUp> fillUpList) {
        this.context = context;
        this.fillUpList = fillUpList;
    }

    @Override
    public int getCount() {
        return fillUpList.size();
    }

    @Override
    public Object getItem(int position) {
        return fillUpList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expense_list_item, null);

            viewHolder = new ViewHolder();

            viewHolder.expenseItem_text_1 = (TextView) convertView.findViewById(R.id.expenseItem_text_1);
            viewHolder.expenseItem_text_2 = (TextView) convertView.findViewById(R.id.expenseItem_text_2);
            viewHolder.expenseItem_text_3 = (TextView) convertView.findViewById(R.id.expenseItem_text_3);
            viewHolder.expenseItem_text_4 = (TextView) convertView.findViewById(R.id.expenseItem_text_4);
            viewHolder.expenseItem_text_5 = (TextView) convertView.findViewById(R.id.expenseItem_text_5);
            viewHolder.expenseItem_text_6 = (TextView) convertView.findViewById(R.id.expenseItem_text_6);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        FuelFillUp fuelFillUp = (FuelFillUp) getItem(position);

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(fuelFillUp.getDate());

        viewHolder.expenseItem_text_1.setText(strDate);
        viewHolder.expenseItem_text_2.setText(fuelFillUp.getAmount()+" Liters");
        viewHolder.expenseItem_text_3.setText("Odo: "+fuelFillUp.getOdoMeter());
        viewHolder.expenseItem_text_4.setText(fuelFillUp.getCostPerLiter()+"0 $/Liter");
        viewHolder.expenseItem_text_5.setText(fuelFillUp.getNote()+" ");
        viewHolder.expenseItem_text_6.setText(fuelFillUp.getCost()+"0 $");


        return convertView;
    }


    private class ViewHolder {

        TextView expenseItem_text_1;
        TextView expenseItem_text_2;
        TextView expenseItem_text_3;
        TextView expenseItem_text_4;
        TextView expenseItem_text_5;
        TextView expenseItem_text_6;
    }


}
