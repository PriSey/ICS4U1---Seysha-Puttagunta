import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window extends JFrame {
    ArrayList<Integer> fibSeq = new ArrayList<Integer>();
    JTextArea txtOutput = new JTextArea();
    JScrollPane scrOutput = new JScrollPane(txtOutput);

    Window() {
        setLayout(new GridBagLayout());
        GridBagUtlity gbc = new GridBagUtlity(0, 0);
        FormattedPanel pnlBody = new FormattedPanel();
        JLabel[] lblInputs = { new JLabel("Term 1"), new JLabel("Term 2"), new JLabel("Number of terms") };
        IntegerInput[] fldInputs = { new IntegerInput(0, 0, Short.MAX_VALUE), new IntegerInput(0, 0, Short.MAX_VALUE),
                new IntegerInput(1, 1, 40`) };
        JButton btnGetSequence = new JButton("Get sequence");
        JComponent[][] elements = { lblInputs, fldInputs };
        scrOutput.setPreferredSize(new Dimension(300, 300));
        pnlBody.addElements(elements);

        btnGetSequence.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText("");
                int term1 = (int) fldInputs[0].getValue();
                int term2 = (int) fldInputs[1].getValue();
                int numberOfIterations = (int) fldInputs[2].getValue();
                if (term1 >= term2) {
                    txtOutput.setText("Term 2 must be larger than term 1");
                }
                if(numberOfIterations > 3){
                    txtOutput.append(Integer.toString(term1) + "\n");
                    txtOutput.append(Integer.toString(term2) + "\n");
                    txtOutput.append(Integer.toString(term1 + term2) + "\n");
                    getFibonacci2(term1, term2, numberOfIterations-1, txtOutput);
                }
                else if (numberOfIterations > 2){
                    txtOutput.append(Integer.toString(term1) + "\n");
                    txtOutput.append(Integer.toString(term2) + "\n");
                    txtOutput.append(Integer.toString(term1 + term2) + "\n");
                }
                else if(numberOfIterations > 1){
                    txtOutput.append(Integer.toString(term1) + "\n");
                    txtOutput.append(Integer.toString(term2) + "\n");
                }
                else if(numberOfIterations > 0){
                    txtOutput.append(Integer.toString(term1) + "\n");
                }
                
            }

        });

        add(new JLabel("Fibonacci generator"), gbc);
        gbc.nextY();
        add(pnlBody, gbc);
        gbc.nextY();
        add(btnGetSequence, gbc);
        gbc.nextY();
        add(scrOutput, gbc);

        pack();
        setVisible(true);
    }


    public long getFibonacci2(int term1, int term2, int numberOfIterations, JTextArea txtOutput) {
        if (numberOfIterations == 0) {
            return term1;
        } else if (numberOfIterations == 1) {
            return term2;
        } else if (numberOfIterations == 2) {
            return term1 + term2;
        }
        long Fibonacci = getFibonacci2(term1, term2, numberOfIterations - 1, txtOutput)
                + getFibonacci2(term1, term2, numberOfIterations - 2, null)
                + getFibonacci2(term1, term2, numberOfIterations - 3, null);
        if (txtOutput != null){
            txtOutput.append(Long.toString(Fibonacci) + "\n");
        }
        return Fibonacci;
    }
}
