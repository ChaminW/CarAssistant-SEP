package com.bitlabs.sep_mobileapp.view.viewModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.data.OtherExpense;
import com.bitlabs.sep_mobileapp.data.Vehicle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Chamin on 6/12/2016.
 */
public class expenseBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<OtherExpense> otherExpenseList;
    public expenseBaseAdapter(Context context, ArrayList<OtherExpense> otherExpenseList) {
        this.context = context;
        this.otherExpenseList = otherExpenseList;
    }

    @Override
    public int getCount() {
        return otherExpenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return otherExpenseList.get(position);
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
            viewHolder.expenseItem_color_bar= (LinearLayout) convertView.findViewById(R.id.expenseItem_color_bar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        OtherExpense otherExpense = (OtherExpense) getItem(position);

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(otherExpense.getDate());

        viewHolder.expenseItem_text_1.setText(strDate);
        viewHolder.expenseItem_text_2.setText(otherExpense.getTitle()+" ");
        viewHolder.expenseItem_text_3.setText(otherExpense.getCategory()+" ");
        viewHolder.expenseItem_text_4.setText("Odo:"+otherExpense.getOdoMeter());
        viewHolder.expenseItem_text_5.setText(otherExpense.getNote()+" ");
        viewHolder.expenseItem_text_6.setText(String.valueOf(otherExpense.getCost())+" $");
        viewHolder.expenseItem_color_bar.setBackgroundColor(Color.rgb(51,255,0));



        return convertView;
    }


    private class ViewHolder {

        TextView expenseItem_text_1;
        TextView expenseItem_text_2;
        TextView expenseItem_text_3;
        TextView expenseItem_text_4;
        TextView expenseItem_text_5;
        TextView expenseItem_text_6;
        LinearLayout expenseItem_color_bar;
    }
}
