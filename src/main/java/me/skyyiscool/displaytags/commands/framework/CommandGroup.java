package me.skyyiscool.displaytags.commands.framework;

import me.skyyiscool.displaytags.DisplayTags;
import me.skyyiscool.displaytags.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandGroup extends Command {
    protected DisplayTags plugin;
    private final Map<String, SubCommand> commands = new HashMap<>();

    protected CommandGroup(String name, DisplayTags plugin) {
        super(name);
        this.plugin = plugin;
    }

    public DisplayTags getPlugin() {
        return this.plugin;
    }

    public void addCommand(SubCommand command) {
        this.commands.put(command.getName(), command);
    }

    public Collection<SubCommand> getCommands() {
        return this.commands.values();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            MessageUtil.error(sender, "Unknown sub-command.");
            return true;
        }

        String commandName = args[0].toLowerCase();
        SubCommand command = commands.get(commandName);
        if (command == null) {
            MessageUtil.error(sender, "Unknown sub-command.");
            return true;
        }

        if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
            MessageUtil.error(sender, "You do not have permission to execute this command.");
            return true;
        }

        return command.execute(sender, sliceArgs(args));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        String name = args[0].toLowerCase();
        if (args.length == 1) {
            return commands.values().stream()
                    .filter((cmd) -> {
                        if (!cmd.getName().startsWith(name)) return false;
                        if (cmd.getPermission() != null) return sender.hasPermission(cmd.getPermission());

                        return true;
                    })
                    .map(SubCommand::getName)
                    .toList();
        }

        SubCommand command = commands.get(name);
        if (command != null) {
            return command.tabComplete(sender, sliceArgs(args));
        }

        return List.of();
    }

    private String[] sliceArgs(String[] args) {
        if (args.length <= 1) return new String[0];
        String[] sliced = new String[args.length - 1];
        System.arraycopy(args, 1, sliced, 0, sliced.length);
        return sliced;
    }
}
