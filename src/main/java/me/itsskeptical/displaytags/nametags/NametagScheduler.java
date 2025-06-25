package me.itsskeptical.displaytags.nametags;

import me.itsskeptical.displaytags.DisplayTags;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class NametagScheduler {
    private final DisplayTags plugin;
    private BukkitTask task;

    public NametagScheduler(DisplayTags plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (plugin.config().getNametagConfig().isEnabled()) {
            plugin.getLogger().info("Starting Nametag scheduler...");
            int interval = (plugin.config().getNametagConfig().getUpdateInterval()) * 20;
            task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Nametag nametag : plugin.getNametagManager().getAll()) {
                    nametag.updateVisibilityForAll();
                }

            }, interval, interval);
        } else {
            plugin.getLogger().warning("Nametags are disabled for this server, therefore the nametag scheduler has not been started.");
            plugin.getLogger().warning("If you want to enable the nametags again, enable them in config.yml and run /displaytags reload.");
        }
    }

    public void stop() {
        if (task != null) {
            plugin.getLogger().info("Stopping Nametag scheduler...");
            task.cancel();
        }
    }
}
