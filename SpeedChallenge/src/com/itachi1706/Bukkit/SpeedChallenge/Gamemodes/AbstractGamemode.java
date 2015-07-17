package com.itachi1706.Bukkit.SpeedChallenge.Gamemodes;

import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for SpeedChallenge in com.itachi1706.Bukkit.SpeedChallenge.Gamemodes
 */
public abstract class AbstractGamemode {

    private static String challengeTitle = "Gamemode Challenge Title";

    public static String getGMTitle() {return challengeTitle;}
    public static void gmInfo(){}
    public static void checkInventory() {}
    public static int checkObjective(Player p) {return 0;}
    public static void listObjectives(Player p) {}

}
