package com.itachi1706.Bukkit.SpeedChallenge;

import com.itachi1706.Bukkit.SpeedChallenge.Utilities.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kenneth on 17/7/2015.
 * for SpeedChallenge in com.itachi1706.Bukkit.SpeedChallenge
 */
public class EndGame implements Runnable {

    private Main plugin;
    private int timer = 10;
    private static ArrayList<Integer> playerListScore = new ArrayList<Integer>();

    public EndGame(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (timer == 0){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
                public void run(){
                    File dir = new File("world/stats");
                    if (dir.isDirectory()){
                        for (String pStats : dir.list()){
                            File statFile = new File(dir, pStats);
                            statFile.delete();
                        }
                    }
                    Bukkit.getLogger().info("Files deleted");
                }
            }, 200L);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sc reset");
            Bukkit.getServer().getScheduler().cancelTask(Main.countDownTimer4);
            timer = -1;
        }
        if (timer > 0){
            timer--;
        }
    }

    public static void getWinner(){
        String final1 = "&6&l=============================================";
        String final2 = "           &6&l  Players Scores";
        String final3 = "&6&l=============================================";
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', final1));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', final2));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', final3));
        for (int i = 0; i < Main.gamePlayerList.size(); i++){
            Player p = Main.gamePlayerList.get(i);
            playerListScore.add(ScoreboardHelper.getFinalScore(p));
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[SpeedChallenge] &6" + p.getDisplayName() + ": &c" + ScoreboardHelper.getFinalScore(p) + " points"));
        }
        if (Main.gamePlayerList.size() != 0){
            int high = playerListScore.get(0);
            int index = 0;
            for (int i = 0; i < playerListScore.size(); i++){
                int lol = playerListScore.get(i);
                if (lol > high){
                    high = lol;
                    index = i;
                }
            }
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[SpeedChallenge] &6&l" + Main.gamePlayerList.get(index).getDisplayName() + " &6&lhas the highest score at &b&l" + playerListScore.get(index) + " points!"));
        } else {
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[SpeedChallenge] &6&lAll players have died! Resulting in this match being a draw!"));
        }


    }

}
