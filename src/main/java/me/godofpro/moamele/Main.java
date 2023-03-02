package me.godofpro.moamele;

import me.godofpro.moamele.commands.FileReaderCommand;
import me.godofpro.moamele.commands.RoleDropDownMenuCommand;
import me.godofpro.moamele.listeners.MessageUpdateListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.time.Duration;

public class Main {
    private static JDA jda;

    public static void main(String[] args) {
        initJDA(args[0]);
        initShutdownHook();
    }

    private static void initShutdownHook() {
        Thread shutdownJDAThread = new Thread(Main::shutdownHook);
        Runtime.getRuntime().addShutdownHook(shutdownJDAThread);
    }

    private static void shutdownHook() {
        jda.shutdown();

        try {
            if (!jda.awaitShutdown(Duration.ofSeconds(10))) { // returns true if shutdown is graceful, false if timeout exceeded
                jda.shutdownNow(); // Cancel all remaining requests, and stop thread-pools
                jda.awaitShutdown(); // Wait until shutdown is complete (indefinitely)
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void initJDA(String token) {
        try {
            jda = JDABuilder.createDefault(token)
                    .addEventListeners(new FileReaderCommand())
                    .addEventListeners(new RoleDropDownMenuCommand())
                    .addEventListeners(new MessageUpdateListener())
                    .build().awaitReady();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        jda.getGuildById("🗿").upsertCommand("roledropdownmenu", "Send the role drop down meny embed").queue();
        jda.getGuildById("🗿").upsertCommand("read", "Reads a txt file and outputs the line the user provides").queue();
    }
}