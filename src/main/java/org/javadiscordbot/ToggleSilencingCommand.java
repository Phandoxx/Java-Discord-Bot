package org.javadiscordbot;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.javadiscordbot.Main;
import org.ini4j.Wini;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ToggleSilencingCommand {
    public static void execute(SlashCommandInteractionEvent event) {
        String executor = event.getUser().getAsTag();
        boolean newState = event.getOption("state").getAsBoolean();

        String terminatorUser = Main.settings.get("terminatorUsers", "null");
        List<String> terminatorUserArray = Main.stringSetting.getAsList(terminatorUser);

        // Check permission
        if (!terminatorUserArray.contains(executor)) {
            event.reply("❌ Higher Perms Required!").setEphemeral(true).queue();
            return;
        }

        try {
            // ✅ Directly write to the ini file
            Wini ini = new Wini(new File("files/options/settings.ini"));
            ini.put("general", "disableSilencing", String.valueOf(newState));
            ini.store();

            // ✅ Confirmation message
            event.reply("Silencing is now **" + (newState ? "disabled" : "enabled") + "**.").queue();

            // ✅ Update in-memory timestamp (so Main.settings notices change immediately)
            File settingsFile = new File("files/options/settings.ini");
            if (settingsFile.exists()) {
                // Touch lastModified time so reload-on-change logic picks it up
                settingsFile.setLastModified(System.currentTimeMillis());
            }

        } catch (IOException e) {
            e.printStackTrace();
            event.reply("⚠️ Failed to update settings file!").setEphemeral(true).queue();
        }
    }
}
