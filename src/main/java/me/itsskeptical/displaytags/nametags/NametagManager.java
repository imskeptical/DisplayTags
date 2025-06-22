package me.itsskeptical.displaytags.nametags;

import me.itsskeptical.displaytags.utils.handlers.VanillaNametagHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NametagManager {
    private final Map<UUID, Nametag> nametags;

    public NametagManager() {
        this.nametags = new ConcurrentHashMap<>();
    }

    public Nametag get(Player player) {
        return this.nametags.get(player.getUniqueId());
    }

    public Collection<Nametag> getAll() {
        return this.nametags.values();
    }

    public void create(Player player) {
        Nametag nametag = new Nametag(player);
        nametag.updateVisibilityForAll();
        this.nametags.put(player.getUniqueId(), nametag);
    }

    public void remove(Player player) {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            VanillaNametagHandler.show(player, viewer);
        }

        Nametag nametag = nametags.get(player.getUniqueId());
        if (nametag != null) {
            nametag.hideForAll();
            nametags.remove(player.getUniqueId());
        }
    }

    public void createAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.create(player);
        }
    }

    public void removeAll() {
        for (Nametag nametag : nametags.values()) {
            this.remove(nametag.getPlayer());
        }
    }
}
