package me.skyyiscool.displaytags.commands.displaytags;

import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.commands.framework.CommandGroup;
import me.skyyiscool.displaytags.commands.framework.SubCommand;
import me.skyyiscool.displaytags.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {
    public ReloadCommand(CommandGroup group) {
        super(group);
        super.setName("reload");
        super.setDescription("Reload the plugin.");
        super.setPermission("displaytags.admin");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        DisplayTags plugin = this.commandGroup.plugin;
        try {
            plugin.reloadPlugin();
            MessageUtil.success(sender, "Successfully reloaded the plugin!");

            return true;
        } catch (Exception error) {
            MessageUtil.error(sender, "Failed to reload the plugin, please check the server logs!");
            plugin.getLogger().severe("Failed to reload plugin configuration:" + error.getMessage());
            error.printStackTrace();

            return false;
        }
    }
}
