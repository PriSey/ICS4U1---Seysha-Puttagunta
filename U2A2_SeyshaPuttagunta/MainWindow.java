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

public class MainWindow extends JFrame {
    Shape[] shapes = { new Rectangle(), new Triangle(), new Parallelogram(), new Donut(), new Circle(), new Hexagon(),
            new Pentagon() };
    Shape[] usedShapes = new Shape[8];
    int usedShapeNum = 0;
    LinkedHashMap<String, JSpinner> spnTileNumbers = new LinkedHashMap<String, JSpinner>();
    MainWindow() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblPlaceHolder = new JLabel();
        JPanel pnlTitle = new JPanel();
        FormattedPanel pnlCheckboxes = new FormattedPanel();
        FormattedPanel pnlCustomizer = new FormattedPanel();
        JTextArea txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        JScrollPane scrOutput = new JScrollPane(txtOutput);
        scrOutput.setPreferredSize(new Dimension(700, 200));
        getContentPane().add(Box.createVerticalGlue());
        JTextField fldArea = new JTextField();
        addDecimalNumberInputValidation(fldArea);
        fldArea.setPreferredSize(new Dimension(200, 20));
        JLabel lblArea = new JLabel("Enter total area of floor (m^2)");
        JLabel lblTitle = new JLabel("Room Tile Calculator");
        JComponent[] btns = { new JButton("Cost"), new JButton("Tile Details"), lblPlaceHolder,
                new JButton("Clear"), new JButton("Exit"), lblPlaceHolder, lblPlaceHolder };
        pnlTitle.add(lblTitle);
        ShapeBox[] cbxShapes = new ShapeBox[7];
        for (int i = 0; i < shapes.length; i++) {
            ShapeBox cbxShape = new ShapeBox(shapes[i].name, shapes[i]);
            cbxShape.addChangeListener(new ChangeListener() {
// This method is responsible for stateChanged.
                public void stateChanged(ChangeEvent e) {
                    updateUsed(cbxShapes);
                }
            });
            cbxShapes[i] = cbxShape;
        }
        JComponent[][] cbxElements = new JComponent[9][3];
        JComponent[] firstRow = { lblPlaceHolder, lblPlaceHolder, lblPlaceHolder };
        JComponent[] secondRow = { lblArea, fldArea, lblPlaceHolder };
        cbxElements[0] = firstRow;
        cbxElements[1] = secondRow;
        for (Shape i : shapes) {
            JSpinner spn = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
            JFormattedTextField txt = ((JSpinner.NumberEditor) spn.getEditor()).getTextField(); // Get the text field                                                                                  // from the spinner
            ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false); // Prevent invalid input
            spn.setVisible(false);
            spnTileNumbers.put(i.getName(), spn);
        }

        for (int i = 0; i < cbxShapes.length; i++) {
            cbxElements[i + 2][0] = cbxShapes[i];
            cbxElements[i + 2][1] = (JSpinner) spnTileNumbers.values().toArray()[i];
            cbxElements[i + 2][2] = btns[i];

        }

        ((JButton) btns[1]).addActionListener(new ActionListener() {
// This method is responsible for actionPerformed.
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText("");
                for (Shape shape : shapes) {
                    txtOutput.append(shape.getName() + ": " + shape.toString() + "| Price(per unit): $"
                            + shape.getUnitPrice() + "\n");
                }

            }

        });
        ((JButton) btns[0]).addActionListener(new ActionListener() {
// This method is responsible for actionPerformed.
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText("");
                double area;
                double cost = 0;
                double calculatedArea = 0;
                double largestShapeNum = 0;
                Shape largestShape = new Rectangle();
                HashMap<Shape, Integer> shapeNumbers = new HashMap<Shape, Integer>();
                try {
                    if (Double.valueOf(fldArea.getText()) < 5) {
                        txtOutput.setText("Room cannot be smaller than 5 m^2");
                        return;
                    } else {
                        area = Double.valueOf(fldArea.getText());
                    }
                } catch (java.lang.NumberFormatException a) {
                    txtOutput.setText("Area field needs to be filled");
                    return;
                }
                if (usedShapeNum == 0) {
                    txtOutput.setText("No Tiles selected");
                    return;
                }
                for (int i = 0; i < usedShapeNum; i++) {
                    Shape current = usedShapes[i];
                    shapeNumbers.put(current, (int) spnTileNumbers.get(current.name).getValue());
                }
                for (int i = 0; i < shapeNumbers.size(); i++) {
                    Shape currentShape = (Shape) shapeNumbers.keySet().toArray()[i];
                    double currentShapeArea = currentShape.getArea() * shapeNumbers.get(currentShape);
                    if (shapeNumbers.get(currentShape) > largestShapeNum) {
                        largestShapeNum = shapeNumbers.get(currentShape);
                        largestShape = currentShape;
                    }
                    cost = cost + currentShape.getUnitPrice() * shapeNumbers.get(currentShape);
                    calculatedArea = calculatedArea + currentShapeArea;
                }
                cost = (Math.rint(cost * 100)/100);
                calculatedArea = (Math.rint(calculatedArea * 100)/100);
                txtOutput.setText("Total cost: $" + cost + "\n");
                txtOutput.append("You are currently buying: " + calculatedArea + "m^2 of tile \n");
                double areaDisc = (calculatedArea - (largestShape.getArea() * 5)) - area;
                int tileNum = (int) Math.floor(areaDisc / largestShape.getArea());
                if (tileNum > 0) {
                    if(tileNum <= largestShapeNum){
                        txtOutput.append("You have too many tiles! Please consider removing " + tileNum + " "
                        + largestShape.name + " tiles (Leaving you 5 extra)");
                    }
                    else{
                        txtOutput.append("You have too many tiles! Please consider removing " + (tileNum - largestShapeNum - 5) + " "
                        + largestShape.name + " tiles (Leaving you 5 extra)");
                    }
                } else if (tileNum < 0){
                    txtOutput.append("You have too few tiles! Please consider adding " + -tileNum + " "
                        + largestShape.name + " tiles (Leaving you 5 extra)");
                }

            }

        });
        
        ((JButton) btns[3]).addActionListener(new ActionListener() {
// This method is responsible for actionPerformed.
            public void actionPerformed(ActionEvent e) {
                txtOutput.setText("");
                for (int i = 0; i < spnTileNumbers.size(); i++){
                    ((JSpinner)spnTileNumbers.values().toArray()[i]).setValue(1);
                }
                fldArea.setText("");
            }
            
        });
        ((JButton) btns[4]).addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {dispose();}});
        getContentPane().add(Box.createVerticalGlue());
        pnlCheckboxes.setAlignmentX(LEFT_ALIGNMENT);
        pnlCheckboxes.addElements(cbxElements);
        gbc.gridy = 0;
        gbc.gridx = 0;

        add(lblTitle, gbc);
        gbc.gridy = 1;
        add(pnlCheckboxes, gbc);
        gbc.gridy = 2;
        add(scrOutput, gbc);
        pack();
        setVisible(true);
    }

// This method is responsible for updateUsed.
    private void updateUsed(ShapeBox[] cbxShapes) {
        usedShapeNum = 0;
        for (ShapeBox cbxShape : cbxShapes) {
            if (cbxShape.isSelected()) {
                spnTileNumbers.get(cbxShape.getShape().name).setVisible(true);
                usedShapes[usedShapeNum] = cbxShape.getShape();
                usedShapeNum++;
            } else {
                spnTileNumbers.get(cbxShape.getShape().name).setVisible(false);
            }
        }
        for (int a = usedShapeNum; a < 7; a++) {
            usedShapes[a] = null;
        }
        pack();
        revalidate();
        repaint();
    }

// This method is responsible for addDecimalNumberInputValidation.
    public void addDecimalNumberInputValidation(JTextField textField) {
        // Adding a key listener to the JTextField to capture key presses and validate
        // input
        textField.addKeyListener(new KeyAdapter() {
            @Override
// This method is responsible for keyPressed.
            public void keyPressed(KeyEvent k) {
                // Allow backspace to delete the last character
                if ((k.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    textField.setEditable(true); // Allow backspace to remove characters
                } else {
                    try {
                        // Try to parse the current text plus the new key pressed as a double (decimal
                        // number)
                        double futureValue = Double.parseDouble(textField.getText() + String.valueOf(k.getKeyChar()));

                        if (futureValue < 1000) { // Limit input to values less than 30 million
                            // Allow decimal point if there isn't already one in the text field
                            if (k.getKeyChar() == '.') {
                                textField.setEditable(textField.getText().indexOf('.') == -1);
                            }
                            // Allow only digits and backspace for valid numeric input
                            else if ((k.getKeyChar() >= '0' && k.getKeyChar() <= '9')) {
                                textField.setEditable(true); // Allow numbers
                            } else {
                                textField.setEditable(false); // Prevent invalid characters
                            }
                            System.out.println(textField.getText().indexOf('.') == -1);
                            String nextValue = textField.getText() + String.valueOf(k.getKeyChar());
                            if ((nextValue.split("\\.").length > 1)) {
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
}
