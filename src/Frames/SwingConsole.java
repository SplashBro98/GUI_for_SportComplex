package Frames;

import javax.swing.*;
import java.awt.*;

public class SwingConsole {
    public static void run(final JFrame frame, int w, int h){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setSize(w,h);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setTitle(frame.getName());
                frame.setBackground(Color.GREEN);
            }
        });


    }
}
