package com.example.akshayb.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    private static final int     EDIT_TASK_REQUEST = 1;
    private static final String  SELECTED_TASK  = "SELECTED_TASK";
    private static final String  SELECTED_POSISTION  = "SELECTED_POSISTION";
    private static final String  SAVED_TASK = "SAVED_TASK";


    ArrayList<String>    items;
    ArrayAdapter<String> itemsAdapater;
    ListView             lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        List<TodoItem> ormItems = SQLite.select().from(TodoItem.class).queryList();
//        for (TodoItem ormItem : ormItems) {
//            ormItem.delete();
//        }

        // readFile();
        readUsingORM();
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapater);

        //items.add("First Item");
        // items.add("Second Item");
        setupClickViewListener();

    }

    @Override
    protected void onStop() {
        super.onStop();
        // writeFile();
        writeUsingORM();
    }

    private void setupClickViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapater.notifyDataSetChanged();
                        // writeFile();
                        writeUsingORM();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskSelected(i);
            }
        });
    }

    private void taskSelected(int posistion) {
        String task = items.get(posistion);
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        intent.putExtra(SELECTED_TASK, task);
        intent.putExtra(SELECTED_POSISTION, posistion);
        startActivityForResult(intent, EDIT_TASK_REQUEST);
    }

    public void onAddItem(View view) {
        EditText editText = (EditText) findViewById(R.id.etNewItem);
        String text = editText.getText().toString();
        itemsAdapater.add(text);
        editText.setText("");
        // writeFile();
        writeUsingORMAtPosition(items.size() - 1);
    }

    private void readFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException ex) {
            items = new ArrayList<String>();
        }
    }

    private void writeFile() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            // items = new ArrayList<String>(FileUtils.readLines(todoFile);
            FileUtils.writeLines(todoFile, items);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeUsingORM() {

        int ix = 0;
        for (String item : items) {
            TodoItem itemRow = new TodoItem();
            itemRow.identifier = ix;
            itemRow.item = item;
            itemRow.save();
            ix+=1;
        }
    }

    private void writeUsingORMAtPosition(int pos) {

        TodoItem itemRow = new TodoItem();
        itemRow.identifier = pos;
        itemRow.item = items.get(pos);
        itemRow.save();
    }

    private void removeUsingORMAtPosition(int pos) {

        TodoItem itemRow = new TodoItem();
        itemRow.identifier = pos;
        itemRow.item = items.get(pos);
        itemRow.delete();
    }


    private void readUsingORM() {
        List<TodoItem> ormItems = SQLite.select().from(TodoItem.class).queryList();
        items = new ArrayList<String>();
        for (TodoItem ormItem : ormItems) {
            items.add(ormItem.item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK) {
            String savedText = data.getStringExtra(SAVED_TASK);
            int pos = data.getIntExtra(SELECTED_POSISTION, 0);
            items.set(pos, savedText);
            itemsAdapater.notifyDataSetChanged();
            // writeFile();
            writeUsingORMAtPosition(pos);
        }
    }
}
