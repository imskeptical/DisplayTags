package me.itsskeptical.displaytags.displays;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPassengers;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientTextDisplay {
    private int entityId;
    private UUID uuid;
    private Location location;

    private Billboard billboard;
    private Vector3f translation;
    private Component text;
    private int background;
    private int flags = 0;

    public ClientTextDisplay(Location location) {
        this.location = location;

        this.entityId = SpigotReflectionUtil.generateEntityId();
        this.uuid = UUID.randomUUID();
    }

    public void setBillboard(Billboard billboard) {
        this.billboard = billboard;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setText(Component text) {
        this.text = text;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setTextShadow(boolean enabled) {
        setFlag(0x01, enabled);
    }

    public void spawn(Player player) {
        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity(
                this.entityId,
                this.uuid,
                EntityTypes.TEXT_DISPLAY,
                SpigotConversionUtil.fromBukkitLocation(this.location),
                0,
                0,
                null
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    public void update(Player player) {
        List<EntityData<?>> data = new ArrayList<>();
        if (this.billboard != null) data.add(new EntityData<>(
                15,
                EntityDataTypes.BYTE,
                (byte) this.billboard.value
        ));
        if (this.translation != null) data.add(new EntityData<>(
                11,
                EntityDataTypes.VECTOR3F,
                this.translation
        ));
        if (this.text != null) data.add(new EntityData<>(
                23,
                EntityDataTypes.ADV_COMPONENT,
                this.text
        ));
        data.add(new EntityData<>(
                25,
                EntityDataTypes.INT,
                this.background
        ));

        data.add(new EntityData<>(
                27,
                EntityDataTypes.BYTE,
                (byte) this.flags
        ));

        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(
                this.entityId,
                data
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    public void mount(Player player, Player viewer) {
        WrapperPlayServerSetPassengers packet = new WrapperPlayServerSetPassengers(
                player.getEntityId(),
                new int[] { this.entityId }
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
    }

    public void despawn(Player player) {
        WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(this.entityId);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    private void setFlag(int mask, boolean enabled) {
        if (enabled) {
            flags |= mask;
        } else {
            flags &= ~mask;
        }
    }
}
