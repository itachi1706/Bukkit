package io.github.itachi1706.Monopoly.Logic;

import io.github.itachi1706.Monopoly.Objects.Chance;
import io.github.itachi1706.Monopoly.Objects.GameProperties;
import io.github.itachi1706.Monopoly.util.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChanceDraw {
	
	public static void drawChanceCard(Player p){
		int draw = (int) (Math.random()*13);
		Chance card = MainGameLogic.chanceList.get(draw);
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " have drawn a Chance card! " + ChatColor.AQUA + card.getName());
		switch (draw){
		case 0:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has moved to " + ChatColor.AQUA + "Pass Go!");
			goToLocation(p, 0);
			break;
		case 1:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has moved to " + ChatColor.AQUA + "Illinois Ave!");
			goToLocation(p, 24);
			break;
		case 2:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has moved to " + ChatColor.AQUA + "St. Charles Place!");
			goToLocation(p, 11);
			break;
		case 3:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "50!");
			getMoney(p, 50);
			break;
		case 4:
			Jailing.getJailCard(p);
			break;
		case 5:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been moved back " + ChatColor.AQUA + "3 spaces!");
			goBackSpace(p, 3);
			break;
		case 6:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been sent to " + ChatColor.AQUA + "JAIL!");
			Jailing.toJail(p);
			break;
		case 7:
			int cost = checkHouses(p);
			takeMoney(p, cost);
			break;
		case 8:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has paid " + ChatColor.AQUA + "15!");
			takeMoney(p, 15);
			break;
		case 9:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has moved to " + ChatColor.AQUA + "Reading Railroad!");
			goToLocation(p, 5);
			break;
		case 10:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has moved to " + ChatColor.AQUA + "Broadwalk!");
			goToLocation(p, 39);
			break;
		case 11:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "150!");
			getMoney(p, 150);
			break;
		case 12:
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "100!");
			getMoney(p, 100);
			break;
		}
	}
	
	public static void getMoney(Player p, int gold){
		ScoreboardHelper.addScore("game_Money", p, gold);
		p.sendMessage(ChatColor.BLUE + "You have been given " + ChatColor.GOLD + gold);
	}
	
	public static void takeMoney(Player p, int gold){
		ScoreboardHelper.removeScore("game_Money", p, gold);
		p.sendMessage(ChatColor.BLUE + "You have paid " + ChatColor.GOLD + gold);
	}
	
	public static void goToLocation(Player p, int location){
		int currLocation = PlayerLocation.getLocation(p);
		if (currLocation > location){
			//Passed Go
			PlayerLocation.passGo(p);
		}
		GameProperties gp = MainGameLogic.propertyList.get(location);
		p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
		PlayerLocation.setNewLocation(p, location);
		if (location == 0){
			PlayerLocation.touchGo(p);
		}
	}
	
	public static int checkHouses(Player p){
		int house = ScoreboardHelper.getScore("game_house", p);
		int hotel = ScoreboardHelper.getScore("game_hotel", p);
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has " + ChatColor.AQUA + house + ChatColor.BLUE + " houses and " + ChatColor.AQUA + hotel + ChatColor.BLUE + " hotels and will be charged " + ChatColor.DARK_GREEN + ((house * 25) + (hotel * 100)));
		return ((house * 25) + (hotel * 100));
	}
	
	public static void goBackSpace(Player p, int times){
		int currLocation = PlayerLocation.getLocation(p);
		int endLocation = currLocation - times;
		if (endLocation < 0)
			endLocation = endLocation + 40;
		GameProperties gp = MainGameLogic.propertyList.get(endLocation);
		p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
		PlayerLocation.setNewLocation(p, endLocation);
		if (endLocation == 0){
			PlayerLocation.touchGo(p);
		}
	}

}
