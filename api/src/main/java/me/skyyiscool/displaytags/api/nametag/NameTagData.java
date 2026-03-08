package me.skyyiscool.displaytags.api.nametag;

import me.skyyiscool.displaytags.api.Util;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Vector;

import java.util.List;

public class NameTagData {
    private boolean showToSelf = true;
    private int visibilityDistance = 32;

    private List<String> lines = List.of();
    private TextDisplay.TextAlignment textAlignment = TextDisplay.TextAlignment.CENTER;
    private Display.Billboard billboard = Display.Billboard.FIXED;

    private boolean textShadow = false;
    private boolean seeThrough = false;
    private int background = 1073741824;
    private int textOpacity = -1;

    private Vector translation = new Vector(0, 0, 0);
    private Vector scale = new Vector(1, 1, 1);

    public boolean shouldShowToSelf() {
        return this.showToSelf;
    }

    public void setShowToSelf(boolean showToSelf) {
        this.showToSelf = showToSelf;
    }

    public int getVisibilityDistance() {
        return this.visibilityDistance;
    }

    public void setVisibilityDistance(int visibilityDistance) {
        this.visibilityDistance = visibilityDistance;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public TextDisplay.TextAlignment getTextAlignment() {
        return this.textAlignment;
    }

    public void setTextAlignment(TextDisplay.TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public Display.Billboard getBillboard() {
        return this.billboard;
    }

    public void setBillboard(Display.Billboard billboard) {
        this.billboard = billboard;
    }

    public boolean hasTextShadow() {
        return this.textShadow;
    }

    public void setTextShadow(boolean textShadow) {
        this.textShadow = textShadow;
    }

    public boolean isSeeThrough() {
        return this.seeThrough;
    }

    public void setSeeThrough(boolean seeThrough) {
        this.seeThrough = seeThrough;
    }

    public int getBackground() {
        return this.background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setBackground(String background) {
        this.background = Util.parseDisplayBackground(background);
    }

    public int getTextOpacity() {
        return this.textOpacity;
    }

    public void setTextOpacity(int textOpacity) {
        this.textOpacity = textOpacity;
    }

    public Vector getTranslation() {
        return this.translation;
    }

    public void setTranslation(Vector translation) {
        this.translation = translation;
    }

    public Vector getScale() {
        return this.scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }
}
