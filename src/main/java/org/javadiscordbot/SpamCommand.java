package org.javadiscordbot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.javadiscordbot.Main;
import java.util.List;

public class SpamCommand {
    public static void execute(SlashCommandInteractionEvent event) {
        String executor = event.getUser().getAsTag();
        var user = event.getOption("user").getAsUser();
        int rate = event.getOption("rate").getAsInt();
        String message = event.getOption("message") != null ? event.getOption("message").getAsString() : "";

        String terminatorUser = Main.settings.get("terminatorUsers", "null");
        List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);

        if (!terminatorUserArray.contains(executor)) {
            event.reply("❌ Higher Perms Required!").setEphemeral(true).queue();
            return;
        }

        event.reply("Spamming " + user.getAsMention() + " " + rate + " times!").queue();

        for (int i = 0; i < rate; i++) {
            event.getChannel().sendMessage(user.getAsMention() + " " + message).queue();
        }
    }
}
