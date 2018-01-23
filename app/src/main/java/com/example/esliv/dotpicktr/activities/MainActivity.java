package com.example.esliv.dotpicktr.activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.esliv.dotpicktr.DotPicktApplication;
import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.fragments.BoardFragment;
import com.example.esliv.dotpicktr.fragments.ColorPickerFragment;
import com.example.esliv.dotpicktr.fragments.GalleryFragment;
import com.example.esliv.dotpicktr.models.Grid;
import com.example.esliv.dotpicktr.persistence.GridContract;
import com.example.esliv.dotpicktr.persistence.QueryHandler;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.example.esliv.dotpicktr.persistence.GridContract.GridEntry;
import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity implements QueryHandler.AsyncQueryListener, ColorPickerFragment.ColorPickerFragmentListener {
    /**
     * The grid we are showing
     */
    private Grid grid;

    int id, size, pencilColor;
    String name, gridString, gridLines;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationViewEx mBottomNav = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        mBottomNav.enableAnimation(false);
        mBottomNav.enableShiftingMode(false);
        mBottomNav.enableItemShiftingMode(false);

        Intent intent = getIntent();

        if (intent.hasExtra(GalleryActivity.ARG_SIZE)) {
            size = intent.getIntExtra(GalleryActivity.ARG_SIZE, 0);
            initBoard(size);
        } else {
            Bundle bundle = intent.getExtras();
            if (bundle != null && !bundle.isEmpty()) {
                uri = bundle.getParcelable(GalleryFragment.URI_KEY);
                if (uri != null) {
                    QueryHandler handler = new QueryHandler(getApplicationContext(), this);
                    handler.startQuery(QueryHandler.OperationToken.TOKEN_QUERY, null, uri, null, null,
                            null, null);
                }
            }

        }

        if (grid != null) {
            displayBoardFragment();
        }
        if (findViewById(R.id.board_container) != null) {
            FragmentManager manager = getSupportFragmentManager();
            if (grid != null) {
                BoardFragment boardFragment = new BoardFragment();
                manager.beginTransaction().replace(R.id.board_container, boardFragment).commit();
                boardFragment.setGrid(grid);
            }
            Bundle bundle = new Bundle();
            bundle.putInt(ColorPickerFragment.ARG_COLOR, Color.BLACK);
            ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
            colorPickerFragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.tools_container, colorPickerFragment).commit();
        }

        mBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                displayBoardFragment();
                                break;
                            case R.id.action_color:
                                displayColorPickerFragment();
                                break;
                            case R.id.action_visible:
                                toggleGridLines();
                                break;

                            case R.id.action_picture:
                                takePicture();
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_grid_on_off:
                toggleGridLines();
                return true;
            case R.id.action_clear_canvas:
                clearCanvas();
                return true;
            case R.id.action_take_picture:
                takePicture();
                return true;
            case android.R.id.home:
                saveGrid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Create a new Grid
     *
     * @param gridSize The size of the grid
     */
    private void initBoard(int gridSize) {
        grid = new Grid(gridSize);
        grid.setGridSize(gridSize);
        grid.setPencilColor(Color.BLACK);
    }

    private void displayBoardFragment() {
        if (findViewById(R.id.fragment_container) != null) {
            //normal sized screen
            FragmentManager manager = getSupportFragmentManager();
            BoardFragment boardFragment = new BoardFragment();
            manager.beginTransaction().replace(R.id.fragment_container, boardFragment).commit();
            boardFragment.setGrid(grid);
        }
    }

    private void displayColorPickerFragment() {
        if (findViewById(R.id.fragment_container) == null) {
            //large screen landscape
        } else {
            //normal
            Bundle bundle = new Bundle();
            bundle.putInt(ColorPickerFragment.ARG_COLOR, grid.getPencilColor());
            FragmentManager manager = getSupportFragmentManager();
            ColorPickerFragment colorPickerFragment = new ColorPickerFragment();
            colorPickerFragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragment_container, colorPickerFragment).commit();
        }

    }

    private void clearCanvas() {
        grid.clearGrid();
        redrawCanvas();
    }

    private void toggleGridLines() {
        grid.setDrawGridLines(!grid.isDrawGridLines());
        redrawCanvas();
    }

    private void takePicture() {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap map = null;
                if (imageBitmap != null) {
                    map = Bitmap.createScaledBitmap(imageBitmap, grid.getGridSize(), grid.getGridSize(), false);
                }
                for (int i = 0; i < grid.getGridSize(); i++) {
                    for (int j = 0; j < grid.getGridSize(); j++) {
                        grid.setPencilColor(map.getPixel(i, j));
                        grid.setColor(i, j);
                    }
                    redrawCanvas();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        saveGrid();
        super.onSaveInstanceState(outState, outPersistentState);

    }

    private void redrawCanvas() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof BoardFragment) {
            BoardFragment boardFragment = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            boardFragment.redrawCanvas();
        } else if (getSupportFragmentManager().findFragmentById(R.id.board_container) instanceof BoardFragment) {
            BoardFragment boardFragment = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_container);
            boardFragment.redrawCanvas();
        }
    }

    private void switchToGallery() {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    /**
     * Saves the Grid in the database
     */
    private void saveGrid() {
        QueryHandler queryHandler = new QueryHandler(getApplicationContext(), null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(GridEntry.COLUMN_GRIDLINES, String.valueOf(grid.isDrawGridLines()));
        contentValues.put(GridEntry.COLUMN_GRID, grid.toString());
        contentValues.put(GridEntry.COLUMN_NAME, grid.getName());
        contentValues.put(GridEntry.COLUMN_SIZE, grid.getGridSize());
        contentValues.put(GridEntry.COLUMN_PENCILCOLOR, grid.getPencilColor());

        if (uri != null) {
            long _id = ContentUris.parseId(uri);
            String selection = GridEntry._ID + " = ?";
            String[] selectionArg = {String.valueOf(_id)};
            queryHandler.startUpdate(QueryHandler.OperationToken.TOKEN_UPDATE, null, GridEntry.CONTENT_URI, contentValues, selection, selectionArg);
        } else {
            queryHandler.startInsert(QueryHandler.OperationToken.TOKEN_INSERT, null, GridEntry.CONTENT_URI, contentValues);
        }

        switchToGallery();
    }


    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (cursor != null && cursor.getCount() == 1 && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry._ID));
            name = cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_NAME));
            gridString = cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_GRID));
            gridLines = cursor.getString(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_GRIDLINES));
            size = cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_SIZE));
            int[][] board = getGrid(gridString, size);
            pencilColor = cursor.getInt(cursor.getColumnIndex(GridContract.GridEntry.COLUMN_PENCILCOLOR));

            grid = new Grid(id, name, pencilColor, board, size, Boolean.parseBoolean(gridLines));
            cursor.close();
        }
        if (findViewById(R.id.board_container) != null && grid != null) {
            FragmentManager manager = getSupportFragmentManager();
            BoardFragment boardFragment = new BoardFragment();
            manager.beginTransaction().replace(R.id.board_container, boardFragment).commit();
            boardFragment.setGrid(grid);
        }
        displayBoardFragment();
    }

    /**
     * Converts the grid from string to a double array
     *
     * @param gridString The grid in String format ex: 1,1,1,1,1,1,1,1,1
     * @param size       The size of the the grid
     * @return A 2D array of int
     */
    private int[][] getGrid(String gridString, int size) {
        int[][] result = new int[size][size];
        String[] array = gridString.split(",");
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = Integer.parseInt(array[counter]);
                counter++;
            }
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = DotPicktApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    public void updateColor(int color) {
        grid.setPencilColor(color);
    }
}
