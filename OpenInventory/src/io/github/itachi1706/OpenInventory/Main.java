package io.github.itachi1706.OpenInventory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private Plugin plugin;
	
	static File playerFile;
    static FileConfiguration playerConfig;
	
	@Override
	public void onEnable(){
		getLogger().info("Enabling Plugin...");
		plugin = this;
		playerFile = new File(getDataFolder(), "players.yml");
		getLogger().info("Loading Player Option File...");
		//Ban/Mute File Config
		try {
			if (!playerFile.exists()){
				if (!playerFile.getParentFile().exists()){
					playerFile.getParentFile().mkdirs();
					copy(getResource("players.yml"), playerFile);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		playerConfig = new YamlConfiguration();
		getLogger().info("Player Option File loaded");
		loadListeners();
		loadYamls();
		getCommand("silentchest").setExecutor(new SilentChest());
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Disabling Plugin...");
		HandlerList.unregisterAll(plugin);
	}
	
	private void reloadCommand(){
		getLogger().info("Reloading Plugin...");
		HandlerList.unregisterAll(plugin);
		loadListeners();
		getLogger().info("Plugin reloaded");
	}
	
	private void loadListeners(){
		getLogger().info("Reloading Listeners...");
		//getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new SilentChest(), this);
		getLogger().info("Listeners reloaded");
	}
	
	private void copy(InputStream in, File file){
		try{
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len=in.read(buf))>0){
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (NullPointerException e){
			getLogger().severe("No File found. Creating...");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void savePlayer(){
		try{
			playerConfig.save(playerFile);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void loadYamls() {
	    try {
	    	playerConfig.load(playerFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("oi")){
			if (args.length == 0){
				sender.sendMessage(ChatColor.GREEN + "OpenInventory plugin by " + ChatColor.GOLD + "itachi1706");
				sender.sendMessage(ChatColor.GREEN + "Do /oi help to view the list of commands");
				return true;
			}
			if (args.length != 1){
				invalidCommandNotice(sender);
				return true;
			}
			if (args[0].equals("help")){
				getHelp(sender);
				return true;
			}
			if (args[0].equals("reload")){
				reloadCommand();
				sender.sendMessage(ChatColor.GREEN + "Plugin Reloaded");
				return true;
			}
			invalidCommandNotice(sender);
			return true;
		}
		return false;
	}
	
	private void getHelp(CommandSender sender){
		sender.sendMessage(ChatColor.GOLD + "OpenInventory Command List");
		sender.sendMessage(ChatColor.GOLD + "/oi help:" + ChatColor.WHITE + " Shows the Help Screen");
		sender.sendMessage(ChatColor.GOLD + "/oi reload:" + ChatColor.WHITE + " Reloads the plugin");
		sender.sendMessage(ChatColor.GOLD + "/silentchest [player]:" + ChatColor.WHITE + " Toggles Silent Chest Mode");
		sender.sendMessage(ChatColor.GOLD + "/silentchest force <player>:" + ChatColor.WHITE + " Force Toggles Silent Chest Mode");
		sender.sendMessage(ChatColor.GOLD + "/silentchest check <player>:" + ChatColor.WHITE + " Check Silent Chest Mode for a player");
	}
	
	public static void invalidCommandNotice(CommandSender sender){
		sender.sendMessage(ChatColor.RED + "INVALID COMMAND. Please do /oi help to view list of commands");
	}
	
	public static void invalidPerms(CommandSender sender){
		sender.sendMessage(ChatColor.RED + "You don't have permission to use this!");
	}
	
	public static void consoleUser(CommandSender sender){
		sender.sendMessage("This command cannot be used in the console!");
	}
	
	public static void invalidUser(CommandSender sender){
		sender.sendMessage(ChatColor.RED + "Please define a player name");
	}

}
