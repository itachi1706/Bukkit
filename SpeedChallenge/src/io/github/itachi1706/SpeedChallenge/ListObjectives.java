package io.github.itachi1706.SpeedChallenge;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListObjectives implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ListObjectives(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("listobjectives")){
			if (args.length > 0){
				sender.sendMessage(ChatColor.RED + "Invalid Usage! Proper Usage: /listobjectives");
				return true;
			}
			if (!Main.gameStart){
				sender.sendMessage(ChatColor.RED + "Game has not started yet!");
			}
			if (sender instanceof Player){
				for (int i = 0; i < Main.playerList.size(); i++){
					Player p = Main.playerList.get(i);
					if (p.getName().equals(sender.getName())){
						//Is a player playing the game
						PreGameRunnable.checkPlayerObjectives((Player) sender);
						return true;
					}
				}
				sender.sendMessage("You are not currently in game, so you cannot use this command!");
				return true;
			} else {
				sender.sendMessage("You must be a player ingame to use this command!");
				return true;
			}
		}
		return false;
	}
}
