package org.javagui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {
    static boolean firstBoot = false;
    static void main() {
        setup();

        ImageIcon image = new ImageIcon("files/logo/amd.png");
        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);

        JLabel label = new JLabel();
        label.setText("AMD logo (its just a random image i had)");
        label.setIcon(image);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setFont(new Font("Times New Roman", Font.BOLD, 16));
        label.setForeground(Color.GREEN);
        label.setIconTextGap(0);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        //label.setBounds(0, 0, 500, 500);

        MyFrame myFrame = new MyFrame();

        //myFrame.setLayout(null);
        myFrame.setVisible(true);
        myFrame.add(label);

        myFrame.pack(); //use last

    }
    public static void setup() {
        if (firstBoot) {
            String username = JOptionPane.showInputDialog("Enter your username");
            JOptionPane.showMessageDialog(null, "Welcome " + username);
        }
    }
}
