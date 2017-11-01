package com.example.esliv.dotpicktr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.esliv.dotpicktr.R;

public class ColorPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String colorString = sharedPref.getString("color", "-1");


        SeekBar seekBarR = (SeekBar) findViewById(R.id.seekBarR);
        SeekBar seekBarG = (SeekBar) findViewById(R.id.seekBarG);
        SeekBar seekBarB = (SeekBar) findViewById(R.id.seekBarB);

        final TextView valueR = (TextView) findViewById(R.id.numberR);
        final TextView valueG = (TextView) findViewById(R.id.numberG);
        final TextView valueB = (TextView) findViewById(R.id.numberB);
        final TextView rgbValue = (TextView) findViewById(R.id.rgbView);

        if(!colorString.equalsIgnoreCase("-1")){
            rgbValue.setBackgroundColor(Integer.valueOf(colorString));
        }
        seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valueR.setText(String.valueOf(i));
                rgbValue.setBackgroundColor(Color.rgb( i,
                        Integer.valueOf(valueG.getText().toString())
                        ,Integer.valueOf(valueB.getText().toString())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valueB.setText(String.valueOf(i));
                rgbValue.setBackgroundColor(Color.rgb( Integer.valueOf(valueR.getText().toString()),
                        i
                        ,Integer.valueOf(valueB.getText().toString())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valueG.setText(String.valueOf(i));
                rgbValue.setBackgroundColor(Color.rgb( Integer.valueOf(valueR.getText().toString()),
                        Integer.valueOf(valueB.getText().toString())
                        ,i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        Button button = (Button)findViewById(R.id.okBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("color", String.valueOf(Color.rgb(
                        Integer.valueOf(valueR.getText().toString()),
                        Integer.valueOf(valueG.getText().toString()),
                        Integer.valueOf(valueB.getText().toString())
                )));

                editor.commit();
                finish();
            }
        });

    }

}
