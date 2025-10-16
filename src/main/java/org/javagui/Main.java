package org.javagui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {
    static boolean firstBoot = false;
    static void main() {
        setup();

        MainFrame MainFrame = new MainFrame();
        MainFrame.setLayout(new BorderLayout());

        JPanel settingsPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        settingsPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setBackground(Color.GRAY);

        settingsPanel.setPreferredSize(new Dimension(100,50));

        ImageIcon image = new ImageIcon("files/logo/amd.png");
        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);


        MainFrame.add(settingsPanel, BorderLayout.NORTH);
        MainFrame.add(mainPanel, BorderLayout.CENTER);
        MainFrame.setSize(750,500);
        MainFrame.setTitle("Java GUI Experiment");
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setResizable(false);

        JLabel mainLabel = new JLabel("AMD logo (it's just a random image I had)", image, JLabel.CENTER);
        mainLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        mainLabel.setForeground(Color.GREEN);
        mainLabel.setHorizontalTextPosition(JLabel.CENTER);
        mainLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mainLabel.setIconTextGap(0);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);

        // Add the label to the panel
        mainPanel.add(mainLabel, BorderLayout.CENTER);



        MainFrame.setVisible(true);
        //myFrame.pack(); //use last

    }
    public static void setup() {
        if (firstBoot) {
            String username = JOptionPane.showInputDialog("Enter your username");
            JOptionPane.showMessageDialog(null, "Welcome " + username);
        }
    }
}
