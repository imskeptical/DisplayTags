package me.itsskeptical.displaytags.listeners;

import io.papermc.paper.event.player.PlayerClientLoadedWorldEvent;
import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.nametags.Nametag;
import me.itsskeptical.displaytags.nametags.NametagManager;
import me.itsskeptical.displaytags.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {
    private final DisplayTags plugin = DisplayTags.getInstance();
    private final NametagManager nametagManager = DisplayTags.getInstance().getNametagManager();

    @EventHandler
    public void onPlayerLoad(PlayerClientLoadedWorldEvent event) {
        Player player = event.getPlayer();
        if (plugin.config().getNametagConfig().isEnabled()) {
            if (PlayerUtils.isNPC(player)) return;

            if (nametagManager.get(player) != null) {
                nametagManager.remove(player);
            }

            nametagManager.create(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Player player = event.getPlayer();
            if (PlayerUtils.isNPC(player)) return;

            nametagManager.remove(player);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Player player = event.getPlayer();
            if (PlayerUtils.isNPC(player)) return;

            Nametag nametag = nametagManager.get(player);
            if (nametag == null) return;

            nametag.hideForAll();
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Player player = event.getPlayer();
            if (PlayerUtils.isNPC(player)) return;

            Nametag nametag = nametagManager.get(player);
            if (nametag == null) return;

            nametag.teleportForAll();
            nametag.updateVisibilityForAll();
        }
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Player player = event.getPlayer();
            if (PlayerUtils.isNPC(player)) return;

            Nametag nametag = nametagManager.get(player);
            if (nametag == null) return;

            nametag.hideForAll();
            nametag.updateVisibilityForAll();
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (plugin.config().getNametagConfig().isEnabled()) {
            Player player = event.getPlayer();
            if (PlayerUtils.isNPC(player)) return;

            Nametag nametag = nametagManager.get(player);
            if (nametag != null) {
                if (event.getNewGameMode() == GameMode.SPECTATOR) {
                    nametag.hideForAll();
                } else if (player.getPreviousGameMode() == GameMode.SPECTATOR) {
                    nametag.updateVisibilityForAll();
                }
            }
        }
    }
}
