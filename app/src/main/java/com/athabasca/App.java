package com.athabasca;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Entry point of the application
        System.out.println("App main method started");

        // Use SwingUtilities.invokeLater to ensure GUI creation occurs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Create and display the Login window
                System.out.println("Creating Login window");
                new Login();
                System.out.println("Login window created");
            } catch (Exception e) {
                // Print stack trace if an exception occurs during Login creation
                e.printStackTrace();
            }
        });
    }
}