package io.github.itachi1706.Banception;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Unmute implements CommandExecutor{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Unmute(Main plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Commands
				if (cmd.getName().equalsIgnoreCase("unmute")){
					//Unbans a player
					if (args.length < 1){
						//Not enough arguments
						sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
						return true;
					}
					if (!sender.hasPermission("banception.mute.unmute")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
						return true;
					}
					OfflinePlayer target = (Bukkit.getServer().getOfflinePlayer(args[0]));
					if (!Main.mute.getBoolean(target.getName() + ".mute")){	//Player wasn't banned
						sender.sendMessage(ChatColor.RED + "Player has not been muted.");
						return true;
					} else {	//Unmute player
						if (args.length == 1 ){
						sender.sendMessage(ChatColor.RED + target.getName() + " is unmuted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + "No reason");
						Main.mute.set(target.getName() + ".mute", false);
						Main.mute.set(target.getName() + ".timeleft", "");
						Main.mute.set(target.getName() + ".time", 0);
						Main.mute.set(target.getName() + ".reason", "");
						Main.mute.set(target.getName() + ".perm", false);
						Main.saveMute();
						return true;
						} else {
							String unmuteReason = "";
							for (int i = 1; i < args.length; i++){
								unmuteReason = unmuteReason + args[i] + " ";
							}
							sender.sendMessage(ChatColor.RED + target.getName() + " is unmuted by " + ChatColor.GREEN + sender.getName() + ChatColor.RED + ". " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + unmuteReason);
							Main.mute.set(target.getName() + ".mute", false);
							Main.mute.set(target.getName() + ".timeleft", "");
							Main.mute.set(target.getName() + ".time", 0);
							Main.mute.set(target.getName() + ".reason", unmuteReason);
							Main.mute.set(target.getName() + ".perm", false);
							Main.saveMute();
							return true;
						}
					}
				}
	
		return false;
	}
	
	public static void unmute(OfflinePlayer player){
		player.setBanned(false);
		Main.mute.set(player.getName() + ".mute", false);
		Main.mute.set(player.getName() + ".perm", false);
		Main.mute.set(player.getName() + ".timeleft", "");
		Main.mute.set(player.getName() + ".time", 0);
		Main.mute.set(player.getName() + ".timeStart", 0);
		Main.mute.set(player.getName() + ".timeEnd", 0);
		Main.mute.set(player.getName() + ".reason", "Tempmute over");
		Main.saveMute();
	}

}