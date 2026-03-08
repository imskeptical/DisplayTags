package me.skyyiscool.displaytags.wrapper.display;

public enum DisplayBillboard {
    FIXED(0),
    VERTICAL(1),
    HORIZONTAL(2),
    CENTER(3);

    public final int value;

    DisplayBillboard(int value) {
        this.value = value;
    }
}
