import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class U2A1_SeyshaPuttagunta {
    public static void main(String[] args) {
        new Window();
    }
}

class Window extends JFrame {
    ArrayList<Employee> Employees = new ArrayList<Employee>();
    HashMap<Integer, Integer> EmployeeYearNumbers = new HashMap<Integer, Integer>();

    // Constructor for the main window setup
    Window() {
        // Initialize panels for layout
        JPanel pnlBody = new JPanel(new GridBagLayout()); // Main panel using GridBagLayout
        JPanel pnlTitle = new JPanel(); // Panel for the title
        JPanel pnlTop = new JPanel(new GridBagLayout()); // Top panel with GridBagLayout for form components
        JPanel pnlMiddle = new JPanel(); // Middle panel to hold the list display
        JPanel pnlBottom = new JPanel(); // Bottom panel to display text output (e.g., success or error messages)

        // Initialize labels for displaying text
        JLabel lblTitle = new JLabel("Employee Records"); // Main window title
        pnlTitle.add(lblTitle); // Add the title label to the title panel

        JLabel lblID = new JLabel("ID # "); // Label for the ID input field
        JLabel lblIDD = new JLabel("Enter an ID# and press remove to"); // Info text for removing an employee
        JLabel lblFName = new JLabel("First Name: "); // Label for first name input
        JLabel lblFNameD = new JLabel("Delete an employee's records"); // Info text for first name field
        JLabel lblLName = new JLabel("Last Name: "); // Label for last name input
        JLabel lblASal = new JLabel("Annual Salary: "); // Label for salary input
        JLabel lblDate = new JLabel("<html>Start Date: <br> mm/dd/yyyy</html>"); // Label for date input with HTML
                                                                                 // format

        // Initialize text fields for user input
        JTextField fldID = new JTextField(); // ID input field
        JTextField fldFName = new JTextField("Seysha"); // First name input field
        JTextField fldLName = new JTextField("Puttagunta"); // Last name input field
        JTextField fldASal = new JTextField("123212"); // Annual salary input field
        JTextField fldDate = new JTextField("11/11/2024"); // Date input field

        // Initialize buttons for actions
        JButton btnAdd = new JButton("Add"); // Button to add a new employee
        JButton btnRemove = new JButton("Remove"); // Button to remove an employee
        JButton btnList = new JButton("List"); // Button to list all employees
        JButton btnListBySalary = new JButton("Sort by salary"); // Button to sort employees by salary

        // Label for displaying messages (like success or error messages)
        JLabel lblEmpty = new JLabel(); // Empty label, not used for text display here

        // Input validation for various fields
        addDateInputValidation(fldDate); // Validate date input
        addDecimalNumberInputValidation(fldASal); // Validate salary input
        addLetterInputValidation(fldFName); // Validate first name input (letters only)
        addLetterInputValidation(fldLName); // Validate last name input (letters only)

        // Label for displaying the employee list
        JLabel lblList = new JLabel("<html></html>"); // Initially empty HTML label
        JScrollPane scrlList = new JScrollPane(lblList); // Scrollable list display area
        scrlList.setPreferredSize(new Dimension(300, 200)); // Set scroll pane dimensions

        // Text field to display messages (e.g., errors or status)
        JTextField txtDisplay = new JTextField(); // Display text field
        txtDisplay.setPreferredSize(new Dimension(300, 20)); // Set preferred size for display field
        txtDisplay.setEditable(false); // Make the display field non-editable
        pnlBottom.add(txtDisplay); // Add to the bottom panel

        pnlMiddle.add(scrlList); // Add scrollable list to the middle panel

        // Action listener for the "Add" button
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate;
                String FName;
                String LName;
                double ASal;
                String currentEmployee;
                txtDisplay.setText(""); // Clear any previous messages
                ArrayList<Integer> months31 = new ArrayList<Integer>();
                ArrayList<Integer> months30 = new ArrayList<Integer>();
                // Add months with 31 and 30 days to respective lists
                months31.addAll(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
                months30.addAll(Arrays.asList(4, 6, 9, 11));

                // Try to parse the salary entered, catch errors if invalid
                try {
                    ASal = Double.parseDouble(fldASal.getText());
                } catch (java.lang.NumberFormatException a) {
                    txtDisplay.setText("Employee salary not filled in");
                    return; // Exit method if salary is invalid
                }

                // Validate start date format (mm/dd/yyyy) and check validity
                if (fldDate.getText().length() == 10) {
                    startDate = fldDate.getText();
                    String[] splitDate = startDate.split("/");
                    int month = Integer.parseInt(splitDate[0]);
                    int day = Integer.parseInt(splitDate[1]);
                    int year = Integer.parseInt(splitDate[2]);

                    // Validate month, day, and year ranges
                    if (month > 12) {
                        txtDisplay.setText("Invalid month");
                        return;
                    } else if ((day > 31 && months31.contains(month)) || (day > 30 && months30.contains(month) || (day > 28 && month == 2))
                            || (day > 29 && month == 28)) {
                        txtDisplay.setText("Invalid day");
                        return;
                    } else if (year > 2024 || year < 1975) {
                        txtDisplay.setText("Invalid year (1975-2024)");
                        return;
                    }
                } else {
                    txtDisplay.setText("Date not properly entered or invalid");
                    return;
                }

                // Validate first and last names (must be at least 2 characters long)
                if (!(fldFName.getText().length() < 2)) {
                    FName = fldFName.getText();
                } else {
                    txtDisplay.setText("First name too short (must be at least 2 characters)");
                    return;
                }
                if (!(fldLName.getText().length() < 2)) {
                    LName = fldLName.getText();
                } else {
                    txtDisplay.setText("Last name too short (must be at least 2 characters)");
                    return;
                }

                // Check if an employee with the same first and last name already exists
                for (int i = 0; i < Employees.size(); i++) {
                    if (Employees.get(i).getFName().toLowerCase().equals(FName.toLowerCase())
                            && Employees.get(i).getLname().toLowerCase().equals(LName.toLowerCase())) {
                        txtDisplay.setText("Employees cannot have the same name");
                        return;
                    }
                }

                // Handle employee ID creation based on year
                String[] splitDate = startDate.split("/");
                int year = Integer.parseInt(splitDate[2]);
                if (EmployeeYearNumbers.get(year) != null) {
                    EmployeeYearNumbers.put(year, EmployeeYearNumbers.get(year) + 1);
                    currentEmployee = String.valueOf(EmployeeYearNumbers.get(year));
                } else {
                    EmployeeYearNumbers.put(year, 1);
                    currentEmployee = String.valueOf(EmployeeYearNumbers.get(year));
                }

                // Create new Employee object and add it to the employee list
                Employees.add(new Employee(FName, LName, startDate, ASal, currentEmployee));
            }
        });

        // Action listener for the "List" button
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = "<html>";
                // Loop through all employees and display their details
                for (int i = 0; i < Employees.size(); i++) {
                    Employee CEmployee = Employees.get(i);
                    text += (CEmployee.toString() + "<br>");
                }
                text += ("</html>");
                lblList.setText(text); // Update the label with the employee list
            }
        });

        // Action listener for the "Remove" button
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String IdToRemove = fldID.getText();
                String Fname = fldFName.getText().toLowerCase();
                String Lname = fldLName.getText().toLowerCase();
                String using;
                int index = -1;
                // Search for the employee by ID
                if(IdToRemove.length() > 0){
                    using = "ID";
                    for (int i = 0; i < Employees.size(); i++) {
                        if (Employees.get(i).getId().equals(IdToRemove)) {
                            index = i;
                        }
                    }
                } else{
                    using = "name";
                    for (int i = 0; i < Employees.size(); i++) {
                        System.out.println();
                        if (Employees.get(i).getFName().toLowerCase().equals(Fname) && Employees.get(i).getLname().toLowerCase().equals(Lname)) {
                            index = i;
                        }
                    }
                }
                //search for the employee by name
                if (index < 0) {
                    txtDisplay.setText("Employee "+using+" incorrect/missing");
                } else {
                    Employees.remove(index); // Remove the employee from the list
                }
            }
        });

        // Action listener for the "Sort by salary" button
        btnListBySalary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Double> EmployeeSalaries = new ArrayList<Double>();
                // Collect all employee salaries
                for (int i = 0; i < Employees.size(); i++) {
                    EmployeeSalaries.add(Employees.get(i).getSalary());
                }
                Collections.sort(EmployeeSalaries); // Sort salaries in ascending order
                Collections.reverse(EmployeeSalaries); // Reverse to get descending order

                String text = "<html>";
                // Display employees sorted by salary
                ArrayList<String> alreadyFound = new ArrayList<String>();

                for (int i = 0; i < EmployeeSalaries.size(); i++) {
                    Double salary = EmployeeSalaries.get(i);
                    for (int j = 0; j < Employees.size(); j++) {
                        System.out.println(alreadyFound.toString());
                        if (Employees.get(j).getSalary() == salary && !alreadyFound.contains(Employees.get(j).getId())) {
                            text += (Employees.get(j).toString() + "<br>");
                            alreadyFound.add(Employees.get(j).getId());
                            System.out.println(alreadyFound);
                            break;
                        }
                    }
                }
                text += ("</html>");
                lblList.setText(text); // Update the label with the sorted list
            }
        });

        // Layout and component placement using 2D arrays of components
        JComponent[][] elements = { { lblID, fldID, lblIDD }, { lblFName, fldFName, lblFNameD },
                { lblLName, fldLName, btnAdd }, { lblASal, fldASal, btnRemove }, { lblDate, fldDate, btnList },
                { lblEmpty, lblEmpty, btnListBySalary } };
        JComponent[][] panels = { { pnlTitle }, { pnlTop }, { pnlMiddle }, { pnlBottom } };

        // Set preferred size for text fields in the form
        for (int i = 0; i < elements.length; i++) {
            elements[i][1].setPreferredSize(new Dimension(100, 20));
        }

        // Add the elements to the top panel
        addElements(elements, pnlTop);

        // Add the panels to the body panel
        addElements(panels, pnlBody);

        add(pnlBody); // Add the body panel to the frame

        pack(); // Adjust the window size to fit components
        setVisible(true); // Make the window visible
    }

    // Method to add elements (like labels, text fields, buttons) to the provided
    // JPanel using GridBagLayout
    void addElements(JComponent[][] Elements, JPanel pnl) {
        GridBagConstraints gbc = new GridBagConstraints(); // Create GridBagConstraints to define the layout properties
                                                           // of components
        gbc.gridx = 0; // Set the initial column for components
        gbc.gridy = 0; // Set the initial row for components
        int length = Elements.length; // Get the number of rows in the elements array
        for (int row = 0; row < length; row++) { // Iterate through each row
            gbc.gridy = row; // Set the row position in GridBagLayout
            for (int column = 0; column < Elements[row].length; column++) { // Iterate through each column in the row
                gbc.gridx = column; // Set the column position in GridBagLayout
                pnl.add(Elements[row][column], gbc); // Add the component (e.g., label, button, textfield) to the panel
            }
        }
    }

    // Method to add input validation for decimal numbers (e.g., salary or monetary
    // values) in a JTextField
    public void addDecimalNumberInputValidation(JTextField textField) {
        // Adding a key listener to the JTextField to capture key presses and validate
        // input
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                // Allow backspace to delete the last character
                if ((k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true); // Allow backspace to remove characters
                } else {
                    try {
                        // Try to parse the current text plus the new key pressed as a double (decimal
                        // number)
                        double futureValue = Double.parseDouble(textField.getText() + String.valueOf(k.getKeyChar()));

                        if (futureValue < 30000000) { // Limit input to values less than 30 million
                            // Allow decimal point if there isn't already one in the text field
                            if (k.getKeyChar() == '.') {
                                textField.setEditable(textField.getText().indexOf('.') == -1);
                            }
                            // Allow only digits and backspace for valid numeric input
                            else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9')) {
                                textField.setEditable(true); // Allow numbers
                            } 
                            else {
                                textField.setEditable(false); // Prevent invalid characters
                            }
                            System.out.println(textField.getText().indexOf('.') == -1);
                            String nextValue =textField.getText() + String.valueOf(k.getKeyChar());
                            if ((nextValue.split("\\.").length > 1)){
                                if (nextValue.split("\\.")[1].length() > 2) {
                                    textField.setEditable(false);
                                }
                            }
                            // Disallow any non-numeric and non-decimal characters
                        } else {
                            textField.setEditable(false); // Prevent input if the value exceeds 30 million
                        }
                    } catch (java.lang.NumberFormatException e) {
                        // Handle invalid numeric input (e.g., user presses letters or symbols)
                        if (k.getKeyChar() == '.') {
                            textField.setEditable(textField.getText().indexOf('.') == -1); // Allow only one decimal
                                                                                           // point
                        }
                        // Allow numbers and backspace
                        else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9')) {
                            textField.setEditable(true); // Allow numeric input
                        }
                        // Disallow other characters (e.g., letters, symbols)
                        else {
                            textField.setEditable(false); // Prevent invalid characters
                        }
                    }
                }
            }
        });
    }

    // Method to add input validation for date format (mm/dd/yyyy) in a JTextField
    public void addDateInputValidation(JTextField textField) {
        // Adding a key listener to the JTextField to validate input for date formatting
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                String text = textField.getText(); // Get the current text in the text field

                // Allow backspace to delete the last character
                if ((k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true); // Allow backspace
                }
                // Allow numeric input for the first 10 characters (mm/dd/yyyy format)
                else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') && text.length() < 10) {
                    textField.setEditable(true); // Allow digits
                    // Automatically insert slashes after the month and day parts (i.e., after 2nd
                    // and 5th character)
                    if (text.length() == 2 || text.length() == 5) {
                        textField.setText(text + "/"); // Insert slash at appropriate positions
                    }
                }
                // Allow slash characters only at positions 2 and 5 (for mm/dd/yyyy format)
                else if (k.getKeyChar() == '/') {
                    if (text.length() == 2 || text.length() == 5) {
                        textField.setEditable(true); // Allow slash at positions 2 and 5
                    } else {
                        textField.setEditable(false); // Disallow slashes elsewhere
                    }
                }
                // Disallow all other characters (letters, symbols, etc.)
                else {
                    textField.setEditable(false); // Prevent invalid characters
                }
            }
        });
    }

    // Method to add input validation for letter-only text (e.g., first and last
    // names) in a JTextField
     

}

class Employee {
    // Private fields for employee attributes
    private String number; // Employee's unique number
    private String date; // Employee's start date
    private String firstName; // Employee's first name
    private String lastName; // Employee's last name
    private double salary; // Employee's salary
    private String ID; // Auto-generated Employee ID

    // Constructor to initialize the Employee object
    Employee(String firstName, String lastName, String date, double salary, String number) {
        this.firstName = firstName; // Assign the first name
        this.lastName = lastName; // Assign the last name
        this.date = date; // Assign the start date
        this.salary = salary; // Assign the salary

        // Ensure the employee number is at least 2 characters long by padding with "0"
        // if necessary
        if (number.length() < 2) {
            this.number = "0" + number;
        } else {
            this.number = number;
        }

        // Generate the unique ID for the employee
        setId();
    }

    // Getter for the employee's ID
    public String getId() {
        return ID;
    }

    // Getter for the employee's salary
    public double getSalary() {
        return salary;
    }

    // Getter for the employee's first name
    public String getFName() {
        return firstName;
    }

    // Getter for the employee's last name
    public String getLname() {
        return lastName;
    }

    // Private method to generate the unique employee ID
    // ID is composed of the last two digits of the start year, first two letters of
    // the first name,
    // first two letters of the last name, and the employee number
    private void setId() {
        ID = date.split("/")[2].substring(2, 4) // Extract last two digits of the year
                + firstName.substring(0, 2) // First two letters of the first name
                + lastName.substring(0, 2) // First two letters of the last name
                + number; // Employee number
    }

    // Override the toString method to provide a readable representation of the
    // employee
    public String toString() {
        return "|Name: " + firstName + " " + lastName // Display full name
                + "|Start date: " + date // Display start date
                + "|Salary: $" + salary // Display salary
                + "|Employee ID: " + ID // Display employee ID
                + "|"; // End of string representation
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // Check type

        Employee other = (Employee) obj;
        // Check logical equality based on relevant fields (e.g., name and salary)
        return Objects.equals(ID, other.ID);
    }
}
