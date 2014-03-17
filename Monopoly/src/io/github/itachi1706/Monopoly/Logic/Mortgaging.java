package io.github.itachi1706.Monopoly.Logic;

import io.github.itachi1706.Monopoly.Monopoly;
import io.github.itachi1706.Monopoly.Objects.GameProperties;
import io.github.itachi1706.Monopoly.util.ScoreboardHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Mortgaging {

	@SuppressWarnings("unused")
	private static String tmpPlayer;
	private static String tmpProperty;
	
	public static void askMortgage(Player p, String[] args){
		if (PlayerTurns.checkTurn(p) == true){
			tmpPlayer = p.getName();
			String property = "";
			int index = 0;
			int lol = args.length-1;
			GameProperties gp = null;
			for (int i = 1; i < args.length - 1; i++){
				property = property + args[i] + " ";
			}
			property = property + args[lol];
			property = property.toLowerCase();
			for (int i = 0; i < MainGameLogic.propertyList.size(); i++){
				gp = MainGameLogic.propertyList.get(i);
				String name = gp.getName().toLowerCase();
				if (name.contains(property)){
					index = i;
					break;
				}
			}
			String owner = MainGameLogic.owned.get(index).toLowerCase();
			String name = p.getName().toLowerCase();
			if (name.equals(owner)){
				//Ask to mortgage
				p.sendMessage(ChatColor.BLUE + "Are you sure you want to mortgage " + ChatColor.GOLD + gp.getName() + ChatColor.BLUE + "? Answer Yes or No");
				tmpProperty = gp.getName();
				ScoreboardHelper.addScore("game_mortgage", p, 1);
			} else {
				p.sendMessage(ChatColor.RED + "This property is not owned by you.");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Please wait for your turn before mortgaging");
		}
		
	}
	
	public static boolean confirmMortgage(Player p){
		if (PlayerTurns.checkTurn(p) == true){
			GameProperties gp = null;
			for (int i = 0; i < MainGameLogic.propertyList.size(); i++){
				gp = MainGameLogic.propertyList.get(i);
				if (gp.getName().contains(tmpProperty)){
					break;
				}
			}
			Monopoly.playerProperties.set(gp.getName() + ".Mortgaged", true);
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has mortgaged " + ChatColor.AQUA + gp.getName());
			ScoreboardHelper.removeScore("game_Properties", p, 1);
			ScoreboardHelper.addScore("game_Money", p, gp.getMortgage());
			return true;
		} else {
			p.sendMessage(ChatColor.RED + "Please wait for your turn before mortgaging");
			return false;
		}
		
	}
	
	
}
