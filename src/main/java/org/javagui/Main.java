package org.javagui;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Properties;
import org.ini4j.Wini;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {


    //static boolean firstBoot = false;
    static void main() throws IOException {
        Wini ini = new Wini(new File("files/options/settings.ini"));
        AtomicReference<settings> settings = new AtomicReference<>(new settings("options/settings.ini"));
        String firstBoot = settings.get().get("firstLoad", "false"); // matches the .ini key
        System.out.println(firstBoot);
        String discordToken = settings.get().get("discordToken", "null"); // matches the .ini key
        System.out.println(discordToken);

        if (firstBoot.equals("true")) {
            setup();
            ini.put("general", "firstLoad", "false");
            ini.store();
        }


        MainFrame MainFrame = new MainFrame();
        MainFrame.setLayout(new BorderLayout());

        JPanel settingsPanel = new JPanel();
        JPanel mainPanel = new JPanel();

        settingsPanel.setBackground(new Color(31, 31, 31));
        mainPanel.setBackground(new Color(51, 51, 51));

        settingsPanel.setPreferredSize(new Dimension(100,50));

        ImageIcon image = new ImageIcon("files/logo/amd.png");
        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);


        MainFrame.add(settingsPanel, BorderLayout.NORTH);
        MainFrame.add(mainPanel, BorderLayout.CENTER);
        MainFrame.setSize(500,250);
        MainFrame.setTitle("Discord bot GUI");
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setResizable(false);

        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(new Color(31, 31, 31));

        JLabel settingsLabel = new JLabel();
        settingsLabel.setText("Discord bot token:");
        settingsLabel.setForeground(Color.WHITE);
        settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally

        settingsPanel.add(Box.createVerticalStrut(5));

        JTextField discordTokenInput = new JTextField(discordToken);
        discordTokenInput.setMaximumSize(new Dimension(400, 25)); // fixed width
        discordTokenInput.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(settingsLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(discordTokenInput);

        JLabel mainLabel = new JLabel();
        //mainLabel.setText("AMD logo (it's just a random image I had)");
        //mainLabel.setIcon(image);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setHorizontalTextPosition(JLabel.CENTER);
        mainLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mainLabel.setIconTextGap(0);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);

        JButton startButton = new JButton();
        startButton.setText("Start");
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setBounds(100, 100, 100, 30);
        mainPanel.add(startButton);

        JButton stopButton = new JButton();
        stopButton.setText("Close/Stop");
        stopButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        stopButton.setForeground(Color.WHITE);
        stopButton.setBackground(Color.DARK_GRAY);
        stopButton.setBounds(500, 100, 100, 30);
        mainPanel.add(stopButton);

        startButton.addActionListener(e -> {
            String enteredToken = discordTokenInput.getText(); // get the text
            System.out.println("token: "+ enteredToken);
            try {
                ini.put("general", "discordToken", enteredToken);
                ini.store();
                System.out.println("Saved token to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save token: " + ex.getMessage());
            }
            System.out.println("Start button clicked!");
            JOptionPane.showMessageDialog(null, "Starting discord bot!");

            DiscordBot bot = new DiscordBot();
            bot.startDiscordBot(enteredToken);

        });

        stopButton.addActionListener(e -> {
            System.out.println("Close/Stop button clicked!");
            System.exit(0);
        });

        // Add the label to the panel
        mainPanel.add(mainLabel, BorderLayout.CENTER);



        MainFrame.setVisible(true);
        //myFrame.pack(); //use last

    }
    public static void setup() {
            String username = JOptionPane.showInputDialog("Enter your username");
            JOptionPane.showMessageDialog(null, "Welcome " + username);
    }
    public static class settings {
        private static final Properties props = new Properties();
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


        public static String get(String key, String defaultValue) {
            return props.getProperty(key, defaultValue);
        }
    }
    public class stringSetting {

        public static List<String> getAsList(String text) {
            String[] parts = text.split(",");
            List<String> result = new ArrayList<>();
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) result.add(trimmed);
            }
            return result;
        }

        public static String getAsString(String text) {
            List<String> list = getAsList(text);
            return String.join(" ", list);
        }
    }
}
