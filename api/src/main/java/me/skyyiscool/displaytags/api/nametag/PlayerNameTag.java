package me.skyyiscool.displaytags.api.nametag;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class PlayerNameTag {
    protected Player player;
    protected NameTagData data;
    protected Set<UUID> viewers;

    public PlayerNameTag(Player player) {
        this.player = player;
        this.data = new NameTagData();
        this.viewers = new HashSet<>();
    }

    public NameTagData getData() {
        return this.data;
    }

    public Player getPlayer() {
        return this.player;
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
