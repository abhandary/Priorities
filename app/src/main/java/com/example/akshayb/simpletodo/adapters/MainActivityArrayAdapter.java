package com.example.akshayb.simpletodo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.example.akshayb.simpletodo.utils.TaskUtils;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by akshayb on 2/7/17.
 */

public class MainActivityArrayAdapter<T extends BaseModel> extends ArrayAdapter<TodoItem> {

    public MainActivityArrayAdapter(Context context, List<TodoItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TodoItem task = (TodoItem) getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        convertView = layoutInflater.inflate(R.layout.item_main_task, parent, false);

        // 2. get the label and content views
        TextView tvLabel    = (TextView) convertView.findViewById(R.id.tvViewTaskLabel);
        TextView tvContent  = (TextView) convertView.findViewById(R.id.tvViewTaskContent);

        // 3. set the task name label and task name content
        tvLabel.setText(task.getTaskName());
        tvContent.setText(TaskUtils.getStringFromDueDate(task.getDueDate()));

        switch (task.getPriority()) {
            case TaskPriorityHigh:
                tvLabel.setTextColor(Color.RED);
                tvContent.setTextColor(Color.RED);
                break;
            case TaskPriorityMedium:
                tvLabel.setTextColor(Color.BLUE);
                tvContent.setTextColor(Color.BLUE);
                break;
            case TaskPriorityLow:
                tvLabel.setTextColor(parent.getResources().getColor(R.color.colorSandyBrown));
                tvContent.setTextColor(parent.getResources().getColor(R.color.colorSandyBrown));
                break;
        }

        // 4. if task is done strike through the task
        if (task.getStatus() == Task.Status.StatusDone) {
            tvLabel.setPaintFlags(tvLabel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvContent.setPaintFlags(tvLabel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return convertView;
    }
}