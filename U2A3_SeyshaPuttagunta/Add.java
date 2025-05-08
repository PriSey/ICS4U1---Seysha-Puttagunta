import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add extends JFrame {

    // Constructor to set up the Add window and its components
    Add() {
        // Set layout manager for the frame
        setLayout(new GridBagLayout());
        
        // Initialize a custom utility for GridBag constraints
        GridBagUtlity gbc = new GridBagUtlity(0, 0);

        // Create a formatted panel for input fields
        FormattedPanel pnlInput = new FormattedPanel();
        
        // Create an output text field to display messages, making it uneditable
        JTextField fldOutput = new JTextField();
        fldOutput.setEditable(false);
        fldOutput.setPreferredSize(new Dimension(600, 20));
        fldOutput.setHorizontalAlignment(JTextField.CENTER);
        
        // Create a button to trigger the "Add" operation
        JButton btnAdd = new JButton("Add");

        // Define dimensions for input fields
        Dimension dim = new Dimension(100, 20);

        // Define labels for input fields
        JComponent[] inputLabels = {
            new JLabel("Name"), 
            new JLabel("Category"), 
            new JLabel("Quantity"),
            new JLabel("Min Quantity"), 
            new JLabel("Vendor Price ($)"), 
            new JLabel("Markup (%)"),
            new JLabel("Discount (%)")
        };

        // Define the input components corresponding to the labels
        JComponent[] inputs = {
            new TextInput(20, dim),                          // Text input for Name
            new JComboBox<String>(Items.categories),         // Combo box for Category
            new IntegerInput(0, 0, 1000, dim),               // Integer input for Quantity
            new IntegerInput(0, 0, 1000, dim),               // Integer input for Min Quantity
            new DoubleInput(100, dim),                       // Double input for Vendor Price
            new IntegerInput(0, 0, 100, dim),                // Integer input for Markup
            new IntegerInput(0, 0, 100, dim)                 // Integer input for Discount
        };

        // Group labels and input fields into an array
        JComponent[][] inputElements = { inputLabels, inputs };

        // Add the input elements to the formatted panel
        pnlInput.addElements(inputElements);

        // Add an action listener to the "Add" button to process user input
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear the output field before processing
                fldOutput.setText("");

                // Retrieve the entered name
                String name = ((TextInput) inputs[0]).getText();
                
                // Validate the name field: it cannot be empty
                if (name.equals("")) {
                    fldOutput.setText("Name field cannot be empty");
                    return;
                } else {
                    // Check if the name already exists in the item list
                    for (String i : Items.categories) {
                        for (int a = 0; a < Items.Items.get(i).size(); a++) {
                            if (Items.Items.get(i).get(a).getName().toLowerCase().equals(name.toLowerCase())) {
                                fldOutput.setText("Name cannot be the same as another item in the list");
                                return;
                            }
                        }
                    }
                }

                // Validate and retrieve the vendor price
                Double vendorPrice;
                try {
                    vendorPrice = Double.valueOf(((DoubleInput) inputs[4]).getText());
                } catch (java.lang.NumberFormatException a) {
                    fldOutput.setText("All fields must be filled in");
                    return;
                }

                System.out.println(vendorPrice);

                // Retrieve the selected category
                @SuppressWarnings("unchecked")
                String category = (String) ((JComboBox<String>) inputs[1]).getSelectedItem();

                // Retrieve other input values
                int Quantity = (int) ((IntegerInput) inputs[2]).getValue();
                int minimumQuantity = (int) ((IntegerInput) inputs[3]).getValue();
                int markup = (int) ((IntegerInput) inputs[5]).getValue();
                int discount = (int) ((IntegerInput) inputs[6]).getValue();

                // Add the new item to the list
                Items.addItem(name, category, Quantity, minimumQuantity, vendorPrice, markup, discount);
                
                // Indicate successful addition
                fldOutput.setText("Added!");

                // Close the Add window
                dispose();
            }
        });

        // Add components to the frame using GridBag constraints
        add(new JLabel("Add a Fruit!"), gbc);
        gbc.nextY();
        add(pnlInput, gbc);
        gbc.nextY();
        add(btnAdd, gbc);
        gbc.nextY();
        add(fldOutput, gbc);

        // Pack the components and make the frame visible
        pack();
        setVisible(true);
    }
}
