package com.simple.apps.simplenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.simple.apps.simplenotes.Adapter.NoteAdapter;
import com.simple.apps.simplenotes.Helper.DatabaseHelper;
import com.simple.apps.simplenotes.Models.Note;

import java.util.ArrayList;

/**
 * Displays every Note by the corresponding title. By clicking on a Note, it will open an activity to view/edit that note.
 */
public class MainActivity extends AppCompatActivity {
    private NoteAdapter noteAdapter;
    private DatabaseHelper database;
    public static final int REQUEST_ADD_NOTE = 7;
    public static final int REQUEST_EDIT_NOTE = 8;
    private TextView emptyView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNote(){
        startActivityForResult(new Intent(this, NoteActivity.class), REQUEST_ADD_NOTE);
    }

    /**
     * Set UI up
     */
    private void setupUI(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        noteAdapter = new NoteAdapter(this, new ArrayList<Note>());

        listView.setAdapter(noteAdapter);

        database = new DatabaseHelper(this);
        emptyView = (TextView)findViewById(R.id.empty);
        loadListView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ADD_NOTE:{
                if (resultCode == RESULT_OK){
                    Snackbar.make(findViewById(R.id.listView), "Your Note was successfully added!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    loadListView();
                }
            }
            case REQUEST_EDIT_NOTE:{
                if (resultCode == RESULT_OK){
                    Snackbar.make(findViewById(R.id.listView), "Your Note was successfully edited!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    loadListView();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    private void loadListView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteAdapter.clear();
                noteAdapter.addAll(database.getAllNotes());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        noteAdapter.notifyDataSetChanged();
                        if (noteAdapter.getCount() == 0)
                            emptyView.setVisibility(View.VISIBLE);
                        else
                            emptyView.setVisibility(View.GONE);
                    }
                });

            }
        }).start();



    }
}
