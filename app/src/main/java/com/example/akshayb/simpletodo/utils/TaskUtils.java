package com.example.akshayb.simpletodo.utils;

import android.content.Context;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.adapters.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by akshayb on 2/7/17.
 */

public class TaskUtils {
    public static String getStringFromPriority(Context context, Task.TaskPriority priority) {

        if( priority == Task.TaskPriority.TaskPriorityLow) {
            return  context.getString(R.string.priority_low);
        }
        if (priority == Task.TaskPriority.TaskPriorityMedium) {
            return context.getString(R.string.priority_medium);
        }
        return context.getString(R.string.priority_high);
    }

    public static String getStringFromStatus(Context context, Task.Status status) {

        if( status == Task.Status.StatusTODO) {
            return  context.getString(R.string.lbl_status_todo);
        }

        return context.getString(R.string.lbl_status_done);
    }

    public static String getStringFromDueDate (Date dueDate) {
        if (dueDate == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(dueDate);
    }
}
