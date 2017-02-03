package com.example.akshayb.simpletodo.models;

import com.example.akshayb.simpletodo.adapters.Task;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by akshayb on 1/28/17.
 */
@Table(database =  TodoDatabase.class)
public class TodoItem extends BaseModel {

    public int getIdentifier() {
        return identifier;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task.TaskPriority getPriority() {
        return priority;
    }

    public Task.Status getStatus() {
        return status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setPriority(Task.TaskPriority priority) {
        this.priority = priority;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column
    @PrimaryKey
    int identifier;

    @Column
    String taskName;

    @Column
    Task.TaskPriority priority;

    @Column
    Task.Status status;

    @Column
    Date dueDate;

    @Column
    String notes;


}
