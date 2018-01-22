package com.example.esliv.dotpicktr.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.esliv.dotpicktr.models.Grid;

/**
 * Created by esliv on 22/01/2018.
 */

public class BoardThumbnail extends View {
    private int[][] grid;

    public BoardThumbnail(Context context) {
        super(context);
    }

    public BoardThumbnail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoardThumbnail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (grid != null) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    drawRect(canvas, i, j, grid[i][j]);
                }
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
        int dotWidth = getWidth() / grid.length;
        canvas.drawRect(x * dotWidth, y * dotWidth, x * dotWidth + dotWidth, y * dotWidth + dotWidth, paint);
    }


}
