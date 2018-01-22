package com.example.esliv.dotpicktr.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.esliv.dotpicktr.models.Grid;


/**
 * Created by esliv on 23/10/2017.
 */

public class Board extends View {
    private Grid grid;


    public Board(Context context) {
        super(context);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Draw the initial raster on the grid
     *
     * @param canvas
     */
    private void drawRaster(Canvas canvas) {
        int dotWidth = getWidth() / grid.getGridSize();
        int gridHeight = dotWidth * grid.getGridSize();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        //draw horizontal grid lines
        for (int i = 0; i < gridHeight + 1; i += dotWidth) {
            canvas.drawLine(0, i, gridHeight, i, paint);
        }
        //draw vertical grid lines
        for (int i = 0; i < gridHeight + 1; i += dotWidth) {
            canvas.drawLine(i, 0, i, gridHeight, paint);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (grid != null) {
            for (int i = 0; i < grid.getGridSize(); i++) {
                for (int j = 0; j < grid.getGridSize(); j++) {
                    drawRect(canvas, i, j, grid.getColor(i, j));

                }
            }
            if (grid.isDrawGridLines()) {
                drawRaster(canvas);
            }
        }
    }

    /**
     * Draw the dot as a rectangle
     *
     * @param canvas The canvas where we will draw the dot on
     * @param x      The position of the dot on de x-axis
     * @param y      The position of the dot on de y-axis
     * @param color  The background color of  the dot
     */
    private void drawRect(Canvas canvas, int x, int y, int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        int dotWidth = getWidth() / grid.getGridSize();
        canvas.drawRect(x * dotWidth, y * dotWidth, x * dotWidth + dotWidth, y * dotWidth + dotWidth, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (grid != null) {
            int dotWidth = getWidth() / grid.getGridSize();
            int x = (int) Math.floor((event.getX() / dotWidth));
            int y = (int) Math.floor((event.getY() / dotWidth));
            try {
                grid.setColor(x, y);
            } catch (IndexOutOfBoundsException e) {

            }
            invalidate();
        }
        return true;

    }

}
