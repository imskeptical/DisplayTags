package me.skyyiscool.displaytags.wrapper;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import me.skyyiscool.displaytags.util.PacketUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

public class EntityWrapper {
    private final int entityId;
    private final UUID uuid;
    private final EntityType type;
    private Location location;

    public EntityWrapper(EntityType type) {
        this.entityId = SpigotReflectionUtil.generateEntityId();
        this.uuid = UUID.randomUUID();
        this.type = type;
        this.location = new Location(Bukkit.getWorlds().getFirst(), 0, 0, 0);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<EntityData<?>> getEntityData() {
        return new ArrayList<>();
    }

    public void spawnFor(UUID viewer) {
        PacketUtil.sendPacket(viewer, this.spawnPacket());
    }

    public void updateFor(UUID viewer) {
        PacketUtil.sendPacket(viewer, this.metadataPacket());
    }

    public void despawnFor(UUID viewer) {
        PacketUtil.sendPacket(viewer, this.destroyPacket());
    }

    public void mountFor(UUID viewer, int vehicleId) {
        PacketUtil.sendPacket(viewer, passengersPacket(vehicleId, this.entityId));
    }

    private WrapperPlayServerSpawnEntity spawnPacket() {
        return new WrapperPlayServerSpawnEntity(
                this.entityId,
                Optional.of(this.uuid),
                this.type,
                SpigotConversionUtil.fromBukkitLocation(this.location).getPosition(),
                location.getPitch(),
                location.getYaw(),
                location.getYaw(),
                0,
                Optional.of(Vector3d.zero())
        );
    }

    private WrapperPlayServerEntityTeleport teleportPacket() {
        return new WrapperPlayServerEntityTeleport(
                this.entityId,
                SpigotConversionUtil.fromBukkitLocation(this.location),
                true
        );
    }

    private WrapperPlayServerEntityMetadata metadataPacket() {
        return new WrapperPlayServerEntityMetadata(
                this.entityId,
                this.getEntityData()
        );
    }

    private WrapperPlayServerSetPassengers passengersPacket(int vehicleId, int... passengers) {
        return new WrapperPlayServerSetPassengers(
                vehicleId,
                passengers
        );
    }

    private WrapperPlayServerDestroyEntities destroyPacket() {
        return new WrapperPlayServerDestroyEntities(this.entityId);
    }
}
