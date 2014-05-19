package io.github.itachi1706.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCmd implements CommandExecutor{
	
	private Main plugin;
	
	public AdminCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("wynncraftadmin")){
			if (!sender.hasPermission("wynncraft.admin")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
				return true;
			}
			if (args.length > 1 || args.length < 1){
				displayHelp(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("tpto")){
				if (Main.commandTpto){
					plugin.getConfig().set("modules.teleport", false);
					sender.sendMessage(ChatColor.RED + "Teleport module has been disabled!");
				} else {
					plugin.getConfig().set("modules.teleport", true);
					sender.sendMessage(ChatColor.GREEN + "Teleport module has been enabled!");
				}
				plugin.saveConfig();
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wynncraft reload");
				return true;
			} else if (args[0].equalsIgnoreCase("speed")){
				if (Main.commandSpeed){
					plugin.getConfig().set("modules.speedmode", false);
					sender.sendMessage(ChatColor.RED + "Speedmode module has been disabled!");
				} else {
					plugin.getConfig().set("modules.speedmode", true);
					sender.sendMessage(ChatColor.GREEN + "Speedmode module has been enabled!");
				}
				plugin.saveConfig();
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wynncraft reload");
				return true;
			} else {
				displayHelp(sender);
				return true;
			}
		}
		return false;
	}
	
	private void displayHelp(CommandSender s){
		s.sendMessage(ChatColor.RED + "Usage: /wynncraftadmin <module> to enable/disable modular commands");
		s.sendMessage(ChatColor.RED + "Available modules: tpto, speed");
	}

}
