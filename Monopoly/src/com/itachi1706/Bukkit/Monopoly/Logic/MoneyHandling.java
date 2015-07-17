package com.itachi1706.Bukkit.Monopoly.Logic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class MoneyHandling {

    private static ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
    private static Scoreboard board = manager.getMainScoreboard();

    public static boolean checkIfMoney(int req, Player p){
        Objective gold = board.getObjective("game_Money");
        Score sc = gold.getScore(p);
        if (sc.getScore() >= req){
            return true;		//Enough money
        }
        return false;			//Not enough money
    }

    public static void deductsMoney(int req, Player p){
        Objective gold = board.getObjective("game_Money");
        Score sc = gold.getScore(p);
        sc.setScore(sc.getScore() - req);
    }

    public static void addMoney(int req, Player p){
        Objective gold = board.getObjective("game_Money");
        Score sc = gold.getScore(p);
        sc.setScore(sc.getScore() + req);
    }

    public static boolean checkProperties(Player p){
        Objective properties = board.getObjective("game_Properties");
        Score sc = properties.getScore(p);
        if (sc.getScore() > 0){
            return true;
        } else {
            return false;
        }
    }

}
