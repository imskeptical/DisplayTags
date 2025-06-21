package me.itsskeptical.displaytags.commands.framework;

import me.itsskeptical.displaytags.DisplayTags;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class Subcommand {
    public DisplayTags plugin;
    private final CommandGroup parentCommand;
    private String name;
    private String description;
    private String permission;

    public Subcommand(DisplayTags plugin, CommandGroup parentCommand) {
        this.plugin = plugin;
        this.parentCommand = parentCommand;
    }

    public CommandGroup getParentCommand() {
        return parentCommand;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
