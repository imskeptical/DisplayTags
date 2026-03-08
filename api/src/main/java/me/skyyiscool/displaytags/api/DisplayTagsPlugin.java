package me.skyyiscool.displaytags.api;

import org.bukkit.Bukkit;

public interface DisplayTagsPlugin {
    static DisplayTagsPlugin get() {
        return (DisplayTagsPlugin) Bukkit.getPluginManager().getPlugin("DisplayTags");
    }

    NameTagManager getNameTagManager();
}
