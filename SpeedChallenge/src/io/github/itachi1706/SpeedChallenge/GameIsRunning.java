package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameIsRunning implements Runnable{

	private Main plugin;
	
	public GameIsRunning(Main plugin){
		this.plugin = plugin;
	}
	
	
	@Override
	public void run() {
		if (Main.pvp == 1 && Main.countdown == 1799){
			String finalCountDown4 = "&b[SpeedChallenge] &6&lPVP will be disabled for 1 minute!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown4));
		}
		/*if (Main.countdown == 1799){
			Bukkit.getServer().getScheduler().cancelTask(Main.countDownTimer2);
			Bukkit.getLogger().info("Stopped pregame countdown");
		}*/
		if (Main.countdown == 1740 && Main.pvp == 1){
			String finalCountDown3 = "&b[SpeedChallenge] &6&lPlayers are no longer invulnerable!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown3));
			Main.invulnerable = false;
		}
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
			String endgen = "&b[SpeedChallenge] &6&lGame will end in " + Main.countdown + " second(s)!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
		}
		if (Main.countdown <= 10){
			//Start counting down
			String finalCountDown = "&b[SpeedChallenge] &6&lGame will end in " + Main.countdown + " second(s)!";
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
			}
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown));
		}
		if (Main.countdown == 0){
			String finalCountDown3 = "&b[SpeedChallenge] &6&lGame Ended! Now calculating scores and determining winner!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown3));
			EndGame.getWinner();
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lServer is restarting in 10 seconds"));
			Main.countDownTimer4 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new EndGame(plugin), 20L, 20L);
			Bukkit.getServer().getScheduler().cancelTask(Main.countDownTimer3);
			Main.countdown = -1;
			Bukkit.getLogger().info("Stopped game countdown");
			
			
		}
		Main.countdown--;
		ScoreboardHelper.gameStartRunning();
		for (int i = 0; i < Main.spectators.size(); i++){
			Player p = Main.spectators.get(i);
			Spec.spectator(p);
		}
		for (Player players : Bukkit.getOnlinePlayers())
		{
			for (int i = 0; i < Main.spectators.size(); i++){
				Player p = Main.spectators.get(i);
				if (GameListeners.checkSpectator(p)){
					players.hidePlayer(p);
				}
			}
			
		}
	}

}
