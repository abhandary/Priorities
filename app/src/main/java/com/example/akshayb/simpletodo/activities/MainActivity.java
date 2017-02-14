package com.example.akshayb.simpletodo.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.akshayb.simpletodo.R;
import com.example.akshayb.simpletodo.adapters.MainActivityArrayAdapter;
import com.example.akshayb.simpletodo.fragments.EditFragment;
import com.example.akshayb.simpletodo.models.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity implements EditFragment.OnFragmentInteractionListener {

    private static final int     EDIT_TASK_REQUEST = 1;
    private static final String  IDENTIFIER  = "IDENTIFIER";
    private static final String  SAVED_TASK = "SAVED_TASK";
    private static final String  EDIT_FRAGMENT  = "EDIT_FRAGMENT";

    ArrayList<TodoItem>                items;
    MainActivityArrayAdapter<TodoItem> itemsAdapater;
    ListView                           lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<>();
        readUsingORM();
        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapater = new MainActivityArrayAdapter<>(this, items);
        lvItems.setAdapter(itemsAdapater);

        setupClickViewListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void setupClickViewListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskSelected(i);
            }
        });
    }

    private void taskSelected(int posistion) {
        TodoItem task = items.get(posistion);
        Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);

        intent.putExtra(IDENTIFIER, task.getIdentifier());
        startActivityForResult(intent, EDIT_TASK_REQUEST);
    }

    private void readUsingORM() {
        List<TodoItem> ormItems = SQLite.select().from(TodoItem.class).queryList();
        items.clear();
        items.addAll(new ArrayList<TodoItem>(ormItems));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK) {
            readUsingORM();
            itemsAdapater.notifyDataSetChanged();
        }
    }


    @Override
    public void onFragmentInteraction(long identifier) {
        readUsingORM();
        itemsAdapater.notifyDataSetChanged();
    }

    public void onAddTask(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        EditFragment editFragment = EditFragment.newInstance(this.items.size(), true);
        editFragment.show(fm, EDIT_FRAGMENT);
    }
}
