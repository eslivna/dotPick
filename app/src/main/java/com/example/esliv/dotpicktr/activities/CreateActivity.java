package com.example.esliv.dotpicktr.activities;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.esliv.dotpicktr.R;
import com.example.esliv.dotpicktr.adapters.GridAdapter;
import com.example.esliv.dotpicktr.fragments.AddGridDialogFragment;
import com.example.esliv.dotpicktr.fragments.GalleryFragment;
import com.example.esliv.dotpicktr.persistence.GridContract;

import butterknife.BindView;

public class CreateActivity extends AppCompatActivity implements AddGridDialogFragment.OnSelectionClickListener, GalleryFragment.GalleryFragmentListener ,GridAdapter.OnEditClickListener{

    public static String ARG_SIZE = "size";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    public CreateActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
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
    public void displayMainActivity(Bundle bundle) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onEditClick(String answer, int id) {
        String selection = GridContract.GridEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        ContentValues contentValues = new ContentValues();
        contentValues.put(GridContract.GridEntry.COLUMN_NAME, answer);
        Uri uri = GridContract.GridEntry.CONTENT_URI;
        getContentResolver().update(uri, contentValues, selection, selectionArgs);
    }
}
