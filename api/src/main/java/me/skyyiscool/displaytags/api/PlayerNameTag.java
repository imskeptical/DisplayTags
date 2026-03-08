package me.skyyiscool.displaytags.api;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class PlayerNameTag {
    protected Player player;
    protected Set<UUID> viewers;

    protected List<String> lines;

    public PlayerNameTag(Player player) {
        this.player = player;
        this.viewers = new HashSet<>();
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public abstract void spawnFor(UUID viewerId);
    public abstract void updateFor(UUID viewerId);
    public abstract void despawnFor(UUID viewerId);
    public abstract void tick();

    public void spawnFor(Player viewer) {
        this.spawnFor(viewer.getUniqueId());
    }

    public void updateFor(Player viewer) {
        this.updateFor(viewer.getUniqueId());
    }

    public void despawnFor(Player viewer) {
        this.despawnFor(viewer.getUniqueId());
    }

    public void updateForViewers() {
        for (UUID uuid : this.viewers) {
            this.updateFor(uuid);
        }
    }

    public void despawnForViewers() {
        for (UUID uuid : this.viewers) {
            this.despawnFor(uuid);
        }
    }
}
