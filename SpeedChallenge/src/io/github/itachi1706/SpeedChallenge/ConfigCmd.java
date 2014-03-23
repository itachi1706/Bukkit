package io.github.itachi1706.SpeedChallenge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigCmd implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public ConfigCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("scconfig")){
			if (args.length < 1 || args.length > 2){
				//Not enough arguments
				displayConfigHelp(sender);
				return true;
			}
			if (args.length == 1){
				if (args[0].equalsIgnoreCase("list")){
					//List all gamemodes
					listGameModes(sender);
				} else {
					displayConfigHelp(sender);
				}
			}
			if (args.length == 2){
				if (sender.hasPermission("sc.override") || Main.playerList.get(0).getName().equals(sender.getName())){
					//Able to do config
					if (args[0].equalsIgnoreCase("gamemode")){
						//Modify gamemode
						sender.sendMessage(ChatColor.DARK_RED + "COMMAND COMING SOON");
						return true;
					} 
					if (args[0].equalsIgnoreCase("pvp")){
						//Modify PVP option
						if (args[1].equalsIgnoreCase("true")){
							//Enables PVP
							Main.pvp = 1;
							sender.sendMessage(ChatColor.BLUE + "Enabled PVP!");
							String pvp = "&b[SpeedChallenge] &4&lPVP will be &a&lenabled!";
							Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
						} else if (args[1].equalsIgnoreCase("false")){
							//Disables PVP
							Main.pvp = 2;
							sender.sendMessage(ChatColor.BLUE + "Disabled PVP!");
							String pvp = "&b[SpeedChallenge] &4&lPVP will be &c&ldisabled!";
							Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
						} else {
							sender.sendMessage(ChatColor.RED + "Only true or false is accepted.");
						}
						return true;
					}
					if (args[0].equalsIgnoreCase("respawn")){
						//Modify Respawn Option
						if (args[1].equalsIgnoreCase("true")){
							//Enables Respawn
							Main.respawn = 1;
							sender.sendMessage(ChatColor.BLUE + "Enabled Respawn On Death!");
							String respawn = "&b[SpeedChallenge] &4&lRespawn on Death will be &a&lenabled!";
							Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
						} else if (args[1].equalsIgnoreCase("false")){
							//Disables Respawn
							Main.respawn = 2;
							sender.sendMessage(ChatColor.BLUE + "Disabled Respawn On Death!");
							String respawn = "&b[SpeedChallenge] &4&lRespawn on Death will be &c&ldisabled!";
							Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
						} else {
							sender.sendMessage(ChatColor.RED + "Only true or false is accepted.");
						}
						return true;
					} else {
						displayConfigHelp(sender);
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "You do not have the ability to modify config of this game!");
				}
			}
			return true;
		}
		return false;
	}
	
	public void displayConfigHelp(CommandSender p){
		String[] msg = {ChatColor.GOLD + "==================================================" ,
				ChatColor.GREEN + "/scconfig gamemode <#> - " + ChatColor.AQUA + "Chooses a minigame map (replace # with number)",
				ChatColor.GREEN + "/scconfig pvp <true/false> - " + ChatColor.AQUA + "Chooses a minigame map",
				ChatColor.GREEN + "/scconfig respawn <true/false> - " + ChatColor.AQUA + "Chooses a minigame map",
				ChatColor.GREEN + "/scconfig list - " + ChatColor.AQUA + "List all available gamemodes",
				ChatColor.GOLD + "=================================================="};
		p.sendMessage(msg);
	}
	
	public void listGameModes(CommandSender p){
		String[] msg = {ChatColor.GOLD + "==================================================" ,
				ChatColor.RED + "No Gamemodes available",
				ChatColor.GOLD + "=================================================="};
		p.sendMessage(msg);
	}

}
