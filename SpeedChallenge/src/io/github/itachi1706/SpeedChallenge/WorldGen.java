package io.github.itachi1706.SpeedChallenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;

public class WorldGen {
	
	public static int randomInt;
	public static boolean resettingWorlds = false;
	
	public static void generateSCWorld(){
		resettingWorlds = true;
		Bukkit.getLogger().info("Generating Worlds...");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "s [SpeedChallenge] Generating Worlds...");
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt();
		generateOverworld();
		generateNether();
		generateEnd();
		Bukkit.getLogger().info("Worlds Generated...");
		resettingWorlds = false;
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "s [SpeedChallenge] Worlds generated!");
	}
	
	public static void deleteSCWorlds(){
		resettingWorlds = true;
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		Collection<MultiverseWorld> collate = mc.getMVWorldManager().getMVWorlds();
		ArrayList<MultiverseWorld> obj = new ArrayList<MultiverseWorld>(collate);
		for (int i = 0; i < obj.size(); i++){
			MultiverseWorld world = obj.get(i);
			if (world.getName().equals("SC")){
				mc.getMVWorldManager().deleteWorld("SC");
			}
			if (world.getName().equals("SC_nether")){
				mc.getMVWorldManager().deleteWorld("SC_nether");
			}
			if (world.getName().equals("SC_the_end")){
				mc.getMVWorldManager().deleteWorld("SC_the_end");
			}
		}
		Bukkit.getServer().broadcastMessage("WORLDS DELETED!");
		resettingWorlds = false;
	}
	
	private static void generateOverworld(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC", Environment.NORMAL, randomInt + "", WorldType.NORMAL, true, null, true);
		//String worldgen = "&b[SpeedChallenge] &2Overworld has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', worldgen));
		Bukkit.getLogger().info("Overworld Generated. Seed: " + randomInt);
	}
	
	private static void generateNether(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC_nether", Environment.NETHER, randomInt + "", WorldType.NORMAL, true, null, true);
		//String nethergen = "&b[SpeedChallenge] &4Nether has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', nethergen));
		Bukkit.getLogger().info("Nether Generated. Seed: " + randomInt);
	}
	
	private static void generateEnd(){
		MultiverseCore mc = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		mc.getMVWorldManager().addWorld("SC_the_end", Environment.THE_END, randomInt + "", WorldType.NORMAL, true, null, true);
		//String endgen = "&b[SpeedChallenge] &eThe End has been randomly generated! Seed used: &b" + randomInt;
		//Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', endgen));
		Bukkit.getLogger().info("End Generated. Seed: " + randomInt);
	}

}
