import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Main class for the Gradient Explorer application
public class U1A6_SeyshaPuttagunta {
    public static void main(String[] args) {
        // Create the main window
        newWindow frmmainWindow = new newWindow(true);
        JScrollPane scrWindow = new JScrollPane(new mainWindow());
        scrWindow.setPreferredSize(new Dimension(500,500)); // Set preferred size for the scroll pane
        frmmainWindow.add(scrWindow); // Add the main window to the frame
        frmmainWindow.setVisible(true); // Make the frame visible
    }
}

// Class representing the main application window
class newWindow extends JFrame {
    public GridBagConstraints gbc = new GridBagConstraints();

    // Constructor to set up the window
    public newWindow(boolean mainWindow) {
        this.setLayout(new GridBagLayout()); // Use GridBagLayout for layout management
        if (mainWindow) {
            this.setSize(new Dimension(500, 500)); // Size for the main window
        } else {
            this.setSize(new Dimension(0, 100)); // Size for secondary windows
        }
        this.gbc.gridx = 0; // Set initial grid position
        this.gbc.gridy = 0;
    }
}

// Class for the main content of the application
class mainWindow extends JPanel {
    ArrayList<Color> clrscolors = new ArrayList<Color>(); // List to store colors
    GridBagConstraints gbc = new GridBagConstraints(); // Layout constraints for components
    ArrayList<Double> lengths = new ArrayList<Double>(); // List to store lengths of colors

    mainWindow() {
        Font fnttitle = new Font("Serif", Font.BOLD, 20); // Set title font
        this.setLayout(new GridBagLayout()); // Use GridBagLayout for layout management

        // Create UI components
        JLabel lblTitle = new JLabel("Gradient explorer");
        JLabel lblDescription = new JLabel("<html><center>Add colors to your gradient and then <br> click generate to see what your gradient looks like!</center></html>");
        JButton btnAddColor = new JButton("Add Color");
        JButton btnGenerateGradient = new JButton("Generate Gradient");
        
        // Set font for the title
        lblTitle.setFont(fnttitle);

        // Action listener for adding colors
        btnAddColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gbc.gridy = gbc.gridy + 1; // Move to the next row
                add(createColorPicker(), gbc); // Add color picker panel
                revalidate(); // Refresh layout
                repaint(); // Redraw components
                System.out.println(clrscolors); // Print current colors to console
            }
        });

        // Action listener for generating gradient
        btnGenerateGradient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newWindow frmGradWindow = new newWindow(false); // Create new gradient window
                // Loop through colors to create gradient panels
                for (int i = 0; i < clrscolors.size() - 1; i++) {
                    GraphicPannel gradient = new GraphicPannel();
                    // Set size and colors for the gradient panel
                    gradient.setPreferredSize(new Dimension((int) Math.round(lengths.get(i) + lengths.get(i + 1)), frmGradWindow.getHeight()));
                    gradient.color1 = clrscolors.get(i);
                    gradient.color2 = clrscolors.get(i + 1);
                    frmGradWindow.add(gradient, frmGradWindow.gbc);
                    frmGradWindow.gbc.gridx = frmGradWindow.gbc.gridx + 1; // Move to the next column
                }
                frmGradWindow.pack(); // Pack the window to fit components
                frmGradWindow.setVisible(true); // Show the gradient window
            }
        });

        // Add components to the main window
        gbc.gridx = 0; gbc.gridy = 0; this.add(lblTitle, gbc);
        gbc.gridy = 1; this.add(lblDescription, gbc);
        gbc.gridy = 2; this.add(btnAddColor, gbc);
        gbc.gridy = 3; this.add(btnGenerateGradient, gbc);
    }

    // Method to create a color picker panel
    public JPanel createColorPicker() {
        JPanel pnlcolor = new JPanel(new GridBagLayout());
        JPanel pnlColorChooser = new JPanel();
        JPanel pnlLengthChooser = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JColorChooser ccrColorChoice = new JColorChooser(); // Color chooser
        JButton btnDoneChoosing = new JButton("SetColor"); // Button to finalize color choice
        JFormattedTextField fldLength = new JFormattedTextField(); // Field for length input
        JLabel lblLetgth = new JLabel("Length (px): "); // Label for length input
        addDecimalNumberInputValidation(fldLength); // Add input validation

        fldLength.setPreferredSize(new Dimension(100, 20)); // Set size for length field

        // Action listener for finalizing color choice
        btnDoneChoosing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!fldLength.getText().isEmpty()) {
                    Color chosenColor = ccrColorChoice.getColor(); // Get chosen color
                    clrscolors.add(chosenColor); // Add color to list
                    double length = Double.parseDouble(fldLength.getText()); // Parse length
                    lengths.add(length); // Add length to list

                    // Create a panel to display chosen color
                    JPanel pnlColorDisplay = new JPanel();
                    JLabel lblColorName = new JLabel("RGB:" + chosenColor.getRed() + "," + chosenColor.getGreen() + "," + chosenColor.getBlue() + " Length: " + length + "px");
                    pnlColorDisplay.setSize(new Dimension(20, 20)); // Set size for color display
                    pnlColorDisplay.setBackground(chosenColor); // Set background color

                    // Update the color picker panel with the chosen color
                    pnlcolor.remove(pnlColorChooser);
                    pnlcolor.remove(btnDoneChoosing);
                    pnlcolor.remove(pnlLengthChooser);
                    gbc.gridx = 0; gbc.gridy = 0; pnlcolor.add(pnlColorDisplay, gbc);
                    gbc.gridx = 1; pnlcolor.add(lblColorName, gbc);
                    pnlcolor.revalidate(); // Refresh layout
                    pnlcolor.repaint(); // Redraw components
                } else {
                    lblLetgth.setText("PLEASE ENTER A VALID SIZE (px): "); // Error message for invalid input
                }
            }
        });

        // Add color chooser to the panel
        pnlColorChooser.add(ccrColorChoice);
        gbc.gridx = 0; gbc.gridy = 0; pnlcolor.add(pnlColorChooser, gbc);
        pnlLengthChooser.add(lblLetgth, gbc);
        gbc.gridx = 1; pnlLengthChooser.add(fldLength, gbc);
        gbc.gridx = 0; gbc.gridy = 1; pnlcolor.add(pnlLengthChooser, gbc);
        gbc.gridx = 0; gbc.gridy = 2; pnlcolor.add(btnDoneChoosing, gbc);
        return pnlcolor; // Return the completed color picker panel
    }

    // Method for validating decimal number input
    public void addDecimalNumberInputValidation(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                // Allow only one decimal point
                if (k.getKeyChar() == '.') {
                    if (textField.getText().indexOf('.') == -1) {
                        textField.setEditable(true);
                    } else {
                        textField.setEditable(false);
                    }
                }
                // Allow numbers and backspace for valid input
                else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9') || (k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true);
                }
                // Disallow other characters
                else {
                    textField.setEditable(false);
                }
            }
        });
    }
}

// Class representing a gradient panel
class GraphicPannel extends JPanel {
    public Color color1; // First color for gradient
    public Color color2; // Second color for gradient

    @Override
    protected void paintComponent(Graphics g) { // Custom paint method
        super.paintComponent(g); // Call superclass method
        Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D for advanced features
        int w = getWidth(); // Get width of panel
        int h = getHeight(); // Get height of panel
        GradientPaint gp = new GradientPaint(0, 0, color1, w, 0, color2); // Create gradient paint
        g2d.setPaint(gp); // Set paint
        g2d.fillRect(0, 0, w, h); // Fill rectangle with gradient
    }
}