package me.itsskeptical.displaytags.entities;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPassengers;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientEntity {
    private final int entityId;
    private final UUID uuid;

    private final EntityType type;
    private Location location;

    public ClientEntity(org.bukkit.entity.EntityType type, Location location) {
        this.entityId = SpigotReflectionUtil.generateEntityId();
        this.uuid = UUID.randomUUID();
        this.type = SpigotConversionUtil.fromBukkitEntityType(type);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<EntityData<?>> getEntityData() {
        return new ArrayList<>();
    }

    public void spawn(Player... viewers) {
        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity(
                this.entityId,
                this.uuid,
                this.type,
                SpigotConversionUtil.fromBukkitLocation(this.location),
                this.location.getYaw(),
                0,
                null
        );
        this.sendPacket(packet, viewers);
    }

    public void update(Player... viewers) {
        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(
                this.entityId,
                this.getEntityData()
        );
        this.sendPacket(packet, viewers);
    }

    public void despawn(Player... viewers) {
        WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(
                this.entityId
        );
        this.sendPacket(packet, viewers);
    }

    public void mount(Entity entity, Player... viewers) {
        WrapperPlayServerSetPassengers packet = new WrapperPlayServerSetPassengers(
                entity.getEntityId(),
                new int[] { this.entityId }
        );
        this.sendPacket(packet, viewers);
    }

    private void sendPacket(PacketWrapper<?> packet, Player... players) {
        for (Player player : players) {
            PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
        }
    }
}
