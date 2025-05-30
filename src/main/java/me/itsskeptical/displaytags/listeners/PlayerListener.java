package me.itsskeptical.displaytags.listeners;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.nametags.Nametag;
import me.itsskeptical.displaytags.nametags.NametagManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
    private final NametagManager nametags = DisplayTags.getNametagManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.nametags.create(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Nametag nametag = this.nametags.get(event.getPlayer());
        nametag.hideForAll();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Nametag nametag = this.nametags.get(event.getPlayer());
        nametag.updateForAll();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.nametags.delete(event.getPlayer());
    }
}
