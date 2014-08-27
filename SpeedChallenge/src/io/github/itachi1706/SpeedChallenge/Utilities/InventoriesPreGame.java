package io.github.itachi1706.SpeedChallenge.Utilities;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.PreGameRunnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoriesPreGame {
	
	// The first parameter, is the inventory owner. I make it null to let everyone use it.
	//The second parameter, is the slots in a inventory. Must be a multiple of 9. Can be up to 54.
	//The third parameter, is the inventory name. This will accept chat colors.
	public static Inventory gamemodeInventory = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Choose a Challenge!");
	public static Inventory hardcoreInventory = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Choose Hardcore Mode Option!");
	public static Inventory pvpInventory = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Choose PVP Option!");
	
	public static ItemStack gmSelector = null;
	public static ItemStack pvpSelector = null;
	public static ItemStack hcSelector = null;
	
	//Insert items to inventories
	static {
		//Fixed Lores
		List<String> hardEnLore = new ArrayList<String>();
		List<String> hardDisLore = new ArrayList<String>();
		List<String> pvpEnLore = new ArrayList<String>();
		List<String> pvpDisLore = new ArrayList<String>();
		List<String> gmLore = new ArrayList<String>();
		gmLore.add("Click here to select");
		gmLore.add("this Challenge!");
		hardEnLore.add("Click here to");
		hardEnLore.add("Enable Hardcore Mode!");
		hardDisLore.add("Click here to");
		hardDisLore.add("Disable Hardcore Mode!");
		pvpEnLore.add("Click here to");
		pvpEnLore.add("Enable PVP!");
		pvpDisLore.add("Click here to");
		pvpDisLore.add("Disable PVP!");
		
		//Gamemode Options
		gamemodeInventory.setItem(0, makeItem(Material.DIRT, ChatColor.GOLD + "Sample Game", gmLore));
		gamemodeInventory.setItem(1, makeItem(Material.CAKE, ChatColor.GOLD + "5-Course Meal", gmLore));
		gamemodeInventory.setItem(2, makeItem(Material.IRON_PICKAXE, ChatColor.GOLD + "Modded ABBA Rules", gmLore));
		gamemodeInventory.setItem(3, makeItem(Material.DIAMOND_PICKAXE, ChatColor.GOLD + "ABBA Rules", gmLore));
		gamemodeInventory.setItem(4, makeItem(Material.GOLD_PICKAXE, ChatColor.GOLD + "Abba Rules (GOD EDITION)", gmLore));
		gamemodeInventory.setItem(8, makeItem(Material.DEAD_BUSH, ChatColor.GOLD + "Randomize Challenges", gmLore));
		//The first parameter, is the slot that is assigned to. Starts counting at 0
		
		//Hardcore Mode Option
		hardcoreInventory.setItem(3, makeItem(Material.WOOL, 1, 5, ChatColor.GREEN + "Enable Hardcore", hardEnLore));
		hardcoreInventory.setItem(5, makeItem(Material.WOOL, 1, 14, ChatColor.RED + "Disable Hardcore", hardDisLore));
		
		//PVP Option
		pvpInventory.setItem(3, makeItem(Material.WOOL, 1, 5, ChatColor.GREEN + "Enable PVP", pvpEnLore));
		pvpInventory.setItem(5, makeItem(Material.WOOL, 1, 14, ChatColor.RED + "Disable PVP", pvpDisLore));
		}
	
	public static ItemStack makeItem(Material material, String displayName, List<String> lore){
		ItemStack i = new ItemStack(material);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack makeItem(Material material, int amount, String displayName, List<String> lore){
		ItemStack i = new ItemStack(material, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack makeItem(Material material, int amount, int datavalue, String displayName, List<String> lore){
		ItemStack i = new ItemStack(material, amount, (short) datavalue);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(displayName);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	
	public static void giveItemToPlayer(Player p){
		p.getInventory().setItem(0, gmSelector);
		p.getInventory().setItem(1, pvpSelector);
		p.getInventory().setItem(2, hcSelector);
	}
	
	public static void removeItemFromPlayer(Player p){
		p.getInventory().remove(gmSelector);
		p.getInventory().remove(hcSelector);
		p.getInventory().remove(pvpSelector);
	}
	
	public static void selectGamemode(Player p, int clickslot){
		if (p.hasPermission("sc.override") || Main.gamePlayerList.get(0).getName().equals(p.getName())){
			switch (clickslot){
			case 0: 	Main.gamemode = 1;
						break;
			case 1: 	Main.gamemode = 2;
						break;
			case 2: 	Main.gamemode = 3;
						break;
			case 3: 	Main.gamemode = 4;
						break;
			case 4: 	Main.gamemode = 5;
						break;
			case 8: 	Main.gamemode = 0;
						p.sendMessage("Selected randomize option! Challenges will be randomized!");
						break;
			default: 	break;
			}
			if (Main.gamemode > 0 && Main.gamemode <= Main.numberOfChallenges){
				String huh = PreGameRunnable.getTitle();
				p.sendMessage(ChatColor.BLUE + "Challenge "+ Main.gamemode + " (" + huh + ") selected!");
				String pvp = "&b[SpeedChallenge] &4&lChallenge " + Main.gamemode + " (" + huh + ") &a&lselected!";
				Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
				return;
			}
		} else {
			p.sendMessage(ChatColor.DARK_RED + "You do not have the ability to modify the config of this game!");
		}
	}
	
	public static void selectHCMode(Player p, int clickslot){
		if (p.hasPermission("sc.override") || Main.gamePlayerList.get(0).getName().equals(p.getName())){
			switch (clickslot){
			case 3: 	Main.respawn = 2;
						p.sendMessage(ChatColor.BLUE + "Enabled Hardcore Mode!");
						String respawn = "&b[SpeedChallenge] &4&lHardcore Mode will be &a&lenabled!";
						Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
						break;
			case 5: 	Main.respawn = 1;
						p.sendMessage(ChatColor.BLUE + "Disabled Hardcore Mode!");
						String respawn1 = "&b[SpeedChallenge] &4&lHardcore Mode will be &c&ldisabled!";
						Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn1));
						break;
			default: 	break;
			}
		} else {
			p.sendMessage(ChatColor.DARK_RED + "You do not have the ability to modify the config of this game!");
		}
	}
	
	public static void selectPVPMode(Player p, int clickslot){
		if (p.hasPermission("sc.override") || Main.gamePlayerList.get(0).getName().equals(p.getName())){
			switch (clickslot){
			case 3: 	Main.pvp = 1;
						p.sendMessage(ChatColor.BLUE + "Enabled PVP!");
						String pvp = "&b[SpeedChallenge] &4&lPVP will be &a&lenabled!";
						Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
						break;
			case 5: 	Main.pvp = 2;
						p.sendMessage(ChatColor.BLUE + "Disabled PVP!");
						String pvp1 = "&b[SpeedChallenge] &4&lPVP will be &c&ldisabled!";
						Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp1));
						break;
			default: 	break;
			}
		} else {
			p.sendMessage(ChatColor.DARK_RED + "You do not have the ability to modify the config of this game!");
		}
	}
	
	public static void initSelectionStuff(){
		ItemStack gmtmp = new ItemStack(Material.GOLD_INGOT);
		ItemStack pvptmp = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack hctmp = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
		ItemMeta gmImtmp = gmtmp.getItemMeta();
		ItemMeta pvpImtmp = pvptmp.getItemMeta();
		ItemMeta hcImtmp = hctmp.getItemMeta();
		gmImtmp.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSelect A Challenge!"));
		pvpImtmp.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lSelect A PVP Option!"));
		hcImtmp.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lSelect Hardcore Mode Option!"));
		gmtmp.setItemMeta(gmImtmp);
		pvptmp.setItemMeta(pvpImtmp);
		hctmp.setItemMeta(hcImtmp);
		gmSelector = gmtmp;
		hcSelector = hctmp;
		pvpSelector = pvptmp;
	}
	

}
