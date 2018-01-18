package com.example.esliv.dotpicktr.activities;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.fragments.AddGridDialogFragment;
import com.example.esliv.dotpicktr.models.Grid;
import com.example.esliv.dotpicktr.persistence.GridContract;
import com.example.esliv.dotpicktr.persistence.GridProvider;

public class CreateActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> , AddGridDialogFragment.OnSelectionClickListener {

    public static String ARG_SIZE = "size";
    public static String ARG_ID = "id";
    public static String ARG_NAME ="name";
    public static String ARG_GRID ="grid";
    public static String ARG_GRIDLINES= "gridLines";
    public static String ARG_PENCILCOLOR ="pencilcolor";

    private SimpleCursorAdapter simpleCursorAdapter;
    private Cursor cursor;
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        String[] from = { GridContract.GridEntry.COLUMN_NAME};
        int[] to = { R.id.name};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.fragment_grid_list, null, from, to, 0);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor = (Cursor) parent.getItemAtPosition(position);

                int _id = cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry._ID));
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                intent.putExtra(ARG_ID, _id);
                intent.putExtra(ARG_NAME, cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_NAME)));
                intent.putExtra(ARG_GRID, cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_GRID)));
                intent.putExtra(ARG_GRIDLINES, cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_GRIDLINES)));
                intent.putExtra(ARG_SIZE, cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_SIZE)));
                intent.putExtra(ARG_PENCILCOLOR, cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_PENCILCOLOR)));
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                AddGridDialogFragment dialogFragment = new AddGridDialogFragment();
                dialogFragment.show(fm, "Create");
            }
        });
    }


    @Override
    public void onGridSize(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (position) {
            case 0:
                intent.putExtra(ARG_SIZE, 16);
                break;
            case 1:
                intent.putExtra(ARG_SIZE, 32);
                break;
            case 2:
                intent.putExtra(ARG_SIZE, 64);
                break;
            case 3:
                intent.putExtra(ARG_SIZE, 128);
                break;
        }
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                GridContract.GridEntry._ID,
                GridContract.GridEntry.COLUMN_SIZE,
                GridContract.GridEntry.COLUMN_GRIDLINES,
                GridContract.GridEntry.COLUMN_GRID,
                GridContract.GridEntry.COLUMN_NAME
        };

        String sortOrder = GridContract.GridEntry.COLUMN_NAME + " ASC";

        return new CursorLoader(this, GridContract.GridEntry.CONTENT_URI, projection, null, null, sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        simpleCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }


}
