package io.github.itachi1706.Banception;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permmute implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Permmute(Main plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Command
		if (cmd.getName().equalsIgnoreCase("mute")){
			if (args.length < 1){
				//Not enough arguments
				sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
				return true;
			}
			if (!sender.hasPermission("banception.mute.perm")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			}
			Player target = (Bukkit.getServer().getPlayer(args[0]));
			Database sql = Main.sql;
			if (target == null){
				//Offline Player process
				OfflinePlayer offvictim = Bukkit.getServer().getOfflinePlayer(args[0]);
				if (Main.mute.getBoolean(offvictim.getName() + ".muted")){	//Player was muted
					sender.sendMessage(ChatColor.RED + "Player has already been muted.");
					return true;
				} else {
					//Mute offline player
					if (args.length == 1){
					sender.sendMessage(ChatColor.RED + offvictim.getName() + " has been permenantly muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + "permenantly" + ChatColor.BLUE + " Reason:" + ChatColor.DARK_RED + " No Reason");
					Main.mute.set(offvictim.getName() + ".mute", true);
					Main.mute.set(offvictim.getName() + ".timeleft", "permenantly");
					Main.mute.set(offvictim.getName() + ".perm", true);
					Main.mute.set(offvictim.getName() + ".time", -1);
					Main.mute.set(offvictim.getName() + ".reason", "No reason");
					Main.mute.set(offvictim.getName() + ".isMutedBy", sender.getName());
					SQLiteHelper.addMuteLog(sql, offvictim.getName(), "permenantly", "No reason", sender.getName());
					Main.saveMute();
					return true;
					} else {
						String muteReason = "";
						for (int i = 1; i < args.length; i++){
							muteReason = muteReason + args[i] + " ";
						}
						sender.sendMessage(ChatColor.RED + offvictim.getName() + " has been permenantly muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + "permenantly" + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + muteReason);
						Main.mute.set(offvictim.getName() + ".mute", true);
						Main.mute.set(offvictim.getName() + ".timeleft", "permenantly");
						Main.mute.set(offvictim.getName() + ".perm", true);
						Main.mute.set(offvictim.getName() + ".time", -1);
						Main.mute.set(offvictim.getName() + ".reason", muteReason);
						Main.mute.set(offvictim.getName() + ".isMutedBy", sender.getName());
						SQLiteHelper.addMuteLog(sql, offvictim.getName(), "permenantly", muteReason, sender.getName());
						Main.saveMute();
						return true;
						}
					}
				} else {
					//Mute Online Player
					Player toMute = Bukkit.getServer().getPlayerExact(args[0]);
					if (args.length == 1){
						toMute.sendMessage(ChatColor.RED + "You have been muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration:" + ChatColor.DARK_AQUA + " permenantly" + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + "No Reason");
						sender.sendMessage(ChatColor.RED + toMute.getName() + " has been permenantly muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + "." + ChatColor.BLUE + " Reason:" + ChatColor.DARK_RED + " No Reason");
						Main.mute.set(toMute.getName() + ".mute", true);
						Main.mute.set(toMute.getName() + ".timeleft", "permenantly");
						Main.mute.set(toMute.getName() + ".perm", true);
						Main.mute.set(toMute.getName() + ".time", -1);
						Main.mute.set(toMute.getName() + ".reason", "No reason");
						Main.mute.set(toMute.getName() + ".isMutedBy", sender.getName());
						SQLiteHelper.addMuteLog(sql, toMute.getName(), "permenantly", "No reason", sender.getName());
						Main.saveMute();
						return true;
						} else {
							String muteReason = "";
							for (int i = 1; i < args.length; i++){
								muteReason = muteReason + args[i] + " ";
							}
							toMute.sendMessage(ChatColor.RED + "You have been muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration:" + ChatColor.DARK_AQUA + " permenantly" + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + muteReason);
							sender.sendMessage(ChatColor.RED + toMute.getName() + " has been permenantly muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + "." + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + muteReason);
							Main.mute.set(toMute.getName() + ".mute", true);
							Main.mute.set(toMute.getName() + ".timeleft", "permenantly");
							Main.mute.set(toMute.getName() + ".perm", true);
							Main.mute.set(toMute.getName() + ".time", -1);
							Main.mute.set(toMute.getName() + ".reason", muteReason);
							Main.mute.set(toMute.getName() + ".isMutedBy", sender.getName());
							SQLiteHelper.addMuteLog(sql, toMute.getName(), "permenantly", muteReason, sender.getName());
							Main.saveMute();
							return true;
							}
						}
				}
		return false;
	}

}
