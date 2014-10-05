package io.github.itachi1706.CheesecakeMinigameLobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
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

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class HubActions implements Listener{
	
	ArrayList<Player> playersInAdminMode = new ArrayList<Player>();
	
	@EventHandler
	private void checkObjects(PlayerJoinEvent e){
		Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld(Main.hubworld);
		if (w == null && p.hasPermission("cheesecakeminigamelobby.staff")){
			p.sendMessage(ChatColor.DARK_RED + "[SEVERE] An error occured (World not found). Unable to give hub items. Please contact a dev for help!");
		}
		if (p.getWorld().equals(w)){
			p.getInventory().clear();
			p.getInventory().setHeldItemSlot(0);
			giveItems(p);
		}
		welcomeMessage(p);
		showPlayersJoin(p);
		hidePlayersJoin(p);
	}
	
	private void giveItems(Player p){
		if (p.hasPermission("cheesecakeminigamelobby.admin")){
			if (playersInAdminMode.contains(p)){
				resetAdminMode(p);
			} else {
				giveAdminMode(p);
			}
		}
		giveCompass(p);
		giveHidePlayerItem(p);
		giveBook(p);
		giveClock(p);
		if (p.hasPermission("cheesecakeminigamelobby.abilities.fly")){
			if (p.getAllowFlight()){
				giveStopFlying(p);
			} else {
				giveFlying(p);
			}
		}
	}
	
	@EventHandler
	private void changeWorld(PlayerChangedWorldEvent e){
		Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld("world");
		if (!p.getWorld().equals(w)){
			showPlayersJoin(p);
		}
	}
	
	@EventHandler
	private void leaveServer(PlayerQuitEvent e){
		Player target = e.getPlayer();
		Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		Iterator<? extends Player> i = onlinePlayers.iterator();
		while(i.hasNext()){
			Player p = (Player) i.next();
			if (!p.canSee(target)){
				//hide players
				p.showPlayer(target);
			}
		}
	}
	
	@EventHandler
	private void togglePlayerActions(PlayerInteractEvent e){
		Player p = e.getPlayer();
		try {
		if (e.getItem().equals(hidePlayerItem())){
			//Hide Players
			hidePlayers(p);
			giveUnhidePlayerItem(p);
			e.setCancelled(true);
		} else if (e.getItem().equals(showPlayerItem())){
			//Show Players
			showPlayers(p);
			giveHidePlayerItem(p);
			e.setCancelled(true);
		}  else if (e.getItem().equals(startFlyItem())){
			//Start Flying
			Bukkit.getServer().dispatchCommand(p, "fly");
			giveStopFlying(p);
			e.setCancelled(true);
		}  else if (e.getItem().equals(stopFlyItem())){
			//Stop Flying
			Bukkit.getServer().dispatchCommand(p, "fly");
			giveFlying(p);
			e.setCancelled(true);
		} else if (e.getItem().equals(toggleAdminModeItem())){
			//Enters Admin Mode
			if (playersInAdminMode.contains(p)){
				p.sendMessage(ChatColor.RED + "You are already in Administrative Mode");
			} else {
				playersInAdminMode.add(p);
				resetAdminMode(p);
				p.sendMessage(ChatColor.GREEN + "Entered Administrative Mode. You can now moves items around in your inventory!");
				e.setCancelled(true);
			}
		} else if (e.getItem().equals(resetInventoryItem())){
			//Exits Admin Mode
			playersInAdminMode.remove(p);
			p.sendMessage(ChatColor.GREEN + "Exited Administrative Mode. Your inventory has now been reset back to lobby default.");
			giveItems(p);
			e.setCancelled(true);
		}
		} catch (NullPointerException ex) {
			Bukkit.getServer().getLogger().warning(p.getName() + " does not have a lobby action item!");
		}
	}
	
	@EventHandler
	private void onItemDrop(PlayerDropItemEvent e){
		World w = Bukkit.getServer().getWorld("world");
		if (e.getPlayer().getWorld().equals(w)){
			if (checkIfItemIsLobbyItem(e.getItemDrop().getItemStack())){
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	private void InventoryMove(InventoryClickEvent e){
		World w = Bukkit.getServer().getWorld("world");
		if (e.getWhoClicked().getWorld().equals(w)){
			if (checkIfItemIsLobbyItem(e.getCurrentItem())){
				if (playersInAdminMode.contains(e.getWhoClicked())){
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
	
	
	
	private void hidePlayers(Player p){
		Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		Iterator<? extends Player> i = onlinePlayers.iterator();
		while(i.hasNext()){
			Player target = (Player) i.next();
			if (!target.hasPermission("cheesecakeminigamelobby.exempt")){
				//hide players
				p.hidePlayer(target);
			}
		}
		p.sendMessage(ChatColor.GOLD + "Hide Player Status: " + ChatColor.RED + "Hidden");
	}
	
	private void hidePlayersJoin(Player p){
		Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		if (!p.hasPermission("cheesecakeminigamelobby.exempt")) {
			Iterator<? extends Player> i = onlinePlayers.iterator();
			while(i.hasNext()){
				Player target = (Player) i.next();
				if (target.getInventory().contains(showPlayerItem())){
					//hide players
					target.hidePlayer(p);
				}
			}
		}
	}
	
	private void showPlayers(Player p){
		Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		Iterator<? extends Player> i = onlinePlayers.iterator();
		while(i.hasNext()){
			Player target = (Player) i.next();
			if (!p.canSee(target)){
				//hide players
				p.showPlayer(target);
			}
		}
		p.sendMessage(ChatColor.GOLD + "Hide Player Status: " + ChatColor.GREEN + "Shown");
	}
	
	private void showPlayersJoin(Player p){
		Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		Iterator<? extends Player> i = onlinePlayers.iterator();
		while(i.hasNext()){
			Player target = (Player) i.next();
			if (!p.canSee(target)){
				//hide players
				p.showPlayer(target);
			}
		}
	}
	
	private void welcomeMessage(Player p){
		String prefix = "";
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			//Use name
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser target = pex.getUser(p);
			if (target == null){
				Bukkit.getLogger().info("Player not found in PEX File, reverting down to fallback...");
			} else {
				String result = target.getPrefix();
				prefix = ChatColor.translateAlternateColorCodes('&', result);
			}
		}
		for (int i = 0; i < Main.lobbymsg.size(); i++){
			String welMsg = Main.lobbymsg.get(i);
			welMsg = welMsg.replaceAll("%prefix%", prefix);
			welMsg = welMsg.replaceAll("%playername%", p.getDisplayName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', welMsg));
		}
	}
	
	private void giveUnhidePlayerItem(Player p){
		p.getInventory().clear(1);
		p.getInventory().setItem(1, showPlayerItem());
	}
	
	private void giveBook(Player p){
		p.getInventory().clear(8);
		p.getInventory().setItem(8, infoBookItem());
	}
	
	private void giveCompass(Player p){
		p.getInventory().clear(0);
		p.getInventory().setItem(0, navigateServerItem());
	}
	
	private void giveClock(Player p){
		p.getInventory().clear(2);
		p.getInventory().setItem(2, navigateLobbyItem());
	}
	
	private void giveHidePlayerItem(Player p){
		p.getInventory().clear(1);
		p.getInventory().setItem(1, hidePlayerItem());
	}
	
	private void giveStopFlying(Player p){
		p.getInventory().clear(3);
		p.getInventory().setItem(3, stopFlyItem());
	}
	
	private void giveFlying(Player p){
		p.getInventory().clear(3);
		p.getInventory().setItem(3, startFlyItem());
	}
	
	private void giveAdminMode(Player p){
		p.getInventory().clear();
		p.getInventory().setItem(7, toggleAdminModeItem());
	}
	
	private void resetAdminMode(Player p){
		p.getInventory().clear(7);
		p.getInventory().setItem(7, resetInventoryItem());
	}
	
	private ItemStack infoBookItem(){
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
		return book;
	}
	
	private ItemStack hidePlayerItem(){
		ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Right-click to hide players!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Hide Player Status: " + ChatColor.GREEN + "Shown");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack showPlayerItem(){
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Right-click to show players!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Hide Player Status: " + ChatColor.RED + "Hidden");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack navigateLobbyItem(){
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta im = item.getItemMeta();
		String lore1 = "Right-click to teleport to other areas in this server!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Teleport to other areas in this server!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack startFlyItem(){
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Right-click to start flying!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Fly Mode: " + ChatColor.RED + "Disabled");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack stopFlyItem(){
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Right-click to stop flying!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Fly Mode: " + ChatColor.GREEN + "Enabled");
		im.setLore(lore);
		im.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack navigateServerItem(){
		ItemStack item = new ItemStack(Material.COMPASS);
		ItemMeta im = item.getItemMeta();
		String lore1 = "Right-click to travel to other servers!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GREEN + "Quick Travel!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack toggleAdminModeItem(){
		ItemStack item = new ItemStack(Material.FIRE);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Right-click to enter admin mode!";
		String lore2 = ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "This allows you to move lobby items around";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		lore.add(lore2);
		im.setDisplayName(ChatColor.GOLD + "Admin Mode: " + ChatColor.RED + "Disabled");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	private ItemStack resetInventoryItem(){
		ItemStack item = new ItemStack(Material.FIRE);
		ItemMeta im = item.getItemMeta();
		String lore1 = ChatColor.DARK_RED + "" + ChatColor.ITALIC + "Right-click to exit admin mode!";
		String lore2 = ChatColor.DARK_RED + "" + ChatColor.ITALIC + "This resets your inventory back to lobby default";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		lore.add(lore2);
		im.setDisplayName(ChatColor.GOLD + "Admin Mode: " + ChatColor.GREEN + "Enabled");
		im.setLore(lore);
		im.addEnchant(Enchantment.SILK_TOUCH, 255, true);
		item.setItemMeta(im);
		return item;
	}
	
	private boolean checkIfItemIsLobbyItem(ItemStack i){
		if (i.equals(navigateServerItem())){
			return true;
		}
		if (i.equals(stopFlyItem())){
			return true;
		}
		if (i.equals(startFlyItem())){
			return true;
		}
		if (i.equals(navigateLobbyItem())){
			return true;
		}
		if (i.equals(showPlayerItem())){
			return true;
		}
		if (i.equals(hidePlayerItem())){
			return true;
		}
		if (i.equals(infoBookItem())){
			return true;
		}
		if (i.equals(toggleAdminModeItem())){
			return true;
		}
		return false;
	}

}
