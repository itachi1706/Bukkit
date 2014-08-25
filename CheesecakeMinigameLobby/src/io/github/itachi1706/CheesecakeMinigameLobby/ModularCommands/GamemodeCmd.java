package io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands;

import io.github.itachi1706.CheesecakeMinigameLobby.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GamemodeCmd(Main plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		boolean stat = false;
		if (cmd.getName().equalsIgnoreCase("gmc")){
			if (!sender.hasPermission("cheesecakeminigamelobby.gamemode.creative")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /gmc [player]");
				return true;
			}
			if (args.length == 0){
				if (!(sender instanceof Player)){
					sender.sendMessage("You cannot gamemode yourself!");
					return true;
				}
				//Sets yourself to creative
				Player p = (Player) sender;
				stat = changeGamemode(p, "1", sender);
			} else {
				//Sets a player to creative
				Player p = Bukkit.getServer().getPlayer(args[0]);
				if (p == null){
					sender.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				stat = changeGamemode(p, "1", sender);
			}
			
			if (!stat){
				availableGamemodes(sender);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gms")){
			if (!sender.hasPermission("cheesecakeminigamelobby.gamemode.survival")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /gms [player]");
				return true;
			}
			
			if (args.length == 0){
				if (!(sender instanceof Player)){
					sender.sendMessage("You cannot gamemode yourself!");
					return true;
				}
				//Sets yourself to survival
				Player p = (Player) sender;
				stat = changeGamemode(p, "0", sender);
			} else {
				//Sets a player to survival
				Player p = Bukkit.getServer().getPlayer(args[0]);
				if (p == null){
					sender.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				stat = changeGamemode(p, "0", sender);
			}
			
			if (!stat){
				availableGamemodes(sender);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gma")){
			if (!sender.hasPermission("cheesecakeminigamelobby.gamemode.adventure")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /gma [player]");
				return true;
			}
			
			if (args.length == 0){
				if (!(sender instanceof Player)){
					sender.sendMessage("You cannot gamemode yourself!");
					return true;
				}
				//Sets yourself to adventure
				Player p = (Player) sender;
				stat = changeGamemode(p, "2", sender);
			} else {
				//Sets a player to adventure
				Player p = Bukkit.getServer().getPlayer(args[0]);
				if (p == null){
					sender.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				
				stat = changeGamemode(p, "2", sender);
			}
			
			if (!stat){
				availableGamemodes(sender);
			}
			return true;
		} else if (cmd.getName().equalsIgnoreCase("gm")){
			if (!sender.hasPermission("cheesecakeminigamelobby.gamemode.all")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			if (args.length > 2){
				sender.sendMessage(ChatColor.RED + "Usage: /gm <gamemode> [player]");
				return true;
			}
			if (args.length == 0){
				sender.sendMessage(ChatColor.RED + "Usage: /gm <gamemode> [player]");
				sender.sendMessage(ChatColor.RED + "Possible gamemodes: c,s,a,sp");
				return true;
			}
			if (args.length == 1){
				if (!(sender instanceof Player)){
					sender.sendMessage("You cannot gamemode yourself!");
					return true;
				}
				//Change your gamemode
				Player p = (Player) sender;
				stat = changeGamemode(p, args[0], sender);
			} else {
				//Changes a player's gamemode
				Player p = Bukkit.getServer().getPlayer(args[1]);
				if (p == null){
					sender.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				stat = changeGamemode(p, args[0], sender);
			}
			
			if (!stat){
				availableGamemodes(sender);
			}
			return true;
		}
		return false;
	}
	
	private static boolean changeGamemode(Player p, String gamemode, CommandSender sender){
		if (gamemode.equals("1") || gamemode.equals("creative") || gamemode.equals("c")){
			if (p.getGameMode().equals(GameMode.CREATIVE)){
				sender.sendMessage(p.getDisplayName() + ChatColor.DARK_AQUA + " is already in Creative Mode");
				return true;
			}
			p.setGameMode(GameMode.CREATIVE);
			sender.sendMessage(ChatColor.DARK_PURPLE + "Changed gamemode of " + p.getDisplayName() + ChatColor.DARK_PURPLE + " to Creative Mode");
			Main.sendAdminMsg("&7&o[" + sender.getName() + ": Set Creative Mode for " + p.getName() + "]");
			return true;
		}
		if (gamemode.equals("2") || gamemode.equals("adventure") || gamemode.equals("a")){
			if (p.getGameMode().equals(GameMode.ADVENTURE)){
				sender.sendMessage(p.getDisplayName() + ChatColor.DARK_AQUA + " is already in Adventure Mode");
				return true;
			}
			p.setGameMode(GameMode.ADVENTURE);
			sender.sendMessage(ChatColor.DARK_PURPLE + "Changed gamemode of " + p.getDisplayName() + ChatColor.DARK_PURPLE + " to Adventure Mode");
			Main.sendAdminMsg("&7&o[" + sender.getName() + ": Set Adventure Mode for " + p.getName() + "]");
			return true;
		}
		if (gamemode.equals("0") || gamemode.equals("survival") || gamemode.equals("s")){
			if (p.getGameMode().equals(GameMode.SURVIVAL)){
				sender.sendMessage(p.getDisplayName() + ChatColor.DARK_AQUA + " is already in Survival Mode");
				return true;
			}
			p.setGameMode(GameMode.SURVIVAL);
			sender.sendMessage(ChatColor.DARK_PURPLE + "Changed gamemode of " + p.getDisplayName() + ChatColor.DARK_PURPLE + " to Survival Mode");
			Main.sendAdminMsg("&7&o[" + sender.getName() + ": Set Survival Mode for " + p.getName() + "]");
			return true;
		}
		if (gamemode.equals("2") || gamemode.equals("spectator") || gamemode.equals("sp")){
			sender.sendMessage(ChatColor.GOLD + "Spectator Mode is only available in Minecraft 1.8");
			return true;
		}
		return false;
	}
	
	private void availableGamemodes(CommandSender sender){
		sender.sendMessage(ChatColor.DARK_RED + "INVALID GAMEMODE");
		sender.sendMessage(ChatColor.RED + "Possible gamemodes: c,s,a,sp");
	}

}
