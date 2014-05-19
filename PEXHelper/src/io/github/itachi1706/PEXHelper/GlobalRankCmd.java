package io.github.itachi1706.PEXHelper;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class GlobalRankCmd implements CommandExecutor{
	
	private Main plugin;
	
	public GlobalRankCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("grank")){
			if (args.length < 3 || args.length > 3){
				sender.sendMessage(ChatColor.RED + "Usage: /grank add/set/remove <player> <rank>");
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
			String msgToSend = "";
			if (args[0].equalsIgnoreCase("add")){
				PermissionGroup group = pex.getGroup(args[2]);
				user.addGroup(group);
				msgToSend = "pex user " + args[1] + " group add " + group.getName();
				sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been added to the rank group of " + ChatColor.RED + args[2]);
			} else if (args[0].equalsIgnoreCase("set")){
				PermissionGroup group = pex.getGroup(args[2]);
				PermissionGroup[] grouped = {group};
				user.setGroups(grouped);
				msgToSend = "pex user " + args[1] + " group set " + group.getName();
				sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been set to the rank of " + ChatColor.RED + args[2]);
			} else if (args[0].equalsIgnoreCase("remove")){
				PermissionGroup group = pex.getGroup(args[2]);
				user.removeGroup(group);
				msgToSend = "pex user " + args[1] + " group remove " + group.getName();
				sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GREEN + " has been removed from the rank group of " + ChatColor.RED + args[2]);
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid Usage! Usage: /grank add/set/remove <player> <rank>");
				return true;
			}
			
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("Forward");
				out.writeUTF("ALL");
				out.writeUTF("PEXHelper");	//Channel name
				
				ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
				DataOutputStream msgout = new DataOutputStream(msgbytes);
				Bukkit.getLogger().info("Message:" + msgToSend);
				msgout.writeUTF(msgToSend); // You can do anything you want with msgout
				msgout.writeShort(123);
				
				out.writeShort(msgbytes.toByteArray().length);
				out.write(msgbytes.toByteArray());
				Bukkit.getServer().sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				sender.sendMessage(ChatColor.RED + "An exception occured trying to send data out to the network");
				sender.sendMessage(ChatColor.RED + "Doing only local server now");
			}
			return true;
			
		}
		return false;
	}

}
