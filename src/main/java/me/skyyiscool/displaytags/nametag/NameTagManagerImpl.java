package me.skyyiscool.displaytags.nametag;

import me.skyyiscool.displaytags.api.NameTagManager;
import me.skyyiscool.displaytags.api.PlayerNameTag;
import me.skyyiscool.displaytags.api.events.NameTagCreateEvent;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NameTagManagerImpl implements NameTagManager {
    private final Map<UUID, PlayerNameTag> tags = new HashMap<>();

    @Override
    public PlayerNameTag createNameTag(Player player) {
        PlayerNameTag tag = new PlayerNameTagImpl(player);

        NameTagCreateEvent event = new NameTagCreateEvent(tag);
        event.callEvent();

        if (tags.containsKey(player.getUniqueId())) this.removeNameTag(player);
        tags.put(player.getUniqueId(), tag);

        return tag;
    }

    @Override
    public PlayerNameTag getByPlayer(Player player) {
        return this.tags.get(player.getUniqueId());
    }

    @Override
    public Collection<PlayerNameTag> getAll() {
        return this.tags.values();
    }

    @Override
    public void removeNameTag(Player player) {
        PlayerNameTag tag = tags.get(player.getUniqueId());
        if (tag == null) return;

        tag.despawnForViewers();
        tags.remove(player.getUniqueId());
    }
}
