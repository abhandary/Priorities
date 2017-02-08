package com.example.akshayb.simpletodo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.example.akshayb.simpletodo.models.ViewTaskRow;

import java.util.List;

/**
 * Created by akshayb on 2/7/17.
 */


public class ViewTaskActivityArrayAdapter<V> extends ArrayAdapter<ViewTaskRow> {

    public ViewTaskActivityArrayAdapter(Context context, List<ViewTaskRow> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Get the task item label and content for the current row
        ViewTaskRow taskRow = (ViewTaskRow) getItem(position);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(R.layout.item_view_task, parent, false);

        // 2. get the label and content views
        TextView tvLabel    = (TextView) convertView.findViewById(R.id.tvViewTaskLabel);
        TextView tvContent  = (TextView) convertView.findViewById(R.id.tvViewTaskContent);

        // 3. set the task name label and task name content
        tvLabel.setText(taskRow.getLabel());
        tvContent.setText(taskRow.getContent());

        return convertView;
    }
}
