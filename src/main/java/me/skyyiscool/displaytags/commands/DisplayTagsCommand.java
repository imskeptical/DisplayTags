package me.skyyiscool.displaytags.commands;

import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.commands.displaytags.ReloadCommand;
import me.skyyiscool.displaytags.commands.framework.CommandGroup;
import me.skyyiscool.displaytags.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DisplayTagsCommand extends CommandGroup {
    public DisplayTagsCommand(DisplayTags plugin) {
        super("displaytags", plugin);
        this.addCommand(new ReloadCommand(this));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            MessageUtil.send(sender, "This server is running <#00BFFF>DisplayTags <gray>v" + plugin.getPluginMeta().getVersion() + "<white>!");
            MessageUtil.send(sender, "Run <gray>'/displaytags help' <white>for a full list of commands.");
            return true;
        }

        return super.execute(sender, commandLabel, args);
    }
}
