package me.skyyiscool.displaytags;

import me.skyyiscool.displaytags.api.DisplayTagsPlugin;
import me.skyyiscool.displaytags.api.nametag.NameTagManager;
import me.skyyiscool.displaytags.api.nametag.PlayerNameTag;
import me.skyyiscool.displaytags.commands.DisplayTagsCommand;
import me.skyyiscool.displaytags.config.DisplayTagsConfiguration;
import me.skyyiscool.displaytags.listener.PlayerListener;
import me.skyyiscool.displaytags.metrics.Metrics;
import me.skyyiscool.displaytags.nametag.NameTagManagerImpl;
import me.skyyiscool.displaytags.nametag.NameTagScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisplayTags extends JavaPlugin implements DisplayTagsPlugin {
    private static DisplayTags INSTANCE;
    private Metrics metrics;

    private DisplayTagsConfiguration config;
    private NameTagManager nameTagManager;
    private NameTagScheduler nameTagScheduler;

    @Override
    public void onLoad() {
        INSTANCE = this;

        this.config = new DisplayTagsConfiguration(this);
        this.nameTagManager = new NameTagManagerImpl();
        this.nameTagScheduler = new NameTagScheduler(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pluginManager = getServer().getPluginManager();
        CommandMap commandMap = getServer().getCommandMap();

        // Configuration
        this.config.load();

        // Name Tags
        this.nameTagScheduler.start();

        // Register Listeners & Commands
        pluginManager.registerEvents(new PlayerListener(this), this);
        commandMap.register("displaytags", new DisplayTagsCommand(this));

        // Metrics
        this.metrics = new Metrics(this, 29009);

        String version = getPluginMeta().getVersion();
        getLogger().info(String.format("Enabled DisplayTags v%s.", version));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        this.nameTagScheduler.end();
        this.metrics.shutdown();

        getLogger().info("Disabled DisplayTags.");
    }

    public boolean reloadPlugin() {
        getLogger().info("Reloading DisplayTags...");

        this.nameTagScheduler.end();
        for (PlayerNameTag tag : this.nameTagManager.getAll()) {
            tag.despawnForViewers();
        }

        try {
            this.config.reload();
        } catch (Exception error) {
            getLogger().severe("Failed to reload plugin configuration:" + error.getMessage());
            error.printStackTrace();

            return false;
        }

        if (this.config().nametag().isEnabled()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.nameTagManager.createNameTag(player).tick();
            }

            this.nameTagScheduler.start();
        }

        getLogger().info("Successfully reloaded!");
        return true;
    }

    public static DisplayTags get() {
        return INSTANCE;
    }

    public DisplayTagsConfiguration config() {
        return this.config;
    }

    @Override
    public NameTagManager getNameTagManager() {
        return this.nameTagManager;
    }
}
