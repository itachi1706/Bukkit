package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GameIsRunning implements Runnable{

	@SuppressWarnings("unused")
	private Main plugin;
	
	public GameIsRunning(Main plugin){
		this.plugin = plugin;
	}
	
	
	@Override
	public void run() {
		if (Main.countdown == 900){
			String finalCountDown2 = "&b[SpeedChallenge] &6&lGame will end in 15 minutes!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown2));
		}
		if (Main.countdown == 600){
			String finalCountDown2 = "&b[SpeedChallenge] &6&lGame will end in 10 minutes!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown2));
		}
		if (Main.countdown == 300){
			String finalCountDown2 = "&b[SpeedChallenge] &6&lGame will end in 5 minute!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown2));
		}
		if (Main.countdown == 60){
			String finalCountDown2 = "&b[SpeedChallenge] &6&lGame will end in 1 minute!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown2));
		}
		if (Main.countdown == 30){
			//Generate nether
			String nethergen = "&b[SpeedChallenge] &6&lGame will end in " + Main.countdown + " second(s)!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
		}
		if (Main.countdown == 15){
			//Generate The End
			String endgen = "&b[SpeedChallenge] &2New End World is currently being generated... Expect lag...";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
		}
		if (Main.countdown <= 10){
			//Start counting down
			String finalCountDown = "&b[SpeedChallenge] &6&lGame will end in " + Main.countdown + " second(s)!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown));
		}
		if (Main.countdown <= 0){
			String finalCountDown3 = "&b[SpeedChallenge] &6&lGame Ended! Now calculating scores and determining winner!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown3));
			Bukkit.getServer().getScheduler().cancelTask(Main.countDownTimer3);
			
		}
		Main.countdown--;
		ScoreboardHelper.updateHealth();
		ScoreboardHelper.gameStartRunning();
	}

}
