package me.skyyiscool.displaytags.util;

import me.skyyiscool.displaytags.DisplayTags;
import org.bukkit.plugin.PluginManager;

public class DependencyUtil {
    private static boolean enabledPlaceholderAPI;

    public static void load(DisplayTags plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        if (pluginManager.isPluginEnabled("PlaceholderAPI")) enabledPlaceholderAPI = true;
    }

    public static boolean enabledPlaceholderAPI() {
        return enabledPlaceholderAPI;
    }
}
