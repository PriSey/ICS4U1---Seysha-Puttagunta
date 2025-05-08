package com.athabasca;

import java.util.Properties; // Import for managing properties like text labels for the date picker
import java.awt.Container;  // Import for handling the container where components are added

import javax.swing.JFrame;  // Import for creating the JFrame (main window)

import org.jdatepicker.impl.JDatePanelImpl;  // Import for creating the date panel
import org.jdatepicker.impl.JDatePickerImpl; // Import for the date picker component
import org.jdatepicker.impl.UtilDateModel;  // Import for the date model, which represents the selected date

/**
 * DateSelector is a class that creates a GUI for selecting dates using JDatePicker.
 */
public class DateSelector {

    /**
     * Initializes and displays the GUI for date selection.
     */
    void GUI() {
        // Create a new JFrame
        JFrame f1 = new JFrame();
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set the default close operation
        f1.setSize(300, 300); // Set the size of the frame
        f1.setVisible(true); // Make the frame visible

        // Get the content pane of the frame and set its layout to null
        Container conn = f1.getContentPane();
        conn.setLayout(null);

        // Create a model to represent the date
        UtilDateModel model = new UtilDateModel();
        // Uncomment and set a default date if needed
        // model.setDate(20, 04, 2014);

        // Create properties for customizing text labels in the date picker
        Properties p = new Properties();
        p.put("text.today", "Today"); // Label for the "Today" button
        p.put("text.month", "Month"); // Label for the month selection
        p.put("text.year", "Year");   // Label for the year selection

        // Create a date panel using the model and properties
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        // Create a date picker using the date panel and a custom formatter
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Set position and size for the date picker
        datePicker.setBounds(50, 50, 200, 30);
        // Add the date picker to the container
        conn.add(datePicker);
    }
}
