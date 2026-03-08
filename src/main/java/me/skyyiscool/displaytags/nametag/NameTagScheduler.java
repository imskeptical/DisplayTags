package me.skyyiscool.displaytags.nametag;

import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.api.PlayerNameTag;
import org.bukkit.scheduler.BukkitTask;

public class NameTagScheduler {
    private final DisplayTags plugin;
    private BukkitTask task;

    public NameTagScheduler(DisplayTags plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (this.plugin.config().nametag().enabled()) {
            int interval = (plugin.config().nametag().updateInterval()) * 20;
            task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
                for (PlayerNameTag nametag : plugin.getNameTagManager().getAll()) {
                    nametag.tick();
                }
            }, interval, interval);
        } else {
            plugin.getLogger().warning("Custom name tags are disabled for this server, therefore the name tag scheduler has not been started.");
            plugin.getLogger().warning("If you want to enable the custom name tags again, enable them in config.yml and run /displaytags reload.");
        }
    }

    public void end() {
        if (task != null) task.cancel();
    }
}
