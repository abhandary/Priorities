package com.example.akshayb.simpletodo.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.adapters.ViewTaskActivityArrayAdapter;
import com.example.akshayb.simpletodo.fragments.EditFragment;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.example.akshayb.simpletodo.models.TodoItem_Table;
import com.example.akshayb.simpletodo.models.ViewTaskRow;
import com.example.akshayb.simpletodo.utils.TaskUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

public class ViewTaskActivity extends AppCompatActivity implements EditFragment.OnFragmentInteractionListener {

    private static final String  IDENTIFIER  = "IDENTIFIER";
    private static final String  EDIT_FRAGMENT  = "EDIT_FRAGMENT";

    private EditText edText;
    private long taskIdentifier;

    ArrayList<ViewTaskRow> items;
    ViewTaskActivityArrayAdapter<ViewTaskRow> itemsAdapater;
    ListView lvItems;

    private boolean taskUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);

        // 1. setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.taskIdentifier = getIntent().getLongExtra(IDENTIFIER, 0);

        // 2. setup the list adapater
        items = new ArrayList<ViewTaskRow>();
        itemsAdapater = new ViewTaskActivityArrayAdapter<>(this, items);
        lvItems = (ListView) findViewById(R.id.lvTasks);
        lvItems.setAdapter(itemsAdapater);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditFragment();
            }
        });

        // 3. setup the list for the given task identifier
        updateListForPosistion(this.taskIdentifier);
    }

    public void onEditTask(MenuItem item) {
        showEditFragment();
    }

    private void showEditFragment() {
        FragmentManager fm = getSupportFragmentManager();
        EditFragment editFragment = EditFragment.newInstance(this.taskIdentifier, false);
        editFragment.show(fm, EDIT_FRAGMENT);
    }

    public void onClose(MenuItem item) {

        if (taskUpdated) {
            // set the intent result to indicate that there was an update
            Intent result = new Intent();
            setResult(RESULT_OK, result);
        }
        finish();
    }

    public void onDeleteTask(MenuItem item) {

        // 1. get the task for the given identifier
        TodoItem ormItem
                = SQLite.select()
                .from(TodoItem.class)
                .where(TodoItem_Table.identifier.eq(taskIdentifier))
                .querySingle();


        // 2. delete
        ormItem.delete();

        // 3. set the intent result to indicate there was an update
        Intent result = new Intent();
        setResult(RESULT_OK, result);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_task, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(long taskIdentifier) {
        taskUpdated = true;
        updateListForPosistion(taskIdentifier);
    }

    private void updateListForPosistion(long taskIdentifier) {

        // 1. get the task
        TodoItem ormItem
                = SQLite.select()
                .from(TodoItem.class)
                .where(TodoItem_Table.identifier.eq(taskIdentifier))
                .querySingle();

        items.clear();

        // 2. add each task field
        items.add(new ViewTaskRow(getString(R.string.lbl_task_name), ormItem.getTaskName()));
        items.add(new ViewTaskRow(getString(R.string.lbl_priority),
                TaskUtils.getStringFromPriority(this, ormItem.getPriority())));
        items.add(new ViewTaskRow(getString(R.string.lbl_due_date),
                TaskUtils.getStringFromDueDate(ormItem.getDueDate())));
        items.add(new ViewTaskRow(getString(R.string.lbl_notes), ormItem.getNotes()));
        items.add(new ViewTaskRow(getString(R.string.lbl_status), TaskUtils.getStringFromStatus(this, ormItem.getStatus())));

        // 3. notify change
        itemsAdapater.notifyDataSetChanged();
    }

}
