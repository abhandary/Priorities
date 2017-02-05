package com.example.akshayb.simpletodo.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;


import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.models.TodoItem_Table;
import com.example.akshayb.simpletodo.adapters.Task;
import com.example.akshayb.simpletodo.adapters.TaskArrayAdapter;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.converter.CalendarConverter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    private static final String POSITION = "position";
    private static final String IS_NEW_TASK = "is_new_task";

    private int     pos;
    private boolean isNewItem;

    List<Object> tasksContent;

    ListView lvTasks;
    TaskArrayAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pos position of the task in the list
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(int pos, boolean isNewItem) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION, pos);
        args.putBoolean(IS_NEW_TASK, isNewItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pos = getArguments().getInt(POSITION);
            isNewItem = getArguments().getBoolean(IS_NEW_TASK);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit, null);

        setupListView(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();
    }

    private void setupListView(View view) {


        tasksContent = new ArrayList<Object>();
        lvTasks = (ListView) view.findViewById(R.id.lvItems);

        if (isNewItem) {
            tasksContent.add("");
            tasksContent.add(Calendar.getInstance().getTime());
            tasksContent.add("");
            tasksContent.add(Task.TaskPriority.TaskPriorityMedium);
            tasksContent.add(Task.Status.StatusTODO);
        }
        else {
            TodoItem ormItem = SQLite.select().from(TodoItem.class).where(TodoItem_Table.identifier.is(pos)).querySingle();
            tasksContent.add(ormItem.getTaskName());
            tasksContent.add(ormItem.getDueDate());
            tasksContent.add(ormItem.getNotes());
            tasksContent.add(ormItem.getPriority());
            tasksContent.add(ormItem.getStatus());
        }
        adapter = new TaskArrayAdapter(getContext(), tasksContent, getFragmentManager());
        lvTasks.setAdapter(adapter);
    }

    String stringFromPriority(Task.TaskPriority priority) {

        if( priority == Task.TaskPriority.TaskPriorityLow) {
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



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }


    public void onCancelButtonClicked(View view) {
        dismiss();
    }

    public void onSaveButtonClicked(View view) {
        writeUsingORMAtPosition(pos);
        dismiss();
    }

    private void writeUsingORMAtPosition(int pos) {


        TodoItem itemRow = new TodoItem();

        // 1. set the unique row identifier which is same as position in task list
        itemRow.setIdentifier(pos);

        // 2. set the task name.
        itemRow.setTaskName(adapter.getTvTaskName().toString());

        // 3. set the notes text
        itemRow.setNotes(adapter.getTvNotes().toString());

        // 4. set the priority
        switch (adapter.getSpPriority().getSelectedItemPosition()) {
            case 0:
                itemRow.setPriority(Task.TaskPriority.TaskPriorityHigh);
                break;
            case 1:
                itemRow.setPriority(Task.TaskPriority.TaskPriorityMedium);
                break;
            default:
                itemRow.setPriority(Task.TaskPriority.TaskPriorityLow);
                break;
        }
        switch (adapter.getSpStatus().getSelectedItemPosition()) {
            case 0:
                itemRow.setStatus(Task.Status.StatusTODO);
                break;
            default:
                itemRow.setStatus(Task.Status.StatusDone);
                break;
        }

        // 5. set the due date.
        Calendar cal = Calendar.getInstance();
        DatePicker dp = adapter.getDpDate();
        cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
        itemRow.setDueDate(cal.getTime());

        // 6. add a new row or update an existing row.
        itemRow.save();
    }


}
