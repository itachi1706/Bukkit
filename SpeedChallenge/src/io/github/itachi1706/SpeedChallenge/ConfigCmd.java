package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Gamemodes.AbbaRules;
import io.github.itachi1706.SpeedChallenge.Gamemodes.AbbaRulesRetardStyle;
import io.github.itachi1706.SpeedChallenge.Gamemodes.EthoSpeedChallenge3;
import io.github.itachi1706.SpeedChallenge.Gamemodes.EthoSpeedChallenge4;
import io.github.itachi1706.SpeedChallenge.Gamemodes.GetAsManyAchievements;
import io.github.itachi1706.SpeedChallenge.Gamemodes.ModAbbaRules;
import io.github.itachi1706.SpeedChallenge.Gamemodes.Sample;

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
				if (Main.initGame){
					sender.sendMessage(ChatColor.RED + "Game has already started!");
					return true;
				}
				if (sender.hasPermission("sc.override") || Main.gamePlayerList.get(0).getName().equals(sender.getName())){
					//Able to do config
					if (args[0].equalsIgnoreCase("gamemode")){
						//Modify gamemode
						try {
							Main.gamemode = Integer.parseInt(args[1]);
							if (Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1]) <= Main.numberOfChallenges){
								String huh = PreGameRunnable.getTitle();
								sender.sendMessage(ChatColor.BLUE + "Challenge "+ args[1] + " (" + huh + ") selected!");
								String pvp = "&b[SpeedChallenge] &4&lChallenge " + args[1] + " (" + huh + ") &a&lselected!";
								Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
								return true;
							} else {
								sender.sendMessage(ChatColor.DARK_RED + "Invalid challenge number!");
								return true;
							}
						} catch (NumberFormatException e){
							sender.sendMessage(ChatColor.DARK_RED + "Invalid challenge number!");
							return true;
						}
					} 
					else if (args[0].equalsIgnoreCase("pvp")){
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
					else if (args[0].equalsIgnoreCase("respawn")){
						//Modify Respawn Option
						if (args[1].equalsIgnoreCase("true")){
							//Enables Respawn
							Main.respawn = 1;
							sender.sendMessage(ChatColor.BLUE + "Enabled Respawn On Death!");
							String respawn = "&b[SpeedChallenge] &4&lHardcore Mode will be &c&ldisabled!";
							Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
						} else if (args[1].equalsIgnoreCase("false")){
							//Disables Respawn
							Main.respawn = 2;
							sender.sendMessage(ChatColor.BLUE + "Disabled Respawn On Death!");
							String respawn = "&b[SpeedChallenge] &4&lHardcore Mode will be &a&lenabled!";
							Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
						} else {
							sender.sendMessage(ChatColor.RED + "Only true or false is accepted.");
						}
						return true;
					} else if (args[0].equalsIgnoreCase("gametime")){
						int time = 1800;
						try {
							time = Integer.parseInt(args[1]);
							if (time > 60){
								Main.customGameTime = time;
								sender.sendMessage(ChatColor.BLUE + "Game Duration will be set at " + (time/60) + " minutes and " + (time%60) + " seconds");
								String gametimer = "&b[SpeedChallenge] &4&lGame Duration will be set at " + (time/60) + " minutes and " + (time%60) + " seconds";
								Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', gametimer));
							} else {
								Main.customGameTime = time;
								sender.sendMessage(ChatColor.BLUE + "Game Duration will be set at " + time + " seconds");
								String gametimer = "&b[SpeedChallenge] &4&lGame Duration will be set at " + time + " seconds";
								Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', gametimer));
							}
						} catch (NumberFormatException ex){
							sender.sendMessage(ChatColor.RED + "Usage: /scconfig gametime <time in seconds>");
							return true;
						}
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
				ChatColor.GREEN + "/scconfig gamemode <#> - " + ChatColor.AQUA + "Chooses a challenge (replace # with number)",
				ChatColor.GREEN + "/scconfig pvp <true/false> - " + ChatColor.AQUA + "Chooses a minigame map",
				ChatColor.GREEN + "/scconfig respawn <true/false> - " + ChatColor.AQUA + "Chooses a minigame map",
				ChatColor.GREEN + "/scconfig list - " + ChatColor.AQUA + "List all available challenge",
				ChatColor.GOLD + "=================================================="};
		p.sendMessage(msg);
	}
	
	public void listGameModes(CommandSender p){
		String[] msg = {ChatColor.GOLD + "==================================================" ,
				ChatColor.RED + "1) " + ChatColor.BOLD + Sample.getGMTitle(),
				ChatColor.RED + "2) " + ChatColor.BOLD + EthoSpeedChallenge3.getGMTitle(),
				ChatColor.RED + "3) " + ChatColor.BOLD + EthoSpeedChallenge4.getGMTitle(),
				ChatColor.RED + "4) " + ChatColor.BOLD + ModAbbaRules.getGMTitle(),
				ChatColor.RED + "5) " + ChatColor.BOLD + AbbaRules.getGMTitle(),
				ChatColor.RED + "6) " + ChatColor.BOLD + AbbaRulesRetardStyle.getGMTitle(),
				ChatColor.RED + "7) " + ChatColor.BOLD + GetAsManyAchievements.getGMTitle(),
				ChatColor.GOLD + "=================================================="};
		p.sendMessage(msg);
	}

}
