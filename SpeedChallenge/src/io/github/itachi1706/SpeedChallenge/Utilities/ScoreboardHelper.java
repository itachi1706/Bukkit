package io.github.itachi1706.SpeedChallenge.Utilities;

import io.github.itachi1706.SpeedChallenge.Main;
import io.github.itachi1706.SpeedChallenge.PreGameRunnable;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardHelper {

	public static ScoreboardManager sm = Bukkit.getServer().getScoreboardManager();
	public static Scoreboard sb = sm.getNewScoreboard();
	
	public static void setScoreOfPlayer(Player p, int score){
		Objective o = sb.getObjective("game_player");
		Score pSco = o.getScore(p);
		pSco.setScore(score);
	}
	
	public static void initPlayersCounter(){
		sb.registerNewObjective("game_player", "dummy");
		Objective o = sb.getObjective("game_player");
		o.setDisplayName("Players");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Players:"));
		score.setScore(Main.players);
		Score score2 = o.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Time:"));
		score2.setScore(Main.countdown);
	}
	
	//Initial game running
	public static void gameStart(){
		Objective o = sb.getObjective("game_player");
		//o.unregister();
		//sb.registerNewObjective("game_player", "dummy");
		//o = sb.getObjective("game_player");
		int min = Main.countdown/60;
		int second = Main.countdown - (min * 60);
		//Bukkit.getLogger().info("MIN: " + min + " SEC: "+ second);
		String display = ChatColor.AQUA + "Speed Challenge" + ChatColor.RED;
		if (min < 10){
			display = display + " 0" + min;
		} else {
			display = display + " " + min;
		}
		if (second < 10){
			display = display + ":0" + second;
		} else {
			display = display + ":" + second;
		}
		o.setDisplayName(display);
		Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Players Scores"));
		score.setScore(9999);
		for (int i = 0; i < Main.playerList.size(); i++){
			Score pSco = o.getScore(Main.playerList.get(i));
			pSco.setScore(0);
		}
		sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Time:"));
		sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Players:"));
		Bukkit.getLogger().info("Game Start Scoreboard Init!");
	}
	
	//Game Is Running
	public static void gameStartRunning(){
		sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Time:"));
		Objective o = sb.getObjective("game_player");
		int min = Main.countdown/60;
		int second = Main.countdown - (min * 60);
		//Bukkit.getLogger().info("MIN: " + min + " SEC: "+ second);
		String display = ChatColor.AQUA + "Speed Challenge" + ChatColor.RED;
		if (min < 10){
			display = display + " 0" + min;
		} else {
			display = display + " " + min;
		}
		if (second < 10){
			display = display + ":0" + second;
		} else {
			display = display + ":" + second;
		}
		o.setDisplayName(display);
		//Does check of players score
		PreGameRunnable.checkPlayerScores();
	}
	
	public static void updatePlayers(){
		Objective o = sb.getObjective("game_player");
		o.setDisplayName("Players");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Players:"));
		score.setScore(Main.players);
	}
	
	public static void updateInitTime(){
		Objective o = sb.getObjective("game_player");
		o.setDisplayName("Players");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Time:"));
		score.setScore(Main.countdown);
	}
	
	public static void updatePreGameTime(){
		Objective o = sb.getObjective("game_player");
		o.setDisplayName("Speed Challenge");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = o.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Time:"));
		score.setScore(PreGameRunnable.countdown);
	}
	
	public static int getFinalScore(Player p){
		Objective o = sb.getObjective("game_player");
		Score sc = o.getScore(p);
		return sc.getScore();
	}
	
	public static void resetScoreboard(){
		Set<Objective> set = sb.getObjectives();
		ArrayList<Objective> obj = new ArrayList<Objective>(set);
		for (int i = 0; i < obj.size(); i++){
			Objective o = obj.get(i);
			o.unregister();
		}
	}
	
	//Reference
	public static void addScore(String objective, Player p, int scoring){
		Objective o = sb.getObjective(objective);
		Score sc = o.getScore(p);
		sc.setScore(sc.getScore() + scoring);
		
	}
	
	public static void setScore(String objective, Player p, int scoring){
		Objective o = sb.getObjective(objective);
		Score sc = o.getScore(p);
		sc.setScore(scoring);
		
	}
	
	public static int getScore(String objective, Player p){
		Objective o = sb.getObjective(objective);
		Score sc = o.getScore(p);
		return sc.getScore();
	}
	
	public static void removeScore(String objective, Player p, int scoring){
		Objective o = sb.getObjective(objective);
		Score sc = o.getScore(p);
		sc.setScore(sc.getScore() - scoring);
	}
	
	public static void addObjective(String objective){
		sb.registerNewObjective(objective, "dummy");
	}
	
	public static void addObjective(String objective, String displayName){
		sb.registerNewObjective(objective, "dummy");
		Objective o = sb.getObjective(objective);
		o.setDisplayName(displayName);
	}
}
