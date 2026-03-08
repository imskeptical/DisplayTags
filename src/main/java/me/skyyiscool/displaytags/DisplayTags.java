package me.skyyiscool.displaytags;

import me.skyyiscool.displaytags.api.DisplayTagsPlugin;
import me.skyyiscool.displaytags.api.NameTagManager;
import me.skyyiscool.displaytags.api.PlayerNameTag;
import me.skyyiscool.displaytags.commands.DisplayTagsCommand;
import me.skyyiscool.displaytags.config.DisplayTagsConfiguration;
import me.skyyiscool.displaytags.listener.PlayerListener;
import me.skyyiscool.displaytags.nametag.NameTagManagerImpl;
import me.skyyiscool.displaytags.nametag.NameTagScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.spec.Specs;

import java.io.File;

public final class DisplayTags extends JavaPlugin implements DisplayTagsPlugin {
    private static DisplayTags INSTANCE;

    private DisplayTagsConfiguration config;
    private NameTagManager nameTagManager;
    private NameTagScheduler nameTagScheduler;

    @Override
    public void onLoad() {
        INSTANCE = this;

        this.config = Specs.fromFile(DisplayTagsConfiguration.class, new File(getDataFolder(), "config.yml").toPath());
        this.nameTagManager = new NameTagManagerImpl();
        this.nameTagScheduler = new NameTagScheduler(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.config.save();

        this.nameTagScheduler.start();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getCommandMap().register("displaytags", new DisplayTagsCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.nameTagScheduler.end();
    }

    public void reloadPlugin() {
        this.nameTagScheduler.end();
        for (PlayerNameTag tag : this.nameTagManager.getAll()) {
            tag.despawnForViewers();
        }

        this.config.reload();

        if (this.config().nametag().enabled()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.nameTagManager.createNameTag(player).tick();
            }

            this.nameTagScheduler.start();
        }
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

    public NameTagScheduler getNameTagScheduler() {
        return this.nameTagScheduler;
    }
}
