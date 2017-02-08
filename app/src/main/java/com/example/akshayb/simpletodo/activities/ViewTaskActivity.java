package com.example.akshayb.simpletodo.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.adapters.MainActivityArrayAdapter;
import com.example.akshayb.simpletodo.adapters.ViewTaskActivityArrayAdapter;
import com.example.akshayb.simpletodo.fragments.EditFragment;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.example.akshayb.simpletodo.models.TodoItem_Table;
import com.example.akshayb.simpletodo.models.ViewTaskRow;
import com.example.akshayb.simpletodo.utils.TaskUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

public class ViewTaskActivity extends AppCompatActivity {

    private static final String  SELECTED_POSISTION  = "SELECTED_POSISTION";
    private static final String  EDIT_FRAGMENT  = "EDIT_FRAGMENT";

    private EditText edText;
    private int pos;

    ArrayList<ViewTaskRow> items;
    ViewTaskActivityArrayAdapter<ViewTaskRow> itemsAdapater;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.pos = getIntent().getIntExtra(SELECTED_POSISTION, 0);

        TodoItem ormItem
                = SQLite.select()
                .from(TodoItem.class)
                .where(TodoItem_Table.identifier.eq(this.pos))
                .querySingle();


        items = new ArrayList<ViewTaskRow>();

        items.add(new ViewTaskRow(getString(R.string.lbl_task_name), ormItem.getTaskName()));
        items.add(new ViewTaskRow(getString(R.string.lbl_priority),
                TaskUtils.getStringFromPriority(this, ormItem.getPriority())));
        items.add(new ViewTaskRow(getString(R.string.lbl_due_date),
                TaskUtils.getStringFromDueDate(ormItem.getDueDate())));
        items.add(new ViewTaskRow(getString(R.string.lbl_notes), ormItem.getNotes()));
        items.add(new ViewTaskRow(getString(R.string.lbl_status), ormItem.getTaskName()));

        lvItems = (ListView) findViewById(R.id.lvTasks);
        itemsAdapater = new ViewTaskActivityArrayAdapter<>(this, items);
        lvItems.setAdapter(itemsAdapater);
    }

//    public void onSaveItem(View view) {
//        Intent result = new Intent();
//        result.putExtra(SAVED_TASK, edText.getText().toString());
//        result.putExtra(SELECTED_POSISTION, this.pos);
//        setResult(RESULT_OK, result);
//        finish();
//    }

    public void onEditTask(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        EditFragment editFragment = EditFragment.newInstance(this.pos, false);
        editFragment.show(fm, EDIT_FRAGMENT);
    }

    public void onClose(MenuItem item) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_task, menu);
        return true;
    }

}
