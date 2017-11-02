package com.example.esliv.dotpicktr.models;

/**
 * Created by esliv on 23/10/2017.
 */

public class Grid {

    /**
     * Using the Singleton pattern.
     */
    private static Grid gridInstance;
    /**
     * The size of the grid
     */
    private int gridSize;

    /**
     * The grid containing all the elements
     */
    private Dot[][] grid;

    private Grid(int gridSize) {
        setGridSize(gridSize);
        grid = new Dot[gridSize][gridSize];

        //Initialise the grid with new elements
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Dot();
            }
        };

    }

    public static Grid get(int gridSize) {
        if (gridInstance == null)
            gridInstance = new Grid(gridSize);
        return gridInstance;
    }

    /**
     * Return the element of this grid
     *
     * @param i the row index
     * @param j the column index
     * @return the requested element
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
    public void setColor( int x, int y, int color) {
        Dot d = getDot(x, y);
        d.setColor(color);
    }

    /**
     * Returns the size of this grid
     *
     * @return the gridsize
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Sets the gridsize for this gird
     *
     * @param gridSize the grid size
     */
    public void setGridSize(int gridSize) {
            this.gridSize = gridSize;
    }



}
