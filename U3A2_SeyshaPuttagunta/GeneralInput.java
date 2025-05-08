import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
public class GeneralInput extends JTextField{
    int max;
    GeneralInput(int max, Dimension size){
        this.max = max;
        setPreferredSize(size);
        addInputValidation(this);
    }

    private void addInputValidation(JTextField textField) {

        // Adding a key listener to the JTextField to validate input for alphabetic
        // characters
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                String futureValue = textField.getText() + String.valueOf(k.getKeyChar());
                // Allow only uppercase letters (A-Z) or backspace
                if ((futureValue.length() <= max) || (k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true); // Allow alphabetic characters and backspace
                } else {
                    textField.setEditable(false); // Disallow any other characters (e.g., numbers or special symbols)
                }
            }
        });
    }
}

