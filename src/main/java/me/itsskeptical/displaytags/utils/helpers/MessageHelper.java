package me.itsskeptical.displaytags.utils.helpers;

import me.itsskeptical.displaytags.utils.ComponentUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MessageHelper {
    private static final String PREFIX = "<#00BFFF>DisplayTags";
    static String SUCCESS = "<#7EFF00>";
    static String DANGER = "<#FF0000>";

    public static void success(CommandSender sender, String message) {
        send(sender, format("{success} <reset>{success_color}" + message));
    }

    public static void error(CommandSender sender, String message) {
        send(sender, format("{danger} <reset>{danger_color}" + message));
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(
                ComponentUtils.format(format("{prefix} <dark_gray>» <reset>" + message))
        );
    }

    public static void send(CommandSender sender, List<String> messages) {
        for (String message : messages) {
            send(sender, message);
        }
    }

    private static String format(String input) {
        return input
                .replace("{prefix}", PREFIX)
                .replace("{success}", "<dark_gray>[{success_color}✔<dark_gray>]")
                .replace("{danger}", "<dark_gray>[{danger_color}❌<dark_gray>]")
                .replace("{success_color}", SUCCESS)
                .replace("{danger_color}", DANGER);
    }
}
