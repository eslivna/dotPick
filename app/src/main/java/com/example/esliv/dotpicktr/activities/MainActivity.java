package com.example.esliv.dotpicktr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.example.esliv.dotpicktr.R;

public class MainActivity extends FragmentActivity implements ToolBoxFragment.OnToolBoxSelectedListener, ColorPicker.OnColorPickerSelectedListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            FragmentManager manager = getSupportFragmentManager();

            BoardFragment firstFragment = new BoardFragment();
            ToolBoxFragment secondFragment = new ToolBoxFragment();
            Bundle arg = new Bundle();
            arg.putInt(BoardFragment.ARG_COLOR, Color.rgb(0,0,0));
            firstFragment.setArguments(arg);

            Bundle args = new Bundle();
            args.putInt(ToolBoxFragment.ARG_COLOR, Color.rgb(0,0,0));
            secondFragment.setArguments(args);
            manager.beginTransaction()
                    .replace(R.id.fragment_container, firstFragment)
                    .add(R.id.fragment_container, secondFragment)
                    .commit();
        }else{
            BoardFragment fragment = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board);
            ToolBoxFragment toolfragment = (ToolBoxFragment) getSupportFragmentManager().findFragmentById(R.id.tools);
            fragment.updateColor(Color.BLACK);
            toolfragment.updateColor(Color.BLACK);
        }

    }

    @Override
    public void onToolSelected(int color) {
        ColorPicker colorPicker = new ColorPicker();

        Bundle args = new Bundle();
        args.putInt(ColorPicker.ARG_COLOR, color);
        colorPicker.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(findViewById(R.id.fragment_container) == null){
            transaction.replace(R.id.tool_container, colorPicker);
            transaction.addToBackStack(null);
        }else{
            transaction.replace(R.id.fragment_container, colorPicker);
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onColorSelected(int color) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        BoardFragment boardFragment = new BoardFragment();
        ToolBoxFragment toolBoxFragment = new ToolBoxFragment();

        Bundle args = new Bundle();
        args.putInt(ToolBoxFragment.ARG_COLOR, color);
        toolBoxFragment.setArguments(args);

        Bundle arg = new Bundle();
        arg.putInt(BoardFragment.ARG_COLOR, color);
        boardFragment.setArguments(arg);

        if(findViewById(R.id.fragment_container) == null){
            BoardFragment fragment = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board);
            ToolBoxFragment toolfragment = (ToolBoxFragment) getSupportFragmentManager().findFragmentById(R.id.tools);
            fragment.updateColor(color);
            toolfragment.updateColor(color);

        }else{
            transaction
                    .replace(R.id.fragment_container, boardFragment)
                    .add(R.id.fragment_container, toolBoxFragment)
                    .commit();
        }

    }
}
