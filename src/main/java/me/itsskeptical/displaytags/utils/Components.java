package me.itsskeptical.displaytags.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Components {
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component mm(String text) {
        return mm.deserialize(text);
    }

    public static Component mm(List<String> lines) {
        List<Component> components = new ArrayList<>();
        for (String line : lines) {
            components.add(mm(line));
        }

        return Component.join(JoinConfiguration.separator(Component.newline()), components);
    }
}
