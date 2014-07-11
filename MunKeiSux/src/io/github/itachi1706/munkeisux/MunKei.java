package io.github.itachi1706.munkeisux;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MunKei extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable(){
		getLogger().info("Enabling Plugin...");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable(){
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("munkei")){
			if (args.length != 1){
				sender.sendMessage(ChatColor.DARK_RED + "YOU NIGGA!");
				return true;
			}
			if (!sender.hasPermission("mk.default")){
				sender.sendMessage(ChatColor.DARK_RED + "YOU NIGGA! NO PERMS BAKA!");
				return true;
			}
			Player p = getServer().getPlayer(args[0]);
			if (p == null){
				sender.sendMessage(ChatColor.DARK_RED + "YOU NIGGA! HES NOT INSIDE BAKA!");
				return true;
			}
			sender.sendMessage(ChatColor.GREEN + "YOLOED " + p.getDisplayName());
			p.sendMessage(ChatColor.RED + "FAGGIT!");
			return true;
			
		}
		return false;
	}
	
	@EventHandler
	public void munkeiJoined(PlayerJoinEvent e){
		if (e.getPlayer().getName().equals("Vulmest")){
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + " THE NIGGA CALLED MUN KEI JOINED!");
		}
	}

}
