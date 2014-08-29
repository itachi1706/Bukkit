package io.github.itachi1706.SpeedChallenge.Gamemodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

public class EthoSpeedChallenge3 extends AbstractGamemode {
	
	private static ArrayList<Integer> checkCompleted = new ArrayList<Integer>();
	private static int maxPts = 5;	//Max points possible to get per player
	private static String challengeTitle = "5-Course Meal (Etho's Speed Challenge)";
	
	public static String getGMTitle(){
		return challengeTitle;
	}

	public static void gmInfo(){
		// List all information about the gamemode
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l               Etho's Speed Challenge"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                   5-Course Meal"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3Based on the Speed Challenge recorded by &6Etho"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bGet 1 of each of the following items to complete this challenge!"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lItems"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cCooked Porkchop"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cCooked Fish"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cBread"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cCake"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cMushroom Stew"));
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
		//Check if user has Cooked Porkchop
		if (p.getInventory().containsAtLeast(new ItemStack(Material.GRILLED_PORK), 1)){
			//Adds a Score
			score += 1;
		}
		//Check if user has Cooked Fish
		if (p.getInventory().containsAtLeast(new ItemStack(Material.COOKED_FISH), 1)){
			score += 1;
		}
		//Check if user has Cake
		if (p.getInventory().containsAtLeast(new ItemStack(Material.CAKE), 1)){
			score += 1;
		}
		//Check if user has Bread
		if (p.getInventory().containsAtLeast(new ItemStack(Material.BREAD), 1)){
			score += 1;
		}
		//Check if user has Mushroom Stew
		if (p.getInventory().containsAtLeast(new ItemStack(Material.MUSHROOM_SOUP), 1)){
			score += 1;
		}
		//End of it add score to player
		ScoreboardHelper.setScoreOfPlayer(p, score);
		return score;
	}
	
	//List all objectives for a player (for future command)
	public static void listObjectives(Player p){
		p.sendMessage(ChatColor.GOLD + "OBJECTIVES CHECK");
		//Check if user has Cooked Porkchop
		if (p.getInventory().containsAtLeast(new ItemStack(Material.GRILLED_PORK), 1)){
			//Adds a Score
			p.sendMessage("Cooked Porkchop - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Cooked Porkchop - " + ChatColor.RED + "Unobtained");
		}
		//Check if user has Cooked Fish
		if (p.getInventory().containsAtLeast(new ItemStack(Material.COOKED_FISH), 1)){
			p.sendMessage("Cooked Fish - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Cooked Fish - " + ChatColor.RED + "Unobtained");
		}
		//Check if user has Cake
		if (p.getInventory().containsAtLeast(new ItemStack(Material.CAKE), 1)){
			p.sendMessage("Cake - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Cake - " + ChatColor.RED + "Unobtained");
		}
		//Check if user has Bread
		if (p.getInventory().containsAtLeast(new ItemStack(Material.BREAD), 1)){
			p.sendMessage("Bread - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Bread - " + ChatColor.RED + "Unobtained");
		}
		//Check if user has Mushroom Stew
		if (p.getInventory().containsAtLeast(new ItemStack(Material.MUSHROOM_SOUP), 1)){
			p.sendMessage("Mushroom Stew - " + ChatColor.GREEN + "Obtained");
		} else {
			p.sendMessage("Mushroom Stew - " + ChatColor.RED + "Unobtained");
		}
	}

}
