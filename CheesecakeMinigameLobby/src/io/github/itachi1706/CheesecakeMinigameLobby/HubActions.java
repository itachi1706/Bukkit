package io.github.itachi1706.CheesecakeMinigameLobby;

import java.util.ArrayList;

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
	private void toggleClock(PlayerInteractEvent e){
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
			if (clicked == 1 || clicked == 0 || clicked == 8){
				e.setCancelled(true);
			}
			int click = e.getHotbarButton();
			if (click == 1 || click == 0 || click == 8){
				e.setCancelled(true);
			}
		}
	}
	
	private void giveHiddenClock(Player p){
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
	
	private void hidePlayersJoin(Player p){
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
	
	private void showPlayers(Player p){
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
	
	private void showPlayersJoin(Player p){
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < onlinePlayers.length; i++){
			Player target = onlinePlayers[i];
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
		/*String[] welMsg = {ChatColor.GOLD + "==================================================" ,
				ChatColor.DARK_GREEN + "Welcome " + prefix + " " + p.getDisplayName() + ChatColor.DARK_GREEN + " to the " + ChatColor.GOLD + "Cheesecake Network!",
				ChatColor.DARK_GREEN + "Use the compass to go to other servers!",
				ChatColor.DARK_GREEN + "The book in your inventory will have more information!", 
				ChatColor.DARK_GREEN + "To hide players, use the Eye Of Ender!", 
				ChatColor.GOLD + "==================================================" ,};*/
		for (int i = 0; i < Main.lobbymsg.size(); i++){
			String welMsg = Main.lobbymsg.get(i);
			welMsg = welMsg.replaceAll("%prefix%", prefix);
			welMsg = welMsg.replaceAll("%playername%", p.getDisplayName());
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', welMsg));
		}
		}

}
