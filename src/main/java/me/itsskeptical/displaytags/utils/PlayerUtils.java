package me.itsskeptical.displaytags.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerUtils {
    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> !isNPC(player))
                .toList();
    }

    public static boolean isNPC(Player player) {
        return player.hasMetadata("NPC");
    }
}
