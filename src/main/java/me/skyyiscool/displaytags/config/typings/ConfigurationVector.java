package me.skyyiscool.displaytags.config.typings;

import org.bukkit.util.Vector;

public record ConfigurationVector(double x, double y, double z) {
    public Vector toBukkitVector() {
        return new Vector(this.x, this.y, this.z);
    }
}
