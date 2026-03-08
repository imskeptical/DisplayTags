package me.skyyiscool.displaytags.api;

import org.bukkit.Color;

public class Util {
    public static Integer parseDisplayBackground(String background) {
        if (background == null || background.equalsIgnoreCase("default")) return 1073741824;
        if (background.equalsIgnoreCase("transparent")) return 0;
        if (background.startsWith("#")) background = background.substring(1);

        Color color = Color.fromARGB((int) Long.parseLong(background, 16));
        if (background.length() == 6) color = color.setAlpha(255);

        return color.asARGB();
    }
}
