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

public class NoteActivity extends AppCompatActivity {
    private EditText title;
    private EditText text;
    private DatabaseHelper database;
    private boolean editing;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setupUI();


    }

    private void setupUI() {
        getSupportActionBar().setTitle("Add Note");
        title = (EditText)findViewById(R.id.editText_Title);
        text = (EditText)findViewById(R.id.editText_note);
        Bundle extras = getIntent().getExtras();
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
        database = new DatabaseHelper(this);

        if (extras != null){
            editing = true;
            int id = extras.getInt(Note.ID_KEY);
            Note note = database.getNote(id);
            if (note == null) return;
            title.setText(note.getTitle());
            text.setText(note.getText());
            this.id = id;
        } else {
            editing = false;
            title.requestFocus();
        }
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

        Note note1 = new Note(note, sTitle, new Date());
        if (editing){
            note1.setId(id);
            database.updateNote(note1);
        } else {
            database.addNote(note1);
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
