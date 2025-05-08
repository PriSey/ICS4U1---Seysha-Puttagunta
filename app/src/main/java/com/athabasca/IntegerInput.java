package com.athabasca;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class IntegerInput extends JSpinner {

    /**
     * Constructor to create an IntegerInput spinner with specified initial value, minimum, maximum, and dimensions.
     *
     * @param initial The initial value of the spinner.
     * @param min The minimum value allowed.
     * @param max The maximum value allowed.
     * @param dim The preferred dimensions of the spinner.
     */
    IntegerInput(int initial, int min, int max, Dimension dim) {
        // Set the spinner model with the initial value, minimum, maximum, and step size of 1
        setModel(new SpinnerNumberModel(initial, min, max, 1));

        // Get the text field from the spinner's number editor
        JFormattedTextField txt = ((JSpinner.NumberEditor) this.getEditor()).getTextField();

        // Configure the formatter to disallow invalid input
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        // Set the preferred size of the spinner
        setSize(dim);
    }
}