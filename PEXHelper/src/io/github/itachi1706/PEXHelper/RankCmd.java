package io.github.itachi1706.PEXHelper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class RankCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public RankCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rank")){
			if (args.length < 3 || args.length > 3){
				sender.sendMessage(ChatColor.RED + "Usage: /rank add/set/remove <player> <rank>");
				return true;
			}
			if (!sender.hasPermission("pexhelper.rank")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
				return true;
			}
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser user = pex.getUser(args[1]);
			PermissionGroup[] groups = pex.getGroups();
			if (user.has("pexhelper.immune")){
				if (!sender.hasPermission("pexhelper.immune.override")){
					sender.sendMessage(ChatColor.RED + "Player is immune to player rank changes");
					return true;
				}
			}
			boolean check = false;
			for (int i = 0; i < groups.length; i++){
				if (groups[i].getName().equals(args[2])){
					check = true;
					break;
				}
			}
			if (!check){
				sender.sendMessage(ChatColor.RED + "Rank does not exist!");
				String msgOfGrp = "";
				for (PermissionGroup grp : groups){
					msgOfGrp = msgOfGrp + grp.getName() + " ";
				}
				sender.sendMessage(ChatColor.RED + "Available ranks (case-sensitive): " + msgOfGrp);
				return true;
			}
			if (args[0].equalsIgnoreCase("add")){
				PermissionGroup group = pex.getGroup(args[2]);
				user.addGroup(group);
				sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been added to the rank group of " + ChatColor.RED + args[2]);
			} else if (args[0].equalsIgnoreCase("set")){
				PermissionGroup group = pex.getGroup(args[2]);
				PermissionGroup[] grouped = {group};
				user.setGroups(grouped);
				sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been set to the rank of " + ChatColor.RED + args[2]);
			} else if (args[0].equalsIgnoreCase("remove")){
				PermissionGroup group = pex.getGroup(args[2]);
				user.removeGroup(group);
				sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been removed from the rank group of " + ChatColor.RED + args[2]);
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid Usage! Usage: /rank add/set/remove <player> <rank>");
				return true;
			}
			return true;
			
		}
		return false;
	}

}
