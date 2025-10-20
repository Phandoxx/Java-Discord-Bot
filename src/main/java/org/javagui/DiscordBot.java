package org.javagui;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;

public class DiscordBot extends ListenerAdapter {

    private JDA jda;

    public void startDiscordBot(String discordToken) {
        try {
            // Define what data/events your bot will have access to
            EnumSet<GatewayIntent> intents = EnumSet.of(
                    GatewayIntent.GUILD_MESSAGES,      // Messages in servers
                    GatewayIntent.DIRECT_MESSAGES,     // Direct messages
                    GatewayIntent.MESSAGE_CONTENT      // Needed to read message text
            );

            jda = JDABuilder.createLight(discordToken, intents)
                    .addEventListeners(this) // listen for events in THIS class
                    .setActivity(Activity.playing("Im always watching..."))
                    .build();

            jda.awaitReady(); // Wait until the bot is fully loaded
            System.out.println("Bot is online!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method runs every time a message is sent in any channel the bot can see
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String silentMode = Main.settings.get("silentMode", "true");
        // Ignore messages sent by bots (including itself)
        if (event.getAuthor().isBot()) return;

        String author = event.getAuthor().getAsTag();
        String content = event.getMessage().getContentDisplay();

        String decapitalizedContent = content.toLowerCase();
        String mentionAuthor = event.getAuthor().getAsMention();

        System.out.printf("[%s] %s\n", author, content);

        String input = Main.settings.get("bannedWords", "null");
        List<String> BannedWords = Main.stringSetting.getAsList(input);

        String crownedUsers = Main.settings.get("crownedUsers", "null");
        List<String> crownedUsersArray = Main.stringSetting.getAsList(crownedUsers);
        String crownedPhrase = Main.settings.get("crownedPhrase", "null");
        String crownedMessageResponse = Main.settings.get("crownedMessageResponse", "null");

        if (decapitalizedContent.contains(crownedPhrase.toLowerCase()) && crownedUsersArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) {
            event.getChannel().sendMessage(crownedMessageResponse + mentionAuthor).queue();
        }

        String terminatorUser = Main.settings.get("terminatorUsers", "null");
        List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);
        String terminatorUserPhrase = Main.settings.get("terminatorUsersPhrase", "null");
        String terminatorUserResponse = Main.settings.get("terminatorUsersResponse", "null");

        if (decapitalizedContent.contains(terminatorUserPhrase.toLowerCase()) && terminatorUserArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) {
            event.getChannel().sendMessage(mentionAuthor + " " + terminatorUserResponse)
                    .queue(message -> {
                        // This runs *after* the message successfully sends
                        System.exit(0);
                    });
        }

        String silencedUsers = Main.settings.get("silencedUser", "null");
        List<String> silencedUsersArray = Main.stringSetting.getAsList(silencedUsers);
        String silencedAllUsers = Main.settings.get("silenceAllUsers", "false");

        if (silencedAllUsers.equalsIgnoreCase("false")) {
            for (String message : BannedWords) {
                // Check if message contains a banned word AND author matches
                if (decapitalizedContent.contains(message) && silencedUsersArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) {
                    event.getMessage().delete().queue();
                    if (silentMode.equals("false")) {
                        event.getChannel().sendMessage("Message deleted, " + event.getAuthor().getAsMention() + "!").queue();
                    }
                    break; // stop checking after first match
                }
            }
        }
        else {
            for (String message : BannedWords) {
                // Check if message contains a banned word AND author matches
                if (decapitalizedContent.contains(message)) {
                    if (crownedUsersArray.stream().anyMatch(u -> u.equalsIgnoreCase(author)) || (terminatorUserArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) ) {
                        System.out.println("Not deleting from crowned or terminator users");
                    }
                    else {
                        event.getMessage().delete().queue();
                        if (silentMode.equals("false")) {
                            event.getChannel().sendMessage("Message deleted, " + event.getAuthor().getAsMention() + "!").queue();
                        }
                        break; // stop checking after first match
                    }
                }
            }
        }
        //
    }
    @Override
    public void onMessageUpdate(@NotNull net.dv8tion.jda.api.events.message.MessageUpdateEvent event) {
        // Ignore messages from bots (including itself)
        if (event.getAuthor().isBot()) return;
        String crownedUsers = Main.settings.get("crownedUsers", "null");
        List<String> crownedUsersArray = Main.stringSetting.getAsList(crownedUsers);
        String crownedPhrase = Main.settings.get("crownedPhrase", "null");
        String crownedMessageResponse = Main.settings.get("crownedMessageResponse", "null");

        String terminatorUser = Main.settings.get("terminatorUsers", "null");
        List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);
        String terminatorUserPhrase = Main.settings.get("terminatorUsersPhrase", "null");
        String terminatorUserResponse = Main.settings.get("terminatorUsersResponse", "null");

        String author = event.getAuthor().getAsTag();
        String content = event.getMessage().getContentDisplay().toLowerCase();

        String input = Main.settings.get("bannedWords", "null");
        List<String> BannedWords = Main.stringSetting.getAsList(input);

        String silencedUsers = Main.settings.get("silencedUser", "null");
        List<String> silencedUsersArray = Main.stringSetting.getAsList(silencedUsers);

        String silencedAllUsers = Main.settings.get("silenceAllUsers", "false");

        if (silencedAllUsers.equalsIgnoreCase("false")){

            for (String banned : BannedWords) {
                if (content.contains(banned) && silencedUsersArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) {
                    event.getMessage().delete().queue(
                            success -> System.out.println("Deleted edited message from " + author),
                            error -> System.out.println("Failed to delete edited message: " + error.getMessage())
                    );

                    break;
                }
            }
        }
        else {
            if (crownedUsersArray.stream().anyMatch(u -> u.equalsIgnoreCase(author)) || (terminatorUserArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) ) {
                System.out.println("Not deleting from crowned or terminator users");
            }
            else {
                for (String banned : BannedWords) {
                    if (content.contains(banned)) {
                        event.getMessage().delete().queue(
                                success -> System.out.println("Deleted edited message from " + author),
                                error -> System.out.println("Failed to delete edited message: " + error.getMessage())
                        );

                        break;
                    }
                }
            }
        }
    }
}
