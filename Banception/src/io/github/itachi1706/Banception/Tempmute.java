package io.github.itachi1706.Banception;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tempmute implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Tempmute(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Command
		if (cmd.getName().equalsIgnoreCase("tempmute")){
			if (args.length < 1){
				//Not enough arguments
				sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
				return true;
			}
			if (!sender.hasPermission("banception.mute.tempmute")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			}
			if (args.length == 1){
				//Mute for 15 mins with no reason (900 seconds)
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				int endTime = currentTime + 900;
				boolean result = executeMute(sender, args, currentTime, endTime);
				if (result = true)
					return true;
				else
					return false;
			} else if (args.length == 2){
				//Mute for time with no reason
				int time = TimeCalc.initCalc(args[1].toLowerCase());	//Length of Mute in seconds	
				if (time == -1){
					sender.sendMessage(ChatColor.RED + "Please input a valid time format.");
					return true;
				}
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				int endTime = currentTime + time;	//Time when ban ends
				boolean result = executeMute(sender, args, currentTime, endTime);
				if (result = true)
					return true;
				else
					return false;
				
			} else {
				//Mute for time with reason
				int time = TimeCalc.initCalc(args[1].toLowerCase());	//Length of Mute in seconds
				if (time == -1){
					sender.sendMessage(ChatColor.RED + "Please input a valid time format.");
					return true;
				}
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				int endTime = currentTime + time;	//Time when ban ends
				boolean result = executeMute(sender, args, currentTime, endTime);
				if (result = true)
					return true;
				else
					return false;
			}
		}
		return false;
	}
	
		@SuppressWarnings("deprecation")
		public boolean executeMute(CommandSender sender, String[] args, int start, int end){
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
					if (args.length <= 2){
						String durationMsg = TimeCalc.calcTimeMsg(start, end);
					sender.sendMessage(ChatColor.RED + offvictim.getName() + " has been temporarily muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason:" + ChatColor.DARK_RED + " No Reason");
					Main.mute.set(offvictim.getName() + ".mute", true);
					Main.mute.set(offvictim.getName() + ".timeleft", durationMsg);
					Main.mute.set(offvictim.getName() + ".time", end - start);
					Main.mute.set(offvictim.getName() + ".timeStart", start);
					Main.mute.set(offvictim.getName() + ".timeEnd", end);
					Main.mute.set(offvictim.getName() + ".reason", "No reason");
					Main.mute.set(offvictim.getName() + ".isMutedBy", sender.getName());
					SQLiteHelper.addMuteLog(sql, offvictim.getName(), durationMsg, "No reason", sender.getName());
					Main.saveMute();
					return true;
					} else {
						String muteReason = "";
						for (int i = 2; i < args.length; i++){
							muteReason = muteReason + args[i] + " ";
						}
						String durationMsg = TimeCalc.calcTimeMsg(start, end);
						sender.sendMessage(ChatColor.RED + offvictim.getName() + " has been temporarily muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + muteReason);
						Main.mute.set(offvictim.getName() + ".mute", true);
						Main.mute.set(offvictim.getName() + ".timeleft", durationMsg);
						Main.mute.set(offvictim.getName() + ".time", end - start);
						Main.mute.set(offvictim.getName() + ".timeStart", start);
						Main.mute.set(offvictim.getName() + ".timeEnd", end);
						Main.mute.set(offvictim.getName() + ".reason", muteReason);
						Main.mute.set(offvictim.getName() + ".isMutedBy", sender.getName());
						SQLiteHelper.addMuteLog(sql, offvictim.getName(), durationMsg, muteReason, sender.getName());
						Main.saveMute();
						return true;
						}
					}
				} else {
					//Mute Online Player
					Player toMute = Bukkit.getServer().getPlayerExact(args[0]);
					if (args.length <= 2){
						String durationMsg = TimeCalc.calcTimeMsg(start, end);
						toMute.sendMessage(ChatColor.RED + "You have been muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + "No Reason");
						sender.sendMessage(ChatColor.RED + toMute.getName() + " has been temporarily muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason:" + ChatColor.DARK_RED + " No Reason");
						Main.mute.set(toMute.getName() + ".mute", true);
						Main.mute.set(toMute.getName() + ".timeleft", durationMsg);
						Main.mute.set(toMute.getName() + ".time", end - start);
						Main.mute.set(toMute.getName() + ".timeStart", start);
						Main.mute.set(toMute.getName() + ".timeEnd", end);
						Main.mute.set(toMute.getName() + ".reason", "No reason");
						Main.mute.set(toMute.getName() + ".isMutedBy", sender.getName());
						SQLiteHelper.addMuteLog(sql, toMute.getName(), durationMsg, "No reason", sender.getName());
						Main.saveMute();
						return true;
						} else {
							String muteReason = "";
							for (int i = 2; i < args.length; i++){
								muteReason = muteReason + args[i] + " ";
							}
							String durationMsg = TimeCalc.calcTimeMsg(start, end);
							toMute.sendMessage(ChatColor.RED + "You have been muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + muteReason);
							sender.sendMessage(ChatColor.RED + toMute.getName() + " has been temporarily muted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + muteReason);
							Main.mute.set(toMute.getName() + ".mute", true);
							Main.mute.set(toMute.getName() + ".timeleft", durationMsg);
							Main.mute.set(toMute.getName() + ".time", end - start);
							Main.mute.set(toMute.getName() + ".timeStart", start);
							Main.mute.set(toMute.getName() + ".timeEnd", end);
							Main.mute.set(toMute.getName() + ".reason", muteReason);
							Main.mute.set(toMute.getName() + ".isMutedBy", sender.getName());
							SQLiteHelper.addMuteLog(sql, toMute.getName(), durationMsg, muteReason, sender.getName());
							Main.saveMute();
							return true;
							}
						}
		}
}
