package me.itsskeptical.displaytags.commands;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.commands.displaytags.HelpCommand;
import me.itsskeptical.displaytags.commands.displaytags.ReloadCommand;
import me.itsskeptical.displaytags.commands.framework.CommandGroup;
import me.itsskeptical.displaytags.utils.helpers.MessageHelper;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DisplayTagsCommand extends CommandGroup {
    private final DisplayTags plugin;

    public DisplayTagsCommand(DisplayTags plugin) {
        super("displaytags");
        this.plugin = plugin;
        this.createCommand(new HelpCommand(plugin, this));
        this.createCommand(new ReloadCommand(plugin, this));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            MessageHelper.send(sender, "This server is running <#00BFFF>DisplayTags <gray>v" + plugin.getPluginMeta().getVersion() + "<white>!");
            MessageHelper.send(sender, "Run <gray>'/displaytags help' <white>for a full list of commands.");
            return true;
        }

        return super.execute(sender, commandLabel, args);
    }
}
