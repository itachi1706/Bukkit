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
public class DelTP implements CommandExecutor {

    private Main plugin;

    public DelTP(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("deltp")){
            if (!sender.hasPermission("wynncraft.admin")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
                return true;
            }
            if (args.length < 1){
                sender.sendMessage(ChatColor.RED + "Usage: /deltp <name>");
                return true;
            }
            boolean valid = false;
            StringBuilder builder = new StringBuilder();
            for (String s : args){
                builder.append(s);
            }
            String name = builder.toString();
            for (int i = 0; i < Main.locations.size(); i++){
                WynnLocations wl = Main.locations.get(i);
                if (wl.getName().replace('_', ' ').equalsIgnoreCase(name)){
                    valid = true;
                    break;
                }
            }
            if (!valid){
                sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Unable to find a location of this name to delete");
                sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Use the command " + ChatColor.GOLD + "/tplist" + ChatColor.RED + " to see all possible locations!");
                return true;
            }
            name = name.replace(' ', '_');
            plugin.getConfig().set("warps." + name, null);
            plugin.saveConfig();
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wynncraft reload");
            name = name.replace('_', ' ');
            String finishMsg = Main.pluginPrefix + "&6" + name + "&r has been removed from the teleport list!";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
            String adminMsg = "&7&o[" + sender.getName() + ": Removed " + name + " from teleport list]";
            Main.sendAdminMsg(adminMsg, sender);
            return true;
        }
        return false;
    }

}
