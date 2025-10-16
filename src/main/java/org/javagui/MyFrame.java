package org.javagui;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    MyFrame(){
        ImageIcon logo = new ImageIcon("files/logo/logo.jpg");

        this.setTitle("Java GUI Experiment");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(Color.DARK_GRAY);


        this.setVisible(true);
    }
}
