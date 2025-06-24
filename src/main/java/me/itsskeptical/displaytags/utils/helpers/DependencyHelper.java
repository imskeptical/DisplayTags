package me.itsskeptical.displaytags.utils.helpers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class DependencyHelper {
    static boolean enabledPlaceholderAPI;
    static boolean enabledTAB;

    public static void load() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.isPluginEnabled("PlaceholderAPI")) enabledPlaceholderAPI = true;
        if (pluginManager.isPluginEnabled("TAB")) enabledTAB = true;
    }

    public static boolean isPlaceholderAPIEnabled() {
        return enabledPlaceholderAPI;
    }

    public static boolean isTABEnabled() {
        return enabledTAB;
    }
}
