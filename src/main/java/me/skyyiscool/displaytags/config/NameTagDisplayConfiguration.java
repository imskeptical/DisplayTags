package me.skyyiscool.displaytags.config;

import me.skyyiscool.displaytags.wrapper.display.DisplayBillboard;
import me.skyyiscool.displaytags.wrapper.display.TextAlignment;
import revxrsal.spec.annotation.Comment;
import revxrsal.spec.annotation.ConfigSpec;
import revxrsal.spec.annotation.Key;
import revxrsal.spec.annotation.Order;

import java.util.List;

@ConfigSpec
public interface NameTagDisplayConfiguration {
    @Order(1) @Key("lines")
    @Comment({
        "The lines of text shown on the name tag, top to bottom.",
        "Supports MiniMessage formatting: https://webui.advntr.dev/",
        "PlaceholderAPI is supported for dynamic data.",
        "",
        "Built-in placeholders (no extra plugin needed):",
        "{player} - Name of the player",
        "{health} - Health of the player"
    })
    default List<String> lines() {
        return List.of(
                "<gray>{player}</gray>",
                "<red>❤ <white>{health}"
        );
    }

    @Order(2) @Key("text-shadow")
    @Comment({
            "Enable shadowed text for the text display of the name tag.",
            "Available values: true, false"
    })
    default boolean hasTextShadow() {
        return true;
    }

    @Order(3) @Key("see-through")
    @Comment({
            "Should the text display of name tags be see through?",
            "Available values: true, false"
    })
    default boolean isSeeThrough() {
        return false;
    }

    @Order(4) @Key("text-alignment")
    @Comment({
            "The alignment of the name tag's text display.",
            "Available values: \"left\", \"right\", \"center\""
    })
    default TextAlignment textAlignment() {
        return TextAlignment.CENTER;
    }

    @Order(5) @Key("background")
    @Comment({
            "The background color of the name tag's text display.",
            "Use a hex color (like \"#FFFFFF\" for white), or \"transparent\" for no background.",
            "Available values: \"default\", \"transparent\", hex codes."
    })
    default String background() {
        return "default";
    }

    @Order(6) @Key("billboard")
    @Comment({
            "The billboard of the name tag's display.",
            "Available values: fixed, vertical, horizontal, center"
    })
    default DisplayBillboard billboard() {
        return DisplayBillboard.VERTICAL;
    }
}
