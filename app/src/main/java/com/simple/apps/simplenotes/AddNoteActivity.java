package com.simple.apps.simplenotes;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simple.apps.simplenotes.Helper.DatabaseHelper;
import com.simple.apps.simplenotes.Models.Note;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {
    private EditText title;
    private EditText text;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setupUI();


    }

    private void setupUI() {
        getSupportActionBar().setTitle("Add Note");
        Button accept = (Button) findViewById(R.id.btn_add_note);
        Button cancel = (Button) findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept(v);
            }
        });
        title = (EditText)findViewById(R.id.editText_Title);
        title.requestFocus();
        text = (EditText)findViewById(R.id.editText_note);
        database = new DatabaseHelper(this);
    }

    private void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void accept(View v) {
        String sTitle = title.getText().toString(), note = text.getText().toString();
        if (sTitle.isEmpty()){
            title.setError("You have to give your note a title!");
            return;
        } else if (note.isEmpty()){
            text.setError("Your note doesn't have any text!");
            return;
        }


        database.addNote(new Note(note, sTitle, new Date()));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
