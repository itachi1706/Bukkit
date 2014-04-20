package io.github.itachi1706.StaffMember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ListStaff implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ListStaff(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("stafflist")) {
			if (args.length >= 1){
				displayMenu(sender);
				return true;
			}
			if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
				PermissionManager pex = PermissionsEx.getPermissionManager();
				getStaff(sender, pex);
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "PermissionsEx is not detected! This command cannot be used!");
				return true;
			}
		}
		return false;
	}
	
	public void getStaff(CommandSender s, PermissionManager pex){
		PermissionUser[] mod = pex.getUsers("Mod");
		PermissionUser[] admin = pex.getUsers("Admin");
		PermissionUser[] op = pex.getUsers("gameOP");
		PermissionUser[] owner = pex.getUsers("Owner");
		PermissionUser[] host = pex.getUsers("Host");
		PermissionUser[] yt = pex.getUsers("YouTuber");
		String admins = "";
		String ops = "";
		String owners = "";
		String mods = "";
		String yts = "";
		String hosts = "";
		for (int i = 0; i < mod.length; i++){
			String name = mod[i].getName();
			mods = mods + name + " ";
		}
		for (int i = 0; i < admin.length; i++){
			String name = admin[i].getName();
			admins = admins + name + " ";
		}
		for (int i = 0; i < op.length; i++){
			String name = op[i].getName();
			ops = ops + name + " ";
		}
		for (int i = 0; i < owner.length; i++){
			String name = owner[i].getName();
			owners = owners + name + " ";
		}
		for (int i = 0; i < host.length; i++){
			String name = host[i].getName();
			hosts = hosts + name + " ";
		}
		for (int i = 0; i < yt.length; i++){
			String name = yt[i].getName();
			yts = yts + name + " ";
		}
		if (admins.length() == 0){
			admins = "There are no players with the rank of ADMIN";
		}
		if (ops.length() == 0){
			ops = "There are no players with the rank of OP";
		}
		if (owners.length() == 0){
			owners = "There are no players with the rank of OWNER";
		}
		if (mods.length() == 0){
			mods = "There are no players with the rank of MOD";
		}
		if (yts.length() == 0){
			yts = "There are no players with the rank of YT";
		}
		if (hosts.length() == 0){
			hosts = "There are no players with the rank of HOST";
		}
		s.sendMessage(ChatColor.GREEN + "Staff Members of the server:");
		s.sendMessage(ChatColor.DARK_RED + "Owners: " + owners);
		s.sendMessage(ChatColor.DARK_BLUE + "OPs: " + ops);
		s.sendMessage(ChatColor.RED + "Admins: " + admins);
		s.sendMessage(ChatColor.DARK_GREEN + "Mods: " + mods);
		s.sendMessage(ChatColor.GOLD + "YouTubers: " + yts);
		s.sendMessage(ChatColor.DARK_AQUA + "Hosts: " + hosts);
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
}
