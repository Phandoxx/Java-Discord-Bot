package org.javadiscordbot;
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
        AtomicReference<Wini> ini = new AtomicReference<>(new Wini(new File("files/options/settings.ini")));
        AtomicReference<settings> settings = new AtomicReference<>(new settings("options/settings.ini"));
        String firstBoot = settings.get().get("firstLoad", "false"); // matches the .ini key
        System.out.println(firstBoot);

        String discordToken = settings.get().get("discordToken", "null"); // matches the .ini key
        System.out.println(discordToken);
        String disableSilencing = settings.get().get("disableSilencing", "null"); // matches the .ini key

        String bannedWords = settings.get().get("bannedWords", "null");
        String silencedUsers = settings.get().get("silencedUser", "null");
        String silentMode = settings.get().get("silentMode", "false");

        String silenceAllUsers = settings.get().get("silenceAllUsers", "false");
        String crownedUsers = settings.get().get("crownedUsers", "null");
        String crownedPhrase = settings.get().get("crownedPhrase", "null");
        String crownedMessageResponse = settings.get().get("crownedMessageResponse", "null");

        String terminatorUsers = settings.get().get("terminatorUsers", "null");
        String terminatorUsersPhrase = settings.get().get("terminatorUsersPhrase", "null");
        String terminatorUsersResponse = settings.get().get("terminatorUsersResponse", "null");
        if (firstBoot.equals("true")) {
            setup();
            ini.get().put("general", "firstLoad", "false");
            ini.get().store();
        }
        JPanel topContainer = new JPanel(new BorderLayout());

        MainFrame MainFrame = new MainFrame();
        MainFrame.setLayout(new BorderLayout());

        //Define panels
        JPanel settingsPanel = new JPanel();
        JPanel silentPanel = new JPanel();
        JPanel crownedPanel = new JPanel();
        JPanel terminatorPanel = new JPanel();

        //Define tabbed pane, add pane to GUI
        JTabbedPane tabbedPane = new JTabbedPane();
        MainFrame.add(tabbedPane);


        //Set panel sizes
        settingsPanel.setPreferredSize(new Dimension(500,200));
        crownedPanel.setPreferredSize(new Dimension(500,200));
        terminatorPanel.setPreferredSize(new Dimension(500,200));
        silentPanel.setPreferredSize(new Dimension(500,200));


        //Set panel layouts, and add them to GUI (mainFrame)
        MainFrame.add(settingsPanel, BorderLayout.CENTER);
        MainFrame.add(silentPanel, BorderLayout.SOUTH);
        MainFrame.add(crownedPanel, BorderLayout.EAST);
        MainFrame.add(terminatorPanel, BorderLayout.EAST);


        //Set BoxLayout
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        silentPanel.setLayout(new BoxLayout(silentPanel, BoxLayout.Y_AXIS));
        crownedPanel.setLayout(new BoxLayout(crownedPanel, BoxLayout.Y_AXIS));
        terminatorPanel.setLayout(new BoxLayout(terminatorPanel, BoxLayout.Y_AXIS));

        //Set background colors for each panel (tab)
        settingsPanel.setBackground(new Color(31, 31, 31));
        crownedPanel.setBackground(new Color(31, 31, 31));
        terminatorPanel.setBackground(new Color(31, 31, 31));
        silentPanel.setBackground(new Color(31, 31, 31));



// General / Discord settings
        JTextField discordTokenInput = addSettingField(settingsPanel, "Discord bot token:", discordToken);
        JTextField bannedWordsInput = addSettingField(settingsPanel, "Banned words:", bannedWords);
        JTextField disableSilencingInput = addSettingField(settingsPanel, "Disable Silencing:", disableSilencing);

// Silent user settings
        JTextField silencedUsersInput = addSettingField(silentPanel, "Silenced user:", silencedUsers);
        JTextField silenceAllInput = addSettingField(silentPanel, "Silence all users:", String.valueOf(silenceAllUsers));
        JTextField silentModeInput = addSettingField(silentPanel, "Silent mode:", silentMode);

// Crowned user settings
        JTextField crownedUsersInput = addSettingField(crownedPanel, "Crowned users:", crownedUsers);
        JTextField crownedPhraseInput = addSettingField(crownedPanel, "Crowned phrase:", crownedPhrase);
        JTextField crownedResponseInput = addSettingField(crownedPanel, "Crowned message response:", crownedMessageResponse);

// Terminator user settings
        JTextField terminatorUsersInput = addSettingField(terminatorPanel, "Terminator users:", terminatorUsers);
        JTextField terminatorPhraseInput = addSettingField(terminatorPanel, "Terminator phrase:", terminatorUsersPhrase);
        JTextField terminatorResponseInput = addSettingField(terminatorPanel, "Terminator response:", terminatorUsersResponse);


        //Set the Jlabel for start and stop buttons
        JLabel mainLabel = new JLabel();
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setHorizontalTextPosition(JLabel.CENTER);
        mainLabel.setVerticalTextPosition(JLabel.BOTTOM);
        mainLabel.setIconTextGap(0);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);

        //Start button
        JButton startButton = new JButton();
        startButton.setText("Start");
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setBounds(100, 100, 100, 30);
        silentPanel.add(startButton);

        //Stop button
        JButton stopButton = new JButton();
        stopButton.setText("Close/Stop");
        stopButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        stopButton.setForeground(Color.WHITE);
        stopButton.setBackground(Color.DARK_GRAY);
        stopButton.setBounds(500, 100, 100, 30);
        silentPanel.add(stopButton);

        //Start button functionaility
        startButton.addActionListener(e -> {
            //Retrieve variables from settings.ini
            String enteredToken = discordTokenInput.getText(); // get the text

            String enteredBannedWords = bannedWordsInput.getText();
            System.out.println("banned words: " + enteredBannedWords);

            String enteredDisableSilencing = disableSilencingInput.getText();
            System.out.println("disabled silencing: " + enteredDisableSilencing);

            String enteredSilencedUsers = silencedUsersInput.getText();
            System.out.println("silenced users: " + enteredSilencedUsers);

            String enteredSilentMode = silentModeInput.getText();
            System.out.println("silent mode: " + enteredSilentMode);

            String enteredSilenceAllUsers = silenceAllInput.getText();
            System.out.println("silence all users: " + enteredSilenceAllUsers);

            String enteredCrownedUsers = crownedUsersInput.getText();
            System.out.println("crowned users: " + enteredCrownedUsers);

            String enteredCrownedPhrase = crownedPhraseInput.getText();
            System.out.println("crowned phrase: " + enteredCrownedPhrase);

            String enteredCrownedResponse = crownedResponseInput.getText();
            System.out.println("crowned message response: " + enteredCrownedResponse);

            String enteredTerminatorUsers = terminatorUsersInput.getText();
            System.out.println("terminator users: " + enteredTerminatorUsers);

            String enteredTerminatorPhrase = terminatorPhraseInput.getText();
            System.out.println("terminator phrase: " + enteredTerminatorPhrase);

            String enteredTerminatorResponse = terminatorResponseInput.getText();
            System.out.println("terminator response: " + enteredTerminatorResponse);

            //Save the settings to settings.ini
            saveSetting(ini.get(), "general", "bannedWords", enteredBannedWords);
            saveSetting(ini.get(), "general", "silencedUser", enteredSilencedUsers);
            saveSetting(ini.get(), "general", "disableSilencing", enteredDisableSilencing);
            saveSetting(ini.get(), "general", "silentMode", enteredSilentMode);
            saveSetting(ini.get(), "general", "silenceAllUsers", enteredSilenceAllUsers);
            saveSetting(ini.get(), "general", "crownedUsers", enteredCrownedUsers);
            saveSetting(ini.get(), "general", "crownedPhrase", enteredCrownedPhrase);
            saveSetting(ini.get(), "general", "crownedMessageResponse", enteredCrownedResponse);
            saveSetting(ini.get(), "general", "terminatorUsers", enteredTerminatorUsers);
            saveSetting(ini.get(), "general", "terminatorUsersPhrase", enteredTerminatorPhrase);
            saveSetting(ini.get(), "general", "terminatorUsersResponse", enteredTerminatorResponse);

            System.out.println("Starting discord bot...");
            JOptionPane.showMessageDialog(null, "Starting discord bot!");


            //Reload settings.ini
            try {
                settings.set(new settings("options/settings.ini")); // reload settings object
                System.out.println("Reloaded Main.settings from file");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to reload settings: " + ex.getMessage());
            }


            //Call the discord bot, from DiscordBot.java
            DiscordBot bot = new DiscordBot();
            bot.startDiscordBot(enteredToken);
        });


        //Stop button functionality
        stopButton.addActionListener(e -> {
            System.out.println("Close/Stop button clicked!");
            System.exit(0);
        });

        //Set control pannel
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(51, 51, 51));
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        MainFrame.add(controlPanel, BorderLayout.SOUTH);

        //Add panes (tabs)
        tabbedPane.addTab("General", settingsPanel);
        tabbedPane.addTab("Silent Settings", silentPanel);
        tabbedPane.addTab("Crowned Settings", crownedPanel);
        tabbedPane.addTab("Terminator Settings", terminatorPanel);

        //Add tabbedPanes to the GUI
        MainFrame.add(tabbedPane, BorderLayout.CENTER);
        MainFrame.setVisible(true);


    }
    public static void setup() { //Old function, will remove at some point
            String username = JOptionPane.showInputDialog("Enter your username");
            JOptionPane.showMessageDialog(null, "Welcome " + username);
    }
    public static class settings { //Retrives settings
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
    public class stringSetting { //Splits String into a String array based on the ","

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
    public static void saveSetting(Wini ini, String sectionName, String optionName, String input) { //Function to save settings to settings.ini
        try {
            ini.put(sectionName, optionName, input);
            ini.store();
            System.out.println("Saved " + optionName + " to settings.ini");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to save " + optionName + ": " + ex.getMessage());
        }
    }
    private static JTextField addSettingField(JPanel panel, String labelText, String initialValue) { //Adds the text inputs
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField input = new JTextField(initialValue);
        input.setMaximumSize(new Dimension(400, 25));
        input.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(input);
        panel.add(Box.createVerticalStrut(10));

        return input;
    }
}
