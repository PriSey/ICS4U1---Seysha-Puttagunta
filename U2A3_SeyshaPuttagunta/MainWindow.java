import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    // Constructor to set up the main window of the application
    MainWindow() {
        // Set the layout manager for the JFrame to GridBagLayout
        setLayout(new GridBagLayout());
        
        // Initialize a custom utility for GridBagConstraints to manage layout
        GridBagUtlity gbc = new GridBagUtlity(0, 0);

        // Create buttons for user actions
        JButton[] btns = {new JButton("Add an item"), new JButton("View an item")};
        
        // Create a formatted panel to organize the buttons
        FormattedPanel pnlMain = new FormattedPanel();
        
        // Add buttons to the formatted panel
        JComponent[][] elements = {btns};
        pnlMain.addElements(elements);

        // Add action listener to the "Add an item" button to open the Add window
        btns[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Add();
            }
        });

        // Add action listener to the "View an item" button to open the Search window
        btns[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Search();
            }
        });

        // Add a label for the main title to the JFrame
        add(new JLabel("Inventory Manager"), gbc);
        gbc.nextY(); // Move to the next row
        
        // Add a label for the action selection prompt to the JFrame
        add(new JLabel("Select Action"), gbc);
        gbc.nextY(); // Move to the next row
        
        // Add the panel containing the buttons to the JFrame
        add(pnlMain, gbc);
        
        // Adjust the size of the JFrame to fit its contents
        pack();
        
        // Make the JFrame visible
        setVisible(true);
    }
}
