package com.simple.apps.simplenotes.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public long addNote(Note note){
        ContentValues values = noteToValue(note);
        long id = database.insert(SQLiteHelper.TABLE_NOTE, null, values);
        System.out.println("ID RETURNED FOR INSERT: " + String.valueOf(id));
        return id;
    }

    public void updateNote(Note note){
        ContentValues values = noteToValue(note);
        database.update(SQLiteHelper.TABLE_NOTE, values, SQLiteHelper.NOTE_ID+"="+note.getId(),null);
    }

    public void deleteNote(Note note){
        database.delete(SQLiteHelper.TABLE_NOTE, SQLiteHelper.NOTE_ID + "=" + note.getId(),null);
        Log.v("Database", "Note deleted.");
    }

    public List<Note> getAllNotes(){
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_NOTE, null,null,null,null,null,null);
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        while (cursor.moveToNext()) {
            try {
                notes.add(new Note(cursor.getInt(0),cursor.getString(SQLiteHelper.NOTE_TEXT_COL), cursor.getString(SQLiteHelper.NOTE_TITLE_COL), dateFormat.parse(cursor.getString(SQLiteHelper.NOTE_DATE_COL))));

            } catch (Exception e){
                //TODO handle parse exception
                Log.e("Database","Parse exception: " + e.getLocalizedMessage());
            }
        }
        cursor.close();
        return notes;
    }

    public Note getNote(int id){
        Cursor cursor = database.query(SQLiteHelper.TABLE_NOTE, null,SQLiteHelper.NOTE_ID+"="+id,null,null,null,null);
        while (cursor.moveToNext()) {
            DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
            try {
                return new Note(cursor.getInt(0),cursor.getString(SQLiteHelper.NOTE_TEXT_COL), cursor.getString(SQLiteHelper.NOTE_TITLE_COL), dateFormat.parse(cursor.getString(SQLiteHelper.NOTE_DATE_COL)));
            } catch (Exception e){
                Log.e("Database","Parse exception: " + e.getLocalizedMessage());
            }
        }
        return null;
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
