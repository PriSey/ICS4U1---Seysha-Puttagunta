package com.athabasca; 
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;

public class CheckAssignments extends JFrame {
    DefaultTableModel model;
    JTable tblClients;

    @SuppressWarnings("Convert2Lambda")
    CheckAssignments(){
        // Set the layout of the JFrame to GridBagLayout
        setLayout(new GridBagLayout());
        GridBagUtil gbc = new GridBagUtil(0, 0);
        
        // Create and configure the refresh button
        JButton btnRefresh = new JButton("Refresh");
        
        // Create a DefaultTableModel with custom cell editability logic
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the "completed" column editable
                if(column == 6){
                    return true;
                } else {
                    return false;
                }
            }
        };

        // Create the JTable with custom table cell rendering
        tblClients = new JTable(model) {
            @Override
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                // Set a tooltip text for the "completed" column cells
                if (c instanceof javax.swing.JComponent) {
                    javax.swing.JComponent jc = (javax.swing.JComponent) c;
                    @SuppressWarnings("unused")
                    Object value = getValueAt(row, column);
                    jc.setToolTipText("Completed");
                }
                return c;
            }
        };

        // Set preferred size for the table's scrollable viewport
        tblClients.setPreferredScrollableViewportSize(new Dimension(1000, 400));

        // Add mouse motion listener for tracking mouse movements over the table
        tblClients.addMouseMotionListener(new MouseMotionAdapter() {           
            @SuppressWarnings("unused")
            public void mouseMoved(MouseEvent e) {
                int row = tblClients.rowAtPoint(e.getPoint());
                int column = tblClients.columnAtPoint(e.getPoint());
            }
        });

        // Add the table to a JScrollPane
        JScrollPane scr = new JScrollPane(tblClients);
        scr.setSize(new Dimension(500,500));

        // Set column identifiers for the table
        String[] columnIdentifiers = new String[Client.Categories.length + 1];
        System.arraycopy(Client.Categories, 0, columnIdentifiers, 0, Client.Categories.length);
        columnIdentifiers[Client.Categories.length] = "completed";
        model.setColumnIdentifiers(columnIdentifiers);

        // Update the table with the assigned clients data
        Session.update(this::updateTable);

        // Set the cell renderer and editor for the "completed" column
        tblClients.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tblClients.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(),model, tblClients));

        // Add action listener to the refresh button
        btnRefresh.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                // Refresh the table with the updated assigned clients data
                Session.update(CheckAssignments.this::updateTable);
            }
        });

        // Add components (label, button, scroll pane) to the frame
        add(new JLabel("Check your assignments"), gbc);
        gbc.nextY();
        add(btnRefresh, gbc);
        gbc.nextY();
        add(scr, gbc);

        // Pack the frame to fit the components
        pack();
    }

    // Override the toString method to provide the window's title
    @Override
    public String toString() {
        return "Check Assignments";
    }

    // Method to update the table with assigned clients' data
    private void updateTable(ArrayList<String> assignedClients) {
        model.setRowCount(0); // Clear the table before updating
        System.out.println("Updating Table");
        System.out.println("Assigned Clients: " + assignedClients);
        
        // Iterate over the assigned clients and populate the table
        for (String clientEmail : assignedClients) {
            for (Client client : Clients.clients) {
                if (client.getEmail().equals(clientEmail.replaceAll("\\\\", "\\."))) {
                    model.addRow(new Object[]{
                        client.getFirstName(),
                        client.getLastName(),
                        client.getPhoneNumber(),
                        client.getAddress(),
                        client.getDateJoined(),
                        client.getEmail().replaceAll("\\\\", "\\."), // Ensure proper email format
                        new JButton("Complete") // Add the "Complete" button in the last column
                    });
                }
            }
        }

        // Set preferred width for each column in the table
        for(int i = 0; i < model.getColumnCount(); i++){
            tblClients.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }
}
