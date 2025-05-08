import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

// The Search class extends JFrame to create a GUI for searching items by SKU or name
public class Search extends JFrame {
    // Constructor to initialize the GUI components
    Search() {
        // Set the layout of the frame to GridBagLayout for flexible component placement
        setLayout(new GridBagLayout());
        GridBagUtlity gbc = new GridBagUtlity(0, 0); // Utility for managing GridBagLayout constraints

        // Create a panel to hold the input elements in a structured format
        FormattedPanel pnlInput = new FormattedPanel();

        // Output field to display search results
        JTextField fldOutput = new JTextField();
        fldOutput.setEditable(false); // Make it read-only
        fldOutput.setPreferredSize(new Dimension(300, 20)); // Set size
        fldOutput.setHorizontalAlignment(JTextField.CENTER); // Center-align the text

        // Labels for the input fields
        JLabel[] lblInputs = { new JLabel("Item SKU"), new JLabel("Item name") };

        // Dimensions for input fields
        Dimension dimInput = new Dimension(100, 20);

        // Input fields for SKU and name
        JComponent[] fldInputs = { new GeneralInput(8, dimInput), new TextInput(20, dimInput) };

        // Buttons for searching by SKU or name
        JButton[] btnInputs = { new JButton("Search by SKU"), new JButton("Search by name") };

        // Organize labels, input fields, and buttons into a 2D array for layout
        JComponent[][] elements = { lblInputs, fldInputs, btnInputs };

        // Exit button to close the application
        JButton btnExit = new JButton("Exit");

        // Add elements to the panel
        pnlInput.addElements(elements);

        // Action listener for the "Search by SKU" button
        btnInputs[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fldOutput.setText(""); // Clear previous output
                String SKU = ((GeneralInput) fldInputs[0]).getText(); // Get SKU input

                // Search for the item by SKU
                for (String i : Items.categories) {
                    ArrayList<Item> items = Items.Items.get(i);
                    for (int a = 0; a < items.size(); a++) {
                        // Compare SKU case-insensitively
                        if (items.get(a).getSKU().toLowerCase().equals(SKU.toLowerCase())) {
                            fldOutput.setText(items.get(a).toString()); // Display item details
                            return;
                        }
                    }
                }
                fldOutput.setText("INVALID SKU"); // Display error if SKU not found
                return;
            }
        });

        // Action listener for the "Search by name" button
        btnInputs[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fldOutput.setText(""); // Clear previous output
                String NAME = ((TextInput) fldInputs[1]).getText(); // Get name input

                // Search for the item by name
                for (String i : Items.categories) {
                    ArrayList<Item> items = Items.Items.get(i);
                    for (int a = 0; a < items.size(); a++) {
                        // Compare name case-insensitively
                        if (items.get(a).getName().toLowerCase().equals(NAME.toLowerCase())) {
                            fldOutput.setText(items.get(a).toString()); // Display item details
                            return;
                        }
                    }
                }
                fldOutput.setText("INVALID NAME"); // Display error if name not found
                return;
            }
        });

        // Action listener for the "Exit" button to close the application
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Dispose of the JFrame
            }
        });

        // Add components to the JFrame with layout constraints
        add(new JLabel("Find an item!"), gbc);
        gbc.nextY(); // Move to the next row
        add(pnlInput, gbc); // Add the input panel
        gbc.nextY();
        add(fldOutput, gbc); // Add the output field
        gbc.nextY();
        add(btnExit, gbc); // Add the exit button

        // Pack the frame to fit all components and make it visible
        pack();
        setVisible(true);
    }
}
