package me.godofpro.moamele.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class RoleUtils {

    private final JDA jda;

    public RoleUtils(JDA jda) {
        this.jda = jda;
    }

    public void giveAnnouncementPingRole(Guild guild, User user) {
        guild.addRoleToMember(user, jda.getRoleById("1081141615197966387")).queue();;
    }

    public void giveVideoPingRole(Guild guild, User user) {
        guild.addRoleToMember(user, jda.getRoleById("1081141644243517521")).queue();;
    }

    public void giveEventPingRole(Guild guild, User user) {
        guild.addRoleToMember(user, jda.getRoleById("1081141678922010684")).queue();
    }
}
