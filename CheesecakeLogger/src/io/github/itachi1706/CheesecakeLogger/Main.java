package io.github.itachi1706.CheesecakeLogger;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

public class Main extends JavaPlugin implements Listener {
	
	static File loggingDB;
	
	public static Database sql;
	public static boolean pexPresent = false;
	
	ArrayList<PlayerIpStorage> playerIpList = new ArrayList<PlayerIpStorage>();
	
	@Override
	public void onEnable(){
		getLogger().info("Enabling Plugin...");
		sql = new SQLite(getLogger(), "[CLogger] " , this.getDataFolder().getAbsolutePath(), "clogger", ".sqlite");
		if (!sql.isOpen()) {
		    sql.open();
		}
		if (sql.open()){
			//Do SQL stuff
			SQLiteHelper.checkTableExist();
		}
		getServer().getPluginManager().registerEvents(this, this);
		if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
		    getLogger().info("Detected PermissionsEx!");
		    pexPresent=true;
		}
		getLogger().info("Plugin loaded!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Disabling Plugin...");
		sql.close();
		getLogger().info("Plugin disabled!");
	}
	
	@EventHandler
	private void onJoin(PlayerLoginEvent e){
		String ipAddr = e.getAddress().getHostAddress();
		SQLiteHelper.addLoginLog(e.getPlayer().getName(), e.getPlayer().getLocation(), ipAddr);
		playerIpList.add(new PlayerIpStorage(e.getPlayer(), ipAddr));
	}
	
	@EventHandler
	private void onQuit(PlayerQuitEvent e){
		for (int i = 0; i < playerIpList.size(); i++){
			if (playerIpList.get(i).getPlayer().equals(e.getPlayer())){
				SQLiteHelper.addLogoutLog(e.getPlayer().getName(), e.getPlayer().getLocation(), playerIpList.get(i).getPlayerIP());
				playerIpList.remove(i);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("cheesecakelogger")){
			sender.sendMessage(ChatColor.BLUE + "View login info of a player with /viewlogins <player> <#>");
			sender.sendMessage(ChatColor.BLUE + "Delete login data of a player with /delloginhistory <player>");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("viewlogins")){
			if (args.length < 1 || args.length > 2){
				sender.sendMessage(ChatColor.RED + "Invalid Usage! Usage: /viewlogins <player> <#>");
				return true;
			}
			if (!sender.hasPermission("cheesecakelogger.logs")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
				return true;
			}
			int value;
			if (args.length == 1){
				SQLiteHelper.checkLoginLogs(sender, args[0], 1);
				return true;
			}
			if (args.length == 2){
				try {
					value = Integer.parseInt(args[1]);
					if (value == 0){
						sender.sendMessage(ChatColor.RED + "Invalid Usage. Please specify a number above 0!");
						return true;
					}
					
					SQLiteHelper.checkLoginLogs(sender, args[0], value);
				} catch (NumberFormatException ex){
					sender.sendMessage(ChatColor.RED + "Invalid Usage. Please specify only a number!");
					return true;
				}
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("delloginhistory")){
			if (args.length < 1 || args.length > 1){
				sender.sendMessage(ChatColor.RED + "Invalid Usage! Usage: /delloginhistory <player>");
				return true;
			}
			if (!sender.hasPermission("cheesecakelogger.admin")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
				return true;
			}
			SQLiteHelper.deleteLogs(sender, args[0]);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("viewplayerstats")){
			if (args.length != 1){
				sender.sendMessage(ChatColor.RED + "Invalid Usage! Usage: /viewplayerstats <player>");
				return true;
			}
			if (!sender.hasPermission("cheesecakelogger.logs")){
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
				return true;
			}
			for (OfflinePlayer p : getServer().getOfflinePlayers()){
				if (p.getName().equals(args[0])){
					SQLiteHelper.checkLoginStats(sender, args[0], p.getUniqueId(), convertTime(p.getFirstPlayed()), convertTime(p.getLastPlayed()));
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + args[0] + " is either a nickname or has never joined this server.");
			return true;
		}
		return false;
	}
	
	private String convertTime(long time){
	    Date date = new Date(time);
	    Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
	    return format.format(date);
	}

}
