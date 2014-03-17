package io.github.itachi1706.Banception;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListBans implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ListBans(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Commands
		if (cmd.getName().equalsIgnoreCase("bans")){
			//Check bans
			if (args.length < 1 || args.length > 1){
				//Not enough arguments
				sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
				return true;
			}
			if (!sender.hasPermission("banception.ban.list")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			}
			Database sql = Main.sql;
			SQLiteHelper.getPlayerBans(sql, sender, args[0]);
			return true;
		}
			
			if (cmd.getName().equalsIgnoreCase("mutes")){
				//Check mutes
				if (args.length < 1 || args.length > 1){
					//Not enough arguments
					sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
					return true;
				}
				if (!sender.hasPermission("banception.mute.list")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
					return true;
				}
				Database sql = Main.sql;
				SQLiteHelper.getPlayerMutes(sql, sender, args[0]);
				return true;
		}
			if (cmd.getName().equalsIgnoreCase("kicks")){
				//Check kicks
				if (args.length < 1 || args.length > 1){
					//Not enough arguments
					sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
					return true;
				}
				if (!sender.hasPermission("banception.kick.list")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
					return true;
				}
				Database sql = Main.sql;
				SQLiteHelper.getPlayerKicks(sql, sender, args[0]);
				return true;
			}
	return false;
	}

}
