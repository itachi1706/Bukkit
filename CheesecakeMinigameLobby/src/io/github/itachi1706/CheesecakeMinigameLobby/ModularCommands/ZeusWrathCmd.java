package io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands;

import java.util.ArrayList;

import io.github.itachi1706.CheesecakeMinigameLobby.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//Smites and kills a player with Zeus Wrath
public class ZeusWrathCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ZeusWrathCmd(Main plugin){
		this.plugin = plugin;
	}
	
	public static ArrayList<Player> playerListZeused = new ArrayList<Player>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("zeus")){
			if (!Main.commandZeus){
				sender.sendMessage(ChatColor.RED + "This command has been disabled for this server!");
				return true;
			}
			if (!(sender instanceof Player)){
				sender.sendMessage("You must be a player in-game to use this command");
				return true;
			}
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /zeus [player]");
				return true;
    		}
			if (!sender.hasPermission("cheesecakeminigamelobby.abilities.zeus")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0){
				//Zeus Wrath yourself
				Player target = Bukkit.getServer().getPlayer(p.getName());
				target.getWorld().strikeLightning(target.getLocation());
				playerListZeused.add(target);
				target.setHealth(0);
				target.sendMessage(ChatColor.YELLOW + "You angered Zeus and hence suffered the Wrath of Zeus!");
				p.sendMessage(ChatColor.GOLD + "Made " + target.getDisplayName() + ChatColor.GOLD + " experience the Wrath of Zeus!");
				return true;
			} else if (args.length == 1){
				//Zeus Wrath a player
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null){
					p.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				target.getWorld().strikeLightning(target.getLocation());
				playerListZeused.add(target);
				target.setHealth(0);
				target.sendMessage(ChatColor.YELLOW + "You angered Zeus and hence suffered the Wrath of Zeus!");
				p.sendMessage(ChatColor.GOLD + "Made " + target.getDisplayName() + ChatColor.GOLD + " experience the Wrath of Zeus!");
				return true;
			}
		}
		return false;
		
	}

}
