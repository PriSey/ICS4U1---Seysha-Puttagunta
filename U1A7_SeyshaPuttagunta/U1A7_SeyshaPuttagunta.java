import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class to run the application
public class U1A7_SeyshaPuttagunta {
    public static void main(String[] args) {
        new Window(); // Create a new instance of Window to display the GUI
    }
}

// Window class that extends JFrame to create the main application window
class Window extends JFrame {
    JTextField fldInput = new JTextField(); // Text field for user input
    JLabel lblRevString = new JLabel(); // Label to display the reversed string
    JLabel lblLength = new JLabel(); // (Unused in the current code)
    JLabel lblCharecterCount = new JLabel(); // Label to display the character counts

    // Constructor to set up the GUI components
    Window() {
        this.setLayout(new GridBagLayout()); // Use GridBagLayout for flexible positioning
        GridBagConstraints gbc = new GridBagConstraints(); // Constraints for positioning components

        JLabel lblInput = new JLabel("Input string here: "); // Label for input prompt
        JButton btnProcessString = new JButton("Process String"); // Button to trigger processing

        fldInput.setPreferredSize(new Dimension(100, 20)); // Set preferred size for the input field

        // Add action listener to the button to process the string when clicked
        btnProcessString.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processString(); // Call processString method
            }
        });

        // Position components in the GridBagLayout
        gbc.gridx = 1; // Column position for input field
        gbc.gridy = 0; // Row position
        add(fldInput, gbc); // Add input field to the layout

        gbc.gridx = 0; // Column position for label
        add(lblInput, gbc); // Add label to the layout

        gbc.gridx = 2; // Column position for button
        add(btnProcessString, gbc); // Add button to the layout

        gbc.gridx = 0; // Column position for reversed string label
        gbc.gridy = 1; // Move to the next row
        add(lblRevString, gbc); // Add reversed string label to the layout

        gbc.gridx = 1; // Column position for character count label
        add(lblCharecterCount, gbc); // Add character count label to the layout

        pack(); // Adjust the window size to fit all components
        setVisible(true); // Make the window visible
    }

    // Method to process the input string
    void processString() {
        String input = this.fldInput.getText().toLowerCase(); // Get input and convert to lowercase
        int a = 0, i = 0, o = 0, u = 0, e = 0; // Counters for vowels
        int blanks = 0; // Counter for blank spaces
        String reversed = ""; // String to hold the reversed input

        // Loop through each character in the input string
        for (int c = 0; c < input.length(); c += 1) {
            Character strngChar = input.charAt(c); // Get the current character
            reversed = strngChar + reversed; // Build the reversed string

            // Count vowels and blank spaces
            switch (strngChar) {
                case 'a':
                    ++a; // Increment 'a' counter
                    break;
                case 'e':
                    ++e; // Increment 'e' counter
                    break;
                case 'i':
                    ++i; // Increment 'i' counter
                    break;
                case 'o':
                    ++o; // Increment 'o' counter
                    break;
                case 'u':
                    ++u; // Increment 'u' counter
                    break;
                case ' ':
                    ++blanks; // Increment blank spaces counter
                    break;
                default:
                    break; // Do nothing for other characters
            }
        }

        // Update labels with results
        lblRevString.setText("<html> Reversed string: <br>" + reversed + "</html>");
        lblCharecterCount.setText("<html>" +
                "\'a\'s: " + Integer.toString(a) + " <br> " +
                "\'e\'s: " + Integer.toString(e) + " <br> " +
                "\'i\'s: " + Integer.toString(i) + " <br> " +
                "\'o\'s: " + Integer.toString(o) + " <br> " +
                "\'u\'s: " + Integer.toString(u) + " <br> " +
                "blank spaces: " + Integer.toString(blanks) + "</html>");
        pack(); // Adjust the window size again to fit new content
    }
}
