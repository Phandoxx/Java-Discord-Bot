package org.javagui;
import javax.swing.*;
import java.awt.*;

public class Main {
    static boolean firstBoot = false;
    static void main() {
        MyFrame myFrame = new MyFrame();
        setup();

    }
    public static void setup() {
        if (firstBoot) {
            String username = JOptionPane.showInputDialog("Enter your username");
            JOptionPane.showMessageDialog(null, "Welcome " + username);
        }
    }
}
