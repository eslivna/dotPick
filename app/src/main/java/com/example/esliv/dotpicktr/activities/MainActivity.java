package com.example.esliv.dotpicktr.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.esliv.dotpicktr.R;

public class MainActivity extends AppCompatActivity {

    private Button colorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorBtn = (Button)findViewById(R.id.colorBtn);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ColorPicker.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String colorString = sharedPref.getString("color", "-1");
        if(colorString != "-1")
            colorBtn.setBackgroundColor(Integer.valueOf(colorString));
    }
}
