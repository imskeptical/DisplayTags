package me.skyyiscool.displaytags.config.spec;

import me.skyyiscool.displaytags.config.typings.ConfigurationVector;
import me.skyyiscool.displaytags.wrapper.display.TextAlignment;
import org.bukkit.entity.Display;
import revxrsal.spec.annotation.Comment;
import revxrsal.spec.annotation.ConfigSpec;
import revxrsal.spec.annotation.Key;
import revxrsal.spec.annotation.Order;

import java.util.List;

@ConfigSpec
public interface NameTagDisplayConfigurationSpec {
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
    default boolean textShadow() {
        return true;
    }

    @Order(3) @Key("see-through")
    @Comment({
            "Should the text display of name tags be see through?",
            "Available values: true, false"
    })
    default boolean seeThrough() {
        return false;
    }

    @Order(4) @Key("text-alignment")
    @Comment({
            "The alignment of the name tag's text display.",
            "Available values: \"left\", \"right\", \"center\""
    })
    default String textAlignment() {
        return TextAlignment.CENTER.name().toLowerCase();
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
    default String billboard() {
        return Display.Billboard.VERTICAL.name().toLowerCase();
    }

    @Order(7) @Key("offset")
    @Comment({
        "The offset of the name tag's display relative to the player's location.",
        "This is measured in blocks. (so, 0.5 = half of a block, 1.0 = a full block)",
        "Set y to 0.25 to move the name tag a little above the player's head so it looks similar to Minecraft's vanilla player name tags.",
        "X = Left/Right",
        "Y = Up/Down",
        "Z = Forward/Backward",
    })
    default ConfigurationVector offset() {
        return new ConfigurationVector(0, 0.25, 0);
    }

    @Order(8) @Key("scale")
    @Comment({
            "The scale of the name tag's display.",
            "This can be used to change the size of the nametag."
    })
    default ConfigurationVector scale() {
        return new ConfigurationVector(1, 1, 1);
    }
}
