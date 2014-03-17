package io.github.itachi1706.Banception;

import lib.PatPeter.SQLibrary.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tempban implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Tempban(Main plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Command
		if (cmd.getName().equalsIgnoreCase("tempban")){
			if (args.length < 1){
				//Not enough arguments
				sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
				return true;
			}
			if (!sender.hasPermission("banception.ban.tempban")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			}
			if (args.length == 1){
				//Ban for 15 mins with no reason (900 seconds)
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				int endTime = currentTime + 900;
				boolean result = executeBan(sender, args, currentTime, endTime);
				if (result = true)
					return true;
				else
					return false;
			} else if (args.length == 2){
				//Ban for time with no reason
				int time = TimeCalc.initCalc(args[1].toLowerCase());	//Length of ban in seconds	
				if (time == -1){
					sender.sendMessage(ChatColor.RED + "Please input a valid time format.");
					return true;
				}
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				int endTime = currentTime + time;	//Time when ban ends
				boolean result = executeBan(sender, args, currentTime, endTime);
				if (result = true)
					return true;
				else
					return false;
				
			} else {
				//Ban for time with reason
				int time = TimeCalc.initCalc(args[1].toLowerCase());	//Length of ban in seconds
				if (time == -1){
					sender.sendMessage(ChatColor.RED + "Please input a valid time format.");
					return true;
				}
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				int endTime = currentTime + time;	//Time when ban ends
				boolean result = executeBan(sender, args, currentTime, endTime);
				if (result = true)
					return true;
				else
					return false;
			}
		}
		return false;		
	}
	
	public boolean executeBan(CommandSender sender, String[] args, int start, int end){
		Player target = (Bukkit.getServer().getPlayer(args[0]));
		Database sql = Main.sql;
		if (target == null){
			//Offline Player process
			OfflinePlayer offvictim = Bukkit.getServer().getOfflinePlayer(args[0]);
			if (Main.ban.getBoolean(offvictim.getName() + ".ban")){	//Player was banned
				sender.sendMessage(ChatColor.RED + "Player has already been banned.");
				return true;
			} else {	//Ban offline player
				if (args.length <= 2){
				String durationMsg = TimeCalc.calcTimeMsg(start, end);
				sender.sendMessage(ChatColor.RED + offvictim.getName() + " is temporarily banned while he is offline. " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + "No reason");
				Main.ban.set(offvictim.getName() + ".ban", true);
				Main.ban.set(offvictim.getName() + ".timeleft", durationMsg);
				Main.ban.set(offvictim.getName() + ".time", end - start);
				Main.ban.set(offvictim.getName() + ".timeStart", start);
				Main.ban.set(offvictim.getName() + ".timeEnd", end);
				Main.ban.set(offvictim.getName() + ".reason", "The ban hammer has spoken!");
				Main.ban.set(offvictim.getName() + ".isBannedBy", sender.getName());
				SQLiteHelper.addBanLog(sql, offvictim.getName(), durationMsg, "The ban hammer has spoken!", sender.getName());
				Main.saveBan();
				return true;
				} else {
					String banReason = "";
					for (int i = 2; i < args.length; i++){
						banReason = banReason + args[i] + " ";
					}
					String durationMsg = TimeCalc.calcTimeMsg(start, end);
					sender.sendMessage(ChatColor.RED + offvictim.getName() + " is temporarily banned while he is offline. " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + banReason);
					Main.ban.set(offvictim.getName() + ".ban", true);
					Main.ban.set(offvictim.getName() + ".timeleft", durationMsg);
					Main.ban.set(offvictim.getName() + ".time", end - start);
					Main.ban.set(offvictim.getName() + ".timeStart", start);
					Main.ban.set(offvictim.getName() + ".timeEnd", end);
					Main.ban.set(offvictim.getName() + ".reason", banReason);
					Main.ban.set(offvictim.getName() + ".isBannedBy", sender.getName());
					SQLiteHelper.addBanLog(sql, offvictim.getName(), durationMsg, banReason, sender.getName());
					Main.saveBan();
					return true;
					}
				}
			} else {
			//Online Player process
			Player victim = Bukkit.getServer().getPlayerExact(args[0]);
			if (args.length <= 2) {
				String durationMsg = TimeCalc.calcTimeMsg(start, end);
				victim.kickPlayer("You have been temporarily banned from the server! "  + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.WHITE + " Reason: "  + ChatColor.DARK_RED + "The ban hammer has spoken!");
				sender.sendMessage(ChatColor.RED + victim.getName() + " is temporarily banned. " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + "No reason");
				Main.ban.set(victim.getName() + ".ban", true);
				Main.ban.set(victim.getName() + ".timeleft", durationMsg);
				Main.ban.set(victim.getName() + ".time", end - start);
				Main.ban.set(victim.getName() + ".timeStart", start);
				Main.ban.set(victim.getName() + ".timeEnd", end);
				Main.ban.set(victim.getName() + ".reason", "The ban hammer has spoken!");
				Main.ban.set(victim.getName() + ".isBannedBy", sender.getName());
				SQLiteHelper.addBanLog(sql, victim.getName(), durationMsg, "The ban hammer has spoken!", sender.getName());
				Main.saveBan();
				return true;
			} else {
				String banReason = "";
				for (int i = 2; i < args.length; i++){
					banReason = banReason + args[i] + " ";
				}
				String durationMsg = TimeCalc.calcTimeMsg(start, end);
				victim.kickPlayer("You have been temporarily banned from the server! "  + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.WHITE + " Reason: "  + ChatColor.DARK_RED + banReason);
				sender.sendMessage(ChatColor.RED + victim.getName() + " is temporarily banned. " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + "No reason");
				Main.ban.set(victim.getName() + ".ban", true);
				Main.ban.set(victim.getName() + ".timeleft", durationMsg);
				Main.ban.set(victim.getName() + ".time", end - start);
				Main.ban.set(victim.getName() + ".timeStart", start);
				Main.ban.set(victim.getName() + ".timeEnd", end);
				Main.ban.set(victim.getName() + ".reason", banReason);
				Main.ban.set(victim.getName() + ".isBannedBy", sender.getName());
				SQLiteHelper.addBanLog(sql, victim.getName(), durationMsg, banReason, sender.getName());
				Main.saveBan();
				return true;
			}
		}
	}
}


