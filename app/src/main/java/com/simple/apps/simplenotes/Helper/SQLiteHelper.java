package com.simple.apps.simplenotes.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anthony on 15.06.16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "notes.db";

    public static final String TABLE_NOTE = "note";
    public static final String NOTE_ID = "id";
    public static final String NOTE_TITLE = "title"; public static final int NOTE_TITLE_COL = 1;
    public static final String NOTE_TEXT = "text"; public static final int NOTE_TEXT_COL = 2;
    public static final String NOTE_DATE_CREATED = "created"; public static final int NOTE_DATE_COL = 3;


    private static final String CREATE_NOTE = "CREATE TABLE " + TABLE_NOTE + "("
            + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTE_TITLE + " TEXT NOT NULL, "
            + NOTE_TEXT + " TEXT NOT NULL, "
            + NOTE_DATE_CREATED + " DATE DEFAULT (datetime('now','localtime'))"
            + ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
