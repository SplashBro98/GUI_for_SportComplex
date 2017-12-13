package Frames;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FindDialog extends JDialog{
    private JToggleButton button1 = new JToggleButton("Find by key");
    private JToggleButton button2 = new JToggleButton("Find above the key");
    private JToggleButton button3 = new JToggleButton("Find less the key");
    private JRadioButton Rbutton1 = new JRadioButton("Client");
    private JRadioButton Rbutton2 = new JRadioButton("Coach");
    private JRadioButton Rbutton3 = new JRadioButton("Minutes");
    private JTextField jtf = new JTextField(20);
    private ArrayList<String> array = new ArrayList<>();


    public FindDialog(JFrame parent) throws HeadlessException {
        super(parent, "Find Dialog", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setVisible(true);
        setSize(400,400);
        setBackground(Color.BLUE);
        JButton button4 = new JButton("Search");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button1.isSelected())
                    array.add("f");
                if(button2.isSelected())
                    array.add("fr");
                if(button3.isSelected())
                    array.add("fl");
                if(Rbutton1.isSelected())
                    array.add("fc");
                if(Rbutton2.isSelected())
                    array.add("fco");
                if(Rbutton3.isSelected())
                    array.add("m");
                System.out.println(array.size());
                if(array.size() != 3) {
                    dispose();
                }
                try {
                    if(array.get(1).equals("f"))
                        Main.findByKey(array);
                    else if(array.get(1).equals("fr"))
                        Main.findByKey(array,new KeyCompRev());
                    else if(array.get(1).equals("fl"))
                        Main.findByKey(array,new KeyComp());
                    jtf.setText("");
                    array.clear();
                    dispose();
                }
                catch (Exception ex){
                    System.out.println(ex.getClass());
                }

            }
        });
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array.add(jtf.getText());

            }
        });
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(button1);
        bg1.add(button2);
        bg1.add(button3);
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(Rbutton1);
        bg2.add(Rbutton2);
        bg2.add(Rbutton3);
        setLayout(new FlowLayout());
        JPanel jp1 = new JPanel();
        jp1.setBorder(new TitledBorder("Categories"));
        jp1.add(button1);
        jp1.add(button2);
        jp1.add(button3);
        add(jp1);
        JPanel jp2 = new JPanel();
        jp2.setBorder(new TitledBorder("Options"));
        jp2.add(Rbutton1);
        jp2.add(Rbutton2);
        jp2.add(Rbutton3);
        add(jp2);
        add(new JLabel("Enter the text: "));
        add(jtf);
        add(button4);
    }
}
