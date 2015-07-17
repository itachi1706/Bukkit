package com.itachi1706.Bukkit.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for WynncraftExperience in com.itachi1706.Bukkit.WynncraftExperience
 */
public class AddTP implements CommandExecutor {

    private Main plugin;

    public AddTP(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("addtp")){
            if (!sender.hasPermission("wynncraft.admin")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
                return true;
            }
            if (args.length < 1){
                sender.sendMessage(ChatColor.RED + "Usage: /addtp <name>");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage("You must be an in-game player to use this command!");
                sender.sendMessage("Support for CONSOLE coming soon");
                return true;
            }
            Player p = (Player) sender;
            Location loc = p.getLocation();
            double x = (Math.round(loc.getX() * 100.0) / 100.0), y = (Math.round(loc.getY() * 100.0) / 100.0), z = (Math.round(loc.getZ() * 100.0) / 100.0);
            float yaw = loc.getYaw(), pitch = loc.getPitch();

            StringBuilder builder = new StringBuilder();
            for (String s : args){
                builder.append(s);
            }
            String name = builder.toString();
            name = name.replace(' ', '_');
            plugin.getConfig().set("warps." + name + ".x", x);
            plugin.getConfig().set("warps." + name + ".y", y);
            plugin.getConfig().set("warps." + name + ".z", z);
            plugin.getConfig().set("warps." + name + ".yaw", yaw);
            plugin.getConfig().set("warps." + name + ".pitch", pitch);
            plugin.getConfig().set("warps." + name + ".type", "");
            plugin.saveConfig();
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wynncraft reload");
            name = name.replace('_', ' ');
            String finishMsg = Main.pluginPrefix + "&6" + name + "&r at &bX&r:" + x + " ,&bY&r:" + y + " ,&bZ&r:" + z + " ,&bYaw&r:" + yaw + " ,&bPitch&r:" + pitch + " &rhas been added into the teleport list!";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
            String adminMsg = "&7&o[" + sender.getName() + ": Added " + name + " into teleport list]";
            Main.sendAdminMsg(adminMsg, sender);
            return true;
        }
        return false;
    }

}
