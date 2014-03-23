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
	
	//Initial
	public static void gameStart(){
		sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.DARK_GREEN + "Time:"));
		sb.resetScores(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Players:"));
		Objective o = sb.getObjective("game_player");
		o.unregister();
		sb.registerNewObjective("game_player", "dummy");
		o = sb.getObjective("game_player");
		int min = Main.countdown/60;
		int second = (Main.countdown%60)*60 - (min*60);
		o.setDisplayName(ChatColor.GOLD + "Speed Challenge" + ChatColor.DARK_RED + "  " + min + ":" + second);
	}
	
	//Game Is Running
	public static void gameStartRunning(){
		Objective o = sb.getObjective("game_player");
		int min = Main.countdown/60;
		int second = (Main.countdown%60)*60 - (min*60);
		o.setDisplayName(ChatColor.AQUA + "Speed Challenge" + ChatColor.RED + "  " + min + ":" + second);
		//Does check of players score
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
	
	public static void setNewHealthObjective(){
		Objective objective = sb.registerNewObjective("showhealth", "health");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName("/ 20");
		 
		for(Player online : Bukkit.getOnlinePlayers()){
		  online.setScoreboard(sb);
		  online.setHealth(online.getHealth()); //Update their health
		}
	}
	
	public static void updateHealth(){
		for (Player online : Bukkit.getOnlinePlayers()){
			online.setScoreboard(sb);
			online.setHealth(online.getHealth());
		}
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
