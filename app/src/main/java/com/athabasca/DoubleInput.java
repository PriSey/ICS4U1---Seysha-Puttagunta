package com.athabasca;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
public class DoubleInput extends JTextField{
    double max;
    int decimals;
    boolean allowDecimal;
    DoubleInput(Long max, Dimension dim, int decimals, boolean allowDecimal) {
        addDecimalNumberInputValidation(this);
        this.max = max;
        this.decimals = decimals;
        setPreferredSize(dim);
        this.allowDecimal = allowDecimal;
    }

    // This method is responsible for addDecimalNumberInputValidation.
    private void addDecimalNumberInputValidation(JTextField textField) {
        // Adding a key listener to the JTextField to capture key presses and validate
        // input
        textField.addKeyListener(new KeyAdapter() {
            @Override
// This method is responsible for keyPressed.
            public void keyPressed(KeyEvent k) {
                // Allow backspace to delete the last character
                if ((k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true); // Allow backspace to remove characters
                } else {
                    try {
                        // Try to parse the current text plus the new key pressed as a double (decimal
                        // number)
                        double futureValue = Double.parseDouble(textField.getText() + String.valueOf(k.getKeyChar()));
                        if((textField.getText() + String.valueOf(k.getKeyChar())).contains(".") && !allowDecimal){
                            textField.setEditable(false);
                        }

                        if (futureValue <= max) { // Limit input to values less than 30 million
                            // Allow decimal point if there isn't already one in the text field
                            if (k.getKeyChar() == '.' && allowDecimal) {
                                textField.setEditable(textField.getText().indexOf('.') == -1);
                            }
                            // Allow only digits and backspace for valid numeric input
                            else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9')) {
                                textField.setEditable(true); // Allow numbers
                            } else {
                                textField.setEditable(false); // Prevent invalid characters
                            }
                            System.out.println(textField.getText().indexOf('.') == -1);
                            String nextValue = textField.getText() + String.valueOf(k.getKeyChar());
                            if ((nextValue.split("\\.").length > 1)) {
                                if (nextValue.split("\\.")[1].length() > decimals) {
                                    textField.setEditable(false);
                                }
                            }
                            // Disallow any non-numeric and non-decimal characters
                        } else {
                            textField.setEditable(false); // Prevent input if the value exceeds 30 million
                        }
                    } catch (java.lang.NumberFormatException e) {
                        // Handle invalid numeric input (e.g., user presses letters or symbols)
                        if (k.getKeyChar() == '.') {
                            textField.setEditable(textField.getText().indexOf('.') == -1); // Allow only one decimal
                                                                                           // point
                        }
                        // Allow numbers and backspace
                        else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9')) {
                            textField.setEditable(true); // Allow numeric input
                        }
                        // Disallow other characters (e.g., letters, symbols)
                        else {
                            textField.setEditable(false); // Prevent invalid characters
                        }
                    }
                }
            }
        });
    }
}
