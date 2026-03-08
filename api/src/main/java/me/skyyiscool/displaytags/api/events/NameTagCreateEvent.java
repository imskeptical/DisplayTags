package me.skyyiscool.displaytags.api.events;

import me.skyyiscool.displaytags.api.PlayerNameTag;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class NameTagCreateEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final PlayerNameTag tag;

    public NameTagCreateEvent(PlayerNameTag tag) {
        this.tag = tag;
    }

    public PlayerNameTag getNameTag() {
        return this.tag;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
