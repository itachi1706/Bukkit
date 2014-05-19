package io.github.itachi1706.WynncraftExperience;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public HelpCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("help")){
			String[] msgList = {"&aWynnCraft Experience Server Help",
					"&6/tpto <town> &e- Teleports to a town",
					"&6/tplist &e- Lists all towns that you can teleport to",
					"&6/speedmode &e- Superspeed! Goes insanely fast and high!"};
			for (String msg : msgList)
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			return true;
		}
		return false;
	}

}
