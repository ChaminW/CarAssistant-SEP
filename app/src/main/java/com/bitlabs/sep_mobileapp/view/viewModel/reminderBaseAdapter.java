package com.bitlabs.sep_mobileapp.view.viewModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.bitlabs.sep_mobileapp.R;
import com.bitlabs.sep_mobileapp.data.FuelFillUp;
import com.bitlabs.sep_mobileapp.data.Reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Chamin on 6/13/2016.
 */
public class reminderBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<Reminder> reminderList;
    public reminderBaseAdapter(Context context, ArrayList<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @Override
    public int getCount() {
        return reminderList.size();
    }

    @Override
    public Object getItem(int position) {
        return reminderList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.reminder_list_item, null);

            viewHolder = new ViewHolder();

            viewHolder.reminderItem_text_1 = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.reminderItem_text_2 = (TextView) convertView.findViewById(R.id.Date);
            viewHolder.reminderItem_text_3 = (TextView) convertView.findViewById(R.id.RecurrenceType);
            viewHolder.reminderItem_switch = (Switch) convertView.findViewById(R.id.reminderOn_switch);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Reminder reminder = (Reminder) getItem(position);

        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormater.format(reminder.getTime());

        viewHolder.reminderItem_text_1.setText(reminder.getTitle()+"");
        viewHolder.reminderItem_text_2.setText(strDate+"");
        viewHolder.reminderItem_text_3.setText(reminder.getReccurenceType());
        viewHolder.reminderItem_switch.setChecked(reminder.getAlarmOn());
        viewHolder.reminderItem_switch.setClickable(false);



        return convertView;
    }

    public void setList(ArrayList<Reminder> reminderList) {
        this.reminderList=reminderList;

    }


    private class ViewHolder {

        TextView reminderItem_text_1;
        TextView reminderItem_text_2;
        TextView reminderItem_text_3;
        Switch reminderItem_switch;
    }


}
