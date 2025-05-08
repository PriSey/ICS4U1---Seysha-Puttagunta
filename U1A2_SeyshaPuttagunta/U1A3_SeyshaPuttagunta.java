import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;

// Main class to execute the application
public class U1A3_SeyshaPuttagunta{
    public static void main(String[] args) {
        Window app = new Window(); // Create a new Window object to display the GUI
    }
}

// Window class which extends JFrame to create the GUI window
class Window extends JFrame{
    Window(){
        // Set the layout of the window to GridBagLayout for flexible component arrangement
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbcWindow = new GridBagConstraints();

        // Title Panel creation and adding the label to the title
        GraphicPannel pnlTitle = new GraphicPannel(); // Create panel to hold the title
        pnlTitle.color1 = new Color(252,241,171);
        pnlTitle.color2 = new Color(106,211,74);
        JLabel lblTitle = new JLabel(" Seysha's Change Exchange"); // Create label for the title text
        lblTitle.setFont(new Font("Serif",Font.BOLD,20)); // Set font for the title text

        pnlTitle.setAlignmentX(CENTER_ALIGNMENT); // Align title panel
        pnlTitle.add(lblTitle); // Add the title label to the title panel


        
        // Panel containing the user input section
        JPanel pnlEnterCurrency = new JPanel(new GridBagLayout()); // Panel for input
        GridBagConstraints gbcEnterCurrency = new GridBagConstraints();
        
        // Input field for entering money amount
        JTextField fldMoney = new JTextField();
        JLabel lblMoney = new JLabel("Amount:");
        lblMoney.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the label
        JTextField fldMoneyOwed = new JTextField();
        JLabel lblMoneyOwed = new JLabel("Amount Owed:");
        lblMoneyOwed.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the label
        JLabel lblAmount = new JLabel("Amount (xxxx.xx)"); // Label to show the format
        JButton btnCalculate = new JButton("Calculate"); // Button for calculating change
        lblMoney.setAlignmentX(RIGHT_ALIGNMENT);
        fldMoney.setPreferredSize(new Dimension(100,20)); // Set size for the input field
        fldMoneyOwed.setPreferredSize(new Dimension(100,20)); // Set size for the input field
        JTextField fldChange = new JTextField();
        JLabel lblChange = new JLabel("Change: ");
        fldChange.setPreferredSize(new Dimension(100,20)); // Set size for the input field
        fldChange.setEditable(false);
        lblChange.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the label


        // Adding components to the user input panel using GridBagConstraints
        JLabel[] labels = {lblMoney, lblMoneyOwed, lblAmount, lblChange};
        JComponent[] components = {fldMoney, fldMoneyOwed, btnCalculate, fldChange};

        // Set up GridBagLayout

        gbcEnterCurrency.fill = GridBagConstraints.HORIZONTAL;

        // Loop to add components to the panel
        for (int i = 0; i < labels.length; i++) {
            // Add label in the first column (gridx = 0)
            gbcEnterCurrency.gridx = 0;
            gbcEnterCurrency.gridy = i;
            pnlEnterCurrency.add(labels[i], gbcEnterCurrency);

            // Add corresponding field or button in the second column (gridx = 1)
            gbcEnterCurrency.gridx = 1;
            pnlEnterCurrency.add(components[i], gbcEnterCurrency);
        }

        // Panel containing the calculated coin values
        JPanel pnlCalculatedCurrency = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCalculatedCurrency = new GridBagConstraints();
        pnlCalculatedCurrency.setAlignmentX(CENTER_ALIGNMENT); // Align center
        pnlCalculatedCurrency.setBorder(new EmptyBorder(10,10,10,10)); // Add padding around the panel

        Dimension dimCoinFld = new Dimension(30,20); // Set dimension for the coin fields

        // Labels and fields for displaying different coin values (Toonies, Loonies, etc.)
        String[] currencyLabels = {"$100 Bill:","$50 Bill:","$20 Bill","$10 Bill:","$5 Bill:","Toonies", "Loonies", "Quarters", "Dimes", "Nickles"};
        JLabel[] lblCurrency = new JLabel[currencyLabels.length];
        JTextField[] fldCurrency = new JTextField[currencyLabels.length];

        for (int i = 0; i < currencyLabels.length; i++) {
            lblCurrency[i] = new JLabel(currencyLabels[i] + ": ");
            fldCurrency[i] = new JTextField();
        }
        // Create a list of coin fields for easy access and the corresponding coin values
        double[] coinValues = {100.0,50.0,20.0,10.0,5.0,2.0, 1.0, 0.25, 0.10, 0.05}; // Coin values in dollars
        
        // Loop to set properties for each coin field (not editable, size)
        for (int i = 0; i < fldCurrency.length; i++){
            fldCurrency[i].setEditable(false);
            fldCurrency[i].setPreferredSize(dimCoinFld);
        }

        addDecimalNumberInputValidation(fldMoneyOwed);
        addDecimalNumberInputValidation(fldMoney);

        // ActionListener for the Calculate button, calculates the change in coins
        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Parse the entered amount of money from the input field
                double moneyGiven = Double.parseDouble(fldMoney.getText());
                double moneyOwed = Double.parseDouble(fldMoneyOwed.getText());

                double moneyAmount = moneyGiven - moneyOwed;

                moneyAmount = Round(moneyAmount, 20);
                if (moneyAmount < 0 ){
                    moneyAmount = 0;
                }
                fldChange.setText(String.format("%.2f",moneyAmount));
                // Loop through each coin type and calcul1ate how many of each are needed
                for (int i = 0; i < fldCurrency.length; i++){
                    moneyAmount = Round(moneyAmount,100); // Round the amount to 2 decimal places                    
                    // Calculate the number of coins for the current denomination
                    double leftOver = moneyAmount % coinValues[i];
                    int coinNum = (int) ((moneyAmount - leftOver) / coinValues[i]);
                    
                    // Set the calculated number of coins in the corresponding text field
                    fldCurrency[i].setText(Integer.toString(coinNum));
                    
                    // Update moneyAmount to the leftover after deducting the coins
                    moneyAmount = leftOver;
                }
            }
        });

        // Labels and fields for the currency

        // Set the GridBagLayout
        gbcCalculatedCurrency.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < lblCurrency.length; i++) {
            // Set gridx: 0, 2, 4 (for labels in each row)
            gbcCalculatedCurrency.gridx = (i % 3) * 2;  // Multiplied by 2 to leave space for text fields
            
            // Set gridy: Increase by 1 every 3 elements (to move to the next row)
            gbcCalculatedCurrency.gridy = i / 3;
        
            // Add label to the panel
            pnlCalculatedCurrency.add(lblCurrency[i], gbcCalculatedCurrency);
        
            // Set gridx for the text field: 1, 3, 5 (aligned with corresponding labels)
            gbcCalculatedCurrency.gridx++;  // Move to the next grid column for the text field
        
            // Add text field to the panel
            pnlCalculatedCurrency.add(fldCurrency[i], gbcCalculatedCurrency);
        }
                // Adding all panels (title, user input, and calculated currency) to the main window
                gbcWindow.gridx = 0;
                gbcWindow.gridy = 0;
                this.add(pnlTitle, gbcWindow);
                gbcWindow.gridy = 1;
                this.add(pnlEnterCurrency, gbcWindow);
                gbcWindow.gridy = 2;
                this.add(pnlCalculatedCurrency, gbcWindow);

                // Pack the components and make the window visible

                this.pack();
                this.setVisible(true);


                
            }

    // Method to round a double to two decimal places
    static double Round(double d, int RoundFactor){
        double number = Math.rint(d * RoundFactor);
        number = number / EFactor;
        return number;
    }
    public void addDecimalNumberInputValidation(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() { 
            @Override
            public void keyPressed(KeyEvent k) {
                // Allow only one decimal point
                if(k.getKeyChar() == '.') {
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

class GraphicPannel extends JPanel{
    public Color color1;
    public Color color2;
    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, color1, w ,h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
