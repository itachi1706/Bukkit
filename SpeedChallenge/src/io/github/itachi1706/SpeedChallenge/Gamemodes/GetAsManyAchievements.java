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
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAchievements can be viewed in the achievements pane in the pause menu"));
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
		Bukkit.savePlayers();
		JSONObject jArr = getStatsOfPlayer(p);
		ArrayList<String> check = new ArrayList<String>();
		check.add(ChatColor.GOLD + "Objectives Check");
		check.add("Legend of this list: ");
		check.add("Green = " + ChatColor.GREEN + "Obtained " + ChatColor.RESET + ",Red = " + ChatColor.RED + "Unobtained");
		if (checkOpenInventory(jArr)){
			check.add(ChatColor.GREEN + "Open Inventory");
		} else {
			check.add(ChatColor.RED + "Open Inventory");
		}
		if (checkMineWood(jArr)){
			check.add(ChatColor.GREEN + "Getting Wood");
		} else {
			check.add(ChatColor.RED + "Getting Wood");
		}
		if (checkBuildWorkbench(jArr)){
			check.add(ChatColor.GREEN + "Benchmarking");
		} else {
			check.add(ChatColor.RED + "Benchmarking");
		}
		if (checkBuildHoe(jArr)){
			check.add(ChatColor.GREEN + "Time to Farm!");
		} else {
			check.add(ChatColor.RED + "Time to Farm!");
		}
		if (checkMakeBread(jArr)){
			check.add(ChatColor.GREEN + "Bake Bread");
		} else {
			check.add(ChatColor.RED + "Bake Bread");
		}
		if (checkBakeCake(jArr)){
			check.add(ChatColor.GREEN + "The Lie");
		} else {
			check.add(ChatColor.RED + "The Lie");
		}
		if (checkBuildSword(jArr)){
			check.add(ChatColor.GREEN + "Time to Strike");
		} else {
			check.add(ChatColor.RED + "Time to Strike");
		}
		if (checkKillEnemy(jArr)){
			check.add(ChatColor.GREEN + "Monster Hunter");
		} else {
			check.add(ChatColor.RED + "Monster Hunter");
		}
		if (checkSnipeSkeleton(jArr)){
			check.add(ChatColor.GREEN + "Sniper Duel");
		} else {
			check.add(ChatColor.RED + "Sniper Duel");
		}
		if (checkKillCow(jArr)){
			check.add(ChatColor.GREEN + "Cow Tipper");
		} else {
			check.add(ChatColor.RED + "Cow Tipper");
		}
		if (checkBreedCow(jArr)){
			check.add(ChatColor.GREEN + "Repopulation");
		} else {
			check.add(ChatColor.RED + "Repopulation");
		}
		if (checkFlyPig(jArr)){
			check.add(ChatColor.GREEN + "When Pigs Fly");
		} else {
			check.add(ChatColor.RED + "When Pigs Fly");
		}
		if (checkBuildPickaxe(jArr)){
			check.add(ChatColor.GREEN + "Time to Mine!");
		} else {
			check.add(ChatColor.RED + "Time to Mine!");
		}
		if (checkBuildBetterPickaxe(jArr)){
			check.add(ChatColor.GREEN + "Getting an Upgrade");
		} else {
			check.add(ChatColor.RED + "Getting an Upgrade");
		}
		if (checkOverpowered(jArr)){
			check.add(ChatColor.GREEN + "Overpowered");
		} else {
			check.add(ChatColor.RED + "Overpowered");
		}
		if (checkBuildFurnance(jArr)){
			check.add(ChatColor.GREEN + "Hot Topic");
		} else {
			check.add(ChatColor.RED + "Hot Topic");
		}
		if (checkAcquireIron(jArr)){
			check.add(ChatColor.GREEN + "Acquire Hardware");
		} else {
			check.add(ChatColor.RED + "Acquire Hardware");
		}
		if (checkCookFish(jArr)){
			check.add(ChatColor.GREEN + "Delicious Fish");
		} else {
			check.add(ChatColor.RED + "Delicious Fish");
		}
		if (checkOnARail(jArr)){
			check.add(ChatColor.GREEN + "On A Rail");
		} else {
			check.add(ChatColor.RED + "On A Rail");
		}
		if (checkDiamonds(jArr)){
			check.add(ChatColor.GREEN + "DIAMONDS!");
		} else {
			check.add(ChatColor.RED + "DIAMONDS!");
		}
		if (checkDiamondsToYou(jArr)){
			check.add(ChatColor.GREEN + "Diamonds To You!");
		} else {
			check.add(ChatColor.RED + "Diamonds To You!");
		}
		if (checkEnchantments(jArr)){
			check.add(ChatColor.GREEN + "Enchanter");
		} else {
			check.add(ChatColor.RED + "Enchanter");
		}
		if (checkOverkill(jArr)){
			check.add(ChatColor.GREEN + "Overkill");
		} else {
			check.add(ChatColor.RED + "Overkill");
		}
		if (checkBookcase(jArr)){
			check.add(ChatColor.GREEN + "Librarian");
		} else {
			check.add(ChatColor.RED + "Librarian");
		}
		if (checkPortal(jArr)){
			check.add(ChatColor.GREEN + "We Need To Go Deeper");
		} else {
			check.add(ChatColor.RED + "We Need To Go Deeper");
		}
		if (checkBlazerod(jArr)){
			check.add(ChatColor.GREEN + "Into Fire");
		} else {
			check.add(ChatColor.RED + "Into Fire");
		}
		if (checkPotion(jArr)){
			check.add(ChatColor.GREEN + "Local Brewery");
		} else {
			check.add(ChatColor.RED + "Local Brewery");
		}
		if (checkExploreAllBiomes(jArr)){
			check.add(ChatColor.GREEN + "Adventuring Time");
		} else {
			check.add(ChatColor.RED + "Adventuring Time");
		}
		if (checkTheEnd(jArr)){
			check.add(ChatColor.GREEN + "The End?");
		} else {
			check.add(ChatColor.RED + "The End?");
		}
		if (checkTheEnd2(jArr)){
			check.add(ChatColor.GREEN + "The End.");
		} else {
			check.add(ChatColor.RED + "The End.");
		}
		if (checkSpawnWither(jArr)){
			check.add(ChatColor.GREEN + "The Beginning?");
		} else {
			check.add(ChatColor.RED + "The Beginning?");
		}
		if (checkKillWither(jArr)){
			check.add(ChatColor.GREEN + "The Beginning.");
		} else {
			check.add(ChatColor.RED + "The Beginning.");
		}
		if (checkFullBeacon(jArr)){
			check.add(ChatColor.GREEN + "Beaconator");
		} else {
			check.add(ChatColor.RED + "Beaconator");
		}
		p.sendMessage(check.get(0));
		p.sendMessage(check.get(1));
		p.sendMessage(check.get(2));
		for (int i = 3; i < check.size(); i++)
		if (i % 3 == 0){
			if ((i + 1) >= check.size()){
				//Only display 1
				p.sendMessage(check.get(i));
			} else if ((i+2) >= check.size()){
				//Only displays 2
				p.sendMessage(check.get(i) + "    " + check.get(i+1));
			} else {
				//Displays 3
				p.sendMessage(check.get(i) + "    " + check.get(i+1) + "    " + check.get(i+2));
			}
				
		}
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
