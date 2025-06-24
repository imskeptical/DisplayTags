package me.itsskeptical.displaytags.config;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.entities.DisplayBillboard;
import me.itsskeptical.displaytags.entities.TextAlignment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.util.List;

public class NametagConfig {
    private final DisplayTags plugin;

    private boolean enabled;
    private boolean showSelf;
    private int updateInterval;
    private int visibilityDistance;
    private List<String> lines;
    private boolean textShadow;
    private boolean seeThrough;
    private TextAlignment textAlignment;
    private String background;
    private DisplayBillboard billboard;
    private Vector scale;

    public NametagConfig(DisplayTags plugin) {
        this.plugin = plugin;
    }

    public void load() {
        this.load(plugin.getConfig());
    }

    public void load(FileConfiguration config) {
        this.enabled = config.getBoolean("nametags.enabled", false);
        this.showSelf = config.getBoolean("nametags.show-self", true);
        this.updateInterval = config.getInt("nametags.update-interval", 1);
        this.visibilityDistance = config.getInt("nametags.visibility-distance", 64);
        this.lines = config.getStringList("nametags.display.lines");
        this.textShadow = config.getBoolean("nametags.display.text-shadow", false);
        this.seeThrough = config.getBoolean("nametags.display.see-through", false);
        this.textAlignment = TextAlignment.valueOf(config.getString("nametags.display.text-alignment", "center").toUpperCase());
        this.background = config.getString("nametags.display.background", "default");
        this.billboard = DisplayBillboard.valueOf(config.getString("nametags.display.billboard", "center").toUpperCase());
        this.scale = parseVector(config, "nametags.display.scale", 1, 1, 1);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public boolean shouldHideSelf() {
        return !showSelf;
    }

    public int getVisibilityDistance() {
        return visibilityDistance;
    }

    public List<String> getLines() {
        return lines;
    }

    public boolean hasTextShadow() {
        return textShadow;
    }

    public boolean isSeeThrough() {
        return seeThrough;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public String getBackground() {
        return background;
    }

    public DisplayBillboard getBillboard() {
        return billboard;
    }

    public Vector getScale() {
        return scale;
    }

    private Vector parseVector(FileConfiguration config, String key, double defaultX, double defaultY, double defaultZ) {
        return new Vector()
                .setX(config.getDouble(key + ".x", defaultX))
                .setY(config.getDouble(key + ".y", defaultY))
                .setZ(config.getDouble(key + ".z", defaultZ));
    }
}
