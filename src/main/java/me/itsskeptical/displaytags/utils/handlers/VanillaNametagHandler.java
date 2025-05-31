package me.itsskeptical.displaytags.utils.handlers;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class VanillaNametagHandler {
    private static String getTeamName(Player target) {
        return "displaytags_" + target.getEntityId();
    }

    public static void hideNametag(Player target, Player viewer) {
        if (target.getUniqueId() == viewer.getUniqueId()) return; // A player's own vanilla nametag is not displayed to them.

        String teamName = getTeamName(target);
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
                teamName,
                WrapperPlayServerTeams.TeamMode.CREATE,
                teamInfo,
                target.getName()
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
    }

    public static void showVanillaNametag(Player target, Player viewer) {
        String teamName = getTeamName(target);

        WrapperPlayServerTeams packet = new WrapperPlayServerTeams(
                teamName,
                WrapperPlayServerTeams.TeamMode.REMOVE,
                (WrapperPlayServerTeams.ScoreBoardTeamInfo) null,
                target.getName()
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
    }
}
