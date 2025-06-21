package me.itsskeptical.displaytags.utils.helpers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class DependencyHelper {
    static boolean enabledPlaceholderAPI;

    public static void load() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.isPluginEnabled("PlaceholderAPI")) enabledPlaceholderAPI = true;
    }

    public static boolean isPlaceholderAPIEnabled() {
        return enabledPlaceholderAPI;
    }
}
