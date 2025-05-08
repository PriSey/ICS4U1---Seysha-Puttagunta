import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

class changeExchange extends JFrame implements ActionListener {

    // Elements for the GUI
    static JFrame frame;
    static JLabel cChange, amtN, entCurrency, hunBill, fifBill, tenBill, fiveBill, toon, loon, quart, dime, nic, status;
    static JTextField amtEnt, customerChange, amountNeeded;
    static JButton calc;
    private static changeExchange act;

    changeExchange() {}

    public static void main(String[] args) {
        new changeExchange();
        changeExchange.act = new changeExchange();
        JPanel pan = new JPanel(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        frame = new JFrame("Change Exchange");

        // Attribute the elements
        amtN = new JLabel("Amount Needed");
        constraint.gridy = 0;
        constraint.gridx = 0;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(5, 5, 5, 5);  // Add padding
        pan.add(amtN, constraint);

        amountNeeded = new JTextField();
        amountNeeded.setPreferredSize(new Dimension(100, 25));
        constraint.gridy = 0;
        constraint.gridx = 1;
        pan.add(amountNeeded, constraint);

        entCurrency = new JLabel("Enter Currency");
        constraint.gridy = 1;
        constraint.gridx = 0;
        pan.add(entCurrency, constraint);

        amtEnt = new JTextField();
        amtEnt.setPreferredSize(new Dimension(100, 25));
        constraint.gridy = 1;
        constraint.gridx = 1;
        pan.add(amtEnt, constraint);

        calc = new JButton("Calculate");
        constraint.gridy = 2;
        constraint.gridx = 1;
        calc.addActionListener(act);
        pan.add(calc, constraint);

        cChange = new JLabel("Customer change: ");
        constraint.gridy = 3;
        constraint.gridx = 0;
        constraint.gridwidth = 1;
        pan.add(cChange, constraint);

        customerChange = new JTextField();
        customerChange.setPreferredSize(new Dimension(100, 25));
        constraint.gridy = 3;
        constraint.gridx = 1;
        pan.add(customerChange, constraint);

        // Adding labels for bills and coins, using grid positioning
        hunBill = new JLabel("100 Dollar Bills: ");
        constraint.gridy = 4;
        constraint.gridx = 0;
        pan.add(hunBill, constraint);

        fifBill = new JLabel("50 Dollar Bills: ");
        constraint.gridy = 4;
        constraint.gridx = 1;
        pan.add(fifBill, constraint);

        tenBill = new JLabel("10 Dollar Bills: ");
        constraint.gridy = 5;
        constraint.gridx = 0;
        pan.add(tenBill, constraint);

        fiveBill = new JLabel("5 Dollar Bills: ");
        constraint.gridy = 5;
        constraint.gridx = 1;
        pan.add(fiveBill, constraint);

        toon = new JLabel("Toonies: ");
        constraint.gridy = 6;
        constraint.gridx = 0;
        pan.add(toon, constraint);

        loon = new JLabel("Loonies: ");
        constraint.gridy = 6;
        constraint.gridx = 1;
        pan.add(loon, constraint);

        quart = new JLabel("Quarters: ");
        constraint.gridy = 7;
        constraint.gridx = 0;
        pan.add(quart, constraint);

        dime = new JLabel("Dimes: ");
        constraint.gridy = 7;
        constraint.gridx = 1;
        pan.add(dime, constraint);

        nic = new JLabel("Nickels: ");
        constraint.gridy = 8;
        constraint.gridx = 0;
        pan.add(nic, constraint);


        status = new JLabel("User Warning: only enter 2 numbers after the decimal.");
        constraint.gridy = 9;
        constraint.gridx = 0;
        constraint.gridwidth = 2;
        pan.add(status, constraint);

        // Final frame setup
        frame.add(pan);
        frame.setSize(400, 400);  // Adjusted size to fit window
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // close program once you close everything
    }

    public void actionPerformed(ActionEvent e) {
        // Define the values of each bill and coin as a big decimal so the rest of the program can use them
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal fifty = new BigDecimal("50");
        BigDecimal ten = new BigDecimal("10");
        BigDecimal five = new BigDecimal("5");
        BigDecimal toonie = new BigDecimal("2");
        BigDecimal loonie = new BigDecimal("1");
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dimes = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");
    
        // On Calculate button click
        String s = e.getActionCommand();
        if (s.equals("Calculate")) {
            // Read the entered and needed amounts, use bug decimal to make things eaasier to manipulate

            //get the amounts inputted and round the decimals to the right values
            BigDecimal amtEntered = new BigDecimal(amtEnt.getText()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal needed = new BigDecimal(amountNeeded.getText()).setScale(2, RoundingMode.HALF_UP);

            // calculates the change from the newly rounded inputs that were taken
            BigDecimal change = amtEntered.subtract(needed);
    
            // Round change to the nearest nickel
            BigDecimal rounded = change.multiply(new BigDecimal("20")).setScale(0, RoundingMode.HALF_UP)
                    //will multiply a nickel by 20 to shift the decimal to the right
                    .divide(new BigDecimal("20"), 2, RoundingMode.HALF_UP);
    
            // Handle negative change scenarios
            if (change.compareTo(BigDecimal.ZERO) < 0) {
                rounded = change;  // No rounding for negative values
            }
    
            // Calculate the total for each bill and coin denomination
            BigDecimal remainder = rounded;
            
            /*
             * HOW THE MATH WORKS: 
             * after converting the rounded change into a big decimal it divides remainder by the bill value
             * then it makes sure there are no decimal places and rounds down
             * the remainder variable then holds the value of the remainder of the calculation 
             * so the same calculation can be done again for each type of money
             * 
            */
            
            int totalHundred = remainder.divide(hundred, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(hundred);
    
            int totalFifty = remainder.divide(fifty, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(fifty);
    
            int totalTen = remainder.divide(ten, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(ten);
    
            int totalFive = remainder.divide(five, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(five);
    
            int totalToonies = remainder.divide(toonie, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(toonie);
    
            int totalLoonies = remainder.divide(loonie, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(loonie);
    
            int totalQuarters = remainder.divide(quarter, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(quarter);
    
            int totalDimes = remainder.divide(dimes, 0, RoundingMode.DOWN).intValue();
            remainder = remainder.remainder(dimes);
    
            int totalNickels = remainder.divide(nickel, 0, RoundingMode.DOWN).intValue();
    
            // Display the change, bills, and coins used
            customerChange.setText("" + rounded);
            hunBill.setText("100 Dollar Bills: " + totalHundred);
            fifBill.setText("50 Dollar Bills: " + totalFifty);
            tenBill.setText("10 Dollar Bills: " + totalTen);
            fiveBill.setText("5 Dollar Bills: " + totalFive);
            toon.setText("Toonies: " + totalToonies);
            loon.setText("Loonies: " + totalLoonies);
            quart.setText("Quarters: " + totalQuarters);
            dime.setText("Dimes: " + totalDimes);
            nic.setText("Nickels: " + totalNickels);
        }
    }
}