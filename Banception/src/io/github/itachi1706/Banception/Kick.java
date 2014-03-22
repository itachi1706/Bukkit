package io.github.itachi1706.Banception;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kick implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Kick(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("kick")){
			//Kicks a player from the server
			if (args.length < 1){
				sender.sendMessage(ChatColor.RED + "Please specify the name of the player you wish to kick");
				return true;
			}
			if (!sender.hasPermission("banception.kick")){
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			}
			Player target = (Bukkit.getServer().getPlayer(args[0]));
			Database sql = Main.sql;
			if (target == null){
				sender.sendMessage(ChatColor.RED + args[0] + " is not online.");
				return true;
			} else {
				//Kicks player
				if (args.length == 1){
					//Kick with no reason
					target.kickPlayer("You were kicked from the server! Reason: " + ChatColor.DARK_RED + "Kicked by a Staff Member for no reason");
					sender.sendMessage(ChatColor.RED + target.getDisplayName() + " has been kicked from the server with the reason: " + ChatColor.DARK_RED + "Kicked by a Staff Member for no reason");
					SQLiteHelper.addKickLog(sql, target.getName(), "Kicked by a Staff Member for no reason", sender.getName());
					return true;
				} else {
					String kickReason = "";
					for (int i = 1; i < args.length; i++){
						kickReason = kickReason + args[i] + " ";
					}
						target.kickPlayer("You were kicked from the server! Reason: " + ChatColor.DARK_RED + kickReason);
						sender.sendMessage(ChatColor.RED + target.getDisplayName() + " has been kicked from the server with the reason: " + ChatColor.DARK_RED + kickReason);
						SQLiteHelper.addKickLog(sql, target.getName(), kickReason, sender.getName());
						return true;
				}
			}
		}
		return false;
	}

}
