package me.godofpro.moamele.commands;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReaderCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("read")) {
            event.deferReply(true).queue();
            int line = event.getOption("line").getAsInt();
            if (event.getOption("file") == null) {
                event.getInteraction().getHook().editOriginal("No File Was Provided").queue();
                return;
            }
            Message.Attachment attachment = event.getOption("file").getAsAttachment();
            if (attachment.getFileExtension() == null || !attachment.getFileExtension().equals("txt")) {
                event.getInteraction().getHook().editOriginal("The file needs to be a txt file").queue();
                return;
            }
            File file = new File("/tmp/" + attachment.getFileName());
            attachment.getProxy().downloadToFile(file).thenRun(() -> {
                try {
                    event.getInteraction().getHook().editOriginal(Files.readAllLines(file.toPath()).get(line - 1)).queue();
                } catch (IOException e) {
                    event.getInteraction().getHook().editOriginal("There was an error reading the file").queue();
                } catch (IndexOutOfBoundsException ex) {
                    event.getInteraction().getHook().editOriginal("Line " + line + " doesn't exists").queue();
                }
                file.delete();
            });
        }
    }
}
