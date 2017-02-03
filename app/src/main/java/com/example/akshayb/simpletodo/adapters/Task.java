package com.example.akshayb.simpletodo.adapters;

import com.example.akshayb.simpletodo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by akshayb on 1/28/17.
 */




public class Task {
    public enum TaskPriority {
        TaskPriorityLow,
        TaskPriorityMedium,
        TaskPriorityHigh
    }

    public enum Status {
        StatusTODO,
        StatusDone
    }

    private String          taskName;
    private TaskPriority    priority;
    private Status          status;
    private Date            dueDate;
    private String          notes;

    TaskPriority getPriority() {
        return priority;
    }

    Status getStatus() {
        return status;
    }

    String getTaskName() {
        return taskName;
    }

    Date getDueDate() {
        return dueDate;
    }

    String getNotes() {
        return notes;
    }
}
