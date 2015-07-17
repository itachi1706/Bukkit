package com.itachi1706.Bukkit.CheesecakeMinigameLobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for CheesecakeMinigameLobby in com.itachi1706.Bukkit.CheesecakeMinigameLobby
 */
public class ModulesCmd implements CommandExecutor {

    private Main plugin;

    public ModulesCmd(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("cmla")){
            if (!sender.hasPermission("cheesecakeminigamelobby.staff")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
                return true;
            }
            if (args.length > 1 || args.length < 1){
                displayHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("fly")){
                if (Main.commandFly){
                    plugin.getConfig().set("modules.fly", false);
                    sender.sendMessage(ChatColor.RED + "Fly module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.fly", true);
                    sender.sendMessage(ChatColor.GREEN + "Fly module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else if (args[0].equalsIgnoreCase("speed")){
                if (Main.commandSpeed){
                    plugin.getConfig().set("modules.speed", false);
                    sender.sendMessage(ChatColor.RED + "Speed module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.speed", true);
                    sender.sendMessage(ChatColor.GREEN + "Speed module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else if (args[0].equalsIgnoreCase("zeus")){
                if (Main.commandZeus){
                    plugin.getConfig().set("modules.zeus", false);
                    sender.sendMessage(ChatColor.RED + "Zeus Wrath module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.zeus", true);
                    sender.sendMessage(ChatColor.GREEN + "Zeus Wrath module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else if (args[0].equalsIgnoreCase("smite")){
                if (Main.commandSmite){
                    plugin.getConfig().set("modules.smite", false);
                    sender.sendMessage(ChatColor.RED + "Smite module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.smite", true);
                    sender.sendMessage(ChatColor.GREEN + "Smite module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else if (args[0].equalsIgnoreCase("fling")){
                if (Main.commandFling){
                    plugin.getConfig().set("modules.fling", false);
                    sender.sendMessage(ChatColor.RED + "Fling module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.fling", true);
                    sender.sendMessage(ChatColor.GREEN + "Fling module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else if (args[0].equalsIgnoreCase("burn")){
                if (Main.commandBurn){
                    plugin.getConfig().set("modules.burn", false);
                    sender.sendMessage(ChatColor.RED + "Burn module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.burn", true);
                    sender.sendMessage(ChatColor.GREEN + "Burn module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else if (args[0].equalsIgnoreCase("wow")){
                if (Main.commandWow){
                    plugin.getConfig().set("modules.wow", false);
                    sender.sendMessage(ChatColor.RED + "Wow module has been disabled!");
                } else {
                    plugin.getConfig().set("modules.wow", true);
                    sender.sendMessage(ChatColor.GREEN + "Wow module has been enabled!");
                }
                plugin.saveConfig();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cml reload");
                return true;
            } else {
                displayHelp(sender);
                return true;
            }
        }
        return false;
    }

    private void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.RED + "Usage: /cmla <module> to enable/disable modular commands");
        s.sendMessage(ChatColor.RED + "Available modules: fly, speed, smite, zeus, fling, wow");
    }

}
