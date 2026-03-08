package me.skyyiscool.displaytags.config;

import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.config.spec.DisplayTagsConfigurationSpec;
import revxrsal.spec.Specs;

import java.io.File;

public class DisplayTagsConfiguration {
    private final DisplayTagsConfigurationSpec config;
    private final NameTagConfiguration nameTagConfig;

    public DisplayTagsConfiguration(DisplayTags plugin) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        this.config = Specs.fromFile(DisplayTagsConfigurationSpec.class, file.toPath());
        this.nameTagConfig = new NameTagConfiguration();

        this.config.save();
        this.load();
    }

    public void load() {
        this.nameTagConfig.load(this.config);
    }

    public void reload() {
        this.config.reload();
        this.load();
    }

    public NameTagConfiguration nametag() {
        return this.nameTagConfig;
    }
}
