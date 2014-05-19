package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Utilities.InventoriesPreGame;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import com.onarandombox.MultiverseCore.MultiverseCore;

public class InitGame implements Runnable {
	
	private Main plugin;
	
	public InitGame(Main plugin){
		this.plugin = plugin;
	}

	public static int randomInt = 0;
	
	public void run() {
		if (Main.countdown == 60){
			String finalCountDown2 = "&b[SpeedChallenge] &6&lGame starts in 1 minute!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown2));
		}
		if (Main.countdown == 31){
			//Generate new overworld
			String worldgen = "&b[SpeedChallenge] &6Generating new world maps with a random seed";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', worldgen));
			}
		if (Main.countdown == 30){
			//Generate new overworld
			String worldgen = "&b[SpeedChallenge] &2Generating new Overworld";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', worldgen));
			generateOverworld();
			}
		if (Main.countdown == 29){
			//Generate nether
			String nethergen = "&b[SpeedChallenge] &4Generating new Nether";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
			generateNether();
		}
		if (Main.countdown == 28){
			//Generate The End
			String endgen = "&b[SpeedChallenge] &eGenerating new End";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
			generateEnd();
		}
		if (Main.countdown == 27){
			//Generate new overworld
			String worldgen = "&b[SpeedChallenge] &6Worlds generated. Seed used: &b" + randomInt;
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', worldgen));
			}
		if (Main.countdown <= 10 && Main.countdown > 0){
			//Start counting down
			String finalCountDown = "&b[SpeedChallenge] &6&lGame begins in " + Main.countdown + " second(s)!";
			for (Player p : Bukkit.getServer().getOnlinePlayers()){
				p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
			}
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown));
		}
		if (Main.countdown == 0){
			Main.initGame = true;
			for (Player online : Bukkit.getOnlinePlayers()){
				online.setLevel(0);
				online.setExp(0);
				InventoriesPreGame.removeItemFromPlayer(online);
			}
			String finalCountDown3 = "&b[SpeedChallenge] &6&lGame Will Start NOW!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown3));
			checkOptionSelected();
			teleportPlayers();
			Main.countDownTimer2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new PreGameRunnable(this.plugin), 20L, 20L);
			Main.countdown = -1;
		}
		if (Main.countdown == -1){
			Main.initGame = true;
			Bukkit.getLogger().info("An error might have occured trying to start the game. Please reboot server if true");
		}
		if (Main.countdown > 0){
			Main.countdown--;
			ScoreboardHelper.updateInitTime();
			for (Player online : Bukkit.getOnlinePlayers()){
				online.setLevel(Main.countdown);
				online.setExp(((float) Main.countdown/90) + 0.01F);
			}
		}
	}
	
	public void generateOverworld(){
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt();
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC", Environment.NORMAL, randomInt + "", WorldType.NORMAL, true, null, true);
		//String worldgen = "&b[SpeedChallenge] &2Overworld has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', worldgen));
	}
	
	public void generateNether(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC_nether", Environment.NETHER, randomInt + "", WorldType.NORMAL, true, null, true);
		//String nethergen = "&b[SpeedChallenge] &4Nether has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
	}
	
	public void generateEnd(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC_the_end", Environment.THE_END, randomInt + "", WorldType.NORMAL, true, null, true);
		//String endgen = "&b[SpeedChallenge] &eThe End has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
	}
	
	public void optionSelected(){
		String troll = "&b[SpeedChallenge] &4&lChallenge Selected: &b&l" + Main.gamemode + " (" + PreGameRunnable.getTitle() + ")";
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', troll));
		String duration = "&b[SpeedChallenge] &4&lGame Duration: &b&l30 Minutes";
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', duration));
		if (Main.pvp == 1){
			String pvp = "&b[SpeedChallenge] &4&lPVP will be &a&lenabled!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
		}
		if (Main.pvp == 2){
			String pvp = "&b[SpeedChallenge] &4&lPVP will be &c&ldisabled!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', pvp));
		}
		if (Main.respawn == 1){
			String respawn = "&b[SpeedChallenge] &4&lHardcore Mode will be &c&ldisabled!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
		}
		if (Main.respawn == 2){
			String respawn = "&b[SpeedChallenge] &4&lHardcore Mode will be &a&lenabled!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', respawn));
		}
	}
	
	public void teleportPlayers(){
		Player[] p = Bukkit.getServer().getOnlinePlayers();
		World w = Bukkit.getWorld("SC");
		Location l = new Location(Bukkit.getServer().getWorld("SC"), Bukkit.getServer().getWorld("SC").getSpawnLocation().getX(), Bukkit.getServer().getWorld("SC").getSpawnLocation().getY(), Bukkit.getServer().getWorld("SC").getSpawnLocation().getZ());
		for (int i = 0; i < p.length; i++){
			if (!p[i].getWorld().equals(w))
				p[i].teleport(l);
		}
	}
	
	public void checkOptionSelected(){
		if (Main.gamemode == 0){
			randomizeGM();
		}
		if (Main.pvp == 0){
			randomizePVP();
		}
		if (Main.respawn == 0){
			randomizeRespawn();
		}
		optionSelected();
	}
	
	//Randomizes Gamemode
	public void randomizeGM(){
		String randomizer = "&b[SpeedChallenge] &6As your challenge was not selected, a random challenge will be assigned!";
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', randomizer));
		Random rn = new Random();
		Main.gamemode = rn.nextInt(Main.numberOfChallenges) + 1;
		Bukkit.getLogger().info("Challenge: " + Main.gamemode);
		if (Main.gamemode <= 0 || Main.gamemode > 3){
			Main.gamemode = rn.nextInt(3) + 1;
			Bukkit.getLogger().info("[ERROR] Challenge: " + Main.gamemode);
		}
	}
	
	//Randomizes Respawn
		public void randomizeRespawn(){
			String randomizer = "&b[SpeedChallenge] &6As your option to Respawn On Death was not selected, a random option will be assigned!";
			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', randomizer));
			Random rn = new Random();
			Main.respawn = rn.nextInt(2) + 1;
			Bukkit.getLogger().info("Respawn: " + Main.respawn);
			if (Main.respawn <= 0 || Main.respawn > 2){
				Main.respawn = rn.nextInt(2) + 1;
				Bukkit.getLogger().info("[ERROR] Respawn: " + Main.respawn);
			}
		}
	
	//Randomizes PVP Enable
	public void randomizePVP(){
		String randomizer = "&b[SpeedChallenge] &6As your option to enable PVP was not selected, a random PVP option will be assigned!";
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', randomizer));
		Random rn = new Random();
		Main.pvp = rn.nextInt(2) + 1;
		Bukkit.getLogger().info("PVP: " + Main.pvp);
		if (Main.pvp <= 0 || Main.pvp > 2){
			Main.pvp = rn.nextInt(2) + 1;
			Bukkit.getLogger().info("[ERROR] PVP: " + Main.pvp);
		}
	}
	
	

}
