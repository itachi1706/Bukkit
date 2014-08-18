package io.github.itachi1706.NickNamer;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public Reload(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("nn")){
			if (args.length < 1 || args.length > 1){
				//Not enough arguments
				sender.sendMessage(ChatColor.GOLD + "/nn reload: " + ChatColor.WHITE + "Reloads the plugin and refreshes all players");	//Notifies Player
				return false;
			}
			if (!sender.hasPermission("nicknamer.nick.reload")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return false;
			}
			if (args[0].equalsIgnoreCase("reload")){
				Main.loadYamls();
				Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
				Iterator<? extends Player> i = playerList.iterator();
				while (i.hasNext()){
					Nick.refreshNameTag(i.next());
				}
				sender.sendMessage(ChatColor.GREEN + "Plugin nicks reloaded from file.");
				return true;
			}	else {
				sender.sendMessage(ChatColor.GOLD + "/nn reload: " + ChatColor.WHITE + "Reloads the plugin and refreshes all players");
				return false;
			}
		}
		return false;
	}

}
