package me.itsskeptical.displaytags.nametags;

import com.github.retrooper.packetevents.util.Vector3f;
import me.clip.placeholderapi.PlaceholderAPI;
import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.config.NametagConfig;
import me.itsskeptical.displaytags.entities.ClientTextDisplay;
import me.itsskeptical.displaytags.entities.DisplayBillboard;
import me.itsskeptical.displaytags.entities.TextAlignment;
import me.itsskeptical.displaytags.utils.ComponentUtils;
import me.itsskeptical.displaytags.utils.handlers.VanillaNametagHandler;
import me.itsskeptical.displaytags.utils.helpers.DependencyHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class Nametag {
    private final DisplayTags plugin;
    private final Player player;
    private final ClientTextDisplay display;
    private final Set<UUID> viewers;

    public Nametag(Player player) {
        this.plugin = DisplayTags.getInstance();
        this.player = player;
        this.display = new ClientTextDisplay(player.getLocation().setRotation(0, 0));
        this.viewers = new HashSet<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void showForAll() {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            this.show(viewer);
        }
    }

    public void hideForAll() {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            this.hide(viewer);
        }
    }

    public void updateVisibility() {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            boolean shouldSee = shouldSee(viewer);
            boolean isVisible = this.viewers.contains(viewer.getUniqueId());

            if (shouldSee) {
                if (isVisible) {
                    this.update(viewer);
                } else {
                    this.show(viewer);
                }
            } else {
                if (isVisible) this.hide(viewer);
            }
        }
    }

    private boolean shouldSee(Player viewer) {
        if (plugin.config().getNametagConfig().shouldHideSelf() && player.getUniqueId().equals(viewer.getUniqueId())) return false;
        if (player.isDead() || player.getGameMode().equals(GameMode.SPECTATOR)) return false;
        if (!viewer.isOnline() || viewer.isDead()) return false;
        if (!viewer.getWorld().getName().equals(player.getWorld().getName())) return false;

        int visibilityDistance = plugin.config().getNametagConfig().getVisibilityDistance();
        return viewer.getLocation().distanceSquared(player.getLocation()) < visibilityDistance * visibilityDistance;
    }

    public void show(Player viewer) {
        VanillaNametagHandler.hide(player, viewer);
        if (plugin.config().getNametagConfig().shouldHideSelf() && player.getUniqueId().equals(viewer.getUniqueId())) return;

        this.viewers.add(viewer.getUniqueId());
        this.display.spawn(viewer);
        this.update(viewer);
    }

    public void hide(Player viewer) {
        this.viewers.remove(viewer.getUniqueId());
        this.display.despawn(viewer);
    }

    public void update(Player viewer) {
        NametagConfig config = plugin.config().getNametagConfig();
        boolean textShadow = config.hasTextShadow();
        boolean seeThrough = config.isSeeThrough();
        String textAlignment = config.getTextAlignment();
        String billboard = config.getBillboard();
        Vector scale = config.getScale();

        this.display.setLocation(player.getLocation());
        this.display.setText(getText());
        this.display.setTextShadow(textShadow);
        this.display.setTextAlignment(TextAlignment.valueOf(textAlignment.toUpperCase()));
        this.display.setSeeThrough(seeThrough);
        this.display.setBackground(getBackground());
        this.display.setBillboard(DisplayBillboard.valueOf(billboard.toUpperCase()));
        this.display.setTranslation(new Vector3f(0, (float) 0.25, 0));
        this.display.setScale(scale);
        this.display.mount(this.player, viewer);
        this.display.update(viewer);
    }

    private Component getText() {
        List<String> lines = new ArrayList<>();
        List<String> rawLines = plugin.config().getNametagConfig().getLines();
        rawLines.forEach((line) -> {
            line = line
                    .replace("{player}", player.getName())
                    .replace("{health}", String.valueOf(player.getHealth()));
            if (DependencyHelper.isPlaceholderAPIEnabled()) {
                line = PlaceholderAPI.setPlaceholders(this.player, line);
            }

            lines.add(line);
        });
        return ComponentUtils.format(lines);
    }

    private int getBackground() {
        String background = plugin.config().getNametagConfig().getBackground();
        if (background == null || background.equalsIgnoreCase("default")) return -1;
        if (background.equalsIgnoreCase("transparent")) return 0;
        if (background.startsWith("#")) background = background.substring(1);

        Color color = Color.fromARGB((int) Long.parseLong(background, 16));
        if (background.length() == 6) color = color.setAlpha(255);
        return color.asARGB();
    }
}
