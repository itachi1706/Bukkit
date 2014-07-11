package io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands;

import io.github.itachi1706.CheesecakeMinigameLobby.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BurnCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public BurnCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("burn")){
			if (!Main.commandBurn){
				sender.sendMessage(ChatColor.RED + "This command has been disabled for this server!");
				return true;
			}
			if (!(sender instanceof Player)){
				sender.sendMessage("You must be a player in-game to use this command");
				return true;
			}
			if (!sender.hasPermission("cheesecakeminigamelobby.abilities.burn")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /burn [player]");
				return true;
    		}
			Player p = (Player) sender;
			if (args.length == 0){
				//Burns yourself
				Player target = Bukkit.getServer().getPlayer(p.getName());
				target.setFireTicks(1000);
				target.sendMessage(ChatColor.RED + "You were burned!");
				p.sendMessage(ChatColor.GOLD + "Burned " + target.getDisplayName());
				return true;
			} else if (args.length == 1){
				//Burns a player
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null){
					p.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				target.setFireTicks(1000);
				target.sendMessage(ChatColor.RED + "You were burned!");
				p.sendMessage(ChatColor.GOLD + "Burned " + target.getDisplayName());
				return true;
			}
		}
		return false;
		
	}

}
