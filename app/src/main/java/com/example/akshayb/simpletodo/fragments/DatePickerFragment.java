package com.example.akshayb.simpletodo.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akshayb.simpletodo.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatePickerDialog.OnDateSetListener} interface
 * to handle interaction events.
 */
public class DatePickerFragment extends DialogFragment {

    public void setOndateSet(DatePickerDialog.OnDateSetListener ondateSet) {
        this.ondateSet = ondateSet;
    }

    DatePickerDialog.OnDateSetListener ondateSet;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, ondateSet, year, month, day);
    }
}
