package com.example.akshayb.simpletodo.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.models.TodoItem_Table;
import com.example.akshayb.simpletodo.adapters.Task;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.akshayb.simpletodo.adapters.Task.Status.StatusDone;
import static com.example.akshayb.simpletodo.adapters.Task.TaskPriority.TaskPriorityHigh;
import static com.example.akshayb.simpletodo.adapters.Task.TaskPriority.TaskPriorityLow;
import static com.example.akshayb.simpletodo.models.TodoItem_Table.status;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IDENTIFIER = "identifier";
    private static final String IS_NEW_TASK = "is_new_task";

    private long    identifier;
    private boolean isNewItem;

    List<Object> tasksContent;

    ListView lvTasks;

    View view;

    private OnFragmentInteractionListener mListener;

    EditText   etTaskName;
    EditText   etNotes;
    DatePicker datePicker;
    Spinner    spStatus;
    Spinner    spPriority;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param identifier identifier for the task item
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(long identifier, boolean isNewItem) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putLong(IDENTIFIER, identifier);
        args.putBoolean(IS_NEW_TASK, isNewItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            identifier = getArguments().getLong(IDENTIFIER);
            isNewItem = getArguments().getBoolean(IS_NEW_TASK);

        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit, null);

        Button saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
        Button cancelButton = (Button) view.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonClicked();
            }
        });

        setupTask(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();
    }

    private void setupTask(View view) {

        // 1. set the views
        etTaskName   = (EditText) view.findViewById(R.id.tvTaskNameContent);
        datePicker   = (DatePicker) view.findViewById(R.id.dpDate);
        etNotes      = (EditText)  view.findViewById(R.id.etTaskNotesContent);
        spStatus     = (Spinner) view.findViewById(R.id.spStatus);
        spPriority   = (Spinner) view.findViewById(R.id.spPriority);

        // 2. set task name label
        TextView tvTaskLabel = (TextView) view.findViewById(R.id.tvTaskLabel);
        tvTaskLabel.setText(getString(R.string.lbl_task_name));

        // 3. set the Due Date Label
        TextView tvDueDateLabel = (TextView) view.findViewById(R.id.tvDatePicker);
        tvDueDateLabel.setText(getString(R.string.lbl_due_date));

        // 4. Set the Notes Label
        TextView tvNotesLabel = (TextView) view.findViewById(R.id.tvTaskNotesLabel);
        tvNotesLabel.setText(getString(R.string.lbl_notes));

        // 5. Set the Priority Label
        TextView tvPriorityLabel = (TextView) view.findViewById(R.id.tvPriorityLabel);
        tvPriorityLabel.setText(getString(R.string.lbl_priority));

        // 6. Set the status spinner items
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_priority_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);

        // 7. Set the Status Label
        TextView tvStatusLabel = (TextView) view.findViewById(R.id.tvStatusLabel);
        tvStatusLabel.setText(getString(R.string.lbl_status));

        // 8. Set the status spinner items
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_status_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapter);




        if (isNewItem == false)
        {
            TodoItem ormItem = SQLite.select().from(TodoItem.class).where(TodoItem_Table.identifier.is(identifier)).querySingle();

            // 1. set the task name
            etTaskName.setText(ormItem.getTaskName());

            // 2. set due date

            // 3. set the notes
            etNotes.setText(ormItem.getNotes());

            // 4. set the priority
            switch (ormItem.getPriority()) {
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

            // 5. set the status

            switch (ormItem.getStatus()) {
                case StatusDone:
                    spStatus.setSelection(1);
                    break;
                default:
                    spStatus.setSelection(0);
                    break;
            }
        } else {
            // 1. set a default priority of medium
            spPriority.setSelection(1);

            // 2. set a default status of TO-DO
            spStatus.setSelection(0);
        }
    }

    String stringFromPriority(Task.TaskPriority priority) {

        if( priority == TaskPriorityLow) {
            return  getContext().getString(R.string.priority_low);
        }
        if (priority == Task.TaskPriority.TaskPriorityMedium) {
            return getContext().getString(R.string.priority_medium);
        }
        return getContext().getString(R.string.priority_high);
    }

    String stringFromStatus(Task.Status status) {

        if( status == Task.Status.StatusTODO) {
            return  getContext().getString(R.string.lbl_status_todo);
        }

        return getContext().getString(R.string.lbl_status_done);
    }

    String getStringFromDueDate (Date dueDate) {
        if (dueDate == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(dueDate);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(long identifier);
    }


    public void onCancelButtonClicked() {
        dismiss();
    }

    public void onSaveButtonClicked() {

        if (etTaskName.getText().toString().trim().length() == 0) {

            Context context = view.getContext();
            CharSequence text = "Please Enter a Task Name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        if (isNewItem) {
            identifier = System.currentTimeMillis();
        }
        writeUsingORMAtPosition(identifier);
        if (mListener != null) {
            mListener.onFragmentInteraction(identifier);
        }
        dismiss();
    }

    private void writeUsingORMAtPosition( long identifier) {

        TodoItem itemRow = new TodoItem();

        // 1. set the unique identifier for this task item
        itemRow.setIdentifier(identifier);

        // 2. set the task name.
        itemRow.setTaskName(etTaskName.getText ().toString());

        // 3. set the notes text
        itemRow.setNotes(etNotes.getText().toString());

        // 4. set the priority
        switch (spPriority.getSelectedItemPosition()) {
            case 0:
                itemRow.setPriority(TaskPriorityHigh);
                break;
            case 1:
                itemRow.setPriority(Task.TaskPriority.TaskPriorityMedium);
                break;
            default:
                itemRow.setPriority(TaskPriorityLow);
                break;
        }
        switch (spStatus.getSelectedItemPosition()) {
            case 0:
                itemRow.setStatus(Task.Status.StatusTODO);
                break;
            default:
                itemRow.setStatus(StatusDone);
                break;
        }

        // 5. set the due date.
        Calendar cal = Calendar.getInstance();
        DatePicker dp = datePicker;
        cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
        itemRow.setDueDate(cal.getTime());

        // 6. add a new row or update an existing row.
        itemRow.save();
    }


}
