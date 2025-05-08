package com.athabasca;

import javax.swing.JButton;           // Import for button component
import javax.swing.JComponent;        // Import for base class of Swing components
import javax.swing.JFrame;            // Import for creating the JFrame (main window)
import javax.swing.JLabel;            // Import for text labels
import javax.swing.JOptionPane;       // Import for message dialog boxes
import javax.swing.JPasswordField;    // Import for password input field
import javax.swing.border.EmptyBorder;// Import for creating empty borders

import java.awt.Dimension;            // Import for setting dimensions of components
import java.awt.GridBagLayout;        // Import for flexible layout manager
import java.awt.event.ActionEvent;    // Import for handling button click events
import java.awt.event.ActionListener; // Import for event listener
import java.util.ArrayList;           // Import for using dynamic arrays

// Class for the Login screen, extends JFrame to create a window
public class Login extends JFrame {

    // Constructor for the Login class
    Login() {
        setTitle("Login"); // Set the title of the window
        setLayout(new GridBagLayout()); // Use GridBagLayout for flexible component placement
        GridBagUtil gbc = new GridBagUtil(0, 0); // Utility for GridBag constraints
        FormattedPanel pnl = new FormattedPanel(); // Custom panel with formatting
        Dimension dimflds = new Dimension(400, 20); // Set preferred size for input fields

        // Create username input field with custom dimension
        GeneralInput flduname = new GeneralInput(200, dimflds);

        // Create password field with custom dimension
        JPasswordField fldPass = new JPasswordField();
        fldPass.setPreferredSize(dimflds);

        // Create a login button
        JButton btnLogin = new JButton("Login");

        // Create a grid of labels and input fields for username and password
        JComponent[][] elements = {
            {new JLabel("Username: "), flduname},
            {new JLabel("Password: "), fldPass}
        };

        // Add these elements to the formatted panel
        pnl.addElements(elements);

        // Add action listener to the login button
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Retrieve username and password input
                String uname = flduname.getText().trim();
                char[] pword = fldPass.getPassword();

                // Authenticate using AuthService
                AuthService as = new AuthService();
                String password = new String(pword); // Convert char array to String

                String token = as.loginUserAndToken(uname, password); // Authenticate user
                if (token != null) {
                    // If authentication is successful, show success message
                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    // Create a new session and update its state
                    new Session(uname, token);
                    Session.update(Login.this::thing); // Call "thing" method with session updates
                    Session.update(result -> {
                        System.out.println("Permission: " + Session.getPermission());
                        // Check permission and open appropriate dashboard
                        if (Session.getPermission() == 1) {
                            new Dashboard(true); // Admin dashboard
                            setVisible(false); // Hide login window
                        } else {
                            new Dashboard(false); // User dashboard
                            setVisible(false); // Hide login window
                        }
                    });
                } else {
                    // Show error message if login fails
                    JOptionPane.showMessageDialog(
                        null,
                        "Invalid Username or Password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Add padding to the panel
        pnl.setBorder(new EmptyBorder(0, 25, 0, 25));

        // Add components to the window using GridBagLayout
        add(new JLabel("Login") {{
            setBorder(new EmptyBorder(25, 0, 0, 0)); // Add top padding to the title label
        }}, gbc);
        gbc.nextY(); // Move to the next row
        add(pnl, gbc); // Add the formatted panel
        gbc.nextY(); // Move to the next row
        add(btnLogin, gbc); // Add the login button

        // Pack components to fit the window and set default behaviors
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on window close
        setVisible(true); // Make the window visible
    }

    // Method to handle session callbacks
    private void thing(ArrayList<String> callback) {
        System.out.println("Callback: " + callback);
    }
}
