package org.javagui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    MainFrame(){
        setTitle("Discord bot GUI");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

    }
}
