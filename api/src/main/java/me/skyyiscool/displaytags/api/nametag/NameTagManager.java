package me.skyyiscool.displaytags.api.nametag;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface NameTagManager {
    PlayerNameTag createNameTag(Player player);
    PlayerNameTag getByPlayer(Player player);
    void removeNameTag(Player player);

    Collection<PlayerNameTag> getAll();
}
