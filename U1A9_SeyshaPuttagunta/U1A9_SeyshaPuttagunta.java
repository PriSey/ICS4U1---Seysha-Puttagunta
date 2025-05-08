import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringJoiner;
import javax.swing.text.NumberFormatter;

public class U1A9_SeyshaPuttagunta {
    // Main method to launch the application
    public static void main(String[] args) {
        new Window(); // Create and display the Window object
    }
}

// Custom class extending JFrame to create the window
class Window extends JFrame {
    public int[] list = new int[20]; // Array to hold up to 20 integers
    public int CurrentIndex = 0; // Tracks the current number of entries in the array
    JSpinner spnInput = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)); // Spinner input to select numbers

    // Constructor to set up the window
    Window() {
        setLayout(new GridBagLayout()); // Set layout manager
        GridBagConstraints gbc = new GridBagConstraints(); // Layout constraints for GridBag
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Create main panels for layout
        JPanel pnlMainBody = new JPanel(new GridBagLayout());
        JPanel pnlFoot = new JPanel(new GridBagLayout());

        // UI Components
        JLabel lblInput = new JLabel("Enter a number: ");
        JLabel lblError = new JLabel(); // Label to display error messages
        JFormattedTextField txt = ((JSpinner.NumberEditor) spnInput.getEditor()).getTextField(); // Get the text field from the spinner
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input
        JButton btnAdd = new JButton("Add"); // Button to add numbers to the list
        JButton btnRemove = new JButton("Remove"); // Button to remove numbers from the list
        JButton btnExit = new JButton("Exit"); // Button to exit the application

        JLabel lblList = new JLabel("<html></html>"); // Label to display the list of numbers
        JButton btnSumA = new JButton("Sum All"); // Button to sum all numbers
        JButton btnSumE = new JButton("Sum Even"); // Button to sum even numbers
        JButton btnSumO = new JButton("Sum Odd"); // Button to sum odd numbers
        JButton btnList = new JButton("List"); // Button to display the list of numbers
        JLabel lblDisplay = new JLabel("<html></html>"); // Label to display results

        // Scrollable panel for the list of numbers
        JScrollPane scrlList = new JScrollPane(lblList);
        scrlList.setPreferredSize(new Dimension(100, 200));

        // Event listener for the "Add" button
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setText(""); // Clear error label
                if (CurrentIndex < list.length) { // If there's space in the list
                    int number = (int) spnInput.getValue(); // Get the number from the spinner
                    list[CurrentIndex] = number; // Add number to the list
                    CurrentIndex += 1; // Increment index
                    UpdateDisplay(list, lblList); // Update the list display
                    lblDisplay.setText("<html></html>"); // Clear display label
                } else {
                    lblError.setText("Number limit reached"); // Display error if list is full
                    lblDisplay.setText("<html></html>");
                    pack(); // Adjust the layout
                    repaint();
                    revalidate();
                }
            }
        });

        // Event listener for the "Remove" button
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Boolean found = false; // Flag to check if number is found
                lblError.setText(""); // Clear error label
                int number = (int) spnInput.getValue(); // Get the number from the spinner
                int mod = 0; // Offset to shift remaining elements after removing
                for (int a = 0; a <= CurrentIndex; a++) {
                    int i = a;
                    if (list[i] == number && !found) { // If the number is found
                        found = true; // Set found flag
                        mod = 1; // Mark that elements need to be shifted
                        CurrentIndex -= 1; // Decrease index count
                    }
                    list[i] = list[i + mod]; // Shift elements to remove the number
                }
                if (!found) {
                    lblError.setText("That number is not in the array! Sorry!"); // Error if number not found
                    lblDisplay.setText("<html></html>");
                    pack();
                    repaint();
                    revalidate();
                }
                lblDisplay.setText("<html></html>");
                UpdateDisplay(list, lblList); // Update list display
            }
        });

        // Event listener for the "Sum All" button
        btnSumA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDisplay.setText("The sum of all numbers in the list is: " + sumList(-1, list)); // Display sum of all numbers
            }
        });

        // Event listener for the "Sum Even" button
        btnSumE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDisplay.setText("The sum of even numbers in the list is: " + sumList(0, list)); // Display sum of even numbers
            }
        });

        // Event listener for the "Sum Odd" button
        btnSumO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblDisplay.setText("The sum of odd numbers in the list is: " + sumList(1, list)); // Display sum of odd numbers
            }
        });

        // Event listener for the "Exit" button
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });

        // Event listener for the "List" button to display current list
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringJoiner joiner = new StringJoiner(","); // String joiner to create list display
                for (int value = 0; value < CurrentIndex; value++) {
                    joiner.add(Integer.toString(list[value])); // Add each value in the list
                }
                lblDisplay.setText("This list is: [" + joiner.toString() + "]"); // Display the list
            }
        });

        // Add elements to the main panel
        JComponent[][] ElementsMain = {{lblInput, spnInput, btnAdd, btnRemove, lblError},
                                       {scrlList, btnList, btnSumA, btnSumE, btnSumO, btnExit}};
        JComponent[][] ElementsFoot = {{lblDisplay}};

        addElements(ElementsMain, pnlMainBody); // Add main elements
        addElements(ElementsFoot, pnlFoot); // Add footer elements

        add(pnlMainBody, gbc); // Add main body panel to frame
        gbc.gridy = 1;
        add(pnlFoot, gbc); // Add footer panel to frame

        pack(); // Adjust window size
        setVisible(true); // Show window
    }

    // Helper method to add components to a panel using GridBagLayout
    void addElements(JComponent[][] Elements, JPanel pnl) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        int length = Elements.length;
        for (int row = 0; row < length; row++) {
            gbc.gridy = row;
            for (int column = 0; column < Elements[row].length; column++) {
                gbc.gridx = column;
                pnl.add(Elements[row][column], gbc);
            }
        }
    }

    // Method to update the display of the list in the UI
    void UpdateDisplay(int[] list, JLabel label) {
        String display = ""; // String to hold the list of numbers
        for (int i = 0; i < CurrentIndex; i++) {
            display += (Integer.toString(list[i]) + " <br>"); // Add each number with a line break
        }
        label.setText("<html>" + display + "</html>"); // Set the label with the list
        revalidate();
        repaint();
    }

    // Method to sum the numbers in the list, optionally summing only even or odd numbers
    int sumList(int mod, int[] list) {
        int number = 0; // Variable to hold the sum
        for (int i = 0; i < CurrentIndex; i++) {
            if (mod > -1) { // If mod is 0 (even) or 1 (odd)
                if (list[i] % 2 == mod) { number += list[i]; } // Add only even or odd numbers
            } else {
                number += list[i]; // Add all numbers if mod is -1
            }
        }
        return number; // Return the sum
    }
}
