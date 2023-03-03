package me.godofpro.moamele;

import me.godofpro.moamele.commands.FileReaderCommand;
import me.godofpro.moamele.commands.RoleDropDownMenuCommand;
import me.godofpro.moamele.listeners.MessageUpdateListener;
import me.godofpro.moamele.utils.RoleUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.time.Duration;

public class Main {

    private static JDA jda;
    private static RoleUtils roleUtils;

    public static void main(String[] args) {
        initJDA(args[0]);
        initShutdownHook();
        roleUtils = new RoleUtils(jda);
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
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build().awaitReady();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        jda.getGuildById("1079885649986977894")
                .upsertCommand("roledropdownmenu", "Send the role drop down meny embed")
                .queue();
        jda.getGuildById("1079885649986977894").
                upsertCommand("read", "Reads a txt file and outputs the line the user provides")
                .addOption(OptionType.ATTACHMENT, "file", "The file you want to read", true)
                .addOptions(new OptionData(OptionType.INTEGER, "line", "The line you want to print", true).setRequiredRange(1, Integer.MAX_VALUE - 1))
                .queue();
    }

    public static RoleUtils getRoleUtils() {
        return roleUtils;
    }
}