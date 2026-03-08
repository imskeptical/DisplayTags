package me.skyyiscool.displaytags.wrapper.display;

public enum TextAlignment {
    CENTER(0),
    LEFT(1),
    RIGHT(2);

    public final int value;

    TextAlignment(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
