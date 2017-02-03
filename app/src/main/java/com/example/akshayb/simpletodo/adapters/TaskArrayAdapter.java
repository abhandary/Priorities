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

/**
 * Created by akshayb on 1/28/17.
 */

public class TaskArrayAdapter extends ArrayAdapter<Object> {

    FragmentManager fragmentManager;

    public TaskArrayAdapter(Context context, List<Object> objects, FragmentManager lclFragmentManager) {
        super(context, android.R.layout.simple_list_item_1, objects);
        fragmentManager = lclFragmentManager;
    }

    private class ViewHolder {
        TextView tvLabel;
        TextView tvText;

    }


    public View getView(int position, View convertView, ViewGroup parent) {

//        ViewHolder viewHolder;
//
//        // get the data item for the position
//
//
//        if (convertView == null || convertView.getTag(R.string.task_item_view_holder) == null) {
//            // if it's not being recycled then inflate the view
//            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//            convertView = layoutInflater.inflate(R.layout.item_task, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
//            viewHolder.tvText = (TextView) convertView.findViewById(R.id.tvTaskContent);
//            convertView.setTag(R.string.task_item_view_holder, viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag(R.string.task_item_view_holder);
//        }
//
//        viewHolder.tvText.setText(taskContent);

        switch (position) {
            case 0:
                String taskContent = (String) getItem(position);
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_task, parent, false);
                TextView tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                TextView tvText = (TextView) convertView.findViewById(R.id.tvTaskContent);

                // set the text and content for the 'Task Name' row
                tvLabel.setText(getContext().getString(R.string.lbl_task_name));
                tvText.setText(taskContent);
                break;
            case 1:
                final Date onDate = (Date) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_date, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);

                // set the text and content for the 'Task Name' row
                tvLabel.setText(getContext().getString(R.string.lbl_due_date));
                EditText etDate = (EditText) convertView.findViewById(R.id.etDate);


                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerFragment newFragment = new DatePickerFragment();
                        newFragment.setOndateSet(ondate);
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.add(R.id.fragment_date_picker, videoFragment).commit();
                        newFragment.show(fragmentManager, "datePicker");
                    }
                });

//                tvText.setText(taskContent);

//                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_due_date));
//                viewHolder.tvText.setText(getStringFromDueDate(task.getDueDate()));
                break;
            case 2:
                taskContent = (String) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_notes, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                tvText = (TextView) convertView.findViewById(R.id.tvTaskContent);

                // set the text and content for the 'Task Name' row
                tvLabel.setText(getContext().getString(R.string.lbl_notes));
                tvText.setText(taskContent);

//                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_notes));
//                viewHolder.tvText.setText(task.getNotes());
                break;
            case 3:
                taskContent = (String) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_priority, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
                // 2. Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.spinner_priority_items, android.R.layout.simple_spinner_item);
                // 3. Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // 4. Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                // set the text and content for the 'Task Name' row
                tvLabel.setText(getContext().getString(R.string.lbl_priority));

//                tvText.setText(taskContent);

//                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_priority));
//                viewHolder.tvText.setText(stringFromPriority(task.getPriority()));
                break;
            case 4:
                taskContent = (String) getItem(position);
                layoutInflater = LayoutInflater.from(getContext());
                convertView = layoutInflater.inflate(R.layout.item_priority, parent, false);
                tvLabel = (TextView) convertView.findViewById(R.id.tvTaskLabel);
                spinner = (Spinner) convertView.findViewById(R.id.spinner);
                // 2. Create an ArrayAdapter using the string array and a default spinner layout
                adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.spinner_status_items, android.R.layout.simple_spinner_item);
                // 3. Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // 4. Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                // set the text and content for the 'Task Name' row
                tvLabel.setText(getContext().getString(R.string.lbl_status));

//                viewHolder.tvLabel.setText(getContext().getString(R.string.lbl_status));
//                viewHolder.tvText.setText(stringFromStatus(task.getStatus()));
                break;
        }

        return convertView;
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            SimpleDateFormat dateF = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
            Calendar cal = new GregorianCalendar();
            cal.set(year, month, dayOfMonth);
            String date =  dateF.format(cal.getTime());

//            beginDate = cal.getTime();
//            etDate.setText(date);
            Log.d("DEBUG", year + "" + month + "" + dayOfMonth + "");
        }
    };

}
