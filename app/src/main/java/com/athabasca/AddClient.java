package com.athabasca;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class AddClient extends JFrame {
    private File selectedFile = null; // Class-level variable to store the selected file

    @SuppressWarnings("Convert2Lambda")
    AddClient() {
        setTitle("Add Client");
        // Set the layout for the frame
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 300));
        
        // Create a formatted panel and set constraints
        FormattedPanel pnl = new FormattedPanel();
        GridBagUtil constraints = new GridBagUtil(0, 0);
        Dimension dimflds = new Dimension(100, 20);

        // Create text fields for client information
        JTextField Fname = new TextInput(15, dimflds);
        JTextField Lname = new TextInput(15, dimflds);
        JTextField phone = new DoubleInput(Long.valueOf("9999999999"), dimflds, 0, false);
        JTextField address = new GeneralInput(Integer.MAX_VALUE, dimflds);
        JTextField email = new GeneralInput(Integer.MAX_VALUE, dimflds);
        
        // Create the date picker model and properties
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        
        // Create the date panel and date picker
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JButton submit = new JButton("Submit Information");
        // Create a 2D array of components to add to the panel
        JComponent[][] elements = {
            { new JLabel("First Name: "), Fname },
            { new JLabel("Last Name: "), Lname },
            { new JLabel("Phone #: "), phone },
            { new JLabel("Address: "), address },
            { new JLabel("Email: "), email },
            { new JLabel("Date Joined: "), datePicker },
            { submit }
        };
        
        // Add elements to the panel
        pnl.addElements(elements);
        add(pnl, constraints);

        // Increment the grid position for the next set of components
        constraints.nextY();
        FormattedPanel pnl2 = new FormattedPanel();
        // Add a label for adding clients by CSV 
        JLabel byCSV = new JLabel("Or Add Clients By CSV ");
        constraints.nextY();
        System.out.println("byCSVLabel y: " + constraints.gridy);
        add(byCSV, constraints);

        // Add a button to open the file chooser
        JButton openFileChooserButton = new JButton("Open");
        // Add a submit button beside the open button
        JButton submit2 = new JButton("Submit");
        // File selected display beside the buttons
        JTextField csvInput = new GeneralInput(25, dimflds);
        csvInput.setEditable(false);

        JComponent[][] elements2 = {
            { openFileChooserButton, submit2, csvInput },
            {}
        };
        pnl2.addElements(elements2);
        constraints.nextY();
        System.out.println("ByCSV y: " + constraints.gridy);

        add(pnl2, constraints);

        JLabel status = new JLabel();
        constraints.nextY();
        add(status, constraints);

        // Add an action listener to the button to open the file chooser
        openFileChooserButton.addActionListener(new ActionListener() {
            @SuppressWarnings("override")
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // Add a file filter to only allow CSV files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Handle the selected file
                    selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.getName().toLowerCase().endsWith(".csv")) {
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                        csvInput.setText(selectedFile.toString());
                        status.setText("");
                    } else {
                        status.setText("Selected file is not a CSV file");
                        csvInput.setText("");
                    }
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            @SuppressWarnings("override")
            public void actionPerformed(ActionEvent e) {
                DatabaseUtil db = new DatabaseUtil();
                
                // Validation: Ensure required fields are not empty
                if (Fname.getText().isEmpty()) {
                    status.setText("First name is required");
                    return;
                }
        
                if (Lname.getText().isEmpty()) {
                    status.setText("Last name is required");
                    return;
                }
        
                if (phone.getText().isEmpty()) {
                    status.setText("Phone number is required");
                    return;
                }
        
                if (address.getText().isEmpty()) {
                    status.setText("Address is required");
                    return;
                }
        
                if (email.getText().isEmpty()) {
                    status.setText("Email is required");
                    return;
                }
        
                // Validate the email format using regex
                if (!Pattern.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", email.getText())) {
                    status.setText("Invalid email format");
                    return;
                }
        
                if (datePicker.getJFormattedTextField().getText().isEmpty()) {
                    status.setText("Date Joined is required");
                    return;
                }
        
                // Convert the phone number to a long
                long phoneNum = Long.parseLong(phone.getText());
                String emailInput = email.getText().toLowerCase();
        
                // Check for duplicates in the client list
                for (Client existingClient : Clients.clients) {
                    if (existingClient.getPhoneNumber() == phoneNum) {
                        status.setText("Phone number already exists");
                        JOptionPane.showMessageDialog(
                            null,
                            "The phone number already exists in the system. Please enter a unique phone number.",
                            "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
        
                    if (existingClient.getEmail().equalsIgnoreCase(emailInput)) {
                        status.setText("Email already exists");
                        JOptionPane.showMessageDialog(
                            null,
                            "The email already exists in the system. Please enter a unique email.",
                            "Duplicate Entry",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }
        
                // Create a new client and add to the database if no duplicates are found
                Client newClient = new Client(
                    Fname.getText(),
                    Lname.getText(),
                    phoneNum,
                    address.getText(),
                    datePicker.getJFormattedTextField().getText(),
                    emailInput
                );
        
                // Prepare the data to add to the database
                Map<String, Map<String, Object>> map = new HashMap<>();
                map.put(newClient.getEmail().replaceAll("\\.", "\\\\"), new HashMap<String, Object>() {{
                    put("f_name", newClient.getFirstName());
                    put("l_name", newClient.getLastName());
                    put("p_number", newClient.getPhoneNumber());
                    put("address", newClient.getAddress());
                    put("date_joined", newClient.getDateJoined());
                }});
        
                // Append the data to the database
                db.appendData("client", map, data -> {
                    if (data) {
                        // Clear the form fields on success
                        Fname.setText("");
                        Lname.setText("");
                        phone.setText("");
                        address.setText("");
                        email.setText("");
                        datePicker.getJFormattedTextField().setText("");
                        status.setText("Client added successfully");
                        JOptionPane.showMessageDialog(null, "Client Added!");
                        dispose(); // Close the window
                    } else {
                        status.setText("Error adding client");
                    }
                });
        
                // Add the client to the in-memory list
                Clients.addClient(newClient);
            }
        });
        
        submit2.addActionListener(new ActionListener() {
            @SuppressWarnings("override")
            public void actionPerformed(ActionEvent e) {
                DatabaseUtil db = new DatabaseUtil();
                String file = returnSelectedFile(); // Get the selected file
                Map<String, Map<String, Object>> map = new HashMap<>();
                try {
                    // Read data from the file
                    List<String> s = FileHelper.readFile(file);
                    for (String line : s) {
                        String[] data = line.split(","); // Split CSV line into fields
                        if (data.length == 6) { // Ensure the CSV line has the correct number of fields
                            long phoneNum = Long.parseLong(data[2]);
                            Client newClient = new Client(data[0], data[1], phoneNum, data[3], data[4], data[5]);
                            map.put(newClient.getEmail().replaceAll("\\.", "\\\\"), new HashMap<String, Object>() {{
                                put("f_name", newClient.getFirstName());
                                put("l_name", newClient.getLastName());
                                put("p_number", newClient.getPhoneNumber());
                                put("address", newClient.getAddress());
                                put("date_joined", newClient.getDateJoined());
                            }});
                        } else {
                            // Log an error if the CSV line format is invalid
                            System.err.println("Invalid CSV format: " + line);
                        }
                    }
                    // Append the data to the database
                    db.appendData("client", map, data2 -> {
                        if (data2) {
                            status.setText("Clients added successfully from CSV");
                            JOptionPane.showMessageDialog(null, "Clients Added!");
                            dispose(); // Close the window
                        } else {
                            status.setText("Error adding clients from CSV");
                        }
                    });
                    // Reload clients into memory
                    Clients.loadClients(a -> {
                        System.out.println("Clients loaded");
                    });
                } catch (IOException b) {
                    // Handle file reading errors
                    System.err.println("Error reading file: " + b.getMessage());
                } catch (NumberFormatException nfe) {
                    // Handle number formatting errors
                    System.err.println("Invalid number format in CSV: " + nfe.getMessage());
                }
            }
        });
        
        // Pack the frame to fit the components
        pack();
        }
        
        // Returns the selected file path or a default message if no file is selected
        private String returnSelectedFile() {
            return selectedFile != null ? selectedFile.toString() : "No file selected";
        }
        
        // Overrides the toString method to return a string representation of the class
        @Override
        public String toString() {
            return "Add Client";
        }
    }
        