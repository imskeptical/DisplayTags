package me.itsskeptical.displaytags.utils;

import me.itsskeptical.displaytags.utils.helpers.PlaceholdersHelper;

public class Util {
    public static void load() {
        PlaceholdersHelper.load();
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
