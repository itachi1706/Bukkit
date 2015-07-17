package com.itachi1706.Bukkit.Banception;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for Banception in com.itachi1706.Bukkit.Banception
 */
public class Reload implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public Reload(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("bc")){
            if (args.length < 1 || args.length > 1){
                //Not enough arguments
                displayMenu(sender);
                return true;
            }
            if (!sender.hasPermission("banception.reload")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                Main.loadYamls();
                sender.sendMessage(ChatColor.GREEN + "Plugin ban and mute logs reloaded from file.");
                return true;
            }	else {
                displayMenu(sender);
                return true;
            }
        }
        return false;
    }

    public void displayMenu(CommandSender s){
        s.sendMessage(ChatColor.GOLD + "-----------Banception Commands-----------");
        s.sendMessage(ChatColor.GOLD + "/bc reload: " + ChatColor.WHITE + "Reloads the plugin");
        s.sendMessage(ChatColor.GOLD + "/ban <player> [reason]: " + ChatColor.WHITE + "Permenantly bans a player");
        s.sendMessage(ChatColor.GOLD + "/tempban <player> [time] [reason]: " + ChatColor.WHITE + "Temporarily bans a player. Defaults to 15 mins. Must provide time if supplying reason");
        s.sendMessage(ChatColor.GOLD + "/unban <player> [reason]: " + ChatColor.WHITE + "Unbans a player");
        s.sendMessage(ChatColor.GOLD + "/bans <player>: " + ChatColor.WHITE + "List player bans");
        s.sendMessage(ChatColor.GOLD + "/mute <player> [reason]: " + ChatColor.WHITE + "Permenantly mutes a player");
        s.sendMessage(ChatColor.GOLD + "/tempmute <player> [time] [reason]: " + ChatColor.WHITE + "Temporarily mutes a player. Defaults to 15 mins. Must provide time if supplying reason");
        s.sendMessage(ChatColor.GOLD + "/unmute <player> [reason]: " + ChatColor.WHITE + "Unmutes a player");
        s.sendMessage(ChatColor.GOLD + "/mutes <player>: " + ChatColor.WHITE + "List player mutes");
        s.sendMessage(ChatColor.GOLD + "/shutup [time(s/m/h/d)]: " + ChatColor.WHITE + "Disables/Enables Server Chat");
    }

}
