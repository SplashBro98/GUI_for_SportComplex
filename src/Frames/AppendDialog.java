package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AppendDialog extends JDialog {
    private static JLabel label1 = new JLabel("Client name: ");
    private static JLabel label2 = new JLabel("Client surname: ");
    private static JLabel label3 = new JLabel("Coach name: ");
    private static JLabel label4 = new JLabel("Coach surname: ");
    private static JLabel label5 = new JLabel("Sport type: ");
    private static JLabel label6 = new JLabel("Minutes: ");
    private static JLabel label7 = new JLabel("Tarif: ");

    private static JTextField field1 = new JTextField(5);
    private static JTextField field2 = new JTextField(5);
    private static JTextField field3 = new JTextField(5);
    private static JTextField field4 = new JTextField(5);
    private static JTextField field5 = new JTextField(5);
    private static JTextField field6 = new JTextField(5);
    private static JTextField field7 = new JTextField(5);
    private static ArrayList<String> array = new ArrayList<>();

    private static JButton button1 = new JButton("Save");

    public AppendDialog(JFrame parent) throws HeadlessException {
        super(parent, "Append Dialog", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400,400);
        setBackground(Color.GREEN);
        field1.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                array.add(field1.getText());
            }
        });
        field2.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                array.add(field2.getText());
            }
        });
        field3.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                array.add(field3.getText());
            }
        });
        field4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array.add(field4.getText());
            }
        });
        field5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array.add(field5.getText());
            }
        });
        field6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array.add(field6.getText());
            }
        });


        button1.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                try {
                    //System.out.println(array.size());
                    if(array.size() != 6){
                        JOptionPane.showMessageDialog(null,"Something Wrong!!!","Error",JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                    else {
                        Main.appendFile(false, array);
                        array.clear();
                        field1.setText("");
                        field2.setText("");
                        field3.setText("");
                        field4.setText("");
                        field5.setText("");
                        field6.setText("");

                        JOptionPane.showMessageDialog(null, "ALL Complete", "Message", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                }
                catch (Exception ex){
                    System.out.println(ex.getClass());
                }
            }
        });
        setLayout(new GridLayout(14,1));
        add(label1);
        add(field1);
        add(label2);
        add(field2);
        add(label3);
        add(field3);
        add(label4);
        add(field4);
        add(label5);
        add(field5);
        add(label6);
        add(field6);
        add(button1);
    }
}
