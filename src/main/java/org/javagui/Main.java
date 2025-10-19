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
        AtomicReference<Wini> ini = new AtomicReference<>(new Wini(new File("files/options/settings.ini")));
        AtomicReference<settings> settings = new AtomicReference<>(new settings("options/settings.ini"));
        String firstBoot = settings.get().get("firstLoad", "false"); // matches the .ini key
        System.out.println(firstBoot);

        String discordToken = settings.get().get("discordToken", "null"); // matches the .ini key
        System.out.println(discordToken);

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

        JPanel settingsPanel = new JPanel();
        JPanel silentPanel = new JPanel();
        JPanel secondSettingsPanel = new JPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        MainFrame.add(tabbedPane);

        settingsPanel.setBackground(new Color(31, 31, 31));
        silentPanel.setBackground(new Color(51, 51, 51));

        settingsPanel.setPreferredSize(new Dimension(500,550));

        secondSettingsPanel.setPreferredSize(new Dimension(500,550));

        ImageIcon image = new ImageIcon("files/logo/amd.png");
        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);


        MainFrame.add(settingsPanel, BorderLayout.CENTER);
        MainFrame.add(silentPanel, BorderLayout.SOUTH);
        MainFrame.add(secondSettingsPanel, BorderLayout.EAST);


        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(new Color(41, 41, 41));
        secondSettingsPanel.setBackground(new Color(31, 31, 31));

// ===== DISCORD TOKEN =====
        JLabel settingsLabel = new JLabel("Discord bot token:");
        settingsLabel.setForeground(Color.WHITE);
        settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField discordTokenInput = new JTextField(discordToken);
        discordTokenInput.setMaximumSize(new Dimension(400, 25));
        discordTokenInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(settingsLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(discordTokenInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== BANNED WORDS =====
        JLabel bannedWordsLabel = new JLabel("Banned words:");
        bannedWordsLabel.setForeground(Color.WHITE);
        bannedWordsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField bannedWordsInput = new JTextField(bannedWords);
        bannedWordsInput.setMaximumSize(new Dimension(400, 25));
        bannedWordsInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(bannedWordsLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(bannedWordsInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== SILENCED USER =====
        JLabel silencedUsersLabel = new JLabel("Silenced user:");
        silencedUsersLabel.setForeground(Color.WHITE);
        silencedUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField silencedUsersInput = new JTextField(silencedUsers);
        silencedUsersInput.setMaximumSize(new Dimension(400, 25));
        silencedUsersInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(silencedUsersLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(silencedUsersInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== SILENCE ALL USERS =====
        JLabel silenceAllLabel = new JLabel("Silence all users:");
        silenceAllLabel.setForeground(Color.WHITE);
        silenceAllLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField silenceAllInput = new JTextField(String.valueOf(silenceAllUsers));
        silenceAllInput.setMaximumSize(new Dimension(400, 25));
        silenceAllInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(silenceAllLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(silenceAllInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== CROWNED USERS =====
        JLabel crownedUsersLabel = new JLabel("Crowned users:");
        crownedUsersLabel.setForeground(Color.WHITE);
        crownedUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField crownedUsersInput = new JTextField(crownedUsers);
        crownedUsersInput.setMaximumSize(new Dimension(400, 25));
        crownedUsersInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(crownedUsersLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(crownedUsersInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== CROWNED PHRASE =====
        JLabel crownedPhraseLabel = new JLabel("Crowned phrase:");
        crownedPhraseLabel.setForeground(Color.WHITE);
        crownedPhraseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField crownedPhraseInput = new JTextField(crownedPhrase);
        crownedPhraseInput.setMaximumSize(new Dimension(400, 25));
        crownedPhraseInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(crownedPhraseLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(crownedPhraseInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== CROWNED MESSAGE RESPONSE =====
        JLabel crownedResponseLabel = new JLabel("Crowned message response:");
        crownedResponseLabel.setForeground(Color.WHITE);
        crownedResponseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField crownedResponseInput = new JTextField(crownedMessageResponse);
        crownedResponseInput.setMaximumSize(new Dimension(400, 25));
        crownedResponseInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(crownedResponseLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(crownedResponseInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== TERMINATOR USERS =====
        JLabel terminatorUsersLabel = new JLabel("Terminator users:");
        terminatorUsersLabel.setForeground(Color.WHITE);
        terminatorUsersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField terminatorUsersInput = new JTextField(terminatorUsers);
        terminatorUsersInput.setMaximumSize(new Dimension(400, 25));
        terminatorUsersInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(terminatorUsersLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(terminatorUsersInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== TERMINATOR PHRASE =====
        JLabel terminatorPhraseLabel = new JLabel("Terminator phrase:");
        terminatorPhraseLabel.setForeground(Color.WHITE);
        terminatorPhraseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField terminatorPhraseInput = new JTextField(terminatorUsersPhrase);
        terminatorPhraseInput.setMaximumSize(new Dimension(400, 25));
        terminatorPhraseInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(terminatorPhraseLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(terminatorPhraseInput);
        settingsPanel.add(Box.createVerticalStrut(10));

// ===== TERMINATOR RESPONSE =====
        JLabel terminatorResponseLabel = new JLabel("Terminator response:");
        terminatorResponseLabel.setForeground(Color.WHITE);
        terminatorResponseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField terminatorResponseInput = new JTextField(terminatorUsersResponse);
        terminatorResponseInput.setMaximumSize(new Dimension(400, 25));
        terminatorResponseInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(terminatorResponseLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(terminatorResponseInput);
        settingsPanel.add(Box.createVerticalStrut(10));

        // Silent Mode
        JLabel silentModeLabel = new JLabel("Silent mode:");
        silentModeLabel.setForeground(Color.WHITE);
        silentModeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField silentModeInput = new JTextField(silentMode);
        silentModeInput.setMaximumSize(new Dimension(400, 25));
        silentModeInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsPanel.add(silentModeLabel);
        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(silentModeInput);
        settingsPanel.add(Box.createVerticalStrut(5));


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
        silentPanel.add(startButton);

        JButton stopButton = new JButton();
        stopButton.setText("Close/Stop");
        stopButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        stopButton.setForeground(Color.WHITE);
        stopButton.setBackground(Color.DARK_GRAY);
        stopButton.setBounds(500, 100, 100, 30);
        silentPanel.add(stopButton);

        startButton.addActionListener(e -> {
            String enteredToken = discordTokenInput.getText(); // get the text
            System.out.println("token: " + enteredToken);

            String enteredBannedWords = bannedWordsInput.getText();
            System.out.println("banned words: " + enteredBannedWords);

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

            try {
                ini.get().put("general", "discordToken", enteredToken);
                ini.get().store();
                System.out.println("Saved token to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save token: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "bannedWords", enteredBannedWords);
                ini.get().store();
                System.out.println("Saved banned words to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save banned words: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "silencedUser", enteredSilencedUsers);
                ini.get().store();
                System.out.println("Saved silenced users to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save silenced users: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "silentMode", enteredSilentMode);
                ini.get().store();
                System.out.println("Saved silent mode to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save silent mode: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "silenceAllUsers", enteredSilenceAllUsers);
                ini.get().store();
                System.out.println("Saved silenceAllUsers to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save silenceAllUsers: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "crownedUsers", enteredCrownedUsers);
                ini.get().store();
                System.out.println("Saved crownedUsers to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save crownedUsers: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "crownedPhrase", enteredCrownedPhrase);
                ini.get().store();
                System.out.println("Saved crownedPhrase to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save crownedPhrase: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "crownedMessageResponse", enteredCrownedResponse);
                ini.get().store();
                System.out.println("Saved crownedMessageResponse to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save crownedMessageResponse: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "terminatorUsers", enteredTerminatorUsers);
                ini.get().store();
                System.out.println("Saved terminatorUsers to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save terminatorUsers: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "terminatorUsersPhrase", enteredTerminatorPhrase);
                ini.get().store();
                System.out.println("Saved terminatorUsersPhrase to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save terminatorUsersPhrase: " + ex.getMessage());
            }
            try {
                ini.get().put("general", "terminatorUsersResponse", enteredTerminatorResponse);
                ini.get().store();
                System.out.println("Saved terminatorUsersResponse to settings.ini");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save terminatorUsersResponse: " + ex.getMessage());
            }

            System.out.println("Start button clicked!");
            JOptionPane.showMessageDialog(null, "Starting discord bot!");


            try {
                settings.set(new settings("options/settings.ini")); // reload settings object
                System.out.println("Reloaded Main.settings from file");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to reload settings: " + ex.getMessage());
            }


            DiscordBot bot = new DiscordBot();
            bot.startDiscordBot(enteredToken);
        });


        stopButton.addActionListener(e -> {
            System.out.println("Close/Stop button clicked!");
            System.exit(0);
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(51, 51, 51));
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        MainFrame.add(controlPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("General", settingsPanel);
        tabbedPane.addTab("Silent Settings", silentPanel);

        MainFrame.add(tabbedPane, BorderLayout.CENTER);


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
