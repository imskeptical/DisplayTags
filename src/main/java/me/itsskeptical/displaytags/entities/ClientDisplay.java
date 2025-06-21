package me.itsskeptical.displaytags.entities;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.util.Vector3f;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.List;

public class ClientDisplay extends ClientEntity {
    private Vector3f translation;
    private Vector3f scale;
    private DisplayBillboard billboard;

    public ClientDisplay(EntityType type, Location location) {
        super(type, location);
    }

    public Vector3f getTranslation() {
        return this.translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector scale) {
        this.scale = new Vector3f().add(
                (float) scale.getX(),
                (float) scale.getY(),
                (float) scale.getZ()
        );
    }

    public DisplayBillboard getBillboard() {
        return this.billboard;
    }

    public void setBillboard(DisplayBillboard billboard) {
        this.billboard = billboard;
    }

    @Override
    public List<EntityData<?>> getEntityData() {
        List<EntityData<?>> data = super.getEntityData();
        if (this.scale != null) data.add(new EntityData<>(
                12,
                EntityDataTypes.VECTOR3F,
                this.scale
        ));
        if (this.translation != null) data.add(new EntityData<>(
                11,
                EntityDataTypes.VECTOR3F,
                this.translation
        ));
        if (this.billboard != null) data.add(new EntityData<>(
                15,
                EntityDataTypes.BYTE,
                (byte) this.billboard.value
        ));
        return data;
    }
}
