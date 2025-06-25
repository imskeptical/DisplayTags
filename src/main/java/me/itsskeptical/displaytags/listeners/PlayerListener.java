package me.itsskeptical.displaytags.listeners;

import io.papermc.paper.event.player.PlayerClientLoadedWorldEvent;
import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.nametags.Nametag;
import me.itsskeptical.displaytags.nametags.NametagManager;
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
        if (event.getPlayer().hasPermission("displaytags.admin")) {
            plugin.checkForUpdates(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerLoad(PlayerClientLoadedWorldEvent event) {
        Player player = event.getPlayer();
        if (plugin.config().getNametagConfig().isEnabled()) {
            nametagManager.create(player);
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
            nametag.updateVisibilityForAll();
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Nametag nametag = nametagManager.get(event.getPlayer());
            if (nametag != null) {
                if (event.getNewGameMode() == GameMode.SPECTATOR) {
                    nametag.hideForAll();
                } else if (event.getPlayer().getPreviousGameMode() == GameMode.SPECTATOR) {
                    nametag.updateVisibilityForAll();
                }
            }
        }
    }
}
