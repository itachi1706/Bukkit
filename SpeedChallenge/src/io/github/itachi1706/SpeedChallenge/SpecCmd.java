package io.github.itachi1706.SpeedChallenge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpecCmd implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public SpecCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spectate")){
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Invalid command! Usage: /spectate or /spectate [Player Name]");
			}
			if (args.length == 0){
				if (sender instanceof Player){
					if (Main.gameStart){
						Player p = (Player) sender;
						boolean check = false;
						for (int i = 0; i < Main.spectators.size(); i++){
							Player pla = Main.spectators.get(i);
							if (pla.equals(p)){
								check = true;
							}
						}
						if (!check){
							Main.spectators.add(p);
						}
						for (int i = 0; i < Main.playerList.size(); i++){
							Player pl = Main.playerList.get(i);
							if (pl.equals(p)){
								Main.playerList.remove(i);
								break;
							}
						}
						Spec.addSpectator(p);
						World w = Bukkit.getWorld("SC");
						if (!p.getWorld().equals(w)){
							Location l = new Location(Bukkit.getServer().getWorld("SC"), Bukkit.getServer().getWorld("SC").getSpawnLocation().getX(), Bukkit.getServer().getWorld("SC").getSpawnLocation().getY(), Bukkit.getServer().getWorld("SC").getSpawnLocation().getZ());
							p.teleport(l);
						}
						sender.sendMessage(ChatColor.GOLD + "You are now a spectator!");
						
					} else {
						sender.sendMessage(ChatColor.RED + "Game has not started for you to spectate!");
					}
				} else {
					sender.sendMessage("You must be an online player to use this command");
				}
				
				return true;
			}
		}
		
		if (args.length == 1){
			if (!(sender instanceof Player)){
				sender.sendMessage("Only In-Game Players can use this command");
			}
			for (Player online: Bukkit.getServer().getOnlinePlayers()){
				if (online.getName().equals(args[0]) || online.getDisplayName().contains(args[0])){
					for (int i = 0; i < Main.playerList.size(); i++){
						Player pla = Main.playerList.get(i);
						if (pla.getName().equals(online.getName())){
							sender.sendMessage(ChatColor.LIGHT_PURPLE + "Teleported to " + online.getDisplayName());
							((Player)sender).teleport(online.getLocation());
							return true;
						}
					}
					//Has a player and teleports to player
					sender.sendMessage(ChatColor.RED + "Player is not a participant of the game!");
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Unable to find player by that name");
			return true;
		}
		return false;
	}

}
