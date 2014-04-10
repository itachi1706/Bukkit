package io.github.itachi1706.StaffMember;

import io.github.itachi1706.StaffMember.MojangStatusChecker.Status;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MojangStatus implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public MojangStatus(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mojang")) {
			if (args.length > 1 || args.length < 1){
				displayMenu(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("status")){
				getMojangStatus(sender);
				return true;
			} else {
				displayMenu(sender);
				return true;
			}
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
		s.sendMessage(ChatColor.GOLD + "/mojang status: " + ChatColor.WHITE + "List the current status of Mojang Servers");
	}
	
	public void getMojangStatus(CommandSender sender){
		//Does /mojang status
		sender.sendMessage(ChatColor.GOLD + "==================================================");
		sender.sendMessage(ChatColor.BLUE + "                Mojang Server Status");
		sender.sendMessage(ChatColor.GOLD + "==================================================");
		for (MojangStatusChecker statusChecker : MojangStatusChecker.values()) {
		    String service = statusChecker.getName();
		    Status status = statusChecker.getStatus(false);
		 
		    sender.sendMessage(service + ": " + status.getColor() + status.getStatus() + " - " + status.getDescription());
		}
		sender.sendMessage(ChatColor.GOLD + "==================================================");
	}

}
