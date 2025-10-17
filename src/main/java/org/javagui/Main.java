package org.javagui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import org.ini4j.Wini;

public class Main {


    //static boolean firstBoot = false;
    static void main() throws IOException {
        Wini ini = new Wini(new File("files/options/settings.ini"));
        settings settings = new settings("options/settings.ini");
        String firstBoot = settings.get("firstLoad", "false"); // matches the .ini key
        System.out.println(firstBoot);

        if (firstBoot.equals("true")) {
            setup();
            ini.put("general", "firstLoad", "false");
            ini.store();
        }


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

        JLabel settingsLabel = new JLabel();
        settingsLabel.setText("Settings:");
        settingsLabel.setForeground(Color.GRAY);
        settingsLabel.setHorizontalAlignment(JLabel.CENTER);
        settingsLabel.setVerticalAlignment(JLabel.TOP);

        JLabel mainLabel = new JLabel();
        mainLabel.setText("AMD logo (it's just a random image I had)");
        mainLabel.setIcon(image);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        mainLabel.setForeground(Color.GREEN);
        mainLabel.setHorizontalTextPosition(JLabel.CENTER);
        mainLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mainLabel.setIconTextGap(0);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);

        // Add the label to the panel
        mainPanel.add(mainLabel, BorderLayout.CENTER);
        settingsPanel.add(settingsLabel, BorderLayout.NORTH);



        MainFrame.setVisible(true);
        //myFrame.pack(); //use last

    }
    public static void setup() {
            String username = JOptionPane.showInputDialog("Enter your username");
            JOptionPane.showMessageDialog(null, "Welcome " + username);
    }
    public static class settings {
        private final Properties props = new Properties();
        public settings(String settingsPath) throws IOException {
            // Try to load from classpath first (inside the JAR)
            InputStream stream = getClass().getResourceAsStream("/" + settingsPath);
            if (stream != null) {
                try (InputStreamReader reader = new InputStreamReader(stream)) {
                    props.load(reader);
                    return;
                }
            }

            // Fallback: load from disk (useful when running in IDE)
            File settingsFile = new File("files/" + settingsPath);
            if (settingsFile.exists()) {
                try (FileReader reader = new FileReader(settingsFile)) {
                    props.load(reader);
                    return;
                }
            }

            throw new FileNotFoundException("settings.ini not found in classpath or disk");
        }

        public String get(String key, String defaultValue) {
            return props.getProperty(key, defaultValue);
        }
    }
}
