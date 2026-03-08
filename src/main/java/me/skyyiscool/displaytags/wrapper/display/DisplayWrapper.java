package me.skyyiscool.displaytags.wrapper.display;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import me.skyyiscool.displaytags.util.ConversionUtil;
import me.skyyiscool.displaytags.wrapper.EntityWrapper;
import org.bukkit.util.Vector;

import java.util.List;

public class DisplayWrapper extends EntityWrapper {
    private Vector translation = new Vector(0, 0, 0);
    private Vector scale = new Vector(1, 1, 1);
    private DisplayBillboard billboard = DisplayBillboard.FIXED;

    public DisplayWrapper(EntityType type) {
        super(type);
    }

    @Override
    public List<EntityData<?>> getEntityData() {
        List<EntityData<?>> data = super.getEntityData();

        data.add(new EntityData<>(
                11,
                EntityDataTypes.VECTOR3F,
                ConversionUtil.fromBukkitVector(this.translation)
        ));
        data.add(new EntityData<>(
                12,
                EntityDataTypes.VECTOR3F,
                ConversionUtil.fromBukkitVector(this.scale)
        ));
        data.add(new EntityData<>(
                15,
                EntityDataTypes.BYTE,
                (byte) this.billboard.value
        ));

        return data;
    }

    public Vector getTranslation() {
        return translation;
    }

    public void setTranslation(Vector translation) {
        this.translation = translation;
    }

    public Vector getScale() {
        return scale;
    }

    public void setScale(Vector scale) {
        this.scale = scale;
    }

    public DisplayBillboard getBillboard() {
        return billboard;
    }

    public void setBillboard(DisplayBillboard billboard) {
        this.billboard = billboard;
    }
}
