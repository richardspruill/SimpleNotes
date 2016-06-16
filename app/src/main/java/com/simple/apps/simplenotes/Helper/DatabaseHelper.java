package com.simple.apps.simplenotes.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simple.apps.simplenotes.Models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 15.06.16.
 */
public class DatabaseHelper {
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase database;

    public DatabaseHelper(Context context){
        sqLiteHelper = new SQLiteHelper(context);
        database = sqLiteHelper.getWritableDatabase();
    }

    public void addNote(Note note){
        ContentValues values = noteToValue(note);
        long id = database.insert(SQLiteHelper.TABLE_NOTE, null, values);
        System.out.println("ID RETURNED FOR INSERT: " + String.valueOf(id));
    }

    public void deleteNote(Note note){
        database.delete(SQLiteHelper.TABLE_NOTE, SQLiteHelper.NOTE_TITLE + "=" + note.getTitle(),null);
    }

    public List<Note> getAllNotes(){
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_NOTE, null,null,null,null,null,null);
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        while (cursor.moveToNext()) {
            try {
                notes.add(new Note(cursor.getString(SQLiteHelper.NOTE_TEXT_COL), cursor.getString(SQLiteHelper.NOTE_TITLE_COL), dateFormat.parse(cursor.getString(SQLiteHelper.NOTE_DATE_COL))));

            } catch (Exception e){
                //TODO handle parse exception
            }
        }
        cursor.close();
        return notes;
    }




    private ContentValues noteToValue(Note note){
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.NOTE_TEXT, note.getText());
        values.put(SQLiteHelper.NOTE_TITLE, note.getTitle());
        values.put(SQLiteHelper.NOTE_DATE_CREATED, dateFormat.format(note.getDateCreated()));
        return values;
    }

    public void close(){
        database.close();
    }
}
