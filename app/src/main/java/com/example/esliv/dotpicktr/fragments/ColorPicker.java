package com.example.esliv.dotpicktr.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.esliv.dotpicktr.R;

public class ColorPicker extends Fragment {
    SeekBar seekBarR, seekBarB, seekBarG;
    TextView valueR, valueG, valueB, rgbValue;
    Button okBtn;
    public final static String ARG_COLOR = "color";
    int currentColor = -1;

    public interface OnColorPickerSelectedListener {
        public void onColorSelected(int color);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            currentColor = savedInstanceState.getInt(ARG_COLOR);
        }
        View v = inflater.inflate(R.layout.activity_color, container, false);
        seekBarR = v.findViewById(R.id.seekBarR);
        seekBarB = v.findViewById(R.id.seekBarB);
        seekBarG = v.findViewById(R.id.seekBarG);

        valueR = v.findViewById(R.id.numberR);
        valueG = v.findViewById(R.id.numberG);
        valueB = v.findViewById(R.id.numberB);

        rgbValue = v.findViewById(R.id.rgbView);

        okBtn = v.findViewById(R.id.okBtn);

        seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valueR.setText(String.valueOf(i));
                rgbValue.setBackgroundColor(Color.rgb(i,
                        Integer.valueOf(valueG.getText().toString())
                        , Integer.valueOf(valueB.getText().toString())));
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
                rgbValue.setBackgroundColor(Color.rgb(Integer.valueOf(valueR.getText().toString()),
                        i
                        , Integer.valueOf(valueB.getText().toString())));
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
                rgbValue.setBackgroundColor(Color.rgb(Integer.valueOf(valueR.getText().toString()),
                        Integer.valueOf(valueG.getText().toString())
                        , i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColor = Color.rgb(
                        seekBarR.getProgress(),
                        seekBarG.getProgress(),
                        seekBarB.getProgress())
                ;
                OnColorPickerSelectedListener mCallback = (OnColorPickerSelectedListener)getActivity();
                mCallback.onColorSelected(currentColor);

            }
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateColor(args.getInt(ARG_COLOR));
        } else if (currentColor != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateColor(currentColor);
        }
    }

    private void updateColor(int colorCode){
        rgbValue.setBackgroundColor(colorCode);

        seekBarR.setProgress(Color.red(colorCode));
        valueR.setText(String.valueOf(Color.red(colorCode)));

        seekBarG.setProgress(Color.green(colorCode));
        valueG.setText(String.valueOf(Color.green(colorCode)));

        seekBarB.setProgress(Color.blue(colorCode));
        valueB.setText(String.valueOf(Color.blue(colorCode)));
    }

}
