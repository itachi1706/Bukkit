package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Gamemodes.AbbaRules;
import io.github.itachi1706.SpeedChallenge.Gamemodes.AbbaRulesRetardStyle;
import io.github.itachi1706.SpeedChallenge.Gamemodes.ModAbbaRules;
import io.github.itachi1706.SpeedChallenge.Gamemodes.EthoSpeedChallenge3;
import io.github.itachi1706.SpeedChallenge.Gamemodes.Sample;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PreGameRunnable implements Runnable{

	public static int countdown = 30;

	private Main plugin;
	
	public PreGameRunnable(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		if (countdown == 30){
			String nethergen = "&b[SpeedChallenge] &6&lGame begins in " + countdown + " second(s)!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
		}
		if (countdown == 29){
			//Get the info of each classes
				getGMInfo();
		}
		if (countdown <= 5){
			//Start counting down
			String finalCountDown = "&b[SpeedChallenge] &6&lGame begins in " + countdown + " second(s)!";
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
			}
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown));
		}
		if (countdown <= 0){
			Main.initGame = true;
			String finalCountDown3 = "&b[SpeedChallenge] &6&lGame Starts NOW!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown3));
			for (Player online : Bukkit.getOnlinePlayers()){
				online.setLevel(0);
				online.setExp(0);
				online.setHealth(online.getMaxHealth());
				online.setFoodLevel(20);
				online.setSaturation(20);
				online.getInventory().clear();
				online.getInventory().setHelmet(new ItemStack(Material.AIR));
				online.getInventory().setBoots(new ItemStack(Material.AIR));
				online.getInventory().setChestplate(new ItemStack(Material.AIR));
				online.getInventory().setLeggings(new ItemStack(Material.AIR));
			}
			initPlayerWithWeapon();
			Main.gameStart = true;
			Main.countdown = 1800;
			ScoreboardHelper.gameStart();
			Main.countDownTimer3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new GameIsRunning(this.plugin), 20L, 20L);
			Bukkit.getServer().getScheduler().cancelTask(Main.countDownTimer2);
			
		}
		countdown--;
		ScoreboardHelper.updatePreGameTime();
		for (Player online : Bukkit.getOnlinePlayers()){
			online.setLevel(countdown);
			online.setExp(((float) countdown/30) + 0.01F);
		}
		
	}
	
	public static void checkPlayerScores(){
		//Activates the specific class files for checks
		switch(Main.gamemode){
		case 1:	Sample.checkInventory();
				break;
		case 2: EthoSpeedChallenge3.checkInventory();
				break;
		case 3: ModAbbaRules.checkInventory();
				break;
		case 4: AbbaRules.checkInventory();
				break;
		case 5: AbbaRulesRetardStyle.checkInventory();
				break;
		default: Bukkit.getServer().broadcastMessage("Error occured");
				break;
		}
	}
	
	public static void initPlayerWithWeapon(){
		//Activates the specific class files for checks
		switch(Main.gamemode){
		case 3: ModAbbaRules.initWeapon();
				break;
		case 4: AbbaRules.initWeapon();
				break;
		case 5: AbbaRulesRetardStyle.initWeapon();
				break;
		default: Bukkit.getLogger().info("No Initialization Needed");
				break;
		}
	}
	
	public static void checkPlayerObjectives(Player p){
		//Activates the specific class files for checks
		
		switch(Main.gamemode){
		case 1:	Sample.listObjectives(p);
				break;
		case 2: EthoSpeedChallenge3.listObjectives(p);
				break;
		case 3: ModAbbaRules.listObjectives(p);
				break;
		case 4: AbbaRules.listObjectives(p);
				break;
		case 5: AbbaRulesRetardStyle.listObjectives(p);
				break;
		default: p.sendMessage("Invalid Gamemode!");
				break;
		}
	}
	
	public static void getGMInfo(){
		//Gets Info of Gamemode
		switch(Main.gamemode){
		case 1:	Sample.gmInfo();
				break;
		case 2: EthoSpeedChallenge3.gmInfo();
				break;
		case 3: ModAbbaRules.gmInfo();
				break;
		case 4: AbbaRules.gmInfo();
				break;
		case 5: AbbaRulesRetardStyle.gmInfo();
				break;
		default: Bukkit.getServer().broadcastMessage("Error occured");
				break;
		}
	}
	
	public static String getTitle(){
		//Gets Info of Gamemode
		switch(Main.gamemode){
		case 1:	return Sample.getGMTitle();
		case 2: return EthoSpeedChallenge3.getGMTitle();
		case 3: return ModAbbaRules.getGMTitle();
		case 4: return AbbaRules.getGMTitle();
		case 5: return AbbaRulesRetardStyle.getGMTitle();
		default: Bukkit.getServer().broadcastMessage("Error occured");
		return "";
		}
	}

}
