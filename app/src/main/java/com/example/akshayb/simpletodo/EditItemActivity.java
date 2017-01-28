package com.example.akshayb.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.akshayb.simpletodo.R.id.editText;

public class EditItemActivity extends AppCompatActivity {

    private static final String  SELECTED_TASK  = "SELECTED_TASK";
    private static final String  SELECTED_POSISTION  = "SELECTED_POSISTION";
    private static final String  SAVED_TASK = "SAVED_TASK";

    private EditText edText;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String task = getIntent().getStringExtra(SELECTED_TASK);
        this.pos = getIntent().getIntExtra(SELECTED_POSISTION, 0);

        edText = (EditText) findViewById(editText);
        edText.setText(task);
        edText.setSelection(edText.getText().length());
        edText.requestFocus();
    }

    public void onSaveItem(View view) {
        Intent result = new Intent();
        result.putExtra(SAVED_TASK, edText.getText().toString());
        result.putExtra(SELECTED_POSISTION, this.pos);
        setResult(RESULT_OK, result);
        finish();
    }

}
