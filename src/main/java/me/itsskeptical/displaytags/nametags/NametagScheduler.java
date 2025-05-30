package me.itsskeptical.displaytags.nametags;

import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.utils.PluginHelper;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class NametagScheduler {
    private final DisplayTags plugin;
    private final NametagManager nametags;
    private final boolean isFolia;

    private BukkitTask bukkitTask;
    private ScheduledTask foliaTask;

    public NametagScheduler(DisplayTags plugin) {
        this.plugin = plugin;
        this.nametags = DisplayTags.getNametagManager();
        this.isFolia = PluginHelper.isFolia();
    }

    public void start() {
        plugin.getLogger().info("Starting Nametag scheduler...");
        int interval = plugin.config().getNametagUpdateInterval();

        if (isFolia) {
            GlobalRegionScheduler scheduler = Bukkit.getGlobalRegionScheduler();
            foliaTask = scheduler.runAtFixedRate(
                    plugin,
                    (scheduledTask) -> {
                        for (Nametag nametag : nametags.getAll()) {
                            nametag.updateForAll();
                        }
                    }, interval, interval
            );
        } else {
            bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Nametag nametag : nametags.getAll()) {
                    nametag.updateForAll();
                }
            }, interval, interval);
        }
    }

    public void stop() {
        plugin.getLogger().info("Stopping Nametag scheduler...");
        if (isFolia && foliaTask != null) {
            foliaTask.cancel();
        } else if (bukkitTask != null) {
            bukkitTask.cancel();
        }
    }
}
