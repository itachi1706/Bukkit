package io.github.itachi1706.Monopoly.Logic;

import io.github.itachi1706.Monopoly.Monopoly;
import io.github.itachi1706.Monopoly.Objects.Chance;
import io.github.itachi1706.Monopoly.Objects.CommunityChest;
import io.github.itachi1706.Monopoly.Objects.GameProperties;
import io.github.itachi1706.Monopoly.util.ScoreboardHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import lib.PatPeter.SQLibrary.Database;

public class MainGameLogic {

	public static ArrayList<GameProperties> propertyList = new ArrayList<GameProperties>();
	public static ArrayList<Chance> chanceList = new ArrayList<Chance>();
	public static ArrayList<CommunityChest> communityChestList = new ArrayList<CommunityChest>();
	public static ArrayList<String> players = new ArrayList<String>();
	public static ArrayList<String> owned = new ArrayList<String>();
	
	//Initializing the game
	public static void initGameLogic(){
		Database sql = Monopoly.sql;
		//Initializing the properties
		try {
			ResultSet results = sql.query("SELECT id,name,type,cost,initRent,oneHou,twoHou,threeHou,fourHou,hotel,houseCost,mortgage,buyback FROM Properties");
			while (results.next()){
				GameProperties gp = new GameProperties(results.getInt("id"), results.getString("name"), results.getString("type"), results.getInt("cost"), results.getInt("initRent"), results.getInt("oneHou"), results.getInt("twoHou"), results.getInt("threeHou"), results.getInt("fourHou"), results.getInt("hotel"), results.getInt("houseCost"), results.getInt("mortgage"), results.getInt("buyback"));
				propertyList.add(gp);
			}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Initializing the chance cards
		try {
			ResultSet results = sql.query("SELECT id,name FROM Chance");
			while (results.next()){
				Chance c = new Chance(results.getInt("id"), results.getString("name"));
				chanceList.add(c);
			}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Initializing the community chest cards
		try {
			ResultSet results = sql.query("SELECT id,name FROM CommunityChest");
			while (results.next()){
				CommunityChest cc = new CommunityChest(results.getInt("id"), results.getString("name"));
				communityChestList.add(cc);
			}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < propertyList.size(); i++){
			GameProperties gp = propertyList.get(i);
			owned.add(Monopoly.playerProperties.getString(gp.getName() + ".Owned"));
		}
		if (Monopoly.configGame.getBoolean("start") == false){
			enterPlayer();
			if (players.size() < 1){
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "Insufficient Players to start game");
			} else {
				Monopoly.configGame.set("start", true);
				Monopoly.configGame.set("players", players.size());
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "GAME STARTS NOW!");
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Note that due to the way the plugin is currently coded, buying hotels/houses automatically is impossible. Notify an admin if you wish to purchase. Admins, please refer to page 7-9 of instructions book for instructions on updating.");
				PlayerTurns.newTurn();
			}
		}
	}
	
	public static void enterPlayer(){
		Player[] playerList = Bukkit.getServer().getOnlinePlayers();
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Objective objec = board.getObjective("game_isPlaying");
		for (int i = 0; i < playerList.length; i++){
			Player p = playerList[i];
			Score sc = objec.getScore(p);
			if (sc.getScore()==1){
				players.add(p.getName());
			}			
		}
	}
	//DEBUG
	public static void printArrays(){
		for (int i = 0; i < propertyList.size(); i++){
			GameProperties gp = propertyList.get(i);
			System.out.println(gp.toString());
		}
		for (int i = 0; i < chanceList.size(); i++){
			Chance gp = chanceList.get(i);
			System.out.println(gp.toString());
		}
		for (int i = 0; i < communityChestList.size(); i++){
			CommunityChest gp = communityChestList.get(i);
			System.out.println(gp.toString());
		}
	}
	
	public static void surrender(Player p){
		Bukkit.getServer().broadcastMessage(ChatColor.AQUA + p.getName() + ChatColor.GOLD + " ran out of gold and is out of the game!");
		for (int i = 0; i < players.size(); i++){
			String s = players.get(i);
			if (s.equals(p.getName())){
				players.remove(i);
				Monopoly.configGame.set("players", Monopoly.configGame.getInt("players") - 1);
				ScoreboardManager m = Bukkit.getServer().getScoreboardManager();
				Scoreboard sc = m.getMainScoreboard();
				Objective o = sc.getObjective("game_isPlaying");
				Score sco = o.getScore(p);
				sco.setScore(0);
				p.sendMessage(ChatColor.RED + "You are out of the game!");
				if (checkEndGame() == true){
					endGame();
				}
			}
		}
	}
	
	public static boolean checkEndGame(){
		if (players.size() == 0){
			return true;
		} else if(players.size() == 1){
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + players.get(0) + " wins the game!");
			return true;
		}
		for (int i = 0; i < players.size(); i++){
			Player p = Bukkit.getServer().getPlayer(players.get(i));
			if (p == null){
				players.remove(i);
				Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN + "As " + players.get(i) + " is not online, player will be removed!");
			} else {
				int game = ScoreboardHelper.getScore("game_isPlaying", p);
				if (game == 1){
					return false;
				}
			}
			
		}
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "An error occured. Please inform the plugin developer on the steps you did so that he can recreate the problem. Game resetting Just in case");
		return true;
	}
	
	public static void endGame(){
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "Game ended! Resetting game...");
		Monopoly.configGame.set("pause", false);
		Monopoly.configGame.set("start", false);
		Monopoly.configGame.set("players", 0);
		ScoreboardHelper.resetScoreboard();
		Monopoly.initPropertiesFile();
		Player[] player = Bukkit.getServer().getOnlinePlayers();
		for (int i = 0; i < player.length; i++){
			player[i].getInventory().clear();
			player[i].getEquipment().clear();
			player[i].kickPlayer("Game ended.");
			player[i].setGameMode(GameMode.ADVENTURE);
		}
	}
}
