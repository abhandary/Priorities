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
import static com.example.akshayb.simpletodo.adapters.Task.Status.StatusDone;
import static com.example.akshayb.simpletodo.adapters.Task.TaskPriority.TaskPriorityHigh;
import static com.example.akshayb.simpletodo.adapters.Task.TaskPriority.TaskPriorityLow;
import static com.example.akshayb.simpletodo.models.TodoItem_Table.status;

/**
 * Created by akshayb on 1/28/17.
 */


class ViewHolder {
    DatePicker dpDate;
    TextView   tvLabel;
    TextView   tvTaskName;
    TextView   tvNotes;
    Spinner    spStatus;
    Spinner    spPriority;
}

public class EditTaskFragmentArrayAdapter extends ArrayAdapter<Object> {

    ViewHolder viewHolder;

    private DatePicker dpDate;
    private TextView   tvTaskName;
    private TextView   tvNotes;
    private Spinner    spStatus;
    private Spinner    spPriority;

    public String taskName;

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

    public EditTaskFragmentArrayAdapter(Context context, List<Object> objects, FragmentManager lclFragmentManager) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.item_edit_heterogeneous, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvLabel    = (TextView) convertView.findViewById(R.id.tvTaskLabel);
            viewHolder.tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskContent);
            viewHolder.dpDate     = (DatePicker) convertView.findViewById(R.id.dpDate);
            viewHolder.tvNotes    = (TextView) convertView.findViewById(R.id.tvTaskNotes);
            viewHolder.spPriority = (Spinner)  convertView.findViewById(R.id.spPriority);
            viewHolder.spStatus   = (Spinner)  convertView.findViewById(R.id.spStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (position) {

            // 1. task name
            case 0:
                String taskContent = (String) getItem(position);
                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_task_name));
                viewHolder.tvTaskName.setText(taskContent);
                viewHolder.dpDate.setVisibility(View.INVISIBLE);
                viewHolder.tvNotes.setVisibility(View.INVISIBLE);
                viewHolder.spPriority.setVisibility(View.INVISIBLE);
                viewHolder.spStatus.setVisibility(View.INVISIBLE);
                break;

            // 2. due date
            case 1:
                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_due_date));
                viewHolder.tvTaskName.setVisibility(View.INVISIBLE);
                viewHolder.dpDate.setVisibility(View.VISIBLE);
                viewHolder.tvNotes.setVisibility(View.INVISIBLE);
                viewHolder.spPriority.setVisibility(View.INVISIBLE);
                viewHolder.spStatus.setVisibility(View.INVISIBLE);

                break;

            // 3. notes
            case 2:
                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_due_date));
                viewHolder.tvTaskName.setVisibility(View.INVISIBLE);
                viewHolder.dpDate.setVisibility(View.INVISIBLE);
                viewHolder.tvNotes.setVisibility(View.VISIBLE);
                viewHolder.spPriority.setVisibility(View.INVISIBLE);
                viewHolder.spStatus.setVisibility(View.INVISIBLE);

                String notesContent = (String) getItem(position);
                viewHolder.tvNotes.setText(notesContent);
                break;

            // priority
            case 3:
                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_priority));
                viewHolder.tvTaskName.setVisibility(View.INVISIBLE);
                viewHolder.dpDate.setVisibility(View.INVISIBLE);
                viewHolder.tvNotes.setVisibility(View.INVISIBLE);
                viewHolder.spPriority.setVisibility(View.VISIBLE);
                viewHolder.spStatus.setVisibility(View.INVISIBLE);

                Task.TaskPriority taskPriority = (Task.TaskPriority) getItem(position);

                // 2. Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(parent.getContext(),
                        R.array.spinner_priority_items, android.R.layout.simple_spinner_item);

                // 3. Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // 4. Apply the adapter to the spinner
                viewHolder.spPriority.setAdapter(adapter);
                switch (taskPriority) {
                    case TaskPriorityHigh:
                        viewHolder.spPriority.setSelection(0);
                        break;
                    case TaskPriorityLow:
                        viewHolder.spPriority.setSelection(2);
                        break;
                    default:
                        viewHolder.spPriority.setSelection(1);
                        break;
                }

                break;

            // status
            case 4:
                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_status));
                viewHolder.tvTaskName.setVisibility(View.INVISIBLE);
                viewHolder.dpDate.setVisibility(View.INVISIBLE);
                viewHolder.tvNotes.setVisibility(View.INVISIBLE);
                viewHolder.spPriority.setVisibility(View.INVISIBLE);
                viewHolder.spStatus.setVisibility(View.VISIBLE);

                Task.Status status = (Task.Status) getItem(position);
                // 3. Create an ArrayAdapter using the string array and a default spinner layout
                adapter = ArrayAdapter.createFromResource(parent.getContext(),
                        R.array.spinner_status_items, android.R.layout.simple_spinner_item);

                // 4. Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // 5. Apply the adapter to the spinner
                viewHolder.spStatus.setAdapter(adapter);
                switch (status) {
                    case StatusDone:
                        viewHolder.spStatus.setSelection(1);
                        break;
                    default:
                        viewHolder.spStatus.setSelection(0);
                        break;
                }

                break;
        }

        return convertView;

        /*
        switch (position) {
            case 0:
                String taskContent = (String) getItem(position);
                if (convertView == null)
                {
                    // 1. get the label and task name views
                    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                    convertView = layoutInflater.inflate(R.layout.item_task, parent, false);
                }
            {
                TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskContent);

                // 2. set the task name lable and task name content
                if (tvLabel != null) {
                    tvLabel.setText(getContext().getString(R.string.lbl_task_name));
                }
                if (tvTaskName != null) {
                    this.tvTaskName.setText(taskContent);
                }

            }
                break;
            case 1:
                // 1. get the label and date picker views
                final Date dueDate = (Date) getItem(position);
                //if (convertView == null)
                {

                    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                    convertView = layoutInflater.inflate(R.layout.item_date, parent, false);
                }
            {
                TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                this.dpDate = (DatePicker) convertView.findViewById(R.id.dpDate);

                // 2. set the text for the label
                tvLabel.setText(getContext().getString(R.string.lbl_due_date));
            }
                // 3. Set the date

                break;
            case 2:
                // 1. get the label and notes views
                String notesContent = (String) getItem(position);
                //if (convertView == null)
                {

                    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                    convertView = layoutInflater.inflate(R.layout.item_notes, parent, false);
                }
            {
                TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                this.tvNotes = (TextView) convertView.findViewById(R.id.tvTaskContent);

                // 2. set the text for the label and task notes
                tvLabel.setText(getContext().getString(R.string.lbl_notes));
                this.tvNotes.setText(notesContent);
            }
                break;
            case 3:
                // if (convertView == null)
                {
                    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                    convertView = layoutInflater.inflate(R.layout.item_priority, parent, false);

                }
            {
                    // 1. get the label and spinner views

                    TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                    spPriority = (Spinner) convertView.findViewById(spinner);

                    Task.TaskPriority taskPriority = (Task.TaskPriority) getItem(position);

                    // 2. Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(parent.getContext(),
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
                }
                break;
            case 4:
                // if (convertView == null)
                {
                    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                    convertView = layoutInflater.inflate(R.layout.item_status, parent, false);
                }

                {
                    // 1. get the label and spinner views

                    TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                    spStatus = (Spinner) convertView.findViewById(spinner);

                    // 2. set the label text
                    tvLabel.setText(getContext().getString(R.string.lbl_status));

                    Task.Status status = (Task.Status) getItem(position);
                    // 3. Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(parent.getContext(),
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
                }
                break;
        }

        return convertView;
        */
    }

}
