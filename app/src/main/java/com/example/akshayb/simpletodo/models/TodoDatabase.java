package com.example.akshayb.simpletodo.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by akshayb on 1/28/17.
 */
@Database(name= TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {
    public static final String NAME = "TodoDatabase";
    public static final int VERSION = 1;
}
