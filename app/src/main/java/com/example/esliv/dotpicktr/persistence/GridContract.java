package com.example.esliv.dotpicktr.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by esliv on 18/01/2018.
 */

public final class GridContract {

    //added voor contentprovider
    public static final String CONTENT_AUTHORITY = "com.example.esliv.provider.dotpicktr";

    //added voor contentprovider
    // BASE_CONTENT_URI: content://com.example.esliv.provider.dotpicktr
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //added voor contentprovider
    // Path to get CLIENT APP to our table
    public static final String PATH_GRIDS = "grids";

    public static final class GridEntry implements BaseColumns {

        //added voor contentprovider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GRIDS);

        // Table Name
        public static final String TABLE_NAME = "grids";

        // Columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PENCILCOLOR = "color";
        public static final String COLUMN_GRIDLINES = "gridlines";
        public static final String COLUMN_GRID = "grid";

    }
}
