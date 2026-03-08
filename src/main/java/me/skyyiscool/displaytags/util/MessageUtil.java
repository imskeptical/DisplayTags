package me.skyyiscool.displaytags.util;

import org.bukkit.command.CommandSender;

import java.util.List;

public class MessageUtil {
    private static final String PREFIX = "<#00BFFF>DisplayTags";

    public static void success(CommandSender sender, String message) {
        send(sender, format("{success} <reset>{success_color}" + message));
    }

    public static void warning(CommandSender sender, String message) {
        send(sender, format("{warn} <reset>{warn_color}" + message));
    }

    public static void error(CommandSender sender, String message) {
        send(sender, format("{danger} <reset>{danger_color}" + message));
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(
                ComponentUtil.render(format(prefixed(message)))
        );
    }

    public static void send(CommandSender sender, List<String> messages) {
        messages.forEach((message) -> send(sender, message));
    }

    public static String prefixed(String message) {
        return format("{prefix} <dark_gray>» <reset>" + message);
    }

    private static String format(String input) {
        return input
                .replace("{prefix}", PREFIX)
                .replace("{success}", "{start}{success_color}✔{end}")
                .replace("{warn}", "{start}{warn_color}⚠{end}")
                .replace("{danger}", "{start}{danger_color}❌{end}")
                .replace("{success_color}", "<#7EFF00>")
                .replace("{warn_color}", "<#FFFF00>")
                .replace("{danger_color}", "<#FF0000>")
                .replace("{start}", "<dark_gray>[")
                .replace("{end}", "<dark_gray>]");
    }
}
