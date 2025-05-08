import javax.swing.*;
import java.awt.*;

public class FormattedPanel extends JPanel {
    FormattedPanel(){
        setLayout(new GridBagLayout());
    }
    
    public void addElements(JComponent[][] Elements) {
        GridBagConstraints gbc = new GridBagConstraints(); // Create GridBagConstraints to define the layout properties
                                                           // of components
        gbc.gridx = 0; // Set the initial column for components
        gbc.gridy = 0; // Set the initial row for components
        int length = Elements.length; // Get the number of rows in the elements array
        for (int row = 0; row < length; row++) { // Iterate through each row
            gbc.gridy = row; // Set the row position in GridBagLayout
            for (int column = 0; column < Elements[row].length; column++) { // Iterate through each column in the row
                gbc.gridx = column; // Set the column position in GridBagLayout
                add(Elements[row][column], gbc); // Add the component (e.g., label, button, textfield) to the panel
            }
        }
    }
}