package io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands;

import io.github.itachi1706.CheesecakeMinigameLobby.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WowCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public WowCmd(Main plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("wow")){
			if (!Main.commandWow){
				sender.sendMessage(ChatColor.RED + "This command has been disabled for this server!");
				return true;
			}
			if (!sender.hasPermission("cheesecakeminigamelobby.abilities.wow")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			if (args.length > 1){
    			displayHelp(sender);
				return true;
    		}
			if (!(sender instanceof Player) && args.length == 0){
				sender.sendMessage(ChatColor.GREEN + "Such command, Very Nothing");
				return true;
			}
			if (args.length == 0){
				//Does /wow
				Player p = (Player) sender;
				p.sendMessage(ChatColor.GREEN + "Such command, Very Nothing");
				for (Player online : Bukkit.getServer().getOnlinePlayers()){
					if (online != p){
						online.sendMessage(ChatColor.GOLD + p.getName() + ChatColor.GRAY + " just got doged :D");
					}
				}
				return true;
			} else if (args.length == 1){
				//Does /wow on another player
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null){
					sender.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				target.sendMessage(ChatColor.GREEN + "Such randomness, Very Nothing");
				sender.sendMessage(ChatColor.GOLD + "Doged " + target.getDisplayName());
				for (Player online : Bukkit.getServer().getOnlinePlayers()){
					if (online != target || !(online.getName().equals(sender.getName()))){
						online.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GRAY + " just got doged :D");
					}
				}
				return true;
			}
		}
		return false;
	}
	
	private void displayHelp(CommandSender s){
		s.sendMessage(ChatColor.RED + "Usage: /wow [player]");
	}

}
