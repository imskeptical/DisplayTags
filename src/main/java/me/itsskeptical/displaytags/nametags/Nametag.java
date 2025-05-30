package me.itsskeptical.displaytags.nametags;

import com.github.retrooper.packetevents.util.Vector3f;
import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.displays.ClientTextDisplay;
import me.itsskeptical.displaytags.utils.Components;
import me.itsskeptical.displaytags.utils.PlaceholdersHelper;
import me.itsskeptical.displaytags.utils.VanillaNametagHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
        this.display = new ClientTextDisplay(player.getLocation().setRotation(0, 0).add(new Vector(0, 2, 0)));
        this.viewers = new HashSet<>();
    }

    public Player getPlayer() {
        return this.player;
    }
    public Set<UUID> getViewers() {
        return this.viewers;
    }

    public void show(Player viewer) {
        if (this.player.isDead()) return;
        if ((viewer.getUniqueId() == this.player.getUniqueId()) && !plugin.config().isShowSelfNametag()) return;

        VanillaNametagHandler.hideNametag(this.player, viewer);
        this.display.spawn(viewer);
        this.display.mount(this.player, viewer);
        this.viewers.add(viewer.getUniqueId());
        this.update(viewer);
    }

    public void hide(Player viewer) {
        this.display.despawn(viewer);
        this.viewers.remove(viewer.getUniqueId());
    }

    public void update(Player viewer) {
        if (this.player.isDead()) return;
        if ((viewer.getUniqueId() == this.player.getUniqueId()) && !plugin.config().isShowSelfNametag()) return;

        this.display.setTextShadow(true);
        this.display.setBillboard(plugin.config().getNametagBillboard());
        this.display.setTranslation(new Vector3f(0f, (float) plugin.config().getNametagOffset(), 0f));
        this.display.setBackground(plugin.config().getNametagBackground());
        this.display.setText(getNametagText());
        this.display.update(viewer);
        this.display.mount(this.player, viewer);
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

    public void updateForAll() {
        if (this.player.isDead()) return;

        Iterator<UUID> iterator = viewers.iterator();
        while (iterator.hasNext()) {
            UUID uuid = iterator.next();
            Player viewer = Bukkit.getPlayer(uuid);
            if (viewer == null || !viewer.isOnline() || !viewer.getWorld().getName().equals(this.player.getWorld().getName())) {
                iterator.remove();
            }
        }

        for (Player viewer : Bukkit.getOnlinePlayers()) {
            if ((viewer.getUniqueId() == this.player.getUniqueId()) && !plugin.config().isShowSelfNametag()) return;

            boolean isVisible = viewers.contains(viewer.getUniqueId());
            boolean shouldBeVisible = canSee(viewer);

            if (shouldBeVisible) {
                if (!viewer.isDead()) {
                    if (!isVisible) {
                        this.show(viewer);
                    } else {
                        this.update(viewer);
                    }
                }
            } else {
                hide(viewer);
            }
        }
    }

    private boolean canSee(Player viewer) {
        if (this.player == null || this.player.isDead() || viewer.isDead() || !viewer.getWorld().getName().equals(player.getWorld().getName())) return false;

        double distanceSq = viewer.getLocation().distanceSquared(this.player.getLocation());
        int visibilityDistance = plugin.config().getNametagVisibilityDistance();
        return distanceSq < visibilityDistance * visibilityDistance;
    }

    public Component getNametagText() {
        List<String> lines = plugin.config().getNametagLines();
        if (lines == null || lines.isEmpty()) return Component.empty();

        List<String> text = new ArrayList<>();
        for (String line : lines) {
            text.add(PlaceholdersHelper.parse(line, this.player));
        }

        return Components.mm(text);
    }
}
