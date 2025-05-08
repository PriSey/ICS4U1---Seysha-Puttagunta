import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main class to initiate the program
public class U1A10_SeyshaPuttagunta {
    public static void main(String[] args) {
        new Window();
    }
}

// Window class extends JFrame to create the GUI
class Window extends JFrame {
    String[][] Students = new String[30][4]; // Array to hold up to 30 students with 4 grades each
    int currentIndex = 0; // Tracks the current number of students

    // Constructor for the Window class
    Window() {
        this.setLayout(new GridLayout(3, 1)); // Divides window into three sections
        JPanel pnlTitle = new JPanel();
        JPanel pnlBody = new JPanel(new GridBagLayout());
        JPanel pnlFooter = new JPanel(new GridLayout(2, 1));

        // Title panel creation
        JLabel lblTitle = new JLabel("Student Grades");
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
        pnlTitle.add(lblTitle);

        // Main body panel creation
        JLabel lblFNInput = new JLabel("First name: ");
        JLabel lblLNInput = new JLabel("Last name: ");
        
        // Fields for inputting first and last names with letter-only validation
        JTextField fldFNInput = new JTextField();
        fldFNInput.setPreferredSize(new Dimension(100, 20));
        addLetterInputValidation(fldFNInput);
        
        JTextField fldLNInput = new JTextField();
        fldLNInput.setPreferredSize(new Dimension(100, 20));
        addLetterInputValidation(fldLNInput);

        // Buttons for adding, displaying averages, listing students, updating, etc.
        JButton btnAdd = new JButton("Add");
        JButton btnSAvg = new JButton("Student Average");
        JButton btnList = new JButton("List");
        JButton btnCAvg = new JButton("Course Average");
        JButton btnRemove = new JButton("Remove");
        JButton btnUpdate = new JButton("Update Grades");
        JButton btnExit = new JButton("Exit");

        // Labels and fields for grades
        JLabel[] lblsGradeLabels = new JLabel[4];
        JTextField[] fldsGradeInputs = new JTextField[4];
        
        for (int i = 0; i < 4; i++) {
            JLabel lbl = new JLabel("Test " + Integer.toString(i) + " grade (%): ");
            JTextField fld = new JTextField();
            addDecimalNumberInputValidation(fld); // Validate only numbers
            fld.setPreferredSize(new Dimension(30, 20));
            lblsGradeLabels[i] = lbl;
            fldsGradeInputs[i] = fld;
        }

        // Define layout structure for main body components
        JComponent[][] Elements = {
                { lblFNInput, fldFNInput, lblLNInput, fldLNInput },
                { lblsGradeLabels[0], fldsGradeInputs[0], btnAdd, btnSAvg },
                { lblsGradeLabels[1], fldsGradeInputs[1], btnList, btnCAvg },
                { lblsGradeLabels[2], fldsGradeInputs[2], btnRemove, btnUpdate },
                { lblsGradeLabels[3], fldsGradeInputs[3], btnExit }
        };
        addElements(Elements, pnlBody);

        // Footer creation for student list display
        JLabel lblList = new JLabel();
        lblList.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        JScrollPane scrlList = new JScrollPane(lblList);
        scrlList.setSize(new Dimension(300, 300));
        
        JLabel lblDisplay = new JLabel();
        lblDisplay.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        
        pnlFooter.add(scrlList);
        pnlFooter.add(lblDisplay);

        // Add student button functionality
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fName = fldFNInput.getText();
                String lName = fldLNInput.getText();
                Boolean add = true;
                String[] grades = new String[4];
                
                if (currentIndex >= 30) { // Class size limit check
                    lblDisplay.setText("Class is at capacity");
                    return;
                }

                // Check if first and last name fields are filled
                if (fName.isEmpty() || lName.isEmpty()) {
                    lblDisplay.setText("Error: Student name fields need to be filled");
                    return;
                } else {
                    for (String[] student : Students) {
                        if (student[0] == null || student[1] == null) continue;
                        if (student[0].equals(fName) && student[1].equals(lName)) { // Check if student already exists
                            add = false;
                            lblDisplay.setText("Student already exists");
                            return;
                        }
                    }
                }

                // Validate grades
                for (int i = 0; i < 4; i++) {
                    String grade = fldsGradeInputs[i].getText();
                    if (grade.isEmpty()) {
                        lblDisplay.setText("All 4 grades must be filled for each student");
                        add = false;
                        return;
                    } else {
                        grades[i] = grade;
                    }
                }

                // Add student if validations pass
                if (add) {
                    String[] student = new String[6];
                    student[0] = fName;
                    student[1] = lName;
                    for (int i = 2; i < 6; i++) {
                        student[i] = grades[i - 2];
                    }
                    Students[currentIndex] = student;
                    currentIndex += 1;
                    lblDisplay.setText("Added!");
                }
            }
        });

        // List students button functionality
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List(Students, lblList);
            }
        });

        // Calculate individual student average
        btnSAvg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fName = fldFNInput.getText();
                String lName = fldLNInput.getText();
                double avg = -1.0;
                
                for (String[] student : Students) {
                    if (fName.equals(student[0]) && lName.equals(student[1])) {
                        avg = studentAvg(student);
                        break;
                    }
                }

                lblDisplay.setText(avg > -1 ? 
                    fName + " " + lName + "'s average is " + avg + "%":
                    "Sorry, this student does not exist");
            }
        });

        // Calculate course average
        btnCAvg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double avg = -1;
                
                for (int i = 0; i < currentIndex; i++) {
                    if(avg < 0){
                        avg = 0;
                    }
                    String[] student = Students[i];
                    avg += studentAvg(student);
                }
                
                lblDisplay.setText(avg > -1 ?
                    "The course average is " + Round(avg / currentIndex,1000) + "%":
                    "There are no students in the class!");
            }
        });

        // Remove student functionality
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean found = false; 
                String fName = fldFNInput.getText();
                String lName = fldLNInput.getText();
                
                for (int i = 0; i <= currentIndex; i++) {
                    if (Students[i][0].equals(fName) && Students[i][1].equals(lName) && !found) {
                        found = true;
                        currentIndex -= 1;
                    }
                    if (found) {
                        Students[i] = i < currentIndex ? Students[i + 1] : null; // Shift elements left
                    }
                }
                
                lblDisplay.setText(found ? "Removed!" : "That student does not exist!");
            }
        });

        // Update student grades functionality
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fName = fldFNInput.getText();
                String lName = fldLNInput.getText();
                String[] grades = new String[4];
                
                for (String[] student : Students) {
                    if (student[0].equals(fName) && student[1].equals(lName)) {
                        for (int i = 0; i < 4; i++) {
                            String grade = fldsGradeInputs[i].getText();
                            if (grade.isEmpty()) {
                                lblDisplay.setText("All 4 grades must be filled for the student");
                                return;
                            } else {
                                grades[i] = grade;
                            }
                        }
                        for (int x = 2; x < 6; x++) {
                            student[x] = grades[x - 2];
                        }
                        break;
                    }
                }
            }
        });

        // Exit button functionality
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add panels to JFrame
        add(pnlTitle);
        add(pnlBody);
        add(pnlFooter);

        pack();
        setVisible(true);
    }

    // Helper method to add components to a grid layout
    void addElements(JComponent[][] Elements, JPanel pnl) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        for (int row = 0; row < Elements.length; row++) {
            gbc.gridy = row;
            for (int column = 0; column < Elements[row].length; column++) {
                gbc.gridx = column;
                pnl.add(Elements[row][column], gbc);
            }
        }
    }

    // Validates input as decimal numbers only
    public void addDecimalNumberInputValidation(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                // Allow only one decimal point
                if (k.getKeyChar() == '.') {
                    textField.setEditable(textField.getText().indexOf('.') == -1);
                }
                // Allow numbers and backspace for valid input
                else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9')
                        || (k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true);
                }
                // Disallow other characters
                else {
                    textField.setEditable(false);
                }
            }
        });
    }

    // Method to list students in a JLabel
    void List(String[][] list, JLabel label) {
        StringBuilder display = new StringBuilder(); 
        
        for (int i = 0; i < currentIndex; i++) {
            display.append("Student: ").append(list[i][0]).append(" ").append(list[i][1]).append(": ");
            for (int j = 2; j < 6; j++) {
                display.append("|Test ").append(j - 1).append(": ").append(list[i][j]).append("%");
            }
            display.append("<br>");
        }
        
        label.setText("<html>" + display + "</html>");
        revalidate();
        repaint();
    }

    // Calculate average for a single student
    double studentAvg(String[] student) {
        double avg = 0;
        
        for (int i = 2; i < 6; i++) {
            avg += Double.parseDouble(student[i]);
        }
        
        return Round(avg / 4, 1000);
    }

    // Rounds a number to the nearest factor
    static double Round(double d, int RoundFactor) {
        return Math.rint(d * RoundFactor) / RoundFactor;
    }

    // Validates input as letters only
    public void addLetterInputValidation(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                if ((k.getKeyCode() > 64 && k.getKeyCode() < 91) || (k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true);
                } else {
                    textField.setEditable(false);
                }
            }
        });
    }
}
