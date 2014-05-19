package io.github.itachi1706.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpTo implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public TpTo(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("tpto")){
			if (!sender.hasPermission("wynncraft.default")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
				return true;
			}
			if (args.length < 1 || args.length > 2){
				sender.sendMessage(ChatColor.RED + "Usage: /tpto <town>");
				return true;
			}
			if (!(sender instanceof Player) && args.length == 1){
				sender.sendMessage("You must be an in-game player to use this command!");
				return true;
			}
			if (args.length == 1 || (args.length == 2 && !(sender.hasPermission("wynncraft.admin")))){
				Player p = (Player) sender;
				if (checkValidity(args[0]) == -1){
					sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Unable to find this location!");
					sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Use the command " + ChatColor.GOLD + "/tplist" + ChatColor.RED + " to see all possible locations!");
					return true;
				}
				int locDetail = checkValidity(args[0]);
				WynnLocations wl = Main.locations.get(locDetail);
				Location loc = new Location(p.getLocation().getWorld(), wl.getX(), wl.getY(), wl.getZ(), wl.getYaw(), wl.getPitch());
				p.teleport(loc);
				String finishMsg = Main.pluginPrefix + "&aYou have teleported to &6" + wl.getName();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
				String adminMsg = "&7&o[" + p.getName() + ": Teleported to " + wl.getName() + "]";
				Main.sendAdminMsg(adminMsg, sender);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null){
				sender.sendMessage(ChatColor.RED + args[0] + " is not online");
				return true;
			}
			if (checkValidity(args[1]) == -1){
				sender.sendMessage(Main.pluginPrefix + ChatColor.RED + "Unable to find this location!");
				return true;
			}
			int locDetail = checkValidity(args[1]);
			WynnLocations wl = Main.locations.get(locDetail);
			Location loc = new Location(target.getLocation().getWorld(), wl.getX(), wl.getY(), wl.getZ(), wl.getYaw(), wl.getPitch());
			target.teleport(loc);
			String finishMsg = Main.pluginPrefix + "&aYou have been teleported to &6" + wl.getName();
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
			String adminMsg = "&7&o[" + sender.getName() + ": Teleported " + target.getName() + " to " + wl.getName() + "]";
			Main.sendAdminMsg(adminMsg, sender);
			return true;
		}
		return false;
	}
	
	private int checkValidity(String locationName){
		for (int i = 0; i < Main.locations.size(); i++){
			WynnLocations wl = Main.locations.get(i);
			if (wl.getName().replace('_', ' ').equalsIgnoreCase(locationName)){
				return i;
			}
		}
		return -1;
	}
	

}
