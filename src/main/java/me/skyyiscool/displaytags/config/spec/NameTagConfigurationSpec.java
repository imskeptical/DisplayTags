package me.skyyiscool.displaytags.config.spec;

import revxrsal.spec.annotation.Comment;
import revxrsal.spec.annotation.ConfigSpec;
import revxrsal.spec.annotation.Key;
import revxrsal.spec.annotation.Order;

@ConfigSpec
public interface NameTagConfigurationSpec {
    @Order(1) @Key("enabled")
    @Comment({
            "Enable custom player name tags.",
            "Set this to false if you want Minecraft's vanilla player name tags to be shown instead."
    })
    default boolean enabled() {
        return true;
    }

    @Order(2) @Key("show-to-self")
    @Comment({
            "Should players see their own name tag?",
            "Set this to true to show your own name tag above your head (in second/third person perspective).",
            "Set this to false if you do not want players seeing their own name tag."
    })
    default boolean showToSelf() {
        return true;
    }

    @Order(3) @Key("update-interval")
    @Comment({
            "How often name tags should update (in seconds).",
            "Lower = more frequent updates = more accurate, but slightly more CPU usage.",
            "Recommended: 1 second"
    })
    default int updateInterval() {
        return 1;
    }

    @Order(4) @Key("visibility-distance")
    @Comment({
            "How far away other players must be to see someone's name tag (in blocks).",
            "Example: 32 = players can see nametags from up to 32 blocks away."
    })
    default int visibilityDistance() {
        return 32;
    }

    @Order(5) @Key("display")
    @Comment("Name Tag Display Properties")
    NameTagDisplayConfigurationSpec display();
}
