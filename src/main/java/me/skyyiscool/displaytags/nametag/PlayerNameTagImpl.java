package me.skyyiscool.displaytags.nametag;

import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBundle;
import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.api.PlayerNameTag;
import me.skyyiscool.displaytags.config.NameTagConfiguration;
import me.skyyiscool.displaytags.config.NameTagDisplayConfiguration;
import me.skyyiscool.displaytags.util.ComponentUtil;
import me.skyyiscool.displaytags.util.PacketUtil;
import me.skyyiscool.displaytags.wrapper.display.DisplayBillboard;
import me.skyyiscool.displaytags.wrapper.display.TextDisplayWrapper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayerNameTagImpl extends PlayerNameTag {
    private final TextDisplayWrapper display;
    private final boolean showSelf;

    public PlayerNameTagImpl(Player player) {
        super(player);

        NameTagConfiguration config = DisplayTags.get().config().nametag();
        NameTagDisplayConfiguration displayConfig = config.display();
        DisplayBillboard billboard = displayConfig.billboard();
        Integer background = this.getBackground(displayConfig);

        this.lines = displayConfig.lines();
        this.showSelf = config.showSelf();

        this.display = new TextDisplayWrapper();
        this.display.setTextShadow(displayConfig.hasTextShadow());
        this.display.setTranslation(new Vector(0.0, 0.25, 0.0));
        this.display.setScale(new Vector(0.75, 0.75, 0.75));

        if (billboard != null) this.display.setBillboard(billboard);
        if (background != null) this.display.setBackground(background);
    }

    @Override
    public void spawnFor(UUID viewerId) {
        if (!this.showSelf) return;
        this.viewers.add(viewerId);

        this.display.spawnFor(viewerId);
        this.display.mountFor(viewerId, this.player.getEntityId());
        this.updateFor(viewerId);
    }

    @Override
    public void updateFor(UUID viewerId) {
        List<String> lines = this.lines
                .stream()
                .map((line) -> line
                        .replace("{player}", player.getName())
                        .replace("{health}", String.valueOf(new DecimalFormat("#.##").format(player.getHealth())))
                )
                .toList();

        Component text = ComponentUtil.render(lines);
        this.display.setText(text);

        this.display.updateFor(viewerId);
    }

    @Override
    public void despawnFor(UUID viewerId) {
        if (!this.viewers.contains(viewerId)) return;
        this.viewers.remove(viewerId);
        this.display.despawnFor(viewerId);
    }

    @Override
    public void tick() {
        this.display.setLocation(this.player.getLocation().setRotation(0, 0));

        this.viewers.removeIf((uuid) -> {
            Player viewer = Bukkit.getPlayer(uuid);
            return viewer == null || !viewer.isOnline();
        });

        for (Player viewer : Bukkit.getOnlinePlayers()) {
            boolean visible = this.viewers.contains(viewer.getUniqueId());
            boolean shouldBeVisible = this.shouldBeVisibleTo(viewer);

            if (shouldBeVisible && !visible) {
                this.spawnFor(viewer);
            } else if (!shouldBeVisible && visible) {
                this.despawnFor(viewer);
            } else if (shouldBeVisible) {
                this.updateFor(viewer);
            }
        }
    }

    private boolean shouldBeVisibleTo(Player viewer) {
        if (viewer == null || !viewer.isOnline()) return false;
        if (viewer.getUniqueId().equals(this.player.getUniqueId()) && !this.showSelf) return false;
        if (!viewer.getWorld().getName().equals(this.player.getWorld().getName())) return false;

        if (this.player.isDead()) return false;
        if (this.player.getGameMode() == GameMode.SPECTATOR) return false;

        return true;
    }

    private Integer getBackground(NameTagDisplayConfiguration config) {
        String background = config.background();
        if (background == null || background.equalsIgnoreCase("default")) return null;
        if (background.equalsIgnoreCase("transparent")) return 0;
        if (background.startsWith("#")) background = background.substring(1);

        Color color = Color.fromARGB((int) Long.parseLong(background, 16));
        if (background.length() == 6) color = color.setAlpha(255);

        return color.asARGB();
    }
}
