package com.itachi1706.Bukkit.WynncraftExperience;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for WynncraftExperience in com.itachi1706.Bukkit.WynncraftExperience
 */
public class TPList implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public TPList(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("tplist")){
            if (!sender.hasPermission("wynncraft.default")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
                return true;
            }
            sender.sendMessage("Places you can teleport to");
            //Town
            sender.sendMessage(ChatColor.RED + "Town");
            for (int i = 0; i < Main.locations.size(); i++){
                WynnLocations wl = Main.locations.get(i);
                if (wl.getType().equalsIgnoreCase("town")){
                    sender.sendMessage(ChatColor.AQUA + "" + (i+1) + ": " + ChatColor.GOLD + wl.getName().replace('_', ' '));
                }
            }
            //Dungeon
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + "Dungeon");
            for (int i = 0; i < Main.locations.size(); i++){
                WynnLocations wl = Main.locations.get(i);
                if (wl.getType().equalsIgnoreCase("dungeon")){
                    sender.sendMessage(ChatColor.AQUA + "" + (i+1) + ": " + ChatColor.GOLD + wl.getName().replace('_', ' '));
                }
            }
            //Miscellaneous Places
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + "Misc Areas");
            for (int i = 0; i < Main.locations.size(); i++){
                WynnLocations wl = Main.locations.get(i);
                if (wl.getType().equalsIgnoreCase("misc")){
                    sender.sendMessage(ChatColor.AQUA + "" + (i+1) + ": " + ChatColor.GOLD + wl.getName().replace('_', ' '));
                }
            }
            //Blanks
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + "Others");
            for (int i = 0; i < Main.locations.size(); i++){
                WynnLocations wl = Main.locations.get(i);
                if (wl.getType().equalsIgnoreCase("")){
                    sender.sendMessage(ChatColor.AQUA + "" + (i+1) + ": " + ChatColor.GOLD + wl.getName().replace('_', ' '));
                }
            }
            return true;
        }
        return false;
    }

}
