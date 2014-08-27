package io.github.itachi1706.SpeedChallenge;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminCmd implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public AdminCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("scadmin")){
			if (args.length > 1 || args.length < 1){
				sender.sendMessage(ChatColor.RED + "Usage: /scadmin forcestart/endgame");
				return true;
			}
			if (!sender.hasPermission("sc.override")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
				return true;
			}
			if (args[0].equalsIgnoreCase("forcestart")){
				if (Main.gameStart){
					sender.sendMessage(ChatColor.DARK_RED + "Game has already begun");
					return true;
				}
				if (Main.initGame){
					sender.sendMessage(ChatColor.DARK_RED + "Game is in initialization mode");
					return true;
				}
				if (Main.countdown > 15){
					Main.countdown = 11;
					sender.sendMessage("Force starting game");
					return true;
				}
				else if (Main.countdown < 10){
					Main.countdown = 5;
					sender.sendMessage("Force starting game");
					return true;
				} else {
					sender.sendMessage("World generating. Please wait to force start");
					return true;
				}
				
			} else if (args[0].equalsIgnoreCase("endgame")){
				if (!Main.gameStart){
					sender.sendMessage(ChatColor.DARK_RED + "Game has not begun");
					return true;
				}
				Main.countdown = 10;
				sender.sendMessage("Force ending game");
				return true;
			}
		}
		return true;
	}

}
