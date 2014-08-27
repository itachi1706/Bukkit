package io.github.itachi1706.SpeedChallenge;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;

import com.onarandombox.MultiverseCore.MultiverseCore;

public class WorldGen {
	
	public int randomInt;
	
	public void generateWorld(){
		Bukkit.getLogger().info("Generating Worlds...");
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt();
		generateOverworld();
		generateNether();
		generateEnd();
		Bukkit.getLogger().info("Worlds Generated...");
	}
	
	private void generateOverworld(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC", Environment.NORMAL, randomInt + "", WorldType.NORMAL, true, null, true);
		//String worldgen = "&b[SpeedChallenge] &2Overworld has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', worldgen));
		Bukkit.getLogger().info("Overworld Generated. Seed: " + randomInt);
	}
	
	private void generateNether(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC_nether", Environment.NETHER, randomInt + "", WorldType.NORMAL, true, null, true);
		//String nethergen = "&b[SpeedChallenge] &4Nether has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
		Bukkit.getLogger().info("Nether Generated. Seed: " + randomInt);
	}
	
	private void generateEnd(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC_the_end", Environment.THE_END, randomInt + "", WorldType.NORMAL, true, null, true);
		//String endgen = "&b[SpeedChallenge] &eThe End has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
		Bukkit.getLogger().info("End Generated. Seed: " + randomInt);
	}

}
