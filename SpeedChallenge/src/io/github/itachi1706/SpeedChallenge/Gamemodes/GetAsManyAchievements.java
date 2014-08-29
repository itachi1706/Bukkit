package io.github.itachi1706.SpeedChallenge.Gamemodes;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetAsManyAchievements extends AbstractGamemode {
	
	private static String challengeTitle = "The Achievements Challenge";
	private static ArrayList<Integer> checkCompleted = new ArrayList<Integer>();
	private static int maxPts = 32;	//Max points possible to get per player (33 in 1.8)
	
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
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "      - &cScoreboard will only update every 10 seconds due to achievement file limitation"));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aTo check your current objectives, do &6/listobjectives"));
	}
	
	//Check every 10 seconds
	public static void checkInventory() {
		if ((Main.countdown % 10) == 0){
			//Save game
			Bukkit.savePlayers();
			checkCompleted.clear();
			for (int i = 0; i < Main.gamePlayerList.size(); i++){
				Player p = Main.gamePlayerList.get(i);
				JSONObject ja = getStatsOfPlayer(p);
				int check = checkObjective(p, ja);
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
	}
	
	public static int checkObjective(Player p, JSONObject jArr) {
		int total = 0;
		if (checkOpenInventory(jArr)){
			total += 1;
		}
		if (checkMineWood(jArr)){
			total += 1;
		}
		if (checkBuildWorkbench(jArr)){
			total += 1;
		}
		if (checkBuildHoe(jArr)){
			total += 1;
		}
		if (checkMakeBread(jArr)){
			total += 1;
		}
		if (checkBakeCake(jArr)){
			total += 1;
		}
		if (checkBuildSword(jArr)){
			total += 1;
		}
		if (checkKillEnemy(jArr)){
			total += 1;
		}
		if (checkSnipeSkeleton(jArr)){
			total += 1;
		}
		if (checkKillCow(jArr)){
			total += 1;
		}
		if (checkBreedCow(jArr)){
			total += 1;
		}
		if (checkFlyPig(jArr)){
			total += 1;
		}
		if (checkBuildPickaxe(jArr)){
			total += 1;
		}
		if (checkBuildBetterPickaxe(jArr)){
			total += 1;
		}
		if (checkOverpowered(jArr)){
			total += 1;
		}
		if (checkBuildFurnance(jArr)){
			total += 1;
		}
		if (checkAcquireIron(jArr)){
			total += 1;
		}
		if (checkCookFish(jArr)){
			total += 1;
		}
		if (checkOnARail(jArr)){
			total += 1;
		}
		if (checkDiamonds(jArr)){
			total += 1;
		}
		if (checkDiamondsToYou(jArr)){
			total += 1;
		}
		if (checkEnchantments(jArr)){
			total += 1;
		}
		if (checkOverkill(jArr)){
			total += 1;
		}
		if (checkBookcase(jArr)){
			total += 1;
		}
		if (checkPortal(jArr)){
			total += 1;
		}
		if (checkBlazerod(jArr)){
			total += 1;
		}
		if (checkPotion(jArr)){
			total += 1;
		}
		if (checkExploreAllBiomes(jArr)){
			total += 1;
		}
		if (checkTheEnd(jArr)){
			total += 1;
		}
		if (checkTheEnd2(jArr)){
			total += 1;
		}
		if (checkSpawnWither(jArr)){
			total += 1;
		}
		if (checkKillWither(jArr)){
			total += 1;
		}
		if (checkFullBeacon(jArr)){
			total += 1;
		}
		ScoreboardHelper.setScoreOfPlayer(p, total);
		return total;
	}
	
	public static void listObjectives(Player p) {
		p.sendMessage("Unimplemented");
	}
	
	
	//Get JSONObject
	private static JSONObject getStatsOfPlayer(Player p){
		UUID uid = p.getUniqueId();
		File dir = new File("world/stats");
		File actualFile = null;
		if (dir.isDirectory()){
			for (String pStats : dir.list()){
				File statFile = new File(dir, pStats);
				if (statFile.getName().equals(uid.toString() + ".json")){
					actualFile = statFile;
					break;
				}
			}
		}
		
		//Check if null
		if (actualFile == null){
			//Error
			return null;
		}
		
		JSONParser parser = new JSONParser();
		BufferedReader reader;
		String line = null;
		String jsonLine = "";
		Object oj = null;
		try {
			reader = new BufferedReader(new FileReader(actualFile));
			while ((line = reader.readLine()) != null) {
				jsonLine += line;
			}
			reader.close();
			oj = parser.parse(jsonLine);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject jArr = (JSONObject) oj;
		
		return jArr;
	}
	
	private static boolean checkOpenInventory(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.openInventory").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkMineWood(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.mineWood").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBuildWorkbench(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.buildWorkBench").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBuildHoe(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.buildHoe").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkMakeBread(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.makeBread").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBakeCake(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.bakeCake").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBuildSword(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.buildSword").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkKillEnemy(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.killEnemy").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkSnipeSkeleton(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.snipeSkeleton").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkKillCow(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.killCow").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBreedCow(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.breedCow").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkFlyPig(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.flyPig").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBuildPickaxe(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.buildPickaxe").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBuildBetterPickaxe(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.buildBetterPickaxe").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkOverpowered(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.overpowered").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBuildFurnance(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.buildFurnance").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkAcquireIron(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.acquireIron").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkCookFish(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.cookFish").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkOnARail(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.onARail").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkDiamonds(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.diamonds").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkDiamondsToYou(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.diamondsToYou").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkEnchantments(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.enchantments").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkOverkill(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.overkill").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBookcase(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.bookcase").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkPortal(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.portal").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkBlazerod(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.blazeRod").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkPotion(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.potion").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkExploreAllBiomes(JSONObject array){
		String o = null;
		try {
			JSONObject sec = (JSONObject) array.get("achievement.exploreAllBiomes");
			o = sec.get("value").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkTheEnd(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.theEnd").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkTheEnd2(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.theEnd2").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkSpawnWither(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.spawnWither").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkKillWither(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.killWither").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}
	
	private static boolean checkFullBeacon(JSONObject array){
		String o = null;
		try {
			o = array.get("achievement.fullBeacon").toString();
		} catch (NullPointerException ex){
			o = "0";
		}
		
		int check = 0;
		try {
			check = Integer.parseInt(o);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return false;
		}
		if (check > 0){
			return true;
		}
		return false;
	}

}
