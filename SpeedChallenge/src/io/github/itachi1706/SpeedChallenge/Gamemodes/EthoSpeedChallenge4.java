package io.github.itachi1706.SpeedChallenge.Gamemodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

public class EthoSpeedChallenge4 extends AbstractGamemode {
	
	private static ArrayList<Integer> checkCompleted = new ArrayList<Integer>();
	private static int maxPts = 5;	//Max points possible to get per player
	private static String challengeTitle = "The Colour Wheel (Etho's Speed Challenge)";
	
	public static String getGMTitle(){
		return challengeTitle;
	}

	public static void gmInfo(){
		// List all information about the gamemode
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l               Etho's Speed Challenge"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                   The Colour Wheel"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3Based on the Speed Challenge recorded by &6Etho"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bGet 1 of each colour of wool to complete this challenge!"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aTo check your current objectives, do &6/listobjectives"));
	}
	
	public static void checkInventory(){
		checkCompleted.clear();
		if (Main.gamePlayerList.size() == 0){
			Bukkit.getLogger().info("INVALID - NO PLAYERS");
			return;
		}
		for (int i = 0; i < Main.gamePlayerList.size(); i++){
			Player p = Main.gamePlayerList.get(i);
			int check = checkObjective(p);
			checkCompleted.add(check);
		}
		int total = 0;
		for (int i = 0; i < checkCompleted.size(); i++){
			total += checkCompleted.get(i);
		}
		if (total == (maxPts*Main.gamePlayerList.size())){
			//Completed
			Bukkit.getServer().broadcastMessage("All players have completed the objective! Game ends now!");
			Main.countdown = 0;
		}
	}
	
	public static int checkObjective(Player p){
		//Basic Score
		int score = 0;
		Inventory i = p.getInventory();
		//Check for the 16 wool colours
		if (checkWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkOrangeWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkMagentaWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkLightBlueWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkYellowWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkLimeWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkPinkWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkGrayWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkLightGrayWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkCyanWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkPurpleWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkBlueWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkBrownWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkGreenWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkRedWool(i)){
			//Adds a Score
			score += 1;
		}
		if (checkBlackWool(i)){
			//Adds a Score
			score += 1;
		}
		//End of it add score to player
		ScoreboardHelper.setScoreOfPlayer(p, score);
		return score;
	}
	
	//List all objectives for a player (for future command)
	public static void listObjectives(Player p){
		ArrayList<String> check = new ArrayList<String>();
		check.add(ChatColor.GOLD + "Objectives Check");
		check.add("Legend: Green = " + ChatColor.GREEN + "Obtained" + ChatColor.RESET + ", Red = " + ChatColor.RED + "Unobtained");
		check.add("");
		Inventory i = p.getInventory();
		//Check for the 16 wool colours
		if (checkWool(i)){
			check.add(ChatColor.GREEN + "White Wool");
		} else {
			check.add(ChatColor.RED + "White Wool");
		}
		if (checkOrangeWool(i)){
			check.add(ChatColor.GREEN + "Orange Wool");
		} else {
			check.add(ChatColor.RED + "Orange Wool");
		}
		if (checkMagentaWool(i)){
			check.add(ChatColor.GREEN + "Magenta Wool");
		} else {
			check.add(ChatColor.RED + "Magenta Wool");
		}
		if (checkLightBlueWool(i)){
			check.add(ChatColor.GREEN + "Light Blue Wool");
		} else {
			check.add(ChatColor.RED + "Light Blue Wool");
		}
		if (checkYellowWool(i)){
			check.add(ChatColor.GREEN + "Yellow Wool");
		} else {
			check.add(ChatColor.RED + "Yellow Wool");
		}
		if (checkLimeWool(i)){
			check.add(ChatColor.GREEN + "Lime Wool");
		} else {
			check.add(ChatColor.RED + "Lime Wool");
		}
		if (checkPinkWool(i)){
			check.add(ChatColor.GREEN + "Pink Wool");
		} else {
			check.add(ChatColor.RED + "Pink Wool");
		}
		if (checkGrayWool(i)){
			check.add(ChatColor.GREEN + "Gray Wool");
		} else {
			check.add(ChatColor.RED + "Gray Wool");
		}
		if (checkLightGrayWool(i)){
			check.add(ChatColor.GREEN + "Light Gray Wool");
		} else {
			check.add(ChatColor.RED + "Light Gray Wool");
		}
		if (checkCyanWool(i)){
			check.add(ChatColor.GREEN + "Cyan Wool");
		} else {
			check.add(ChatColor.RED + "Cyan Wool");
		}
		if (checkPurpleWool(i)){
			check.add(ChatColor.GREEN + "Purple Wool");
		} else {
			check.add(ChatColor.RED + "Purple Wool");
		}
		if (checkBlueWool(i)){
			check.add(ChatColor.GREEN + "Blue Wool");
		} else {
			check.add(ChatColor.RED + "Blue Wool");
		}
		if (checkBrownWool(i)){
			check.add(ChatColor.GREEN + "Brown Wool");
		} else {
			check.add(ChatColor.RED + "Brown Wool");
		}
		if (checkGreenWool(i)){
			check.add(ChatColor.GREEN + "Green Wool");
		} else {
			check.add(ChatColor.RED + "Green Wool");
		}
		if (checkRedWool(i)){
			check.add(ChatColor.GREEN + "Red Wool");
		} else {
			check.add(ChatColor.RED + "Red Wool");
		}
		if (checkBlackWool(i)){
			check.add(ChatColor.GREEN + "Black Wool");
		} else {
			check.add(ChatColor.RED + "Black Wool");
		}
		p.sendMessage(ChatColor.GOLD + "==================================================");
		p.sendMessage(check.get(0));
		p.sendMessage(check.get(1));
		p.sendMessage(check.get(2));
		for (int c = 3; c < check.size(); c++)
		if (c % 3 == 0){
			if ((c + 1) >= check.size()){
				//Only display 1
				p.sendMessage(check.get(c));
			} else if ((c+2) >= check.size()){
				//Only displays 2
				p.sendMessage(check.get(c) + ChatColor.RESET + "  -  " + check.get(c+1));
			} else {
				//Displays 3
				p.sendMessage(check.get(c) + ChatColor.RESET + "  -  " + check.get(c+1) + ChatColor.RESET + "  -  " + check.get(c+2));
			}
				
		}
		p.sendMessage(ChatColor.GOLD + "==================================================");
	}
	
	private static boolean checkWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 0);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkOrangeWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 1);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkMagentaWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 2);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkLightBlueWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 3);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkYellowWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 4);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkLimeWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 5);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkPinkWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 6);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkGrayWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 7);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkLightGrayWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 8);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkCyanWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 9);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkPurpleWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 10);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkBlueWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 11);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkBrownWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 12);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkGreenWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 13);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkRedWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 14);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}
	
	private static boolean checkBlackWool(Inventory inv){
		ItemStack item = new ItemStack(Material.WOOL);
		item.setDurability((short) 15);
		if (inv.containsAtLeast(item, 1)){
			return true;
		}
		return false;
	}

}
