package com.athabasca;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public class Dashboard extends JFrame {
    // Constructor for the Dashboard class, accepts an admin flag to determine the layout
    Dashboard(Boolean admin) {
        setTitle("Dashboard"); // Set the title of the window
        setLayout(new GridBagLayout()); // Set the layout of the frame to GridBagLayout
        GridBagUtil gbc = new GridBagUtil(0, 0); // Create GridBagUtil instance for component positioning
        FormattedPanel pnl = new FormattedPanel(); // Create a formatted panel for containing components
        JFrame[] toUse; // Array to hold different JFrame components
        JButton[] buttons; // Array to hold buttons for each JFrame

        // If admin is true, use 3 options (Client List, Assign Client, Add Client), otherwise use 2 options (Client List, Check Assignments)
        if (admin) {
            toUse = new JFrame[3]; // Admin has access to 3 frames
            toUse[0] = new ClientList(); // Client List option
            toUse[1] = new AssignClient(); // Assign Client option
            toUse[2] = new AddClient(); // Add Client option
        } else {
            toUse = new JFrame[2]; // Non-admin (Rep) has access to 2 frames
            toUse[0] = new ClientList(); // Client List option
            toUse[1] = new CheckAssignments(); // Check Assignments option
        }

        buttons = new JButton[toUse.length]; // Create buttons for each frame
        // Iterate through the toUse array to create buttons
        for (int i = 0; i < toUse.length; i++) {
            JButton btnNew = new JButton(toUse[i].toString()); // Create a new button with the frame's title
            final int inner_i = i; // Use a final reference for the index inside the action listener
            // Add an ActionListener to the button to show the corresponding JFrame when clicked
            btnNew.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    toUse[inner_i].setVisible(true); // Make the corresponding JFrame visible
                }
            });
            buttons[i] = btnNew; // Store the button
        }

        // Add buttons to the panel
        JComponent[][] elements = {buttons};
        pnl.addElements(elements); // Add the array of buttons to the formatted panel

        // Add a label at the top of the panel depending on whether the user is an admin or not
        if (admin) {
            add(new JLabel("Admin Dashboard"), gbc); // Label for admin dashboard
        } else {
            add(new JLabel("Rep Dashboard"), gbc); // Label for rep dashboard
        }

        pnl.setBorder(new EmptyBorder(25, 25, 25, 25)); // Add a border to the panel
        gbc.nextY(); // Move the grid position down
        add(pnl, gbc); // Add the panel with buttons to the frame

        System.out.println("Got to a point"); // Debug message to indicate the code has reached this point
        pack(); // Adjust the size of the window to fit the components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setVisible(true); // Make the window visible
    }
}
