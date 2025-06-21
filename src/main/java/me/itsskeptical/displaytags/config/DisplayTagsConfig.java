package me.itsskeptical.displaytags.config;

import me.itsskeptical.displaytags.DisplayTags;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DisplayTagsConfig {
    private final DisplayTags plugin;
    private final NametagConfig nametagConfig;

    public DisplayTagsConfig(DisplayTags plugin) {
        this.plugin = plugin;
        this.nametagConfig = new NametagConfig(plugin);
    }

    public void load() {
        this.nametagConfig.load();
    }

    public void reload() throws IOException, InvalidConfigurationException {
        File file = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);

        nametagConfig.load(config);
    }

    public NametagConfig getNametagConfig() {
        return nametagConfig;
    }
}
