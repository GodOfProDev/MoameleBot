package me.godofpro.moamele.commands;

import me.godofpro.moamele.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class RoleDropDownMenuCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("roledropdownmenu")) {
            StringSelectMenu menu = StringSelectMenu.create("menu:role")
                    .setPlaceholder("The role you want")
                    .setRequiredRange(1, 3)
                    .addOption("Announcement Ping", "role:announcementping")
                    .addOption("Video Ping", "role:videoping")
                    .addOption("Event Ping", "role:eventping")
                    .build();
            event.reply("Chose your role below")
                    .setEphemeral(true)
                    .addActionRow(menu)
                    .queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("menu:role")) {
            if (event.getValues().isEmpty()) {
                event.reply("No role was selected").setEphemeral(true).queue();
                return;
            }
            for (String value : event.getValues()) {
                if (value.equals("role:announcementping")) {
                    Main.getRoleUtils().giveAnnouncementPingRole(event.getGuild(), event.getUser());
                } else if (value.equals("role:videoping")) {
                    Main.getRoleUtils().giveVideoPingRole(event.getGuild(), event.getUser());
                } else if (value.equals("role:eventping")) {
                    Main.getRoleUtils().giveEventPingRole(event.getGuild(), event.getUser());
                } else {
                    event.reply("No role was selected").setEphemeral(true).queue();
                    return;
                }
            }
            event.reply("You got the role(s)!").setEphemeral(true).queue();
        }
    }
}
