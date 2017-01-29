package com.example.akshayb.simpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by akshayb on 1/28/17.
 */
@Table(database =  TodoDatabase.class)
public class TodoItem extends BaseModel {

    @Column
    @PrimaryKey
    int identifier;

    @Column
    String item;

}
