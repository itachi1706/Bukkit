package io.github.itachi1706.StaffMember;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListOnlineStaff implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ListOnlineStaff(Main plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staffonline")) {
			if (args.length >= 1){
				displayMenu(sender);
				return true;
			}
			
			getOnlineStaff(sender);
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
	
	public void getOnlineStaff(CommandSender s){
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		ArrayList<String> ops = new ArrayList<String>();
		ArrayList<String> admins = new ArrayList<String>();
		ArrayList<String> owners = new ArrayList<String>();
		ArrayList<String> mods = new ArrayList<String>();
		ArrayList<String> yts = new ArrayList<String>();
		String admin = "";
		String op = "";
		String owner = "";
		String mod = "";
		String yt = "";
		for (int i = 0; i < players.length; i++){
			Player p = players[i];
			if (p.hasPermission("staffmember.isowner")){
				owners.add(p.getDisplayName());
			} else if (p.hasPermission("staffmember.isop")){
				ops.add(p.getDisplayName());
			} else if (p.hasPermission("staffmember.isadmin")){
				admins.add(p.getDisplayName());
			} else if (p.hasPermission("staffmember.ismod")){
				mods.add(p.getDisplayName());
			} else if (p.hasPermission("staffmember.isyt")){
				yts.add(p.getDisplayName());
			} 
		}
		for (int i1 = 0; i1 < ops.size(); i1++){
			String tmp = ops.get(i1);
			op = op + tmp + " ";
		}
		for (int i1 = 0; i1 < admins.size(); i1++){
			String tmp = admins.get(i1);
			admin = admin + tmp + " ";
		}
		for (int i1 = 0; i1 < owners.size(); i1++){
			String tmp = owners.get(i1);
			owner = owner + tmp + " ";
		}
		for (int i1 = 0; i1 < mods.size(); i1++){
			String tmp = mods.get(i1);
			mod = mod + tmp + " ";
		}
		for (int i1 = 0; i1 < yts.size(); i1++){
			String tmp = yts.get(i1);
			yt = yt + tmp + " ";
		}
		if (admin.length() == 0){
			admin = "None is currently online";
		}
		if (op.length() == 0){
			op = "None is currently online";
		}
		if (owner.length() == 0){
			owner = "None is currently online";
		}
		if (mod.length() == 0){
			mod = "None is currently online";
		}
		if (yt.length() == 0){
			yt = "None is currently online";
		}
		
			s.sendMessage(ChatColor.GREEN + "Staff Members currently online:");
			s.sendMessage(ChatColor.DARK_RED + "Owners: " + ChatColor.WHITE + owner);
			s.sendMessage(ChatColor.DARK_BLUE + "OPs: " + ChatColor.WHITE + op);
			s.sendMessage(ChatColor.RED + "Admins: " + ChatColor.WHITE + admin);
			s.sendMessage(ChatColor.DARK_GREEN + "Mods: " + ChatColor.WHITE + mod);
			s.sendMessage(ChatColor.GOLD + "YouTubers: " + ChatColor.WHITE + yt);
		
	}

}
