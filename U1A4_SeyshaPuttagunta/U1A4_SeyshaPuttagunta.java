import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import java.awt.*;

// Main class to run the application
public class U1A4_SeyshaPuttagunta {
    public static void main(String[] args) {
        new Window(); // Create an instance of the Window class
    }
}

// Class to create the main window of the application
class Window extends JFrame {
    Window() {
        // Create a GridBagConstraints object for layout management
        GridBagConstraints gbc = new GridBagConstraints();
        // Create the main panel with a GridBagLayout
        JPanel pnlMainBody = new JPanel(new GridBagLayout());

        // Title label for the window
        JLabel lblTitle = new JLabel("Hurricane Scale");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 20)); // Set font style and size

        // Input label and spinner for hurricane category input
        JLabel lblInput = new JLabel("Please enter a hurricane category (0-5): ");
        JSpinner spnInput = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)); // Spinner for category selection
        JFormattedTextField txt = ((JSpinner.NumberEditor) spnInput.getEditor()).getTextField(); // Get the text field from the spinner
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input

        // Response header and result labels
        JLabel lblRespHead = new JLabel("Windspeeds for category 1 are: ");
        JLabel lblResult = new JLabel("74-95 mph or 64-82 kt or 119-153 km/hr");

        // Add a ChangeListener to the spinner to update labels based on selected category
        spnInput.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int input = (int) spnInput.getValue(); // Get the selected value from the spinner
                lblRespHead.setText("Windspeeds for category " + input + " are:"); // Update response header
                
                // Determine windspeed ranges based on the selected category
                switch (input) {
                    case 1:
                        lblResult.setText("74-95 mph or 64-82 kt or 119-153 km/hr");
                        break;
                    case 2:
                        lblResult.setText("96-110 mph or 83-95 kt or 154-177 km/hr");
                        break;
                    case 3:
                        lblResult.setText("111-130 mph or 96-113 kt or 178-209 km/hr");
                        break;
                    case 4:
                        lblResult.setText("131-155 mph or 114-135 kt or 210-249 km/hr");
                        break;
                    case 5:
                        lblResult.setText("greater than 155 mph or 135 kt or 249 km/hr");
                        break;
                    default:
                        lblResult.setText("Unknown Category"); // Fallback case (shouldn't occur with current model)
                        break;
                }
            }
        });

        // Configure layout constraints and add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlMainBody.add(lblTitle, gbc);
        gbc.gridy = 1;
        pnlMainBody.add(lblInput, gbc);
        gbc.gridx = 1;
        pnlMainBody.add(spnInput, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlMainBody.add(lblRespHead, gbc);
        gbc.gridy = 3;
        pnlMainBody.add(lblResult, gbc);

        // Add the main panel to the JFrame
        this.add(pnlMainBody);
        this.setMinimumSize(new Dimension(200, 0)); // Set a minimum size for the window
        this.pack(); // Pack the components within the window
        this.setVisible(true); // Make the window visible
    }
}
