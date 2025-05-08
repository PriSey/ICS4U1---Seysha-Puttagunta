package com.athabasca;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

class ButtonEditor extends DefaultCellEditor {
    // Declare instance variables
    private JButton button;
    private String label;
    @SuppressWarnings("unused")
    private boolean isPushed;
    private JTable table; // Reference to the table for context
    private DefaultTableModel model;

    // Constructor that initializes the button and sets up action listener
    public ButtonEditor(JCheckBox checkBox, DefaultTableModel model, JTable table) {
        super(checkBox);
        this.table = table;
        this.model = model;
        button = new JButton();
        button.setOpaque(true); // Make button background opaque
        button.addActionListener(new ActionListener() {
            // Action performed when the button is clicked
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Stop editing and finalize the button click
                performAction(); // Call the method to perform the action
            }
        });
    }

    // Set up the button for display in the table cell
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString(); // Set the button label
        button.setText(label); // Display the label on the button
        isPushed = true; // Indicate the button has been pressed
        return button; // Return the button component
    }

    // Return the value of the cell being edited (the label of the button)
    @Override
    public Object getCellEditorValue() {
        isPushed = false; // Reset the button press state
        return label; // Return the button label
    }

    // Stop editing when the button is clicked
    @Override
    public boolean stopCellEditing() {
        isPushed = false; // Reset the press state when editing stops
        return super.stopCellEditing(); // Call the parent method to stop editing
    }

    // Perform the action associated with the button click
    private void performAction() {
        // Get the row and column where the button was clicked
        int row = table.getSelectedRow();
        int column = table.getSelectedColumn();

        // Retrieve data from the clicked row and print it
        String rowData = table.getValueAt(row, 5).toString();
        System.out.println("Button clicked in row " + row + ", column " + column + ": " + rowData);

        DatabaseUtil db = new DatabaseUtil(); // Initialize the database utility
        List<String> list = Session.getAssigned(); // Get the list of assigned clients
        list.remove(0); // Remove the first item in the list
        // Write the updated list to the database
        db.writeData("employee/" + Session.getEmail().replaceAll("\\.", "\\\\") + "/assigned", list, data -> {
            System.out.println("Data written? " + data);
            // Update the session and clear the table row when the write operation is complete
            Session.update(e -> {
                for (byte i = 0; i < 6; i++) {
                    table.setValueAt("", row, i); // Clear the row in the table
                }
            });
        });
        // Update the table to reflect the changes
        updateTable(Session.getAssigned());
    }

    // Update the table with the latest assigned clients
    private void updateTable(List<String> assignedClients) {
        model.setRowCount(0); // Clear the existing rows in the table
        System.out.println("Updating Table");
        System.out.println("Assigned Clients: " + assignedClients);
        // Loop through the assigned clients and update the table with their data
        for (String clientEmail : assignedClients) {
            for (Client client : Clients.clients) {
                if (client.getEmail().equals(clientEmail.replaceAll("\\\\", "\\."))) {
                    model.addRow(new Object[]{
                        client.getFirstName(),
                        client.getLastName(),
                        client.getPhoneNumber(),
                        client.getAddress(),
                        client.getDateJoined(),
                        client.getEmail().replaceAll("\\\\", "\\."), // Add the email of the client
                        new JButton("Complete") // Add a "Complete" button in the last column
                    });
                }
            }
        }
    }
}
