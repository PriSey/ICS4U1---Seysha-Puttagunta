import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.DoubleBinaryOperator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

public class CustomizerWindow extends JFrame {
    Shape[] shapes = { new Rectangle(), new Triangle(), new Parallelogram(), new Donut(), new Circle(), new Hexagon(),
            new Pentagon() };
    LinkedHashMap<Shape, JSpinner[]> spnInputs = new LinkedHashMap<Shape, JSpinner[]>();

    CustomizerWindow() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        FormattedPanel pnlbody = new FormattedPanel();
        JComponent[][] elements = new JComponent[7][5];
        JLabel lblPlaceHolder = new JLabel();

        for (int i = 0; i < shapes.length; i++) {
            Shape shape = shapes[i];
            elements[i][0] = new JLabel(shape.getName() + ": ");
            System.out.println(shape.getDimensionList().length);
            System.out.println(shape.getDimensionList()[0]);
            if (shape.getDimensionList().length == 2) {
                JSpinner spn = new JSpinner(new SpinnerNumberModel(shape.getDimensionList()[0], 0.01, 10, 0.01));
                JFormattedTextField txt = ((JSpinner.NumberEditor) spn.getEditor()).getTextField(); // Get the text field                                                                                  // from the spinner
                ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input
                spn.addChangeListener(new ChangeListener() {public void stateChanged(ChangeEvent e) {updateDimensions();}});
                JSpinner[] dimList = { spn };
                elements[i][1] = new JLabel("Radius: ");
                elements[i][2] = spn;
                elements[i][3] = lblPlaceHolder;
                elements[i][4] = lblPlaceHolder;

                spnInputs.put(shape, dimList);

            } else if (shape.getDimensionList().length == 3) {
                JSpinner spn = new JSpinner(new SpinnerNumberModel(shape.getDimensionList()[0], 0.01, 10, 0.01));
                JFormattedTextField txt = ((JSpinner.NumberEditor) spn.getEditor()).getTextField(); // Get the text                                                                                   // spinner
                ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input
                JSpinner spn2 = new JSpinner(new SpinnerNumberModel(shape.getDimensionList()[1], 0.01, 10, 0.01));
                txt = ((JSpinner.NumberEditor) spn.getEditor()).getTextField(); // Get the text field // from the                                                                 // spinner
                ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input
                spn.addChangeListener(new ChangeListener() {public void stateChanged(ChangeEvent e) {updateDimensions();}});
                spn2.addChangeListener(new ChangeListener() {public void stateChanged(ChangeEvent e) {updateDimensions();}});
                JSpinner[] dimList = { spn, spn2 };
                elements[i][1] = new JLabel("Width: ");
                elements[i][2] = spn;
                elements[i][3] = new JLabel("Length: ");
                elements[i][4] = spn2;
                spnInputs.put(shape, dimList);

            }
        }

        pnlbody.addElements(elements);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Customize your shape dimensions"), gbc);
        gbc.gridy = 1;
        add(pnlbody, gbc);
        pack();
        setVisible(true);
    }
    public void updateDimensions() {
        for (int i = 0; i < shapes.length; i++) {
            double[] values = new double[spnInputs.get(shapes[i]).length];
            for (int j = 0; j < values.length; j++) {
                values[j] = (double) spnInputs.get(shapes[i])[j].getValue();
                if (shapes[i].getClass().equals(Donut.class)) {
                    if(values[1]>=values[0]){
                        spnInputs.get(shapes[i])[1].setBackground(Color.red);
                        return;
                    } else {
                        spnInputs.get(shapes[i])[1].setBackground(Color.white);
                    }
                }
            }
            shapes[i].setDimensions(values); // Set all dimensions
            shapes[i].setDimList(); // Reflect the updated dimensions
        }
    }
}
