import javax.swing.*; // Importing the Swing library for GUI components
import java.awt.Dimension; // For setting dimensions of components
import java.awt.GridBagConstraints; // For managing layout
import java.awt.GridBagLayout; // For grid bag layout
import java.awt.event.*; // For handling events
import java.text.CharacterIterator; // Not used in this code
import java.util.HashMap; // Not used in this code

// Main class to run the application
public class U1A8_SeyshaPuttagunta {
    public static void main(String[] args) {
        new Window(); // Create an instance of the Window class
    }
}

// Class representing the main application window
class Window extends JFrame {
    Window() {
        GridBagConstraints gbc = new GridBagConstraints(); // Constraints for layout management
        JPanel pnlResults = new JPanel(new GridBagLayout()); // Panel for results
        JPanel pnlInputs = new JPanel(new GridBagLayout()); // Panel for input fields

        setLayout(new GridBagLayout()); // Setting the layout of the JFrame

        // Creating labels and input fields
        JLabel lblinput = new JLabel("Enter your phrase");
        JTextField fldInput = new JTextField(); // Text field for user input
        JButton btnConvertText = new JButton("Click me to process your string"); // Button to trigger conversion

        // Result labels for different conversions
        JLabel lblAscii = new JLabel("Ascii: ");
        JLabel lblBinary = new JLabel("Binary: ");
        JLabel lblOctal = new JLabel("Octal: ");
        JLabel lblHexaDec = new JLabel("Hexal: ");

        // Array to hold result labels
        JComponent[] Labels = {lblAscii, lblBinary, lblOctal, lblHexaDec};

        LimitTextField(fldInput); // Limiting input in text field
        fldInput.setPreferredSize(new Dimension(100, 20)); // Setting preferred size for input field

        // Action listener for button click
        btnConvertText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = fldInput.getText(); // Get the text from the input field
                String AsciiText = "Ascii: ";
                String BinaryText = "Binary: ";
                String OctalText = "Octal: ";
                String HexalText = "Hexal: ";

                // Loop through each character in the input
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i); // Get the character
                    int AsciiCode = (int) c; // Convert character to ASCII code
                    AsciiText += (Integer.toString(AsciiCode) + " "); // Append ASCII to result string
                    BinaryText += (ChangeBase(AsciiCode, 2) + " "); // Convert ASCII to binary
                    OctalText += (ChangeBase(AsciiCode, 8) + " "); // Convert ASCII to octal
                    HexalText += (ChangeBase(AsciiCode, 16) + " "); // Convert ASCII to hexadecimal
                }

                // Update the result labels with converted values
                lblAscii.setText(AsciiText);
                lblBinary.setText(BinaryText);
                lblOctal.setText(OctalText);
                lblHexaDec.setText(HexalText);
                
                // Refresh the frame to display updated labels
                pack();
                revalidate();
                repaint();
            }
        });

        // Adding input field and button to input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlInputs.add(fldInput, gbc);
        gbc.gridx = 1;
        pnlInputs.add(btnConvertText, gbc);

        // Adding result labels to results panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (int i = 0; i < Labels.length; i++) {
            pnlResults.add(Labels[i], gbc);
            ++gbc.gridy; // Move to next row
        }

        // Adding panels to the main frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(pnlInputs, gbc);
        ++gbc.gridy;
        add(pnlResults, gbc);

        pack(); // Pack components within the frame
        setVisible(true); // Make the frame visible
    }

    // Method to limit the text field input
    public void LimitTextField(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                // Allow only one decimal point and limit length to 15 characters
                if (k.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    textField.setEditable(true);
                } else if (textField.getText().length() < 15) {
                    textField.setEditable(true);
                } else {
                    textField.setEditable(false); // Disable further input
                }
            }
        });
    }

    // Method to convert a number to a specified base
    public String ChangeBase(int num, int base) {
        String Product = ""; // String to hold the result
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // For bases higher than 10
        
        // While the number is greater than 1, perform base conversion
        while (num > 1) {
            String bit = Integer.toString(num % base); // Get remainder
            num = (num - Integer.valueOf(bit)) / base; // Update number
            
            // If the value is greater than 9, convert to corresponding letter
            if (Integer.valueOf(bit) > 9) {
                bit = Character.toString(Alphabet.charAt(Integer.valueOf(bit) - 10));
            }
            Product = bit + Product; // Prepend to result string
        }
        if (num > 0) {
            Product = Integer.toString(num) + Product; // Handle the last remaining number
        }
        return Product; // Return the converted string
    }
}
