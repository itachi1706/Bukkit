/*
 * Sample class file for gamemodes (Item Collection)
 */

package io.github.itachi1706.SpeedChallenge.Gamemodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

public class Sample {
	
	private static ArrayList<Integer> checkCompleted = new ArrayList<Integer>();
	private static int maxPts = 2;	//Max points possible to get per player
	private static String challengeTitle = "SAMPLE GAME";
	
	public static String getGMTitle(){
		return challengeTitle;
	}

	public static void gmInfo(){
		// List all information about the gamemode
		// Lists at the start
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                  SAMPLE GAME"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bGet 1 of each of the following items to complete this challenge!"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lItems"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cOrange Wool Block"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cDirt Block"));
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
		//If item has durability, do this
		ItemStack i = new ItemStack(Material.WOOL);
		i.setDurability((short) 1);
		//Check if user has orange wool (ID: Material.WOOL:1 / 35:1)
		if (p.getInventory().containsAtLeast(i, 1)){
			//Adds a Score
			score += 1;
		}
		//Check if user has dirt (ID: Material.DIRT / 3)
		if (p.getInventory().containsAtLeast(new ItemStack(Material.DIRT), 1)){
			score += 1;
		}
		//End of it add score to player
		ScoreboardHelper.setScoreOfPlayer(p, score);
		return score;
	}
	
	//List all objectives for a player (for future command)
	public static void listObjectives(Player p){
		p.sendMessage(ChatColor.GOLD + "OBJECTIVES CHECK");
		//If item has durability, do this
			ItemStack i = new ItemStack(Material.WOOL);
			i.setDurability((short) 1);
		//Check if user has orange wool (ID: Material.WOOL:1 / 35:1)
		if (p.getInventory().containsAtLeast(i,1)){
			//Adds a Score
			p.sendMessage("Orange Wool - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Orange Wool - " + ChatColor.RED + "Unobtained");
		}
		//Check if user has dirt (ID: Material.DIRT / 3)
		if (p.getInventory().containsAtLeast(new ItemStack(Material.DIRT),1)){
			p.sendMessage("Dirt - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Dirt - " + ChatColor.RED + "Unobtained");
		}
	}
}
