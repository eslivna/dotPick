package com.example.esliv.dotpicktr.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.fragments.BoardFragment;
import com.example.esliv.dotpicktr.fragments.ColorPickerFragment;
import com.example.esliv.dotpicktr.models.Grid;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    /**
     * The grid we are showing
     */
    private Grid grid;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationViewEx mBottomNav = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        mBottomNav.enableAnimation(false);
        mBottomNav.enableShiftingMode(false);
        mBottomNav.enableItemShiftingMode(false);

        Intent intent = getIntent();
        int size = intent.getIntExtra(CreateActivity.ARG_SIZE, 16);
        initBoard(size);
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            FragmentManager manager = getSupportFragmentManager();
            BoardFragment boardFragment = new BoardFragment();
            manager.beginTransaction().replace(R.id.fragment_container, boardFragment).commit();
            boardFragment.setGrid(grid);

        } else {
            BoardFragment fragment = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board);
        }

        mBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof ColorPickerFragment) {
                                    ColorPickerFragment colorPickerFragment = (ColorPickerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                                    grid.setPencilColor(colorPickerFragment.getCurrentColor());
                                }
                                switchToBoardFragment();
                                break;
                            case R.id.action_color:
                                switchToColorPickerFragment();
                                break;
                            case R.id.action_visible:

                            case R.id.action_revert:

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
                toggleGrid();
                return true;
            case R.id.action_clear_canvas:
                clearCanvas();
                return true;
            case R.id.action_take_picture:
                takePicture();
                return true;
            case android.R.id.home:
                switchToMainActivity();
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
        grid.setPencilColor(Color.BLACK);
    }


    private void switchToBoardFragment() {
        if (findViewById(R.id.fragment_container) == null) {
//TODO
        } else {
            FragmentManager manager = getSupportFragmentManager();
            BoardFragment boardFragment = new BoardFragment();
            manager.beginTransaction().replace(R.id.fragment_container, boardFragment).commit();
            boardFragment.setGrid(grid);
        }
    }

    private void switchToColorPickerFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(ColorPickerFragment.ARG_COLOR, grid.getPencilColor());

        if (findViewById(R.id.fragment_container) == null) {
//TODO
        } else {
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

    private void toggleGrid() {
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

    private void redrawCanvas() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) instanceof BoardFragment) {
            BoardFragment boardFragment = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            boardFragment.redrawCanvas();
        }
    }

    private void switchToMainActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }


}
