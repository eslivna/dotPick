package com.example.esliv.dotpicktr.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.fragments.AddGridDialogFragment;

public class CreateActivity extends AppCompatActivity implements AddGridDialogFragment.OnSelectionClickListener {

    public static String ARG_SIZE = "size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            case 1:
                intent.putExtra(ARG_SIZE, 16);
                break;
            case 2:
                intent.putExtra(ARG_SIZE, 32);
                break;
            case 3:
                intent.putExtra(ARG_SIZE, 64);
                break;
            case 4:
                intent.putExtra(ARG_SIZE, 128);
                break;
        }
        startActivity(intent);
    }
}
