package io.github.itachi1706.SpeedChallenge.Gamemodes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GetAsManyAchievements extends AbstractGamemode {
	
	private static String challengeTitle = "The Achievements Challenge";
	
	/*
	 * List of Achievements [achievement.<achievementtag>]
	 * Open Inventory - openInventory
	 * Getting Wood - mineWood
	 * Benchmarking - buildWorkBench
	 * Time to Farm! - buildHoe
	 * Bake Bread - makeBread
	 * The Lie - bakeCake
	 * Time to Strike - buildSword
	 * Monster Hunter - killEnemy
	 * Sniper Duel - snipeSkeleton
	 * Cow Tipper - killCow
	 * Repopulation - breedCow
	 * When Pigs Fly - flyPig
	 * Time to Mine! - buildPickaxe
	 * Getting an Upgrade - buildBetterPickaxe
	 * Overpowered (1.8 Only) - overpowered
	 * Hot Topic - buildFurnance
	 * Acquire Hardware - acquireIron
	 * Delicious Fish - cookFish
	 * On A Rail - onARail
	 * DIAMONDS! - diamonds
	 * Diamonds To You! - diamondsToYou
	 * Enchanter - enchantments
	 * Overkill - overkill
	 * Librarian - bookcase
	 * We Need To Go Deeper - portal
	 * Into Fire - blazeRod
	 * Local Brewery - potion
	 * Adventuring Time - exploreAllBiomes
	 * The End? - theEnd
	 * The End. - theEnd2
	 * The Beginning? - spawnWither
	 * The Beginning. - killWither
	 * Beaconator - fullBeacon
	 */
	
	public static String getGMTitle() {return challengeTitle;}
	public static void gmInfo(){
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l         The Achievements Challenge"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bComplete as many achievements as you can!"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAchievements can be viewed in the achievements pane"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lin the pause menu"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', ""));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "Note: - &cAchievements like Overpowered are not available"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "      - &cAchievements like On A Rail, Beaconator or Adventuring Time etc. while impossible, will still be available to obtain"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aTo check your current objectives, do &6/listobjectives"));
	}
	public static void checkInventory() {}
	public static int checkObjective(Player p) {return 0;}
	public static void listObjectives(Player p) {}

}
