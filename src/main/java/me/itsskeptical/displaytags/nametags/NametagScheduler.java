package me.itsskeptical.displaytags.nametags;

import me.itsskeptical.displaytags.DisplayTags;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class NametagScheduler {
    private final DisplayTags plugin;
    private final NametagManager nametags;
    private BukkitTask task;

    public NametagScheduler(DisplayTags plugin) {
        this.plugin = plugin;
        this.nametags = DisplayTags.getNametagManager();
    }

    public void start() {
        plugin.getLogger().info("Starting Nametag scheduler...");
        int interval = plugin.config().getNametagUpdateInterval();
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Nametag nametag : nametags.getAll()) {
                nametag.updateForAll();
            }
        }, interval, interval);
    }

    public void stop() {
        if (task != null) {
            plugin.getLogger().info("Stopping Nametag scheduler...");
            task.cancel();
        }
    }
}
