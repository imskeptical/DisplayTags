package me.itsskeptical.displaytags.listeners;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.nametags.Nametag;
import me.itsskeptical.displaytags.nametags.NametagManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
    private final DisplayTags plugin = DisplayTags.getInstance();
    private final NametagManager nametagManager = DisplayTags.getInstance().getNametagManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            // Delay by 10 ticks
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Player player = event.getPlayer();
                nametagManager.create(player);
            }, 10L);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Player player = event.getPlayer();
            nametagManager.remove(player);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Nametag nametag = nametagManager.get(event.getPlayer());
            nametag.hideForAll();
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Nametag nametag = nametagManager.get(event.getPlayer());
            nametag.updateVisibility();
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Nametag nametag = nametagManager.get(event.getPlayer());
            if (event.getNewGameMode() == GameMode.SPECTATOR) {
                nametag.hideForAll();
            } else if (event.getPlayer().getPreviousGameMode() == GameMode.SPECTATOR) {
                nametag.updateVisibility();
            }
        }
    }
}
