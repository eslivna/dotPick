package com.example.esliv.dotpicktr.models;

import android.graphics.Color;

/**
 * Created by esliv on 23/10/2017.
 */

public class Dot {
    private int color;
    
    public Dot(){
        this(-1);
    }

    public Dot(int color){
        this.color=color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
