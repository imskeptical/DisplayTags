package me.skyyiscool.displaytags.util;

import com.github.retrooper.packetevents.util.Vector3f;
import org.bukkit.util.Vector;

public class ConversionUtil {
    public static Vector3f fromBukkitVector(Vector vector) {
        return new Vector3f(
                (float) vector.getX(),
                (float) vector.getY(),
                (float) vector.getZ()
        );
    }
}
