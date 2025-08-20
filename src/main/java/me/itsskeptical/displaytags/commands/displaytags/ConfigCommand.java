package me.itsskeptical.displaytags.commands.displaytags;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.commands.framework.CommandGroup;
import me.itsskeptical.displaytags.commands.framework.Subcommand;
import me.itsskeptical.displaytags.config.NametagConfig;
import me.itsskeptical.displaytags.entities.DisplayBillboard;
import me.itsskeptical.displaytags.entities.TextAlignment;
import me.itsskeptical.displaytags.utils.helpers.MessageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigCommand extends Subcommand {
    public ConfigCommand(DisplayTags plugin, CommandGroup parentCommand) {
        super(plugin, parentCommand);
        super.setName("config");
        super.setDescription("View the plugin configuration.");
        super.setPermission("displaytags.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        NametagConfig config = plugin.config().getNametagConfig();
        boolean enabled = config.isEnabled();
        boolean hideSelf = config.shouldHideSelf();
        int updateInterval = config.getUpdateInterval();
        int visibilityDistance = config.getVisibilityDistance();
        List<String> lines = config.getLines();
        boolean textShadow = config.hasTextShadow();
        boolean seeThrough = config.isSeeThrough();
        TextAlignment textAlignment = config.getTextAlignment();
        String background = config.getBackground();
        DisplayBillboard billboard = config.getBillboard();
        Vector scale = config.getScale();
        List<String> scaleText = new ArrayList<>();
        scaleText.add("<white>X <dark_gray>→ <gray>" + scale.getX());
        scaleText.add("<white>Y <dark_gray>→ <gray>" + scale.getY());
        scaleText.add("<white>Z <dark_gray>→ <gray>" + scale.getZ());

        List<String> messages = new ArrayList<>();
        messages.add("<dark_gray>• <white>Nametags");
        messages.add("  <white>Enabled <dark_gray>→ " + booleanToString(enabled));
        messages.add("  <white>Show Self <dark_gray>→ " + booleanToString(!hideSelf));
        messages.add("  <white>Update Interval <dark_gray>→ <gray>" + updateInterval + " ticks");
        messages.add("  <white>Visibility Distance <dark_gray>→ <gray>" + visibilityDistance + " blocks");
        messages.add("<dark_gray>• <white>Display");
        messages.add("  <white>Lines <dark_gray>→ <hover:show_text:'{lines}'><gray><u>Hover".replace("{lines}", String.join("\n", lines)));
        messages.add("  <white>Text Shadow <dark_gray>→ " + booleanToString(textShadow));
        messages.add("  <white>See Through <dark_gray>→ " + booleanToString(seeThrough));
        messages.add("  <white>Text Alignment <dark_gray>→ <gray>" + textAlignment.name());
        messages.add("  <white>Background <dark_gray>→ " + color(background) + background(background));
        messages.add("  <white>Billboard <dark_gray>→ <gray>" + billboard.name());
        messages.add("  <white>Scale <dark_gray>→ <hover:show_text:'{scale}'><gray><u>Hover".replace("{scale}", String.join("\n", scaleText)));

        sender.sendMessage("");
        MessageHelper.send(sender, "<dark_gray>[<#00BFFF>&lᴘʟᴜɢɪɴ ᴄᴏɴꜰɪɢᴜʀᴀᴛɪᴏɴ<dark_gray>]");
        MessageHelper.send(sender, messages);
        sender.sendMessage("");
    }

    private String booleanToString(boolean b) {
        if (b) {
            return "<green>Yes";
        } else {
            return "<red>No";
        }
    }

    private String background(String background) {
        if (Objects.equals(background, "default")) return "Default";
        if (Objects.equals(background, "Transparent")) return "Transparent";
        return background;
    }

    private String color(String hex) {
        if (Objects.equals(hex, "default")) return "<gray>";
        if (Objects.equals(hex, "transparent")) return "<white>";
        return "<" + hex + ">";
    }
}
