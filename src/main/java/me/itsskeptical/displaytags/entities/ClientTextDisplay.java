package me.itsskeptical.displaytags.entities;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.List;

public class ClientTextDisplay extends ClientDisplay {
    private Component text;
    private int background = -1;
    private int flags = 0;

    public ClientTextDisplay(Location location) {
        super(EntityType.TEXT_DISPLAY, location);
    }

    public Component getText() {
        return this.text;
    }

    public void setText(Component text) {
        this.text = text;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setTextShadow(boolean enabled) {
        setFlag(0x01, enabled);
    }

    public void setSeeThrough(boolean enabled) {
        setFlag(0x02, enabled);
    }

    public void setTextAlignment(TextAlignment alignment) {
        flags &= ~(0x08 | 0x10);
        switch (alignment) {
            case CENTER -> {}
            case LEFT -> flags |= 0x08;
            case RIGHT -> flags |= 0x10;
        }
    }

    @Override
    public List<EntityData<?>> getEntityData() {
        List<EntityData<?>> data = super.getEntityData();
        if (this.text != null) data.add(new EntityData<>(
                23,
                EntityDataTypes.ADV_COMPONENT,
                this.text
        ));
        if (this.background != -1) data.add(new EntityData<>(
                25,
                EntityDataTypes.INT,
                this.background
        ));
        if (this.flags != 0) data.add(new EntityData<>(
                27,
                EntityDataTypes.BYTE,
                (byte) this.flags
        ));
        return data;
    }

    private void setFlag(int mask, boolean enabled) {
        if (enabled) {
            flags |= mask;
        } else {
            flags &= ~mask;
        }
    }
}
