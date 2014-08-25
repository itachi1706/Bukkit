package io.github.itachi1706.CheesecakeMinigameLobby;

import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.BurnCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.FlingCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.FlyCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.GamemodeCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.SmiteCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.SpeedCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.WowCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.ZeusWrathCmd;
import io.github.itachi1706.CheesecakeMinigameLobby.ModularCommands.ZeusWrathListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	static double pluginVersion = 1.2;
	public static String pluginPrefix = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "Cheesecake Minigame Lobby" + ChatColor.DARK_RED + "] " + ChatColor.WHITE;
	
	public static List<String> lobbymsg = new ArrayList<String>();
	public static List<String> advmsg = new ArrayList<String>();
	public static List<String> survmsg = new ArrayList<String>();
	public static String pluginMode;
	public static String advMapName;
	public static String advMapAuthor;
	public static String hubworld;
	public static String playerlistformat;
	
	//Modules Command
	public static boolean commandFly, commandSpeed, commandSmite, commandZeus, commandFling, commandBurn, commandWow;
	
	@Override
	public void onEnable(){
		//Logic when plugin gets enabled
		getLogger().info("Enabling Plugin...");
		this.getConfig().options().copyDefaults(true);
		this.saveDefaultConfig();
		this.saveConfig();
		initializeConfig();
		getLogger().info("Enabling Plugin listeners...");
		loadListeners();
		getCommand("fly").setExecutor(new FlyCmd(this));
		getCommand("wow").setExecutor(new WowCmd(this));
		getCommand("speed").setExecutor(new SpeedCmd(this));
		getCommand("cmla").setExecutor(new ModulesCmd(this));
		getCommand("zeus").setExecutor(new ZeusWrathCmd(this));
		getCommand("smite").setExecutor(new SmiteCmd(this));
		getCommand("burn").setExecutor(new BurnCmd(this));
		getCommand("fling").setExecutor(new FlingCmd(this));
		getCommand("gm").setExecutor(new GamemodeCmd(this));
		getCommand("gmc").setExecutor(new GamemodeCmd(this));
		getCommand("gms").setExecutor(new GamemodeCmd(this));
		getCommand("gma").setExecutor(new GamemodeCmd(this));
	}
	
	@Override
	public void onDisable(){
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
		this.saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Main Command
    	if(cmd.getName().equalsIgnoreCase("cml")){
    		if (args.length < 1 || args.length > 1){
    			displayHelp(sender);
				return true;
    		}
    		if (!sender.hasPermission("cheesecakeminigamelobby.staff")){
    			sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
    			return true;
    		}
    		if (args[0].equalsIgnoreCase("version")){
    			//Show Plugin Version
    			sender.sendMessage(pluginPrefix + ChatColor.GOLD + "======================================");
				sender.sendMessage(pluginPrefix + ChatColor.BLUE + "Cheesecake Minigame Lobby Plugin");
				sender.sendMessage(pluginPrefix + ChatColor.GOLD + "======================================");
				sender.sendMessage(pluginPrefix + "Version: " + ChatColor.AQUA + pluginVersion);
    			return true;
    		} else if (args[0].equalsIgnoreCase("reload")){
    			//Reload Plugin
    			reloadCommand(this);
    			sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Configuration reloaded");
    			return true;
    		} else if (args[0].equalsIgnoreCase("stats")){
    			//Show statistics
    			showStats(sender);
    			return true;
    		} else if (args[0].equalsIgnoreCase("module")){
    			listModules(sender);
    			return true;
    		} else {
    			//Error
    			displayHelp(sender);
    			return true;
    		}
		}
    	return false;
	}
    	
	public void displayHelp(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------CheesecakeMinigameLobby Commands-----------");
    	s.sendMessage(ChatColor.GOLD + "/cml version: " + ChatColor.WHITE +  "Check current plugin version");
    	s.sendMessage(ChatColor.GOLD + "/cml reload: " + ChatColor.WHITE +  "Reload Config");
    	s.sendMessage(ChatColor.GOLD + "/cml stats: " + ChatColor.WHITE +  "Display Current Config stats");
    	s.sendMessage(ChatColor.GOLD + "/cml module: " + ChatColor.WHITE +  "Display Current Modules");
    	s.sendMessage(ChatColor.GOLD + "/cmla <module>: " + ChatColor.WHITE +  "Administration Command");
    	s.sendMessage(ChatColor.GOLD + "/gmc [player]: " + ChatColor.WHITE +  "Sets Player to Creative Mode");
    	s.sendMessage(ChatColor.GOLD + "/gms [player]: " + ChatColor.WHITE +  "Sets Player to Survival Mode");
    	s.sendMessage(ChatColor.GOLD + "/gma [player]: " + ChatColor.WHITE +  "Sets Player to Adventure Mode");
    	s.sendMessage(ChatColor.GOLD + "/gm [player]: " + ChatColor.WHITE +  "Changes a Player's Gamemode");
    	if (commandFly){
    		s.sendMessage(ChatColor.GOLD + "/fly [player]: " + ChatColor.WHITE +  "Allows a player to fly");
    	}
    	if (commandSpeed){
    		s.sendMessage(ChatColor.GOLD + "/speed fly/walk check/reset/<speed> [player]: " + ChatColor.WHITE +  "Display Current Config stats");
    	}
    	if (commandSmite){
    		s.sendMessage(ChatColor.GOLD + "/smite [player]: " + ChatColor.WHITE +  "Smites a player with lightning!");
    	}
    	if (commandZeus){
    		s.sendMessage(ChatColor.GOLD + "/zeus [player]: " + ChatColor.WHITE +  "Kills a player with the wrath of Zeus");
    	}
    	if (commandFling){
    		s.sendMessage(ChatColor.GOLD + "/fling [player]: " + ChatColor.WHITE +  "Flings a player into the air!");
    	}
    	if (commandBurn){
    		s.sendMessage(ChatColor.GOLD + "/burn [player]: " + ChatColor.WHITE +  "Burns a player!");
    	}
    	if (commandWow){
    		s.sendMessage(ChatColor.GOLD + "/wow [player]: " + ChatColor.WHITE +  "Doges a player!");
    	}
	}
	
	private void initializeConfig(){
		lobbymsg = this.getConfig().getStringList("lobbymsg");
		advmsg = this.getConfig().getStringList("advmsg");
		survmsg = this.getConfig().getStringList("survmsg");
		advMapName = this.getConfig().getString("mapname");
		advMapAuthor = this.getConfig().getString("mapauthor");
		pluginMode = this.getConfig().getString("mode");
		commandFly = this.getConfig().getBoolean("modules.fly");
		commandSpeed = this.getConfig().getBoolean("modules.speed");
		commandSmite = this.getConfig().getBoolean("modules.smite");
		commandZeus = this.getConfig().getBoolean("modules.zeus");
		commandFling = this.getConfig().getBoolean("modules.fling");
		commandBurn = this.getConfig().getBoolean("modules.burn");
		commandWow = this.getConfig().getBoolean("modules.wow");
		hubworld = this.getConfig().getString("hubworld");
		playerlistformat = this.getConfig().getString("playerlistformat");
	}
	
	private void reloadCommand(Plugin plugin){
		this.reloadConfig();
		initializeConfig();
		HandlerList.unregisterAll(plugin);
		loadListeners();
	}
	
	private void loadListeners(){
		if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
		    getLogger().info("Detected PermissionsEx! Enabling prefix welcome message!");
		}
		getServer().getPluginManager().registerEvents(this, this);
		if (pluginMode.equalsIgnoreCase("lobby")){
			getServer().getPluginManager().registerEvents(new HubActions(), this);
		} else if (pluginMode.equalsIgnoreCase("adv")){
			getServer().getPluginManager().registerEvents(new AdvMapActions(), this);
		} else if (pluginMode.equalsIgnoreCase("surv")){
			getServer().getPluginManager().registerEvents(new SurvMapActions(), this);
		}
		if (commandZeus){
			getServer().getPluginManager().registerEvents(new ZeusWrathListener(), this);
		}
	}
	
	private void showStats(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------CheesecakeMinigameLobby Stats-----------");
    	s.sendMessage(ChatColor.GOLD + "Plugin Mode: " + ChatColor.WHITE + pluginMode);
    	s.sendMessage(ChatColor.GOLD + "Lobby Message Format: ");
    	for (int i = 0; i < lobbymsg.size(); i++){
    		s.sendMessage(ChatColor.translateAlternateColorCodes('&', lobbymsg.get(i)));
    	}
    	s.sendMessage(ChatColor.GOLD + "Hub World: " + ChatColor.WHITE + hubworld);
    	s.sendMessage(ChatColor.GOLD + "Adventure Map Name: " + ChatColor.WHITE + advMapName);
    	s.sendMessage(ChatColor.GOLD + "Adventure Map Author: " + ChatColor.WHITE + advMapAuthor);
    	s.sendMessage(ChatColor.GOLD + "Adventure Message Format: ");
    	for (int i = 0; i < advmsg.size(); i++){
    		s.sendMessage(ChatColor.translateAlternateColorCodes('&', advmsg.get(i)));
    	}
    	s.sendMessage(ChatColor.GOLD + "Survival Message Format: ");
    	for (int i = 0; i < survmsg.size(); i++){
    		s.sendMessage(ChatColor.translateAlternateColorCodes('&', survmsg.get(i)));
    	}
    	s.sendMessage(ChatColor.GOLD + "Player List Format: " + ChatColor.WHITE + playerlistformat);
	}
	
	private void listModules(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------CheesecakeMinigameLobby Modules-----------");
		s.sendMessage(ChatColor.GOLD + "Fly: " + ChatColor.WHITE + commandFly);
		s.sendMessage(ChatColor.GOLD + "Modify Speed: " + ChatColor.WHITE + commandSpeed);
		s.sendMessage(ChatColor.GOLD + "Zeus Wrath: " + ChatColor.WHITE + commandZeus);
		s.sendMessage(ChatColor.GOLD + "Smite: " + ChatColor.WHITE + commandSmite);
		s.sendMessage(ChatColor.GOLD + "Fling player: " + ChatColor.WHITE + commandFling);
		s.sendMessage(ChatColor.GOLD + "Burn: " + ChatColor.WHITE + commandBurn);
		s.sendMessage(ChatColor.GOLD + "Wow: " + ChatColor.WHITE + commandWow);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> list = new ArrayList<String>();
		 // Now, it's just like any other command.
        // Check if the sender is a player.
        if (sender instanceof Player) {
        	// Check if the command is "something."
            if (cmd.getName().equalsIgnoreCase("cml")){
            	// If the player has not typed anything in
                if (args.length == 0) {
                	// Add a list of words that you'd like to show up
                    // when the player presses tab.
                	list.add("module");
                	list.add("reload");
                    list.add("stats");
                    list.add("version");
                    // Sort them alphabetically.
                    Collections.sort(list);
                    // return the list.
                    return list;
                // If player has typed one word in.
                // This > "/command hello " does not count as one
                // argument because of the space after the hello.
                } else if (args.length == 1) {
                	list.add("module");
                	list.add("reload");
                    list.add("stats");
                    list.add("version");
                    for (int i = 0; i < list.size(); i++){
                    	String s = list.get(i);
                        // Since the player has already typed something in,
                        // we ant to complete the word for them so we check startsWith().
                        if (!s.toLowerCase().startsWith(args[0].toLowerCase())){
                        	list.remove(i);
                        	i = 0;
                        }
                    }
                    Collections.sort(list);
                    return list;
                }
            }
        }
		return null;
	}
	
	public static void sendAdminMsg(String msg){
		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			if (p.hasPermission("bukkit.broadcast.admin")){
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
	}

}
