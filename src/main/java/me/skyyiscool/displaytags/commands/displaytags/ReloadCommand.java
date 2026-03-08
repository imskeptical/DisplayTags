package me.skyyiscool.displaytags.commands.displaytags;

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
        boolean reloaded = this.getPlugin().reloadPlugin();

        if (reloaded) {
            MessageUtil.success(sender, "Successfully reloaded the plugin!");
        } else {
            MessageUtil.error(sender, "Failed to reload the plugin, please check the server logs!");
        }

        return true;
    }
}
