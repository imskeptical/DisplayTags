package me.itsskeptical.displaytags.config;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.displays.Billboard;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class DisplayTagsConfig {
    private final DisplayTags plugin;

    private boolean nametagsEnabled;
    private boolean showSelfNametag;
    private int nametagUpdateInterval;
    private int nametagVisibilityDistance;
    private int nametagBackground;
    private Billboard nametagBillboard;
    private double nametagOffset;
    private List<String> nametagLines;

    public DisplayTagsConfig(DisplayTags plugin) {
        this.plugin = plugin;
    }

    public void load() {
        FileConfiguration config = plugin.getConfig();

        this.nametagsEnabled = config.getBoolean("nametags.enabled", true);
        this.showSelfNametag = config.getBoolean("nametags.show-self", true);
        this.nametagUpdateInterval = (config.getInt("nametags.update-interval", 1)) * 20;
        this.nametagVisibilityDistance = config.getInt("nametags.visibility-distance", 32);
        this.nametagBackground = parseNametagBackground(config.getString("nametags.background", "transparent"));
        this.nametagBillboard = parseNametagBillboard(config.getString("nametags.billboard", "center"));
        this.nametagOffset = config.getDouble("nametags.offset", 0.25);
        this.nametagLines = config.getStringList("nametags.lines");
    }

    public boolean isNametagsEnabled() {
        return nametagsEnabled;
    }

    public boolean isShowSelfNametag() {
        return showSelfNametag;
    }

    public int getNametagUpdateInterval() {
        return nametagUpdateInterval;
    }

    public int getNametagVisibilityDistance() {
        return nametagVisibilityDistance;
    }

    public int getNametagBackground() {
        return nametagBackground;
    }

    public Billboard getNametagBillboard() {
        return nametagBillboard;
    }

    public double getNametagOffset() {
        return nametagOffset;
    }

    public List<String> getNametagLines() {
        return nametagLines;
    }

    private int parseNametagBackground(String background) {
        if (background == null || background.equalsIgnoreCase("transparent")) return 0x000000;
        background = background.replace("#", "");
        if (background.length() == 6) background = "FF" + background;

        try {
            return (int) Long.parseLong(background, 16);
        } catch (NumberFormatException e) {
            return 0x000000;
        }
    }

    private Billboard parseNametagBillboard(String string) {
        Billboard billboard = null;
        switch (string.toLowerCase()) {
            case "fixed" -> billboard = Billboard.FIXED;
            case "vertical" -> billboard = Billboard.VERTICAL;
            case "horizontal" -> billboard = Billboard.HORIZONTAL;
            case "center" -> billboard = Billboard.CENTER;
        }
        return billboard;
    }
}
