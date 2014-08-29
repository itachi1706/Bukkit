package io.github.itachi1706.SpeedChallenge.Gamemodes;

import org.bukkit.entity.Player;

public abstract class AbstractGamemode {
	
	private static String challengeTitle = "Gamemode Challenge Title";
	
	public static String getGMTitle() {return challengeTitle;}
	public static void gmInfo(){}
	public static void checkInventory() {}
	public static int checkObjective(Player p) {return 0;}
	public static void listObjectives(Player p) {}
}
