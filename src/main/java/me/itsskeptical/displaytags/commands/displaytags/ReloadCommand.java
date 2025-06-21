package me.itsskeptical.displaytags.commands.displaytags;

import me.itsskeptical.displaytags.DisplayTags;
import me.itsskeptical.displaytags.commands.framework.CommandGroup;
import me.itsskeptical.displaytags.commands.framework.Subcommand;
import me.itsskeptical.displaytags.utils.helpers.MessageHelper;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends Subcommand {
    public ReloadCommand(DisplayTags plugin, CommandGroup parentCommand) {
        super(plugin, parentCommand);
        super.setName("reload");
        super.setDescription("Reload the plugin.");
        super.setPermission("displaytags.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean success = this.plugin.reloadPlugin();
        if (success) {
            MessageHelper.success(sender, "Successfully reloaded!");
        } else {
            MessageHelper.error(sender, "Failed to reload the plugin, please check server logs!");
        }
    }
}
