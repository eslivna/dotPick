package com.example.esliv.dotpicktr.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationViewEx mBottomNav = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        mBottomNav.enableAnimation(false);
        mBottomNav.enableShiftingMode(false);
        mBottomNav.enableItemShiftingMode(false);
        //TODO chance default 17
        initBoard(17);
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

    /**
     * Create a new Grid
     *
     * @param gridSize The size of the grid
     */
    private void initBoard(int gridSize) {
        grid = new Grid(gridSize);
        grid.setPencilColor(Color.BLACK);
    }


    public void switchToBoardFragment() {
        if (findViewById(R.id.fragment_container) == null) {
//TODO
        } else {
            FragmentManager manager = getSupportFragmentManager();
            BoardFragment boardFragment = new BoardFragment();
            manager.beginTransaction().replace(R.id.fragment_container, boardFragment).commit();
            boardFragment.setGrid(grid);
        }
    }

    public void switchToColorPickerFragment() {
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
}
