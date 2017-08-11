package com.app.task_2_android_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucifer on 12/2/17.
 */
public class data_adapter extends ArrayAdapter<data> {

    public data_adapter(Activity context, ArrayList<data> arrayList)
    {
        super(context, 0, arrayList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        data current_data = getItem(position);
        TextView pic_name = (TextView) listItemView.findViewById(R.id.name);
        pic_name.setText(current_data.getname());
        return listItemView;
    }
}
