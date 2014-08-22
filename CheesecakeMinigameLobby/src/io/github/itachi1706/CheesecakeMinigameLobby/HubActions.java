package io.github.itachi1706.CheesecakeMinigameLobby;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
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
			giveCompass(p);
			giveHidePlayerItem(p);
			giveBook(p);
			giveClock(p);
		}
		welcomeMessage(p);
		showPlayersJoin(p);
		hidePlayersJoin(p);
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
	private void toggleHidePlayer(PlayerInteractEvent e){
		Player p = e.getPlayer();
		try {
		if (e.getItem().getType().equals(Material.EYE_OF_ENDER)){
			//Hide Players
			hidePlayers(p);
			giveUnhidePlayerItem(p);
			e.setCancelled(true);
		} else if (e.getItem().getType().equals(Material.ENDER_PEARL)){
			//Show Players
			showPlayers(p);
			giveHidePlayerItem(p);
			e.setCancelled(true);
		} 
		} catch (NullPointerException ex) {
			Bukkit.getServer().getLogger().warning(p.getName() + " does not have a hide player item!");
		}
	}
	
	private void giveBook(Player p){
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
	
	private void giveCompass(Player p){
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
	
	private void giveClock(Player p){
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta im = item.getItemMeta();
		String lore1 = "Right-click to teleport to other areas in this server!";
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(lore1);
		im.setDisplayName(ChatColor.GOLD + "Teleport to other areas in this server!");
		im.setLore(lore);
		item.setItemMeta(im);
		p.getInventory().clear(2);
		p.getInventory().setItem(2, item);
	}
	
	private void giveHidePlayerItem(Player p){
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
	private void onItemDrop(PlayerDropItemEvent e){
		World w = Bukkit.getServer().getWorld("world");
		if (e.getPlayer().getWorld().equals(w)){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	private void InventoryMove(InventoryClickEvent e){
		World w = Bukkit.getServer().getWorld("world");
		if (e.getWhoClicked().getWorld().equals(w)){
			int clicked = e.getSlot();
			if (clicked == 1 || clicked == 0 || clicked == 8 || clicked == 2){
				e.setCancelled(true);
			}
			int click = e.getHotbarButton();
			if (click == 1 || click == 0 || click == 8 || clicked == 2){
				e.setCancelled(true);
			}
		}
	}
	
	private void giveUnhidePlayerItem(Player p){
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
				if (target.getInventory().contains(Material.ENDER_PEARL)){
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

}
