package me.skyyiscool.displaytags.api.events;

import me.skyyiscool.displaytags.api.nametag.PlayerNameTag;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class NameTagRemoveEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final PlayerNameTag tag;

    public NameTagRemoveEvent(PlayerNameTag tag) {
        this.tag = tag;
    }

    public PlayerNameTag getNameTag() {
        return this.tag;
    }

    public Player getPlayer() {
        return this.tag.getPlayer();
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
