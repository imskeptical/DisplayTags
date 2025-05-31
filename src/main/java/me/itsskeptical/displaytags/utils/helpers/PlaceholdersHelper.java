package me.itsskeptical.displaytags.utils.helpers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholdersHelper {
    private static boolean isPAPIEnabled;

    public static void load() {
        isPAPIEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public static String parse(String text, Player player) {
        text = text
                .replace("{player}", player.getName())
                .replace("{health}", String.valueOf(Math.round(player.getHealth())));
        if (isPAPIEnabled) text = PlaceholderAPI.setPlaceholders(player, text);

        return text;
    }
}
