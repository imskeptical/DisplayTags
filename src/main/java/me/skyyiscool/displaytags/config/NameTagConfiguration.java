package me.skyyiscool.displaytags.config;

import me.skyyiscool.displaytags.config.spec.DisplayTagsConfigurationSpec;
import me.skyyiscool.displaytags.wrapper.display.DisplayBillboard;
import me.skyyiscool.displaytags.wrapper.display.TextAlignment;
import org.bukkit.util.Vector;

import java.util.List;

public class NameTagConfiguration {
    private boolean enabled;
    private boolean showToSelf;
    private int updateInterval;
    private int visibilityDistance;

    private List<String> lines;
    private boolean textShadow;
    private boolean seeThrough;
    private TextAlignment textAlignment;
    private String background;
    private DisplayBillboard billboard;
    private Vector offset;
    private Vector scale;

    public void load(DisplayTagsConfigurationSpec config) {
        TextAlignment alignment = TextAlignment.valueOf(config.nametag().display().textAlignment().toUpperCase());
        DisplayBillboard billboard = DisplayBillboard.valueOf(config.nametag().display().billboard().toUpperCase());

        this.enabled = config.nametag().enabled();
        this.showToSelf = config.nametag().showToSelf();
        this.updateInterval = config.nametag().updateInterval();
        this.visibilityDistance = config.nametag().visibilityDistance();
        this.lines = config.nametag().display().lines();
        this.textShadow = config.nametag().display().textShadow();
        this.seeThrough = config.nametag().display().seeThrough();
        this.textAlignment = alignment;
        this.background = config.nametag().display().background();
        this.billboard = billboard;
        this.offset = config.nametag().display().offset().toBukkitVector();
        this.scale = config.nametag().display().scale().toBukkitVector();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean showToSelf() {
        return this.showToSelf;
    }

    public int getUpdateInterval() {
        return this.updateInterval;
    }

    public int getVisibilityDistance() {
        return this.visibilityDistance;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public boolean hasTextShadow() {
        return this.textShadow;
    }

    public boolean isSeeThrough() {
        return this.seeThrough;
    }

    public TextAlignment getTextAlignment() {
        return this.textAlignment;
    }

    public String getBackground() {
        return this.background;
    }

    public DisplayBillboard getBillboard() {
        return this.billboard;
    }

    public Vector getOffset() {
        return this.offset;
    }

    public Vector getScale() {
        return this.scale;
    }
}
