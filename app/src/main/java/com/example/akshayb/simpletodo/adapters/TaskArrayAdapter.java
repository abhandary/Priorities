package com.example.akshayb.simpletodo.adapters;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.fragments.DatePickerFragment;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.R.attr.priority;
import static com.example.akshayb.simpletodo.R.id.etDate;
import static com.example.akshayb.simpletodo.R.id.spinner;

/**
 * Created by akshayb on 1/28/17.
 */

public class TaskArrayAdapter extends ArrayAdapter<Object> {

    private DatePicker dpDate;
    private TextView   tvTaskName;
    private TextView   tvNotes;
    private Spinner    spStatus;
    private Spinner    spPriority;

    public DatePicker getDpDate() {
        return dpDate;
    }

    public TextView getTvTaskName() {
        return tvTaskName;
    }

    public TextView getTvNotes() {
        return tvNotes;
    }

    public Spinner getSpStatus() {
        return spStatus;
    }

    public Spinner getSpPriority() {
        return spPriority;
    }

    public TaskArrayAdapter(Context context, List<Object> objects, FragmentManager lclFragmentManager) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    private class ViewHolder {
        TextView tvLabel;
        TextView tvText;

    }


    public View getView(int position, View convertView, ViewGroup parent) {

        switch (position) {
            case 0:
                // 1. get the label and task name views
                String taskContent = (String) getItem(position);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_task, parent, false);
                TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskContent);

                // 2. set the task name lable and task name content
                tvLabel.setText(getContext().getString(R.string.lbl_task_name));
                this.tvTaskName.setText(taskContent);

                break;
            case 1:
                // 1. get the label and date picker views
                final Date dueDate = (Date) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_date, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                this.dpDate = (DatePicker) convertView.findViewById(R.id.dpDate);

                // 2. set the text for the label
                tvLabel.setText(getContext().getString(R.string.lbl_due_date));

                // 3. Set the date

                break;
            case 2:
                // 1. get the label and notes views
                taskContent = (String) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_notes, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                this.tvNotes = (TextView) convertView.findViewById(R.id.tvTaskContent);

                // 2. set the text for the label and task notes
                tvLabel.setText(getContext().getString(R.string.lbl_notes));
                this.tvNotes.setText(taskContent);
                break;
            case 3:

                // 1. get the label and spinner views
                Task.TaskPriority taskPriority = (Task.TaskPriority) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_priority, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                spPriority = (Spinner) convertView.findViewById(spinner);

                // 2. Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.spinner_priority_items, android.R.layout.simple_spinner_item);

                // 3. Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // 4. Apply the adapter to the spinner
                spPriority.setAdapter(adapter);
                switch (taskPriority) {
                    case TaskPriorityHigh:
                        spPriority.setSelection(0);
                        break;
                    case TaskPriorityLow:
                        spPriority.setSelection(2);
                        break;
                    default:
                        spPriority.setSelection(1);
                        break;
                }

                // set the text and content for the 'Task Name' row
                tvLabel.setText(getContext().getString(R.string.lbl_priority));

                break;
            case 4:
                // 1. get the label and spinner views
                Task.Status status = (Task.Status) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_status, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                spStatus = (Spinner) convertView.findViewById(spinner);

                // 2. set the label text
                tvLabel.setText(getContext().getString(R.string.lbl_status));

                // 3. Create an ArrayAdapter using the string array and a default spinner layout
                adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.spinner_status_items, android.R.layout.simple_spinner_item);

                // 4. Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // 5. Apply the adapter to the spinner
                spStatus.setAdapter(adapter);
                switch (status) {
                    case StatusDone:
                        spStatus.setSelection(1);
                        break;
                    default:
                        spStatus.setSelection(0);
                        break;
                }

                break;
        }

        return convertView;
    }
}
