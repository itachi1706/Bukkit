package io.github.itachi1706.CheesecakeMinigameLobby;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin implements Listener{
	
	static double pluginVersion = 1.1;
	static String pluginPrefix = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "Cheesecake Minigame Lobby" + ChatColor.DARK_RED + "] " + ChatColor.WHITE;
	@Override
	public void onEnable(){
		//Logic when plugin gets enabled
		getLogger().info("Enabling Plugin...");
		getLogger().info("Enabling Plugin listeners...");
		if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
		    getLogger().info("Detected PermissionsEx! Enabling prefix welcome message!");
		    
		}
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable(){
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Main Command
    	if(cmd.getName().equalsIgnoreCase("cheesecakeminigamelobby")){
    		if (args.length < 1 || args.length > 1){
    			displayHelp(sender);
				return true;
    		}
    		if (args[0].equalsIgnoreCase("version")){
    			//Reloads Plugin
    			sender.sendMessage(pluginPrefix + ChatColor.GOLD + "======================================");
				sender.sendMessage(pluginPrefix + ChatColor.BLUE + "Cheesecake Minigame Lobby Plugin");
				sender.sendMessage(pluginPrefix + ChatColor.GOLD + "======================================");
				sender.sendMessage(pluginPrefix + "Version: " + ChatColor.AQUA + pluginVersion);
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
    	s.sendMessage(ChatColor.GOLD + "/cheesecakeminigamelobby version: " + ChatColor.WHITE +  "Check current plugin version");
	}
	
	@EventHandler
	public void checkObjects(PlayerJoinEvent e){
		Player p = e.getPlayer();
		p.getInventory().clear();
		p.getInventory().setHeldItemSlot(0);
		giveCompass(p);
		giveClock(p);
		giveBook(p);
		welcomeMessage(p);
		showPlayersJoin(p);
		hidePlayersJoin(p);
	}
	
	@EventHandler
	public void changeWorld(PlayerChangedWorldEvent e){
		Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld("world");
		if (!p.getWorld().equals(w)){
			showPlayersJoin(p);
		}
	}
	
	@EventHandler
	public void leaveServer(PlayerQuitEvent e){
		Player target = e.getPlayer();
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.length; i++){
			Player p = onlinePlayers[i];
			if (!p.canSee(target)){
				//hide players
				p.showPlayer(target);
			}
		}
	}
	
	@EventHandler
	public void toggleClock(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if (e.getItem().getType().equals(Material.EYE_OF_ENDER)){
			//Hide Players
			hidePlayers(p);
			giveHiddenClock(p);
			e.setCancelled(true);
		} else if (e.getItem().getType().equals(Material.ENDER_PEARL)){
			//Show Players
			showPlayers(p);
			giveClock(p);
			e.setCancelled(true);
		}
	}
	
	public void giveBook(Player p){
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm = (BookMeta) book.getItemMeta();
		String lol = ChatColor.DARK_GREEN + "===================\n" + ChatColor.DARK_GREEN + "Welcome to the" + ChatColor.GOLD + " Cheesecake Network!\n" + ChatColor.DARK_GREEN + "===================\n" +
		ChatColor.DARK_RED + "\nUse the compass to go to other servers!\nUse the eye of ender to hide players!\n\nObey all " + ChatColor.DARK_RED + "server rules" + ChatColor.DARK_RED + ", accessible with " + ChatColor.GOLD +
		"/rules\n" + ChatColor.DARK_GREEN + "HAVE FUN ON THE SERVER!!!";
		String lore = "The server's Welcome Message and Info book!";
		ArrayList<String> lored = new ArrayList<String>();
		lored.add(lore);
		bm.addPage(lol);
		bm.setAuthor(ChatColor.GOLD + "Cheesecake Network");
		bm.setDisplayName(ChatColor.GREEN + "Welcome!");
		bm.setTitle(ChatColor.GREEN + "Welcome!");
		bm.setLore(lored);
		book.setItemMeta(bm);
		p.getInventory().clear(8);
		p.getInventory().setItem(8, book);
	}
	
	public void giveCompass(Player p){
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta im = item.getItemMeta();
		String lore1 = "Right-click to travel to other servers!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GREEN + "Quick Travel!");
		im.setLore(lore);
		item.setItemMeta(im);
		p.getInventory().clear(0);
		p.getInventory().setItem(0, item);
	}
	
	public void giveClock(Player p){
		ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Right-click to hide players!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Hide Player Status: " + ChatColor.GREEN + "Shown");
		im.setLore(lore);
		item.setItemMeta(im);
		p.getInventory().clear(1);
		p.getInventory().setItem(1, item);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		World w = Bukkit.getServer().getWorld("world");
		if (e.getPlayer().getWorld().equals(w)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void InventoryMove(InventoryClickEvent e){
		World w = Bukkit.getServer().getWorld("world");
		if (e.getWhoClicked().getWorld().equals(w)){
			int clicked = e.getSlot();
			if (clicked == 1 || clicked == 0 || clicked == 8){
				e.setCancelled(true);
			}
			int click = e.getHotbarButton();
			if (click == 1 || click == 0 || click == 8){
				e.setCancelled(true);
			}
		}
	}
	
	public void giveHiddenClock(Player p){
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Right-click to show players!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Hide Player Status: " + ChatColor.RED + "Hidden");
		im.setLore(lore);
		item.setItemMeta(im);
		p.getInventory().clear(1);
		p.getInventory().setItem(1, item);
	}
	
	public void hidePlayers(Player p){
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.length; i++){
			Player target = onlinePlayers[i];
			if (!target.hasPermission("cheesecakeminigamelobby.exempt")){
				//hide players
				p.hidePlayer(target);
			}
		}
		p.sendMessage(ChatColor.GOLD + "Hide Player Status: " + ChatColor.RED + "Hidden");
	}
	
	public void hidePlayersJoin(Player p){
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		if (!p.hasPermission("cheesecakeminigamelobby.exempt")) {
			for (int i = 0; i < onlinePlayers.length; i++){
				Player target = onlinePlayers[i];
				if (target.getInventory().contains(Material.ENDER_PEARL)){
					//hide players
					target.hidePlayer(p);
				}
			}
		}
	}
	
	public void showPlayers(Player p){
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.length; i++){
			Player target = onlinePlayers[i];
			if (!p.canSee(target)){
				//hide players
				p.showPlayer(target);
			}
		}
		p.sendMessage(ChatColor.GOLD + "Hide Player Status: " + ChatColor.GREEN + "Shown");
	}
	
	public void showPlayersJoin(Player p){
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.length; i++){
			Player target = onlinePlayers[i];
			if (!p.canSee(target)){
				//hide players
				p.showPlayer(target);
			}
		}
	}
	
	public void welcomeMessage(Player p){
		String prefix = "";
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			//Use name
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser target = pex.getUser(p);
			if (target == null){
				getLogger().info("Player not found in PEX File, reverting down to fallback...");
			} else {
				String result = target.getPrefix();
				prefix = ChatColor.translateAlternateColorCodes('&', result);
			}
		}
		String[] welMsg = {ChatColor.GOLD + "==================================================" ,
				ChatColor.DARK_GREEN + "Welcome " + prefix + " " + p.getDisplayName() + ChatColor.DARK_GREEN + " to the " + ChatColor.GOLD + "Cheesecake Network!",
				ChatColor.DARK_GREEN + "Use the compass to go to other servers!",
				ChatColor.DARK_GREEN + "The book in your inventory will have more information!", 
				ChatColor.DARK_GREEN + "To hide players, use the Eye Of Ender!", 
				ChatColor.GOLD + "==================================================" ,};
		p.sendMessage(welMsg);
		}

}
