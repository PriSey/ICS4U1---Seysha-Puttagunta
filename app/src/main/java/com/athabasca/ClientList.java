package com.athabasca;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class ClientList extends JFrame {
    private DefaultTableModel model;

    // Constructor for initializing the Client List window
    ClientList() {
        setTitle("Client List"); // Set the title of the frame
        setLayout(new GridBagLayout()); // Set layout for the frame
        GridBagUtil gbc = new GridBagUtil(0, 0); // GridBagLayout utility for component placement
        FormattedPanel pnlActions = new FormattedPanel(); // Panel to hold search and filter components

        // Create the table model for displaying clients
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the cells non-editable
            }
        };

        // Create the table with the client data
        JTable tblClients = new JTable(model) {
            // Customize table cell rendering to show tooltips
            @Override
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof javax.swing.JComponent) {
                    javax.swing.JComponent jc = (javax.swing.JComponent) c;
                    Object value = getValueAt(row, column);
                    jc.setToolTipText(value == null ? "" : value.toString()); // Set tooltip text to the value of the cell
                }
                return c;
            }
        };

        // Add the table to a scroll pane
        JScrollPane scr = new JScrollPane(tblClients);
        scr.setSize(new Dimension(500, 400)); // Set the size of the scroll pane

        JPanel pnl = new JPanel(); // Panel for additional components
        pnl.setLayout(new GridBagLayout()); // Set layout for the panel

        // Set the preferred size for the table
        tblClients.setPreferredScrollableViewportSize(new Dimension(1000, 400));

        // Mouse motion listener for the table (not used in the current code)
        tblClients.addMouseMotionListener(new MouseMotionAdapter() {
            @SuppressWarnings("unused")
            public void mouseMoved(MouseEvent e) {
                int row = tblClients.rowAtPoint(e.getPoint());
                int column = tblClients.columnAtPoint(e.getPoint());
            }
        });

        // Combo box for selecting client categories (First Name, Last Name, etc.)
        JComboBox<String> bxCategories = new JComboBox<String>(Client.Categories);

        // Search field for entering the search query
        GeneralInput fldSearch = new GeneralInput(200, new Dimension(100, 20));

        // Button to clear search and refresh the list
        JButton btnRefresh = new JButton("Clear Search/Refresh");

        // Button to initiate the search
        JButton btnSearch = new JButton("Search");

        
        // Action components (search field, search button, etc.)
        JComponent[][] elemsAction = {
                {new JLabel("Search: "), new JLabel("by:"), bxCategories, fldSearch, btnSearch}
        };

        // Action listener for the search button
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client[] found;

                // Create an array of client data based on the selected category
                String[] toSort = new String[Clients.clients.size()];
                int category = bxCategories.getSelectedIndex();
                for (int i = 0; i < Clients.clients.size(); i++) {
                    String[] categories = Clients.clients.get(i).toString().split("\\|"); // Split client data by "|"
                    toSort[i] = categories[category]; // Get the category to sort by
                }

                // Create a search helper to find the clients matching the search query
                SearchHelper searcher = new SearchHelper();
                int[] indices = searcher.originalIndicesBinary(toSort, fldSearch.getText().trim()); // Search for matching entries
                found = new Client[indices.length];

                // Retrieve the matching clients from the indices
                for (int i = 0; i < found.length; i++) {
                    found[i] = Clients.clients.get(indices[i]);
                }

                // If no clients were found, display an error message
                if (found.length == 0) {
                    JOptionPane.showMessageDialog(
                        null,
                        "No client Found",
                        "Search Failed",
                        JOptionPane.ERROR_MESSAGE
                    );
                } 
                else {
                    updateTable(found); // Update the table with the found clients
                }
            }
        });

        // Add action components (search, category, etc.) to the panel
        pnlActions.addElements(elemsAction);

        // Set the column identifiers for the table (client categories)
        model.setColumnIdentifiers(Client.Categories);

        // Load the list of clients
        Clients.loadClients(this::callback);

        // Action listener for the refresh button
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Refresh the table with all clients
                updateTable(Clients.clients.toArray(new Client[Clients.clients.size()]));
            }
        });

        // Set border for the actions panel
        pnlActions.setBorder(new EmptyBorder(0, 25, 0, 25));

        // Add the components (labels, buttons, table, etc.) to the frame
        add(new JLabel("Client List") {{
            setBorder(new EmptyBorder(25, 25, 0, 25)); // Set border for the title label
        }}, gbc);
        gbc.nextY();
        add(pnlActions, gbc); // Add the actions panel
        gbc.nextY();
        add(btnRefresh, gbc); // Add the refresh button
        gbc.nextY();
        add(scr, gbc); // Add the scroll pane with the table

        // Pack the frame to adjust its size based on the components
        pack();
    }

    // Method to update the table with the client data
    private void updateTable(Client[] clients) {
        model.setRowCount(0); // Clear the existing rows
        for (Client client : clients) {
            String[] clientData = client.toString().split("\\|"); // Split the client data into columns
            model.addRow(clientData); // Add the client data as a new row in the table
        }
    }

    // Callback method to update the table when the clients are loaded
    private void callback(ArrayList<Client> clients) {
        model.setRowCount(0); // Clear the existing rows
        for (Client client : clients) {
            model.addRow(client.toString().split("\\|")); // Add each client's data to the table
        }
    }

    // Override toString method for the class name
    @Override
    public String toString() {
        return "Client List";
    }
}
