package org.javadiscordbot;

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

    public void startDiscordBot(String discordToken) { //Starts discord bot
        try {
            // Define what data/events your bot will have access to
            EnumSet<GatewayIntent> intents = EnumSet.of(
                    GatewayIntent.GUILD_MESSAGES,      // Messages in servers
                    GatewayIntent.DIRECT_MESSAGES,     // Direct messages
                    GatewayIntent.MESSAGE_CONTENT,      // Needed to read message text
                    GatewayIntent.GUILD_MEMBERS        //for user lookup
            );

            jda = JDABuilder.createLight(discordToken, intents)
                    .addEventListeners(this) // listen for events in THIS class
                    .setActivity(Activity.playing("Im always watching..."))
                    .build();

            jda.upsertCommand("spam", "spam a user")
                    .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.USER, "user", "The user to spam", true)
                    .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER, "rate", "How many times to spam", true)
                    .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "message", "the message to send")
                    .queue();

            jda.awaitReady(); // Wait until the bot is fully loaded
            System.out.println("Bot is online!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method runs every time a message is sent in any channel the bot can see
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) { //runs whenever a message is sent
        String disableSilencing = Main.settings.get("disableSilencing", "null");// when disableSilencing setting is true, don't censor message
        if (disableSilencing.equalsIgnoreCase("true")) {
            return;
        }
        String silentMode = Main.settings.get("silentMode", "true");
        // Ignore messages sent by bots (including itself)
        if (event.getAuthor().isBot()) return;

        //Gets the author of the message, and the content of the message
        String author = event.getAuthor().getAsTag();
        String content = event.getMessage().getContentDisplay();

        //Decapitalizes the content
        String decapitalizedContent = content.toLowerCase();
        //Mentions (pings) author of the message
        String mentionAuthor = event.getAuthor().getAsMention();

        //Prints out the message, useful for debugging
        System.out.printf("[%s] %s\n", author, content);

        //Gets the bannedWords string from settings.ini
        String input = Main.settings.get("bannedWords", "null");
        List<String> BannedWords = Main.stringSetting.getAsList(input);

        //Gets string array for the crowned user(s), and get the string for the phrase and response
        String crownedUsers = Main.settings.get("crownedUsers", "null");
        List<String> crownedUsersArray = Main.stringSetting.getAsList(crownedUsers);
        String crownedPhrase = Main.settings.get("crownedPhrase", "null");
        String crownedMessageResponse = Main.settings.get("crownedMessageResponse", "null");

        //Checks for crowned message (phrase), responds with crowned message response
        if (decapitalizedContent.contains(crownedPhrase.toLowerCase()) && crownedUsersArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) {
            event.getChannel().sendMessage(crownedMessageResponse + mentionAuthor).queue();
        }

        //Gets string array for the terminator user(s), the phrase and the response
        String terminatorUser = Main.settings.get("terminatorUsers", "null");
        List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);
        String terminatorUserPhrase = Main.settings.get("terminatorUsersPhrase", "null");
        String terminatorUserResponse = Main.settings.get("terminatorUsersResponse", "null");

        //Checks for the terminator users message (phrase), respond with terminator message if found
        if (decapitalizedContent.contains(terminatorUserPhrase.toLowerCase()) && terminatorUserArray.stream().anyMatch(u -> u.equalsIgnoreCase(author))) {
            event.getChannel().sendMessage(mentionAuthor + " " + terminatorUserResponse)
                    .queue(message -> {
                        // This runs *after* the message successfully sends
                        System.exit(0);
                    });
        }

        //Gets string array for silenced Users, and string for silencedAllUsers setting
        String silencedUsers = Main.settings.get("silencedUser", "null");
        List<String> silencedUsersArray = Main.stringSetting.getAsList(silencedUsers);
        String silencedAllUsers = Main.settings.get("silenceAllUsers", "false");

        //If silencedAllUsers settings is enabled:
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
        else { //If silencedAllUsers in not enabled:
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
        String disableSilencing = Main.settings.get("disableSilencing", "null");
        if (disableSilencing.equalsIgnoreCase("true")) { // when disableSilencing setting is true, don't censor message
            return;
        }
        // Ignore messages from bots (including itself)
        //Check string array for crownedUsers
        if (event.getAuthor().isBot()) return;
        String crownedUsers = Main.settings.get("crownedUsers", "null");
        List<String> crownedUsersArray = Main.stringSetting.getAsList(crownedUsers);
        String crownedPhrase = Main.settings.get("crownedPhrase", "null");
        String crownedMessageResponse = Main.settings.get("crownedMessageResponse", "null");

        //Check string array for terminator users
        String terminatorUser = Main.settings.get("terminatorUsers", "null");
        List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);
        String terminatorUserPhrase = Main.settings.get("terminatorUsersPhrase", "null");
        String terminatorUserResponse = Main.settings.get("terminatorUsersResponse", "null");

        //stores the author of the edited message and the content of the now edited message
        String author = event.getAuthor().getAsTag();
        String content = event.getMessage().getContentDisplay().toLowerCase();

        //Gets string array of banned words
        String input = Main.settings.get("bannedWords", "null");
        List<String> BannedWords = Main.stringSetting.getAsList(input);

        //Gets string array of silenced Users
        String silencedUsers = Main.settings.get("silencedUser", "null");
        List<String> silencedUsersArray = Main.stringSetting.getAsList(silencedUsers);

        String silencedAllUsers = Main.settings.get("silenceAllUsers", "false");

        //If silencedAllUsers is not enabled:
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
        else {// If silencedAllUsers is enabled:
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
    @Override
    public void onSlashCommandInteraction(@NotNull net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent event) {
        if (event.getName().equals("spam")) {
            // Get the command options
            String executor = event.getUser().getAsTag();
            var user = event.getOption("user").getAsUser();
            int rate = event.getOption("rate").getAsInt();
            String message = event.getOption("message") != null ? event.getOption("message").getAsString() : "";

            String terminatorUser = Main.settings.get("terminatorUsers", "null");
            List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);

            // Check if the command executor has permission
            if (!terminatorUserArray.contains(executor)) {
                event.reply("❌ Higher Perms Required!").setEphemeral(true).queue();
                return;
            }

            // Respond to the interaction (required by Discord)
            event.reply("Spamming " + user.getAsMention() + " " + rate + " times!").queue();

            // Actually do the spam
            for (int i = 0; i < rate; i++) {
                event.getChannel().sendMessage(user.getAsMention() + " " + message).queue();
            }
        }
    }

}
