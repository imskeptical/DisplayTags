package me.itsskeptical.displaytags;

import me.itsskeptical.displaytags.commands.DisplayTagsCommand;
import me.itsskeptical.displaytags.config.DisplayTagsConfig;
import me.itsskeptical.displaytags.listeners.PlayerListener;
import me.itsskeptical.displaytags.nametags.NametagManager;
import me.itsskeptical.displaytags.nametags.NametagScheduler;
import me.itsskeptical.displaytags.utils.PlaceholdersHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisplayTags extends JavaPlugin {
    private static DisplayTags instance;
    private static DisplayTagsConfig config;
    private static NametagManager nametags;
    private static NametagScheduler nametagScheduler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        saveDefaultConfig();

        config = new DisplayTagsConfig(this);
        config.load();

        nametags = new NametagManager();
        nametagScheduler = new NametagScheduler(this);
        nametagScheduler.start();

        PlaceholdersHelper.load();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        registerCommands();

        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        nametagScheduler.stop();
        getLogger().info("Disabled.");
    }

    private void registerCommands() {
        this.getServer().getCommandMap().register(
                this.getName().toLowerCase(),
                new DisplayTagsCommand(this)
        );
    }

    public void reloadPlugin() {
        getLogger().info("Reloading configuration...");
        this.reloadConfig();
        config.load();

        nametagScheduler.stop();
        nametags.deleteAll();
        if (config().isNametagsEnabled()) {
            nametags.createAll();
        }
        nametagScheduler.start();

        getLogger().info("Reloaded!");
    }

    public DisplayTagsConfig config() {
        return config;
    }

    public static DisplayTags getInstance() {
        return instance;
    }

    public static NametagManager getNametagManager() {
        return nametags;
    }
}
