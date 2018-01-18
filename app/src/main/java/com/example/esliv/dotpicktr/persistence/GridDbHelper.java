package com.example.esliv.dotpicktr.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.esliv.dotpicktr.persistence.GridContract.GridEntry;

/**
 * Created by esliv on 11/01/2018.
 */

public class GridDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dotpick.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_GRIDS_TABLE
            = "CREATE TABLE " + GridEntry.TABLE_NAME
            + " (" + GridEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GridEntry.COLUMN_GRIDLINES + " TEXT, "
            + GridEntry.COLUMN_GRID + " TEXT, "
            + GridEntry.COLUMN_NAME + " TEXT, "
            + GridEntry.COLUMN_SIZE + " INTEGER, "
            + GridEntry.COLUMN_PENCILCOLOR + " INTEGER"
            + ")";

    private static final String SQL_DROP_GRID_TABLE =
            "DROP TABLE IF EXISTS " + GridEntry.TABLE_NAME;

    public GridDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GRIDS_TABLE);
    }

    /**
     * Executed whenever the database is upgraded
     *
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop older table if existed
        db.execSQL(SQL_DROP_GRID_TABLE);
        //Create tables again
        onCreate(db);
    }
}
