import javax.swing.*; // Import Swing components for GUI
import javax.swing.event.ChangeEvent; // Import for handling change events
import javax.swing.event.ChangeListener; // Import for change listener interface
import javax.swing.text.NumberFormatter; // Import for formatting numbers in text fields
import java.awt.*; // Import AWT components for layout management

public class U1A5_SeyshaPuttagunta {
    public static void main(String[] args) {
        new Window(); // Create an instance of the Window class
    }
}

class Window extends JFrame { // Create a Window class that extends JFrame
    // Create UI components
    JSpinner spnHeight = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    JCheckBox cbxBackProblems = new JCheckBox("Back problems?"); // Checkbox for back problems
    JCheckBox cbxHeartTrouble = new JCheckBox("Heart trouble?"); // Checkbox for heart trouble
    JLabel lblsafe = new JLabel(); // Label to display safety message

    Window() { // Constructor for the Window class
        JPanel pnl = new JPanel(new GridBagLayout()); // Create a panel with GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Create constraints for layout

        // Create and configure UI components
        JLabel lbltitle = new JLabel("RollerCoaster Ride"); // Title label
        JLabel lblHeight = new JLabel("Height in cm? (Press Enter key to set)"); // Height label

        // Get the text field from the spinner and configure its formatter
        JFormattedTextField txt = ((JSpinner.NumberEditor) spnHeight.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input

        // Add change listeners to handle user interactions
        spnHeight.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) { onChange(); } // Call onChange when height changes
        });
        cbxBackProblems.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) { onChange(); } // Call onChange when back problems checkbox changes
        });
        cbxHeartTrouble.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) { onChange(); } // Call onChange when heart trouble checkbox changes
        });

        // Set up layout for the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnl.add(lbltitle, gbc); // Add title to panel
        gbc.gridy = 1;
        pnl.add(lblHeight, gbc); // Add height label to panel
        gbc.gridx = 1;
        pnl.add(spnHeight, gbc); // Add height spinner to panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnl.add(cbxBackProblems, gbc); // Add back problems checkbox to panel
        gbc.gridy = 3;
        pnl.add(cbxHeartTrouble, gbc); // Add heart trouble checkbox to panel
        gbc.gridy = 4;
        pnl.add(lblsafe, gbc); // Add safety message label to panel
        
        this.setMinimumSize(new Dimension(200, 0)); // Set minimum size of the window

        // Configure the window
        this.add(pnl); // Add panel to the window
        this.pack(); // Pack the components within the window
        this.setVisible(true); // Make the window visible
    }

    void onChange() { // Method to handle changes in input
        int Height = (int) spnHeight.getValue(); // Get the current height from the spinner
        boolean BackProblems = cbxBackProblems.isSelected(); // Check if back problems checkbox is selected
        boolean HeartTrouble = cbxHeartTrouble.isSelected(); // Check if heart trouble checkbox is selected

        // Determine if it is safe to ride based on the height and health conditions
        if ((Height <= 188 && Height >= 122) && !(BackProblems || HeartTrouble)) {
            lblsafe.setText("It is safe for you to ride the coaster. Have fun!"); // Safe message
        } else {
            lblsafe.setText("Sorry, it is unsafe for you to ride the roller coaster."); // Unsafe message
        }
    }
}
