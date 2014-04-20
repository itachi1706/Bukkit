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
				displayMenu(sender);
				return true;
			}
			getServerStats(sender);
			return true;
		}
		return false;
	}
	
	public void displayMenu(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------StaffMember Commands-----------");
		s.sendMessage(ChatColor.GOLD + "/staffonline: " + ChatColor.WHITE + "List all staff currently online");
		s.sendMessage(ChatColor.GOLD + "/staffmember commands: " + ChatColor.WHITE + "List all plugin commands");
		s.sendMessage(ChatColor.GOLD + "/staffmember reload: " + ChatColor.WHITE + "Reloads plugin");
		s.sendMessage(ChatColor.GOLD + "/staffmember: " + ChatColor.WHITE + "Main plugin command");
		s.sendMessage(ChatColor.GOLD + "/stafflist: " + ChatColor.WHITE + "List all staff (online/offline)");
		s.sendMessage(ChatColor.GOLD + "/getuuid: " + ChatColor.WHITE + "Gets the UUID of an online player");
		s.sendMessage(ChatColor.GOLD + "/mojang status: " + ChatColor.WHITE + "List the current status of Mojang Servers");
		s.sendMessage(ChatColor.GOLD + "/serverproperties: " + ChatColor.WHITE + "List the current server properties");
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
