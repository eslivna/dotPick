package com.example.esliv.dotpicktr.models;

import android.graphics.Color;

/**
 * Created by esliv on 23/10/2017.
 */

public class Grid {


    /**
     * The active color that will be used to color a dot on the grid
     */
    private int pencilColor;

    /**
     * The grid containing all the elements
     */
    private Dot[][] grid;


    /**
     * Initialize the Grid with in each element of the Grid a Dot
     * @param gridSize The number of Dots on a row
     */
    public Grid(int gridSize) {
        grid = new Dot[gridSize][gridSize];

        //Initialise the grid with new elements
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Dot();
            }
        };
    }


    /**
     * Return the element of this grid
     *
     * @param i the row index
     * @param j the column index
     * @return the requested {@link Dot}
     */
    public Dot getDot(int i, int j) {
        return grid[i][j];
    }


    /**
     * Set the color for a certain dot
     *
     * @param x      the row index
     * @param y      the column index
     */
    public void setColor( int x, int y) throws IndexOutOfBoundsException {
        Dot d = getDot(x, y);
        d.setColor(pencilColor);
    }

    /**
     * Returns the size of this grid
     *
     * @return the gridsize
     */
    public int getGridSize() {
        return grid.length;
    }


    public int getPencilColor() {
        return pencilColor;
    }

    public void setPencilColor(int pencilColor) {
        this.pencilColor = pencilColor;
    }
}
