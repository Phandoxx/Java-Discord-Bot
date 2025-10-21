package org.javadiscordbot;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    MainFrame(){ //Sets the MainFrame for the GUI
        setTitle("Discord bot GUI");
        setSize(600, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

    }
}
