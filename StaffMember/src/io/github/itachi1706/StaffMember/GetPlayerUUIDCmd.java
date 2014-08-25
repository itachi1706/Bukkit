package io.github.itachi1706.StaffMember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPlayerUUIDCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public GetPlayerUUIDCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("getuuid")){
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /getuuid [player]");
				displayHelp(sender);
				return true;
			}
			if (!sender.hasPermission("staffmember.getuuid")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
				return true;
			}
			if (args.length == 0){
				//Get own UUID
				if (!(sender instanceof Player)){
					sender.sendMessage("CONSOLE does not have a UUID. You must be an in-game player to use this command!");
					return true;
				}
				Player p = (Player) sender;
				p.sendMessage(ChatColor.GOLD + "UUID of " + p.getDisplayName() + ChatColor.GOLD + " is: " + p.getUniqueId());
				return true;
			} else if (args.length == 1){
				//Get another user's UUID
				String playername = args[0];
				Player target = Bukkit.getServer().getPlayer(playername);
				if (target == null){
					//Attempt to get an offline player
					for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()){
						if (op.getName().equals(playername)){
							sender.sendMessage(ChatColor.GOLD + "UUID of " + op.getName() + ChatColor.GOLD + " is: " + op.getUniqueId());
							return true;
						}
					}
					sender.sendMessage(ChatColor.RED + "Unable to get UUID. Players must have joined at least once to get their UUID");
					return true;
				}
				sender.sendMessage(ChatColor.GOLD + "UUID of " + target.getDisplayName() + ChatColor.GOLD + " is: " + target.getUniqueId());
				return true;
			}
		}
		return false;
	}
	
	public void displayHelp(CommandSender s){
		if (s.hasPermission("staffmember.admin")){
			s.sendMessage(ChatColor.GREEN + "Do /staffmember help to see all the commands");
		} else {
			s.sendMessage(ChatColor.GREEN + "Do /staffmember commands to see all the commands");
		}
	}

}
