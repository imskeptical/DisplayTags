package me.itsskeptical.displaytags.commands;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.utils.helpers.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class DisplayTagsCommand extends Command {
    private final DisplayTags plugin;
    public DisplayTagsCommand(DisplayTags plugin) {
        super("displaytags");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        String USAGE_STRING = "Usage: <gray>/displaytags <reload|version>";

        if (args.length < 1) {
            MessageHelper.send(sender, USAGE_STRING);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                if (sender.hasPermission("displaytags.command.reload")) {
                    plugin.reloadPlugin();
                    MessageHelper.success(sender, "Reloaded!");
                } else {
                    MessageHelper.error(sender, "You do not have permission to execute this command.");
                }
                return true;
            }
            case "version" -> {
                String version = plugin.getPluginMeta().getVersion();
                MessageHelper.send(sender, "This server is running <#00BFFF>DisplayTags <gray>v" + version + "<white>!");
                return true;
            }
            default -> {
                MessageHelper.send(sender, USAGE_STRING);
                return false;
            }
        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return Stream.of("reload", "version")
                    .filter((option) -> option.startsWith(args[0].toLowerCase()))
                    .toList();
        }
        return List.of();
    }
}
