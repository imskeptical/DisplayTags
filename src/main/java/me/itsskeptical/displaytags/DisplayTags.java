package me.itsskeptical.displaytags;

import me.itsskeptical.displaytags.commands.DisplayTagsCommand;
import me.itsskeptical.displaytags.config.DisplayTagsConfig;
import me.itsskeptical.displaytags.listeners.PlayerListener;
import me.itsskeptical.displaytags.nametags.NametagManager;
import me.itsskeptical.displaytags.nametags.NametagScheduler;
import me.itsskeptical.displaytags.utils.UpdateChecker;
import me.itsskeptical.displaytags.utils.helpers.DependencyHelper;
import me.itsskeptical.displaytags.utils.helpers.MessageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class DisplayTags extends JavaPlugin {
    private static DisplayTags instance;

    private static UpdateChecker updateChecker;
    private static DisplayTagsConfig config;
    private NametagManager nametagManager;
    private NametagScheduler nametagScheduler;

    @Override
    public void onEnable() {
        instance = this;
        updateChecker = new UpdateChecker(this, "voqEPXf8");
        PluginManager pluginManager = getServer().getPluginManager();

        // Configuration
        saveDefaultConfig();
        config = new DisplayTagsConfig(this);
        config.load();

        // Nametags
        this.nametagManager = new NametagManager();
        this.nametagScheduler = new NametagScheduler(this);
        nametagScheduler.start();

        // Register Listeners & Commands
        pluginManager.registerEvents(new PlayerListener(), this);
        registerCommands();

        // Other
        DependencyHelper.load();
        checkForUpdates(getServer().getConsoleSender());

        String version = getPluginMeta().getVersion();
        getLogger().info(String.format("DisplaysTags v%s has been enabled.", version));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        nametagScheduler.stop();
        getLogger().info("DisplayTags has been disabled.");
    }

    private void registerCommands() {
        getServer().getCommandMap().register(
                "displaytags",
                new DisplayTagsCommand(this)
        );
    }

    public void checkForUpdates(CommandSender sender) {
        String version = this.getPluginMeta().getVersion();
        updateChecker.getLatestVersion((latest) -> {
            if (latest == null) return;

            if (latest.equals(version)) {
                MessageHelper.success(sender, "This server is using the latest version of DisplayTags (v{latest}).".replace("{latest}", latest));
            } else {
                List<String> text = new ArrayList<>();
                text.add("This server is running an outdated version of DisplayTags (v{current})".replace("{current}", version));
                text.add("<u><click:open_url:'{url}'><hover:show_text:'<#00BFFF>➡ <reset><u>{url}'>Click to download the latest version (v{latest})".replace("{latest}", latest)
                        .replace("{url}", "https://modrinth.com/plugin/displaytags/version/" + latest)
                );
                text.forEach((line) -> MessageHelper.warning(sender, line));
            }
        });
    }

    public boolean reloadPlugin() {
        try {
            getLogger().info("Reloading...");
            config.reload();
            nametagScheduler.stop();
            nametagManager.removeAll();
            if (config().getNametagConfig().isEnabled()) {
                nametagManager.createAll();
            }
            nametagScheduler.start();

            getLogger().info("Reloaded!");
            return true;
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to reload plugin: " + e.getMessage(), e);
            return false;
        }
    }

    public static DisplayTags getInstance() {
        return instance;
    }

    public DisplayTagsConfig config() {
        return config;
    }

    public NametagManager getNametagManager() {
        return nametagManager;
    }
}
