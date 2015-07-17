package com.itachi1706.Bukkit.SpeedChallenge.Gamemodes;

import com.itachi1706.Bukkit.SpeedChallenge.Main;
import com.itachi1706.Bukkit.SpeedChallenge.Utilities.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by Kenneth on 17/7/2015.
 * for SpeedChallenge in com.itachi1706.Bukkit.SpeedChallenge.Gamemodes
 */
public class EthoSpeedChallenge3 extends AbstractGamemode {

    private static ArrayList<Integer> checkCompleted = new ArrayList<Integer>();
    private static int maxPts = 5;	//Max points possible to get per player
    private static String challengeTitle = "5-Course Meal (Etho's Speed Challenge)";

    public static String getGMTitle(){
        return challengeTitle;
    }

    public static void gmInfo(){
        // List all information about the gamemode
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l               Etho's Speed Challenge"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                   5-Course Meal"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3Based on the Speed Challenge recorded by &6Etho"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bGet 1 of each of the following items to complete this challenge!"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lItems"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cCooked Porkchop"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cCooked Fish"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cBread"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cCake"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cMushroom Stew"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aTo check your current objectives, do &6/listobjectives"));
    }

    public static void checkInventory(){
        checkCompleted.clear();
        if (Main.gamePlayerList.size() == 0){
            Bukkit.getLogger().info("INVALID - NO PLAYERS");
            return;
        }
        for (int i = 0; i < Main.gamePlayerList.size(); i++){
            Player p = Main.gamePlayerList.get(i);
            int check = checkObjective(p);
            checkCompleted.add(check);
        }
        int total = 0;
        for (int i = 0; i < checkCompleted.size(); i++){
            total += checkCompleted.get(i);
        }
        if (total == (maxPts* Main.gamePlayerList.size())){
            //Completed
            Bukkit.getServer().broadcastMessage("All players have completed the objective! Game ends now!");
            Main.countdown = 0;
        }
    }

    public static int checkObjective(Player p){
        //Basic Score
        int score = 0;
        Inventory i = p.getInventory();
        //Check if user has Cooked Porkchop
        if (checkPorkchop(i)){
            //Adds a Score
            score += 1;
        }
        //Check if user has Cooked Fish
        if (checkFish(i)){
            score += 1;
        }
        //Check if user has Cake
        if (checkCake(i)){
            score += 1;
        }
        //Check if user has Bread
        if (checkBread(i)){
            score += 1;
        }
        //Check if user has Mushroom Stew
        if (checkMushroomStew(i)){
            score += 1;
        }
        //End of it add score to player
        ScoreboardHelper.setScoreOfPlayer(p, score);
        return score;
    }

    //List all objectives for a player (for future command)
    public static void listObjectives(Player p){
        Inventory inv = p.getInventory();
        ArrayList<String> check = new ArrayList<String>();
        check.add(ChatColor.GOLD + "Objectives Check");
        check.add("Legend: Green = " + ChatColor.GREEN + "Obtained" + ChatColor.RESET + ", Red = " + ChatColor.RED + "Unobtained");
        check.add("");
        //Check if user has Cooked Porkchop
        if (checkPorkchop(inv)){
            //Adds a Score
            check.add(ChatColor.GREEN + "Cooked Porkchop");
        } else {
            check.add(ChatColor.RED + "Cooked Porkchop");
        }
        //Check if user has Cooked Fish
        if (checkFish(inv)){
            check.add(ChatColor.GREEN + "Cooked Fish");
        } else {
            check.add(ChatColor.RED + "Cooked Fish");
        }
        //Check if user has Cake
        if (checkCake(inv)){
            check.add(ChatColor.GREEN + "Cake");
        } else {
            check.add(ChatColor.RED + "Cake");
        }
        //Check if user has Bread
        if (checkBread(inv)){
            check.add(ChatColor.GREEN + "Bread");
        } else {
            check.add(ChatColor.RED + "Bread");
        }
        //Check if user has Mushroom Stew
        if (checkMushroomStew(inv)){
            check.add(ChatColor.GREEN + "Mushroom Stew");
        } else {
            check.add(ChatColor.RED + "Mushroom Stew");
        }

        p.sendMessage(ChatColor.GOLD + "==================================================");
        p.sendMessage(check.get(0));
        p.sendMessage(check.get(1));
        p.sendMessage(check.get(2));
        for (int i = 3; i < check.size(); i++) {
            p.sendMessage(check.get(i));
        }
        p.sendMessage(ChatColor.GOLD + "==================================================");
    }

    private static boolean checkCake(Inventory inv){
        if (inv.containsAtLeast(new ItemStack(Material.CAKE), 1)){
            return true;
        }
        return false;
    }

    private static boolean checkPorkchop(Inventory inv){
        if (inv.containsAtLeast(new ItemStack(Material.GRILLED_PORK), 1)){
            return true;
        }
        return false;
    }

    private static boolean checkFish(Inventory inv){
        if (inv.containsAtLeast(new ItemStack(Material.COOKED_FISH), 1)){
            return true;
        }
        return false;
    }

    private static boolean checkBread(Inventory inv){
        if (inv.containsAtLeast(new ItemStack(Material.BREAD), 1)){
            return true;
        }
        return false;
    }

    private static boolean checkMushroomStew(Inventory inv){
        if (inv.containsAtLeast(new ItemStack(Material.MUSHROOM_SOUP), 1)){
            return true;
        }
        return false;
    }

}
