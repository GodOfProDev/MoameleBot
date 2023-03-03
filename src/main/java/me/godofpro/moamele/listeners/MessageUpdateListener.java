package me.godofpro.moamele.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class MessageUpdateListener extends ListenerAdapter {

    @Override
        public void onMessageUpdate(MessageUpdateEvent event) {
        Objects.requireNonNull(event.getJDA().getChannelById(TextChannel.class, "1081254972794077206")).sendMessage(
                event.getMessage().getContentRaw() + "\n" + event.getMessage().getJumpUrl()
        ).queue();
    }
}
