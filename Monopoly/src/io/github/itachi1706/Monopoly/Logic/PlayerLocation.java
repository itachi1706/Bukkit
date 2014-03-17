package io.github.itachi1706.Monopoly.Logic;

import io.github.itachi1706.Monopoly.Monopoly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PlayerLocation {
	
	//Move to location based on dice value
	public static int moveToLocationDice(Player p, int dice){
		int currentLocation = getLocation(p);
		int endLocation = currentLocation + dice;
		if (endLocation > 39){
			endLocation = endLocation - 40;
			passGo(p);
		}
		setNewLocation(p, endLocation);
		return endLocation;
	}

	//Get current location of the player
	public static int getLocation(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_location");
		Score score = objective.getScore(p);
		return score.getScore();
	}
	
	//Sets new location of player
	public static void setNewLocation(Player p, int newLocation){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_location");
		Score score = objective.getScore(p);
		score.setScore(newLocation);
	}
	
	//Checks if the player has passed Go
	public static void checkPassGo(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_location");
		Score score = objective.getScore(p);
		int currLocation = score.getScore();
		if (currLocation == 0){
			passGo(p);
		}
	}
	
	//Gives player pass go money
	public static void passGo(Player p){
		int passGoValue = Monopoly.configGame.getInt("passGo");
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_Money");
		Score score = objective.getScore(p);
		score.setScore(score.getScore() + passGoValue);
		p.sendMessage(ChatColor.GOLD + "You have been given " + passGoValue + " for passing GO!");
	}
	
	//Touches go give player
	public static void touchGo(Player p){
		int passGoValue = Monopoly.configGame.getInt("passGo");
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_Money");
		Score score = objective.getScore(p);
		score.setScore(score.getScore() + passGoValue);
		p.sendMessage(ChatColor.GOLD + "You have been given an extra " + passGoValue + " for landing on GO!");
	}

}
