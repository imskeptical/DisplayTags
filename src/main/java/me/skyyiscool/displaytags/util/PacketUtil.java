package me.skyyiscool.displaytags.util;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBundle;

import java.util.UUID;

public class PacketUtil {
    public static void sendPacket(UUID uuid, PacketWrapper<?> packet) {
        PacketEventsAPI<?> api = PacketEvents.getAPI();
        Object channel = api.getProtocolManager().getChannel(uuid);
        if (channel == null) return;

        api.getProtocolManager().sendPacket(channel, packet);
    }

    public static WrapperPlayServerBundle createBundlePacket() {
        return new WrapperPlayServerBundle();
    }
}
