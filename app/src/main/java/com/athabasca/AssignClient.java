package com.athabasca;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AssignClient extends JFrame{
    AssignClient(){
        setTitle("Client Assignement");
        setLayout(new GridBagLayout());
        FormattedPanel panel = new FormattedPanel();
        GridBagUtil constraints = new GridBagUtil(0, 0);
        Dimension dimflds = new Dimension(100,20);

        JTextField idInput = new GeneralInput(Integer.MAX_VALUE,dimflds);
        JTextField assignment = new GeneralInput(Integer.MAX_VALUE,dimflds);

        JComponent[][] elements = {{new JLabel("Rep Email: "), idInput}, {new JLabel("Assignment Email: "), assignment}};
        panel.addElements(elements);
        panel.setBorder(new EmptyBorder(25, 25, 0, 25));
        add(panel, constraints);

        JButton assign = new JButton("Assign");
        constraints.nextY();
        add(assign, constraints);

        assign.addActionListener(new ActionListener(){
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e){
                String id = idInput.getText().replaceAll("\\.", "\\\\");
                String assign = assignment.getText().replaceAll("\\.", "\\\\");
                DatabaseUtil db = new DatabaseUtil();

                if(!(Pattern.matches("^[\\w\\\\%+-]+@[\\w\\\\-]+\\\\[a-zA-Z]{2,6}$", id)&&Pattern.matches("^[\\w\\\\%+-]+@[\\w\\\\-]+\\\\[a-zA-Z]{2,6}$", assign)))
                {
                    JOptionPane.showMessageDialog(null, "Invalid inputs", "Assignement Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                db.readData("employee", a -> {
                    try {
                        Map<String, Map<String, Object>> loadedData = (Map<String, Map<String, Object>>) a;
                        for (Map.Entry<String, Map<String, Object>> entry : loadedData.entrySet()) {
                            if(entry.getKey().equals(id)) {
                                for(Client c : Clients.clients) {
                                    if(c.getEmail().replaceAll("\\.", "\\\\").equals(assign)) {
                                        db.readEmployee(id, data -> {
                                            try {
                                                ArrayList<String> f = data!=null?((ArrayList<String>) data):new ArrayList<>();
                                                f.add(assign);
                                                db.writeData("employee/"+id+"/assigned", f, data2 -> {
                                                    System.out.println("Data written? " + data2);
                                                });
                                            }
                                            catch(Exception b) {
                                                System.out.println(b.getMessage()+"\n"+b.getStackTrace());
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    } catch(Exception b) {
                        System.err.println(b.getMessage());
                    }
                });
                JOptionPane.showMessageDialog(null, "Assigned Client!");
                setVisible(false);
                idInput.setText("");
                assignment.setText("");
            }
        });
        
        pack();
    }
    @Override
    public String toString() {
        return "Assign Client";
    }
}