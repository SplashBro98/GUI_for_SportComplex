package Frames;

import javax.swing.*;
import java.awt.*;

public class MyDialog extends JDialog {
    private JLabel label = new JLabel();

    public MyDialog(JFrame parent) throws HeadlessException {
        super(parent,"About author",true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(250,200);
        setLayout(new FlowLayout());
        label.setText("Mazaliuk Ivan, second course, 10 group");
        add(label,BorderLayout.CENTER);

    }
}
