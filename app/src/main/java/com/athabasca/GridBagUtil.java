package com.athabasca;
import java.awt.GridBagConstraints;

// A utility class extending GridBagConstraints to simplify its usage in layouts
public class GridBagUtil extends GridBagConstraints {

    // Constructor to initialize gridx and gridy values
    GridBagUtil(int x, int y) {
        gridx = x; // Set initial x-coordinate
        gridy = y; // Set initial y-coordinate
    }

    /**
     * Sets the grid position (x, y) for the component.
     * 
     * @param x the column index for the component
     * @param y the row index for the component
     */
    public void set(int x, int y) {
        gridx = x; // Update the x-coordinate
        gridy = y; // Update the y-coordinate
    }

    /**
     * Resets the grid position to the top-left corner (0, 0).
     */
    public void reset() {
        gridx = 0; // Reset x-coordinate to 0
        gridy = 0; // Reset y-coordinate to 0
    }

    /**
     * Moves the grid position down by one row.
     */
    public void nextY() {
        gridy++; // Increment the y-coordinate by 1
    }

    /**
     * Moves the grid position to the next column.
     */
    public void nextX() {
        gridx++; // Increment the x-coordinate by 1
    }

    /**
     * Resets the grid position's x-coordinate to the first column (0).
     */
    public void resetX() {
        gridx = 0; // Set x-coordinate to 0
    }

    /**
     * Resets the grid position's y-coordinate to the first row (0).
     */
    public void resetY() {
        gridy = 0; // Set y-coordinate to 0
    }
}
