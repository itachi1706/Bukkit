package io.github.itachi1706.NickNamer;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public NickCmd(Main plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("nick")){	//Start of Nick
			if (args.length < 1){
				//Not enough arguments
				sender.sendMessage(ChatColor.RED + "Please enter a player's name to nick into or enter /nickreset to reset your nick");	//Notifies Player
				return true;
			}
			if (args.length > 2){
				//Too many arguments
				sender.sendMessage(ChatColor.RED + "Invalid Usage");
				return true;
			}
			if (args.length == 1){
				//Nicking yourself
				if (!sender.hasPermission("nicknamer.nick")) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
					return true;
				}
				if (sender instanceof Player) {
			           Player player = (Player) sender;
			           // do something
			           Collection<? extends Player> checker = Bukkit.getServer().getOnlinePlayers();
			           Iterator<? extends Player> i = checker.iterator();
			           boolean notACheck = true;
			           while (i.hasNext()){
			        	   if (Main.nick.getString(i.next().getName()  + ".nick").equals(args[0])){
			        		   notACheck = false;
			        	   }
			           }
			           if (notACheck == false){
			        	   player.sendMessage(ChatColor.RED + "Nickname has already been taken!");
			        	   return true;
			           } else {
			           player.sendMessage(ChatColor.RED + "Your nickname is now " + ChatColor.GOLD + args[0]);
			           Main.nick.set(player.getName() + ".nick", args[0]);
			           Main.saveYamls();
			           Nick.refreshNameTag(player);
			           Nick.updateChatName(player);
			           Nick.updateTabList(player);
			           return true;
			           }
			        } else {
			           sender.sendMessage(ChatColor.RED + "You must be a player in game to use this command!");
			           return true;
			        }
			
		} else if (args.length == 2){
			//Nicking others
			if (!sender.hasPermission("nicknamer.nick.other")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			}
			Player target = (Bukkit.getServer().getPlayer(args[0]));
	           if (target == null){
	        	   sender.sendMessage(ChatColor.BLUE + args[0] + " is not online!");
	           } else {
	           // do something
	        	   Collection<? extends Player> checker = Bukkit.getServer().getOnlinePlayers();
		           Iterator<? extends Player> i = checker.iterator();
		           boolean notACheck = true;
		           while (i.hasNext()){
		        	   if (Main.nick.getString(i.next().getName()  + ".nick").equals(args[1])){
		        		   notACheck = false;
		        	   }
		           }
		           if (notACheck == false){
		        	  sender.sendMessage(ChatColor.RED + "Nickname has already been taken!");
		        	  return true;
		           } else {
	        	   sender.sendMessage(ChatColor.RED + target.getName() + " has their nickname changed to " + ChatColor.GOLD + args[1]);
	        	   target.sendMessage(ChatColor.RED + "Your nickname is now " + ChatColor.GOLD + args[1]);
	        	   Main.nick.set(target.getName()  + ".nick", args[1]);
	        	   Main.saveYamls();
	        	   Nick.refreshNameTag(target);
	        	   Nick.updateChatName(target);
		           Nick.updateTabList(target);
		           return true;
		           }
	           
		}	
	}	
		

	}
		
		if (cmd.getName().equalsIgnoreCase("nickreset")){
				//Reset your nick
			if (args.length > 1){
				//Too many arguments
				sender.sendMessage(ChatColor.RED + "Invalid Usage");
				return true;
			}
				if (args.length == 0){
					//Reset your nick
					if (!sender.hasPermission("nicknamer.nick.reset")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
						return true;
					}
					if (sender instanceof Player) {
				           Player player = (Player) sender;	//Person executing the command
				           player.sendMessage(ChatColor.RED + "Your nickname has been reset");
				           Main.nick.set(player.getName() + ".nick", player.getName());
				           Main.saveYamls();
				           Nick.refreshNameTag(player);
				           Nick.updateChatName(player);
				           Nick.updateTabList(player);
				           return true;
				        } else {
				           sender.sendMessage(ChatColor.RED + "You must be a player in game to use this command!");
				           return true;
				        }
				} else if (args.length == 1){
					//Reset other player's nick
					if (!sender.hasPermission("nicknamer.nick.reset.other")) {
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
						return true;
				}
			           Player target = (Bukkit.getServer().getPlayer(args[0]));
			           if (target == null){
			        	   sender.sendMessage(ChatColor.BLUE + args[0] + " is not online!");
			           } else {
			        	   sender.sendMessage(ChatColor.RED + target.getName() + " has their nickname reset");
			        	   target.sendMessage(ChatColor.RED + "Your nickname has been reset");
			        	   Main.nick.set(target.getName() + ".nick", target.getName());
			        	   Main.saveYamls();
			        	   Nick.refreshNameTag(target);
			        	   Nick.updateChatName(target);
				           Nick.updateTabList(target);
				           return true;
				}
				
			}
		}
		return false;
	}
}
