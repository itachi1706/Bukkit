package io.github.itachi1706.SpeedChallenge;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SpecCmd implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public SpecCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spectate")){
			sender.sendMessage(ChatColor.DARK_RED + "COMMAND COMING SOON");
			return true;
		}
		return false;
	}

}
