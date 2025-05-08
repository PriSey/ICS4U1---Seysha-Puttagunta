package com.athabasca;

import javax.swing.JButton;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;

class ButtonRenderer extends JButton implements TableCellRenderer {
    // Constructor to initialize the button and make it opaque
    public ButtonRenderer() {
        setOpaque(true); // Ensure the button's background is fully visible
    }

    // Method to set up the button's appearance in the table cell
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText("Completed"); // Set the button's text to "Completed"
        return this; // Return the button component to render in the table cell
    }
}
