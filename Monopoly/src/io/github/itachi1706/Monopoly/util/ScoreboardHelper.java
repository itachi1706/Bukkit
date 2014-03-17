package io.github.itachi1706.Monopoly.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardHelper {
	
	public static ScoreboardManager sm = Bukkit.getServer().getScoreboardManager();
	public static Scoreboard sb = sm.getMainScoreboard();
	
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
	
	public static void initScoreboard(){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		board.registerNewTeam("Player");		//Team 1
		board.registerNewTeam("Zombie");		//Team 2
		board.registerNewTeam("Creeper");		//Team 3
		board.registerNewTeam("Skeleton");		//Team 4
		board.registerNewObjective("game_isPlaying", "dummy");
		board.registerNewObjective("game_isTurn", "dummy");
		board.registerNewObjective("game_inJail", "dummy");
		Objective gold = board.registerNewObjective("game_Money", "dummy");
		board.registerNewObjective("game_location", "dummy");
		board.registerNewObjective("game_askRent", "dummy");
		board.registerNewObjective("game_mortgage", "dummy");
		board.registerNewObjective("game_buyback", "dummy");
		board.registerNewObjective("game_useCard", "dummy");
		board.registerNewObjective("game_pay", "dummy");
		board.registerNewObjective("game_house", "dummy");
		board.registerNewObjective("game_hotel", "dummy");
		board.registerNewObjective("game_jail", "dummy");
		board.registerNewObjective("game_card", "dummy");
		board.registerNewObjective("game_diceRoll", "dummy");
		board.registerNewObjective("game_Properties", "dummy");
		gold.setDisplayName("Money");
		gold.setDisplaySlot(DisplaySlot.SIDEBAR);	
		Bukkit.getServer().broadcastMessage("Game initialized!");
		
	}
	
	public static void resetScoreboard(){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		for(OfflinePlayer player : board.getPlayers())
		{
		    	board.resetScores(player);
		}
		Objective obj = board.getObjective("game_isPlaying");
		obj.unregister();
		obj = board.getObjective("game_isTurn");
		obj.unregister();
		obj = board.getObjective("game_Money");
		obj.unregister();
		obj = board.getObjective("game_location");
		obj.unregister();
		obj = board.getObjective("game_askRent");
		obj.unregister();
		obj = board.getObjective("game_mortgage");
		obj.unregister();
		obj = board.getObjective("game_buyback");
		obj.unregister();
		obj = board.getObjective("game_useCard");
		obj.unregister();
		obj = board.getObjective("game_pay");
		obj.unregister();
		obj = board.getObjective("game_house");
		obj.unregister();
		obj = board.getObjective("game_hotel");
		obj.unregister();
		obj = board.getObjective("game_jail");
		obj.unregister();
		obj = board.getObjective("game_card");
		obj.unregister();
		obj = board.getObjective("game_diceRoll");
		obj.unregister();
		obj = board.getObjective("game_Properties");
		obj.unregister();
		obj = board.getObjective("game_inJail");
		obj.unregister();
		Team t1 = board.getTeam("Zombie");
		t1.unregister();
		t1 = board.getTeam("Skeleton");
		t1.unregister();
		t1 = board.getTeam("Creeper");
		t1.unregister();
		t1 = board.getTeam("Player");
		t1.unregister();
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "GAME RESET!");
		initScoreboard();
	}

}
