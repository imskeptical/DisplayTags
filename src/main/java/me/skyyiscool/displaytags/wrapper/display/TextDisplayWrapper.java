package me.skyyiscool.displaytags.wrapper.display;

import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import net.kyori.adventure.text.Component;

import java.util.List;

public class TextDisplayWrapper extends DisplayWrapper {
    private Component text = Component.empty();
    private int lineWidth = 200; // Default line width
    private int background = 1073741824; // Default background
    private int textOpacity = -1; // Default text opacity
    private int flags = 0;

    public TextDisplayWrapper() {
        super(EntityTypes.TEXT_DISPLAY);
    }

    @Override
    public List<EntityData<?>> getEntityData() {
        List<EntityData<?>> data = super.getEntityData();

        data.add(new EntityData<>(
                23,
                EntityDataTypes.ADV_COMPONENT,
                text
        ));
        data.add(new EntityData<>(
                24,
                EntityDataTypes.INT,
                this.lineWidth
        ));
        data.add(new EntityData<>(
                25,
                EntityDataTypes.INT,
                this.background
        ));
        data.add(new EntityData<>(
                26,
                EntityDataTypes.BYTE,
                (byte) this.textOpacity
        ));
        data.add(new EntityData<>(
                27,
                EntityDataTypes.BYTE,
                (byte) this.flags
        ));

        return data;
    }

    public Component getText() {
        return this.text;
    }

    public void setText(Component text) {
        this.text = text;
    }

    public int getLineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getBackground() {
        return this.background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getTextOpacity() {
        return this.textOpacity;
    }

    public void setTextOpacity(int opacity) {
        this.textOpacity = opacity;
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

    private void setFlag(int mask, boolean enabled) {
        if (enabled) {
            flags |= mask;
        } else {
            flags &= ~mask;
        }
    }
}
