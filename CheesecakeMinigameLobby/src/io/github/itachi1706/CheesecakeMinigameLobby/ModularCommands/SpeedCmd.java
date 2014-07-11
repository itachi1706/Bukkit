package io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.itachi1706.CheesecakeMinigameLobby.Main;

//Changes a player walk or fly speed
public class SpeedCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public SpeedCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("speed")){
			if (!Main.commandSpeed){
				sender.sendMessage(ChatColor.RED + "This command has been disabled for this server!");
				return true;
			}
			if (!(sender instanceof Player)){
				sender.sendMessage("You must be a player in-game to use this command");
				return true;
			}
			if (!sender.hasPermission("cheesecakeminigamelobby.abilities.speed")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
				return true;
			}
			if (args.length < 2 || args.length > 3){
    			displayHelp(sender);
				return true;
    		}
			Player p = (Player) sender;
			if (args.length == 2){
				//Adjust own speed
				if (args[0].equalsIgnoreCase("fly")){
					//Adjust fly speed (-1 to 1) (default 0.1f)
					if (args[1].equalsIgnoreCase("reset")){
						//Resets Fly Speed
						p.setFlySpeed(0.1f);
						p.sendMessage(ChatColor.GOLD + "Fly Speed has been " + ChatColor.RED + "reset");
						return true;
					} else if (args[1].equalsIgnoreCase("check")){
						//Check Fly Speed
						p.sendMessage(ChatColor.GOLD + "Fly Speed: " + ChatColor.AQUA + (p.getFlySpeed()*10));
						return true;
					} else {
						try{
						double flyspeed = (Integer.parseInt(args[1]) / 10.0);
						p.setFlySpeed((float) flyspeed);
						p.sendMessage(ChatColor.GOLD + "Fly Speed set to " + ChatColor.AQUA + args[1]);
						} catch (NumberFormatException e){
							p.sendMessage(ChatColor.RED + "Usage: /speed fly reset/check/<flyspeed>");
							p.sendMessage(ChatColor.RED + "Fly speed can only be from -10 to 10");
						} catch (IllegalArgumentException ex){
							p.sendMessage(ChatColor.RED + "Usage: /speed fly reset/check/<flyspeed>");
							p.sendMessage(ChatColor.RED + "Fly speed can only be from -10 to 10");
						}
						return true;
					}
				} else if (args[0].equalsIgnoreCase("walk")){
					//Adjust walking speed (-1 to 1) (default 0.2f)
					if (args[1].equalsIgnoreCase("reset")){
						//Resets Fly Speed
						p.setWalkSpeed(0.2f);
						p.sendMessage(ChatColor.GOLD + "Walk Speed has been " + ChatColor.RED + "reset");
						return true;
					} else if (args[1].equalsIgnoreCase("check")){
						//Check walk speed
						p.sendMessage(ChatColor.GOLD + "Walk Speed: " + ChatColor.AQUA + (p.getWalkSpeed()*10));
						return true;
					} else {
						try{
						double walkspeed = (Integer.parseInt(args[1]) / 10.0);
						p.setWalkSpeed((float) walkspeed);
						p.sendMessage(ChatColor.GOLD + "Walk Speed set to " + ChatColor.AQUA + args[1]);
						} catch (NumberFormatException e){
							p.sendMessage(ChatColor.RED + "Usage: /speed walk reset/check/<walkspeed>");
							p.sendMessage(ChatColor.RED + "Walk speed can only be from -10 to 10");
						} catch (IllegalArgumentException ex){
							p.sendMessage(ChatColor.RED + "Usage: /speed walk reset/check/<walkspeed>");
							p.sendMessage(ChatColor.RED + "Walk speed can only be from -10 to 10");
						}
						return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Usage: /speed walk/fly reset/check/<speed>");
					p.sendMessage(ChatColor.RED + "Speed can only be from -10 to 10");
					return true;
				}
			} else if (args.length == 3){
				//Adjust another player's speed
				Player target = Bukkit.getServer().getPlayer(args[2]);
				if (target == null){
					p.sendMessage(ChatColor.RED + "Player is not online!");
					return true;
				}
				if (args[0].equalsIgnoreCase("fly")){
					//Adjust fly speed (-1 to 1) (default 0.1f)
					if (args[1].equalsIgnoreCase("reset")){
						//Resets Fly Speed
						target.setFlySpeed(0.1f);
						target.sendMessage(ChatColor.GOLD + "Fly Speed has been " + ChatColor.RED + "reset");
						p.sendMessage(ChatColor.GOLD + "Fly speed for " + target.getDisplayName() + ChatColor.GOLD + " has been " + ChatColor.RED + "reset");
						return true;
					} else if (args[1].equalsIgnoreCase("check")){
						p.sendMessage(target.getDisplayName() + ChatColor.GOLD + " Fly Speed: " + ChatColor.AQUA + (target.getFlySpeed()*10));
						return true;
					} else {
						try{
						double flyspeed = (Integer.parseInt(args[1]) / 10.0);
						target.setFlySpeed((float) flyspeed);
						target.sendMessage(ChatColor.GOLD + "Fly Speed set to " + ChatColor.AQUA + args[1]);
						p.sendMessage(ChatColor.GOLD + "Fly speed for " + target.getDisplayName() + ChatColor.GOLD + " has been set to " + ChatColor.AQUA + args[1]);
						} catch (NumberFormatException e){
							p.sendMessage(ChatColor.RED + "Usage: /speed fly reset/check/<flyspeed>");
							p.sendMessage(ChatColor.RED + "Fly speed can only be from -10 to 10");
						} catch (IllegalArgumentException ex){
							p.sendMessage(ChatColor.RED + "Usage: /speed fly reset/check/<flyspeed>");
							p.sendMessage(ChatColor.RED + "Fly speed can only be from -10 to 10");
						}
						return true;
					}
				} else if (args[0].equalsIgnoreCase("walk")){
					//Adjust walking speed (-1 to 1) (default 0.2f)
					if (args[1].equalsIgnoreCase("reset")){
						//Resets Fly Speed
						target.setWalkSpeed(0.2f);
						target.sendMessage(ChatColor.GOLD + "Walk Speed has been " + ChatColor.RED + "reset");
						p.sendMessage(ChatColor.GOLD + "Walk speed for " + target.getDisplayName() + ChatColor.GOLD + " has been " + ChatColor.RED + "reset");
						return true;
					} else if (args[1].equalsIgnoreCase("check")){
						p.sendMessage(target.getDisplayName() + ChatColor.GOLD + " Walk Speed: " + ChatColor.AQUA + (target.getWalkSpeed()*10));
						return true;
					} else {
						try{
						double walkspeed = (Integer.parseInt(args[1]) / 10.0);
						target.setWalkSpeed((float) walkspeed);
						target.sendMessage(ChatColor.GOLD + "Walk Speed set to " + ChatColor.AQUA + args[1]);
						p.sendMessage(ChatColor.GOLD + "Walk speed for " + target.getDisplayName() + ChatColor.GOLD + " has been set to " + ChatColor.AQUA + args[1]);
						} catch (NumberFormatException e){
							p.sendMessage(ChatColor.RED + "Usage: /speed walk reset/check/<walkspeed>");
							p.sendMessage(ChatColor.RED + "Walk speed can only be from -10 to 10");
						} catch (IllegalArgumentException ex){
							p.sendMessage(ChatColor.RED + "Usage: /speed walk reset/check/<walkspeed>");
							p.sendMessage(ChatColor.RED + "Walk speed can only be from -10 to 10");
						}
						return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Usage: /speed walk/fly reset/check/<speed>");
					p.sendMessage(ChatColor.RED + "Speed can only be from -10 to 10");
					return true;
				}
			}
		}
		return false;
	}
	
	private void displayHelp(CommandSender s){
		s.sendMessage(ChatColor.RED + "Usage: /speed walk/fly reset/check/<speed>");
		s.sendMessage(ChatColor.RED + "Speed can only be from -10 to 10");
	}

}
