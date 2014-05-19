package io.github.itachi1706.StaffMember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ServerStatsCommand implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ServerStatsCommand(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("serverproperties")) {
			if (args.length > 0){
				displayHelp(sender);
				return true;
			}
			getServerStats(sender);
			return true;
		}
		return false;
	}
	
	public void displayHelp(CommandSender s){
		s.sendMessage(ChatColor.RED + "Usage: /serverproperties");
		if (s.hasPermission("staffmember.admin")){
			s.sendMessage(ChatColor.GREEN + "Do /staffmember help to see all the commands");
		} else {
			s.sendMessage(ChatColor.GREEN + "Do /staffmember commands to see all the commands");
		}
	}
	
	public void getServerStats(CommandSender sender){
		sender.sendMessage(ChatColor.GOLD + "==================================================");
		sender.sendMessage(ChatColor.BLUE + "                    Server Status");
		sender.sendMessage(ChatColor.GOLD + "==================================================");
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Version: &b" + Bukkit.getVersion()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Bukkit Version: &b" + Bukkit.getBukkitVersion()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Name: &b" + Bukkit.getServerName()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server ID: &b" + Bukkit.getServerId()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server MOTD: &b" + Bukkit.getMotd()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server IP: &b" + Bukkit.getIp() + ":" + Bukkit.getPort()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Max Allowed Players: &b" + Bukkit.getMaxPlayers()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Spawn Radius: &b" + Bukkit.getSpawnRadius()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server View Distance: &b" + Bukkit.getViewDistance()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server World Type: &b" + Bukkit.getWorldType()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Allow End: &b" + Bukkit.getAllowEnd()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Allow Nether: &b" + Bukkit.getAllowNether()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Allow Flight: &b" + Bukkit.getAllowFlight()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Default Gamemode: &b" + Bukkit.getDefaultGameMode().toString()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Generate Structure: &b" + Bukkit.getGenerateStructures()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Server Whitelist: &b" + Bukkit.hasWhitelist()));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Hardcore Mode: &b" + Bukkit.isHardcore()));
		sender.sendMessage(ChatColor.GOLD + "==================================================");
	}

}
