package me.itsskeptical.displaytags.utils.helpers;

import me.itsskeptical.displaytags.utils.Components;
import org.bukkit.command.CommandSender;

public class MessageHelper {
    private static final String PREFIX = "<#00BFFF>DisplayTags";

    public static void success(CommandSender sender, String message) {
        send(sender, "<green>" + message);
    }

    public static void error(CommandSender sender, String message) {
        send(sender, "<red>" + message);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(
                Components.mm(PREFIX + " <dark_gray>» <reset>" + message)
        );
    }
}
