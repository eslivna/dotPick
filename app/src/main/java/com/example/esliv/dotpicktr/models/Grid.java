package com.example.esliv.dotpicktr.models;

import android.graphics.Color;

/**
 * Created by esliv on 23/10/2017.
 */

public class Grid {
    /**
     * The id of the grid in the db
     */
    private int id;
    /**
     * The active color that will be used to color a dot on the grid
     */
    private int pencilColor;

    /**
     * The grid containing all the elements
     */
    private int[][] grid;

    /**
     * Whether or not we are drawing the gridlines on the canvas
     */
    private boolean drawGridLines;

    /**
     * The name of the Grid
     */
    private String name;

    /**
     * The size of the grid
     */
    private int gridSize;

    /**
     * Initialize the Grid with white elements
     *
     * @param gridSize The number of elements on a row
     */
    public Grid(int gridSize)
    {
        setName("untitled");
        this.drawGridLines = true;
        this.grid = new int[gridSize][gridSize];
        //Initialise the grid with white elements
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = Color.WHITE;
            }
        }
    }

    public Grid(int id, String name, int pencilColor, int[][] grid, int gridSize, boolean drawGridLines) {
        this.id = id;
        this.name = name;
        this.gridSize = gridSize;
        this.pencilColor = pencilColor;
        this.grid = grid;
        this.drawGridLines = drawGridLines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getColor(int x, int y) {
        return grid[x][y];
    }

    /**
     * Set the color for a certain dot
     *
     * @param x the row index
     * @param y the column index
     */
    public void setColor(int x, int y) throws IndexOutOfBoundsException {
        grid[x][y] = pencilColor;
    }

    public int getPencilColor() {
        return pencilColor;
    }

    public void setPencilColor(int pencilColor) {
        this.pencilColor = pencilColor;
    }

    public void clearGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = Color.WHITE;
            }
        }
    }

    public boolean isDrawGridLines() {
        return drawGridLines;
    }

    public void setDrawGridLines(boolean drawGridLines) {
        this.drawGridLines = drawGridLines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getGridSize() {
        return gridSize;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                builder.append(getColor(i, j)).append(",");
            }
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}
