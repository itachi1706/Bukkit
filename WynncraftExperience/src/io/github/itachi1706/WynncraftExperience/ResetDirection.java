package io.github.itachi1706.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetDirection implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ResetDirection(Main plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("resetpitch")){
			if (!sender.hasPermission("wynncraft.admin")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
				return true;
			}
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /resetpitch [player]");
				return true;
			}
			if (!(sender instanceof Player) && args.length == 0){
				sender.sendMessage("You must be an in-game player to use this command!");
				return true;
			}
			if (args.length == 0){
				Player p = (Player) sender;
				Location loc = p.getLocation();
				loc.setPitch(0f);
				p.teleport(loc);
				String finishMsg = Main.pluginPrefix + "&aPitch Reset!";
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
				String adminMsg = "&7&o[" + p.getName() + ": Reset his pitch]";
				Main.sendAdminMsg(adminMsg, sender);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null){
				sender.sendMessage(ChatColor.RED + args[0] + " is not online");
				return true;
			}
			Location loc = target.getLocation();
			loc.setPitch(0f);
			target.teleport(loc);
			String finishMsg = Main.pluginPrefix + "&aYour pitch has been reset!";
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
			String adminMsg = "&7&o[" + sender.getName() + ": Reset " + target.getName() + "'s Pitch]";
			Main.sendAdminMsg(adminMsg, sender);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("resetyaw")){
			if (!sender.hasPermission("wynncraft.admin")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
				return true;
			}
			if (args.length > 1){
				sender.sendMessage(ChatColor.RED + "Usage: /resetyaw [player]");
				return true;
			}
			if (!(sender instanceof Player) && args.length == 0){
				sender.sendMessage("You must be an in-game player to use this command!");
				return true;
			}
			if (args.length == 0){
				Player p = (Player) sender;
				Location loc = p.getLocation();
				loc.setYaw(0f);
				p.teleport(loc);
				String finishMsg = Main.pluginPrefix + "&aDirection facing Reset!";
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
				String adminMsg = "&7&o[" + p.getName() + ": Reset his facing direction]";
				Main.sendAdminMsg(adminMsg, sender);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null){
				sender.sendMessage(ChatColor.RED + args[0] + " is not online");
				return true;
			}
			Location loc = target.getLocation();
			loc.setYaw(0f);
			target.teleport(loc);
			String finishMsg = Main.pluginPrefix + "&aYour facing direction has been reset!";
			target.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
			String adminMsg = "&7&o[" + sender.getName() + ": Reset " + target.getName() + "'s facing direction]";
			Main.sendAdminMsg(adminMsg, sender);
			return true;
		}
		return false;
	}
}
