package io.github.itachi1706.Monopoly.Logic;

import io.github.itachi1706.Monopoly.Monopoly;
import io.github.itachi1706.Monopoly.util.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PlayerTurns {

	//Checks if its the player's turn
	public static boolean checkTurn(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_isTurn");
		Score score = objective.getScore(p);
		if (score.getScore() == 1){
			return true;
		} else {
			return false;
		}
	}
	
	//When user says "end turn"
	public static void endTurn(Player p){
		if (checkTurn(p) == true){
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getMainScoreboard();
			Objective objective = board.getObjective("game_isTurn");
			Score score = objective.getScore(p);
			Objective obj = board.getObjective("game_diceRoll");
			Score sc1 = obj.getScore(p);
			Objective obj2 = board.getObjective("game_Money");
			Score sc2 = obj2.getScore(p);
			if (sc1.getScore() == 1){
				p.sendMessage(ChatColor.BLUE + "You still have a dice roll remaining. Roll it before ending or do " + ChatColor.GREEN + "/monopoly skip");
			} else if (sc2.getScore() >= 0) {
				ScoreboardHelper.setScore("game_mortgage", p, 0);
				ScoreboardHelper.setScore("game_buyback", p, 0);
				ScoreboardHelper.setScore("game_askRent", p, 0);
				ScoreboardHelper.setScore("game_useCard", p, 0);
				ScoreboardHelper.setScore("game_pay", p, 0);
				int currentPlayer = -1, newPlayer;
				for (int i = 0; i < MainGameLogic.players.size(); i++){
					String play = MainGameLogic.players.get(i);
					if (play.equals(p.getName())){
						currentPlayer = i;
						break;
					}
				}
				if (currentPlayer == (Monopoly.configGame.getInt("players") - 1)){
					newPlayer = 0;
				} else {
					newPlayer = currentPlayer + 1;
				}
				score.setScore(0);
				changeTurn(newPlayer);
			} else {
				p.sendMessage(ChatColor.BLUE + "Please sell off some properties. Insufficient gold.");
			}
		} else {
			p.sendMessage(ChatColor.BLUE + "It is currently not your turn!");
		}
	}
	
	//Changing player turn
	public static void changeTurn(int newPlayer){
		String play = MainGameLogic.players.get(newPlayer);
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_isTurn");
		Objective objective1 = board.getObjective("game_diceRoll");
		Player p = null;
		Player[] playerArray = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < playerArray.length; i++){
			p = playerArray[i];
			if (p.getName().equals(play)){
				break;
			}
		}
		Score score = objective.getScore(p);
		score.setScore(1);
		score = objective1.getScore(p);
		score.setScore(1);
		p.sendMessage(ChatColor.BLUE + "Roll the dice!");
		Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "Turn ended. Currently " + ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " turn!");
	}
	
	//on /monopoly skip command
	public static void forceEndTurn(){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objective = board.getObjective("game_isTurn");
		Player p = null;
		Player[] playerArray = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < playerArray.length; i++){
			p = playerArray[i];
			Score score = objective.getScore(p);
			if (score.getScore() == 1){
				score.setScore(0);
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_PURPLE + "'s turn has been forcibly skipped!");
				if (i==(Monopoly.configGame.getInt("players") - 1)){
					changeTurn(0);
				} else 
				changeTurn(i+1);
				break;
			}
			
		}
	}
	
	public static void newTurn(){
		int hi = (int) (Math.random() * (Monopoly.configGame.getInt("players")));
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + MainGameLogic.players.get(hi) + " will start the game!");
		changeTurn(hi);
	}
	
}
