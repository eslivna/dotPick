package com.example.esliv.dotpicktr.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.esliv.dotpicktr.models.Grid;


/**
 * Created by esliv on 23/10/2017.
 */

public class Board extends View {
    private Grid grid;

    public Board(Context context) {
        super(context);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBoard();
    }

    private void initBoard(){
        grid = Grid.get(17);
    }

    private void drawRaster(Canvas canvas){
        int dotWidth = getWidth()/grid.getGridSize();
        int gridHeight = dotWidth*grid.getGridSize();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        //draw horizontal gridlines
        for(int i = 0; i< getWidth() +1 ; i+=dotWidth){
            canvas.drawLine(0,i,getWidth(),i,paint);
        }
        //draw vertical gridlines
        for(int i = 0; i< gridHeight +1 ; i+=dotWidth){
            canvas.drawLine(i,0,i,gridHeight,paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRaster(canvas);
        for( int i = 0 ; i< grid.getGridSize();i++){
            for(int j =0; j < grid.getGridSize();j++){
                if(grid.getDot(i,j).getColor() != -1 || grid.getDot(i,j).getColor() != -1 ) {
                    drawRect(canvas, i,j,grid.getDot(i,j).getColor());
                }
            }
        }
    }


    private void drawRect(Canvas canvas,int x,int y,int color){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#CD5C5C"));
        int dotWidth = getWidth()/grid.getGridSize();
        canvas.drawRect(x*dotWidth , y*dotWidth, x*dotWidth+dotWidth, y*dotWidth+dotWidth, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int dotWidth = getWidth()/grid.getGridSize();
        int x =(int) Math.floor((event.getX()/dotWidth));
        int y= (int) Math.floor((event.getY()/dotWidth));
        grid.setColor(x,y, 20);
        invalidate();
        return true;
    }






}