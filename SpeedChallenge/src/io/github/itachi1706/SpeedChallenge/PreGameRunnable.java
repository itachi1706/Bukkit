package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
			//Generate nether
			String nethergen = "&b[SpeedChallenge] &6&lGame begins in " + countdown + " second(s)!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
		}
		if (countdown == 15){
			//Generate The End
			String endgen = "&b[SpeedChallenge] &6&lGame begins in " + countdown + " second(s)!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
		}
		if (countdown <= 10){
			//Start counting down
			String finalCountDown = "&b[SpeedChallenge] &6&lGame begins in " + countdown + " second(s)!";
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
				//online.getInventory().clear();
				online.getInventory().setHelmet(new ItemStack(Material.AIR));
				online.getInventory().setBoots(new ItemStack(Material.AIR));
				online.getInventory().setChestplate(new ItemStack(Material.AIR));
				online.getInventory().setLeggings(new ItemStack(Material.AIR));
			}
			Main.gameStart = true;
			Main.countdown = 1800;
			Main.countDownTimer3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new GameIsRunning(this.plugin), 20L, 20L);
			startGamemode();
			Bukkit.getServer().getScheduler().cancelTask(Main.countDownTimer2);
			
		}
		countdown--;
		ScoreboardHelper.updatePreGameTime();
		for (Player online : Bukkit.getOnlinePlayers()){
			online.setLevel(countdown);
			online.setExp(((float) countdown/30) + 0.01F);
		}
		
	}
	
	public void startGamemode(){
		//Activates the specific listeners
		ScoreboardHelper.gameStart();
		ScoreboardHelper.setNewHealthObjective();
		switch(Main.gamemode){
		case 1:	Bukkit.getServer().broadcastMessage("Start gamemode 1");
				break;
		case 2: Bukkit.getServer().broadcastMessage("Start gamemode 2");
				break;
		default: Bukkit.getServer().broadcastMessage("Error occured");
				break;
		}
	}

}
