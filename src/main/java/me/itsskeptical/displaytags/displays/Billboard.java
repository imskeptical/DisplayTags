package me.itsskeptical.displaytags.displays;

public enum Billboard {
    FIXED(0),
    VERTICAL(1),
    HORIZONTAL(2),
    CENTER(3);

    public final int value;

    Billboard(int value) {
        this.value = value;
    }
}
