package me.itsskeptical.displaytags.nametags;

import me.itsskeptical.displaytags.DisplayTags;
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
        return nametags.get(player.getUniqueId());
    }

    public Collection<Nametag> getAll() {
        return nametags.values();
    }

    public boolean has(Player player) {
        return nametags.containsKey(player.getUniqueId());
    }

    public void createAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.create(player);
        }
    }

    public void deleteAll() {
        for (Nametag nametag : nametags.values()) {
            this.delete(nametag.getPlayer());
        }
    }

    public void create(Player player) {
        Nametag nametag = new Nametag(player);
        nametag.showForAll();
        this.nametags.put(player.getUniqueId(), nametag);
    }

    public void delete(Player player) {
        Nametag nametag = this.nametags.get(player.getUniqueId());
        nametag.hideForAll();
        this.nametags.remove(player.getUniqueId());
    }
}
