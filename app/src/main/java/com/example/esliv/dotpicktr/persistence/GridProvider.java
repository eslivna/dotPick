package com.example.esliv.dotpicktr.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.example.esliv.dotpicktr.persistence.GridContract.CONTENT_AUTHORITY;
import static com.example.esliv.dotpicktr.persistence.GridContract.GridEntry.TABLE_NAME;
import static com.example.esliv.dotpicktr.persistence.GridContract.PATH_GRIDS;

import com.example.esliv.dotpicktr.persistence.GridContract.GridEntry;

/**
 * Created by esliv on 11/01/2018.
 */

public class GridProvider extends ContentProvider {

    private static final String TAG = GridProvider.class.getSimpleName();

    private GridDbHelper databaseHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int GRIDS = 1;
    private static final int GRIDS_ID = 2;

    static {
        //contains all URI patterns
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_GRIDS, GRIDS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_GRIDS + "/#", GRIDS_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new GridDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {

            case GRIDS:
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case GRIDS_ID:
                selection = GridEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)) {
            case GRIDS:
                return insertRecord(uri, values, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues values, String tableName) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long rowId = database.insert(tableName, null, values);

        if (rowId == -1) {
            Log.e(TAG, "Insert error for URI " + uri);
            return null;
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case GRIDS:            // To delete whole table
                return deleteRecord(uri, null, null, TABLE_NAME);
            case GRIDS_ID:        // To delete a row by ID
                selection = GridEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri, selection, selectionArgs, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private int deleteRecord(Uri uri, String selection, String[] selectionArgs, String tableName) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsDeleted = database.delete(tableName, selection, selectionArgs);

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case GRIDS:    // For Grids Table
                return updateRecord(uri, values, selection, selectionArgs, GridEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Unknown URI: " + uri);
        }
    }

    private int updateRecord(Uri uri, ContentValues values, String selection, String[] selectionArgs, String tableName) {

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsUpdated = database.update(tableName, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}