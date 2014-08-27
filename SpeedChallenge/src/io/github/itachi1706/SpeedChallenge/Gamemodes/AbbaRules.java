package io.github.itachi1706.SpeedChallenge.Gamemodes;

import java.util.ArrayList;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbbaRules {
	
	private static String challengeTitle = "ABBA Rules";
	
	public static String getGMTitle(){
		return challengeTitle;
	}
	
	public static void gmInfo(){
		// List all information about the gamemode
		// Lists at the start
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                     ABBA RULES"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                   MindCrack Style"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3This is the exact version of the MindCrack ABBA Rules game invented by &6VintageBeef"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bGet as much of the following items. The person with the highest points wins!"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are given a &6&lSILK TOUCH DIAMOND PICKAXE &bfor this challenge"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bShould you lose it, you can do &6/reequip&b to regain it"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aNOTE:&b This Challenge requires you to mine the ores with silk touch. The ore ingots will not count"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lItems and its respective scores"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cLapis Lazulli Ore - &a1 point"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cRedstone Ore - &a1 point"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cGold Ore - &a3 points"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cDiamond Ore - &a5 points"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cEmerald Ore - &a7 points"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cName Tag - &a10 points"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cGolden Apple - &a10 points"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cAny Horse Armor - &a10 points"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aTo check your current objectives, do &6/listobjectives"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lBETA TEST GAMEMODE - SCORE AND TIME GIVEN SUBJECTED TO CHANGE"));
	}
	
	public static void checkInventory(){
		if (Main.gamePlayerList.size() == 0){
			Bukkit.getLogger().info("INVALID - NO PLAYERS");
			return;
		}
		for (int i = 0; i < Main.gamePlayerList.size(); i++){
			Player p = Main.gamePlayerList.get(i);
			@SuppressWarnings("unused")
			int check = checkObjective(p);
		}
	}
	
	public static int checkObjective(Player p){
		//Basic Score
		int score = 0;
		int tmpAmount = 0;
		//Grab the amount of each item of the player
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.LAPIS_ORE));
		score += (tmpAmount);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.REDSTONE_ORE));
		score += (tmpAmount);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_ORE));
		score += (tmpAmount*3);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_ORE));
		score += (tmpAmount*5);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.EMERALD_ORE));
		score += (tmpAmount*7);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.NAME_TAG));
		score += (tmpAmount*10);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLDEN_APPLE));
		score += (tmpAmount*10);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_BARDING));
		score += (tmpAmount*10);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.IRON_BARDING));
		score += (tmpAmount*10);
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_BARDING));
		score += (tmpAmount*10);
		//End of it add score to player
		ScoreboardHelper.setScoreOfPlayer(p, score);
		return score;
	}
	
	public static int amountInInventory(Inventory inventory, ItemStack item){
		int amount = 0;
		ItemStack[] items = inventory.getContents();
		for (int i = 0; i < items.length; i++){
			if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()){
				amount += items[i].getAmount();
			}
		}
		return amount;
	}
	
	public static void initWeapon(){
		ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("This is a pickaxe that is given at the start");
		lore.add("to allow you to play ABBA Rules!");
		im.setDisplayName(ChatColor.AQUA + "ABBA Rules Pickaxe");
		im.setLore(lore);
		is.setItemMeta(im);
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
		is.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
		is.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
		for (int i = 0; i < Main.gamePlayerList.size(); i++){
			Player p = Main.gamePlayerList.get(i);
			p.getInventory().addItem(is);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You have been given a &aSilk Touch Diamond Pickaxe with Efficiency II and Unbreaking&6 for this challenge!"));
		}
	}
	
	public static void checkWeapon(Player p){
		ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta im = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("This is a pickaxe that is given at the start");
		lore.add("to allow you to play ABBA Rules!");
		im.setDisplayName(ChatColor.AQUA + "ABBA Rules Pickaxe");
		im.setLore(lore);
		is.setItemMeta(im);
		is.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
		is.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
		is.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
		if (p.getInventory().contains(is)){
			p.sendMessage(ChatColor.RED + "You already have the required equipment!");
			return;
		} else {
			p.getInventory().addItem(is);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You have been regiven a &aSilk Touch Diamond Pickaxe with Efficiency II and Unbreaking&6! Dont lose it again!"));
		}
		return;
	}
	
	//List all objectives for a player (for future command)
	public static void listObjectives(Player p){
		p.sendMessage(ChatColor.GOLD + "OBJECTIVES CHECK");
		int tmpAmount = 0;
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.LAPIS_ORE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Lapis Lazulli Ore - &a(1 point)&r: &b" + tmpAmount));
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.REDSTONE_ORE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Redstone Ore - &a(1 point)&r: &b" + tmpAmount));
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_ORE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Gold Ore - &a(3 points)&r: &b" + tmpAmount));
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_ORE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Diamond Ore - &a(5 points)&r: &b" + tmpAmount));
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.EMERALD_ORE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Emerald Ore - &a(7 points)&r: &b" + tmpAmount));
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.NAME_TAG));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Name Tag - &a(10 points)&r: &b" + tmpAmount));
		tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLDEN_APPLE));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Golden Apple - &a(10 points)&r: &b" + tmpAmount));
		int tmpAmount1 = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_BARDING));
		int tmpAmount2 = amountInInventory(p.getInventory(), new ItemStack(Material.IRON_BARDING));
		int tmpAmount3 = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_BARDING));
		tmpAmount = tmpAmount1 + tmpAmount2 + tmpAmount3;
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Horse Armor - &a(10 points)&r: &b" + tmpAmount));
		
	}

}
