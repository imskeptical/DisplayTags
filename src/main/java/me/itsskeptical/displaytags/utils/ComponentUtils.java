package me.itsskeptical.displaytags.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class ComponentUtils {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    public static Component format(String input) {
        Component legacy = LEGACY_SERIALIZER.deserialize(input.replace('§', '&'));
        String modern = MINI_MESSAGE.serialize(legacy).replace("\\", "");
        return MINI_MESSAGE.deserialize(modern);
    }

    public static Component format(List<String> lines) {
        List<Component> components = new ArrayList<>();
        for (String line : lines) {
            components.add(format(line));
        }

        return Component.join(
                JoinConfiguration.separator(Component.newline()),
                components
        );
    }
}