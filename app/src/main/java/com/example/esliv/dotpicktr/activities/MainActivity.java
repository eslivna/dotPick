package com.example.esliv.dotpicktr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.esliv.dotpicktr.R;

public class MainActivity extends AppCompatActivity {

    private Button colorBtn;
    private Button drawMethodBtn;
    private Button revertBtn;
    protected boolean drawLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorBtn = (Button) findViewById(R.id.colorBtn);
        drawMethodBtn = (Button) findViewById(R.id.drawMethodBtn);
        revertBtn = (Button) findViewById(R.id.revertBtn);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ColorPicker.class);
                startActivity(intent);
            }
        });

        drawMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawLine = !drawLine;
                drawMethodBtn.setText(drawLine ? "-" : ".");
            }
        });

        //Clear sharedPreferences at when creating the activity
        //set basis color to black
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.putInt("color", Color.rgb(0, 0, 0));
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //update the button after picking a new color
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int colorCode = sharedPref.getInt("color", -1);
        if (colorCode != -1)
            colorBtn.setBackgroundColor(colorCode);
    }
}
