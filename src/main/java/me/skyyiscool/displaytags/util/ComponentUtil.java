package me.skyyiscool.displaytags.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;

public class ComponentUtil {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final JoinConfiguration JOIN_CONFIG = JoinConfiguration.separator(Component.newline());

    public static Component render(String text) {
        return MINI_MESSAGE.deserialize(text);
    }

    public static Component render(List<String> lines) {
        List<Component> components = lines
                .stream()
                .map(ComponentUtil::render)
                .toList();
        return join(components);
    }

    private static Component join(List<Component> components) {
        return Component.join(JOIN_CONFIG, components);
    }
}
