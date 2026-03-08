package me.skyyiscool.displaytags.api;

import me.skyyiscool.displaytags.api.nametag.NameTagManager;
import org.bukkit.Bukkit;

public interface DisplayTagsPlugin {
    static DisplayTagsPlugin get() {
        return (DisplayTagsPlugin) Bukkit.getPluginManager().getPlugin("DisplayTags");
    }

    NameTagManager getNameTagManager();
}
