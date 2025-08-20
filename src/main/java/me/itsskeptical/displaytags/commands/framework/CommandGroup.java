package me.itsskeptical.displaytags.commands.framework;

import me.itsskeptical.displaytags.utils.helpers.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandGroup extends Command {
    Map<String, Subcommand> commands = new HashMap<>();

    protected CommandGroup(@NotNull String name) {
        super(name);
    }

    public void createCommand(Subcommand command) {
        this.commands.put(command.getName(), command);
    }

    public void sendSubcommands(CommandSender sender) {
        List<String> messages = new ArrayList<>();
        messages.add(
                "Commands <dark_gray>(<white>{commands}<dark_gray>)<white>:"
                        .replace("{commands}", String.valueOf(commands.size()))
        );
        for (Subcommand command : commands.values()) {
            messages.add(
                    "<gray>/{name} <dark_gray>→ <white>{description}"
                            .replace("{name}", command.getName())
                            .replace("{description}", command.getDescription()));
        }

        MessageHelper.send(sender, messages);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            MessageHelper.error(sender, "Unknown sub-command.");
            return true;
        }

        String name = args[0].toLowerCase();
        Subcommand command = commands.get(name);
        if (command == null) {
            MessageHelper.error(sender, "Unknown sub-command.");
            return true;
        }
        if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
            MessageHelper.error(sender, "You do not have permission to execute this command.");
            return true;
        }

        command.execute(sender, sliceArgs(args));
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        String name = args[0].toLowerCase();
        if (args.length == 1) {
            return commands.keySet().stream()
                    .filter((cmd) -> cmd.startsWith(name))
                    .toList();
        }

        Subcommand command = commands.get(name);
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
