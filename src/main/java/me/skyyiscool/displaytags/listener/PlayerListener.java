package me.skyyiscool.displaytags.listener;

import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.api.nametag.PlayerNameTag;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {
    private final DisplayTags plugin;

    public PlayerListener(DisplayTags plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            this.plugin.getNameTagManager().createNameTag(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            this.plugin.getNameTagManager().removeNameTag(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerSneakToggle(PlayerToggleSneakEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            PlayerNameTag tag = this.plugin.getNameTagManager().getByPlayer(event.getPlayer());
            if (tag == null) return;

            if (event.isSneaking()) {
                tag.getData().setTextOpacity(50);
            } else {
                tag.getData().setTextOpacity(-1);
            }

            tag.updateForViewers();
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            PlayerNameTag tag = this.plugin.getNameTagManager().getByPlayer(event.getPlayer());
            if (tag == null) return;

            tag.despawnForViewers();
            tag.tick();
        }
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            Player player = event.getPlayer();
            PlayerNameTag tag = this.plugin.getNameTagManager().getByPlayer(player);
            if (tag == null) return;

            GameMode previousGameMode = player.getGameMode();
            GameMode newGameMode = event.getNewGameMode();

            // If the player was previously in spectator mode, update the player's name tag.
            if (previousGameMode == GameMode.SPECTATOR) {
                tag.tick();
            }

            // If the player is now entering spectator mode, despawn the player's name tag.
            if (newGameMode == GameMode.SPECTATOR) {
                tag.despawnForViewers();
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            PlayerNameTag tag = this.plugin.getNameTagManager().getByPlayer(event.getPlayer());
            if (tag == null) return;

            tag.despawnForViewers();
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plugin.config().nametag().isEnabled()) {
            PlayerNameTag tag = this.plugin.getNameTagManager().getByPlayer(event.getPlayer());
            if (tag == null) return;

            tag.tick();
        }
    }
}
