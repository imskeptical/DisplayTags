package me.itsskeptical.displaytags.utils.handlers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import me.itsskeptical.displaytags.utils.helpers.DependencyHelper;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.NameTagManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class NametagHandler {
    public static void hide(Player target, Player viewer) {
        if (DependencyHelper.isTABEnabled()) {
            TabAPI api = TabAPI.getInstance();
            NameTagManager manager = api.getNameTagManager();
            TabPlayer tabTarget = api.getPlayer(target.getUniqueId());
            TabPlayer tabViewer = api.getPlayer(viewer.getUniqueId());
            if (manager != null && !manager.hasHiddenNameTag(tabTarget, tabViewer)) {
                manager.hideNameTag(tabTarget, tabViewer);
            }
        } else {
            String name = getTeamName(target);
            WrapperPlayServerTeams.ScoreBoardTeamInfo teamInfo = new WrapperPlayServerTeams.ScoreBoardTeamInfo(
                    Component.empty(),
                    Component.empty(),
                    Component.empty(),
                    WrapperPlayServerTeams.NameTagVisibility.NEVER,
                    WrapperPlayServerTeams.CollisionRule.ALWAYS,
                    null,
                    WrapperPlayServerTeams.OptionData.NONE
            );
            WrapperPlayServerTeams packet = new WrapperPlayServerTeams(
                    name,
                    WrapperPlayServerTeams.TeamMode.CREATE,
                    teamInfo,
                    target.getName()
            );
            sendPacket(packet, viewer);
        }
    }

    public static void show(Player target, Player viewer) {
        if (DependencyHelper.isTABEnabled()) {
            TabAPI api = TabAPI.getInstance();
            NameTagManager manager = api.getNameTagManager();
            TabPlayer tabTarget = api.getPlayer(target.getUniqueId());
            TabPlayer tabViewer = api.getPlayer(viewer.getUniqueId());
            if (manager != null && manager.hasHiddenNameTag(tabTarget, tabViewer)) {
                manager.showNameTag(tabTarget, tabViewer);
            }
        } else {
            String name = getTeamName(target);
            WrapperPlayServerTeams packet = new WrapperPlayServerTeams(
                    name,
                    WrapperPlayServerTeams.TeamMode.REMOVE,
                    (WrapperPlayServerTeams.ScoreBoardTeamInfo) null,
                    target.getName()
            );
            sendPacket(packet, viewer);
        }
    }

    private static String getTeamName(Player target) {
        return "displaytags_" + target.getEntityId();
    }

    private static void sendPacket(PacketWrapper<?> packet, Player viewer) {
        PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
    }
}
