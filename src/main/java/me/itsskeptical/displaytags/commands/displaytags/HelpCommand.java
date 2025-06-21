package me.itsskeptical.displaytags.commands.displaytags;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.commands.framework.CommandGroup;
import me.itsskeptical.displaytags.commands.framework.Subcommand;
import org.bukkit.command.CommandSender;

public class HelpCommand extends Subcommand {
    public HelpCommand(DisplayTags plugin, CommandGroup parentCommand) {
        super(plugin, parentCommand);
        super.setName("help");
        super.setDescription("List available commands for the plugin.");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        getParentCommand().sendSubcommands(sender);
    }
}
