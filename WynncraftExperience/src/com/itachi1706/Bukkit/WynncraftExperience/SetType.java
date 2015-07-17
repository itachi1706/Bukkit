package com.itachi1706.Bukkit.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for WynncraftExperience in com.itachi1706.Bukkit.WynncraftExperience
 */
public class SetType implements CommandExecutor {

    private Main plugin;

    public SetType(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("settype")){
            if (!sender.hasPermission("wynncraft.admin")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
                return true;
            }
            if (args.length < 2 || args.length > 2){
                sender.sendMessage(ChatColor.RED + "Usage: /settype <location> <town/dungeon/misc/reset>");
                return true;
            }
            boolean valid = false;
            String name = args[0];
            for (int i = 0; i < Main.locations.size(); i++){
                WynnLocations wl = Main.locations.get(i);
                if (wl.getName().equalsIgnoreCase(name)){
                    valid = true;
                    break;
                }
            }
            if (!valid){
                sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Unable to find a location of this name to delete");
                sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Use the command " + ChatColor.GOLD + "/tplist" + ChatColor.RED + " to see all possible locations!");
                return true;
            }
            if (args[1].equalsIgnoreCase("town")){
                plugin.getConfig().set("warps." + name + ".type", "town");
                plugin.saveConfig();
            } else if (args[1].equalsIgnoreCase("dungeon")){
                plugin.getConfig().set("warps." + name + ".type", "dungeon");
                plugin.saveConfig();
            } else if (args[1].equalsIgnoreCase("misc")){
                plugin.getConfig().set("warps." + name + ".type", "misc");
                plugin.saveConfig();
            } else if (args[1].equalsIgnoreCase("reset")){
                plugin.getConfig().set("warps." + name + ".type", "");
                plugin.saveConfig();
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid Type! Usage: /settype <location> <town/dungeon/misc>");
                return true;
            }
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wynncraft reload");
            String finishMsg = Main.pluginPrefix + "&6" + name + "'s&r type is changed to &b" + args[1];
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
            String adminMsg = "&7&o[" + sender.getName() + ": Changed " + name + " type to " + args[1] + "]";
            Main.sendAdminMsg(adminMsg, sender);
            return true;
        }
        return false;
    }

}
