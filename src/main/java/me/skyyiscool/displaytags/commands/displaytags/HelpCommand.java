package me.skyyiscool.displaytags.commands.displaytags;

import me.skyyiscool.displaytags.commands.framework.CommandGroup;
import me.skyyiscool.displaytags.commands.framework.SubCommand;
import me.skyyiscool.displaytags.util.ComponentUtil;
import me.skyyiscool.displaytags.util.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HelpCommand extends SubCommand {
    private Component HELP_MESSAGE;

    public HelpCommand(CommandGroup group) {
        super(group);
        this.setName("help");
        this.setDescription("List the available commands under <gray>/" + group.getName() + "<white>.");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (this.HELP_MESSAGE == null) {
            Collection<SubCommand> commands = this.getCommandGroup().getCommands();

            List<String> lines = new ArrayList<>();
            lines.add(MessageUtil.prefixed(
                    String.format("Commands <dark_gray>(<white>%s<dark_gray>)<white>:", commands.size()))
            );

            commands.forEach((cmd) ->
                    lines.add(String.format(
                            "<gray>/%s <dark_gray>→ <white>%s",
                            cmd.getName(),
                            cmd.getDescription() != null ? cmd.getDescription() : "No description."
                    ))
            );

            this.HELP_MESSAGE = ComponentUtil.render(lines);
        }

        sender.sendMessage(this.HELP_MESSAGE);
        return true;
    }
}
