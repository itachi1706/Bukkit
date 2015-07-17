package com.itachi1706.Bukkit.Monopoly.Logic;

import com.itachi1706.Bukkit.Monopoly.Monopoly;
import com.itachi1706.Bukkit.Monopoly.Objects.GameProperties;
import com.itachi1706.Bukkit.Monopoly.util.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class Unmortgaging {

    @SuppressWarnings("unused")
    private static String tmpPlayer;
    private static String tmpProperty;

    public static void askUnMortgage(Player p, String[] args){
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
            boolean mortgaged = Monopoly.playerProperties.getBoolean(gp.getName() + ".Mortgaged");
            String owner = MainGameLogic.owned.get(index);
            if (p.getName().contains(owner) && mortgaged == true){
                if (mortgaged == true){
                    //Ask to mortgage
                    p.sendMessage(ChatColor.BLUE + "Are you sure you want to buyback " + ChatColor.GOLD + gp.getName() + ChatColor.BLUE + "? Answer Yes or No");
                    tmpProperty = gp.getName();
                    ScoreboardHelper.addScore("game_buyback", p, 1);
                } else {
                    p.sendMessage(ChatColor.RED + "Property is not mortgaged.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "This property is not owned by you.");
            }
        } else {
            p.sendMessage(ChatColor.RED + "Please wait for your turn before buyback");
        }

    }

    public static boolean confirmUnMortgage(Player p){
        if (PlayerTurns.checkTurn(p) == true){
            GameProperties gp = null;
            for (int i = 0; i < MainGameLogic.propertyList.size(); i++){
                gp = MainGameLogic.propertyList.get(i);
                if (gp.getName().contains(tmpProperty)){
                    break;
                }
            }
            Monopoly.playerProperties.set(gp.getName() + ".Mortgaged", false);
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has bought back " + ChatColor.AQUA + gp.getName());
            ScoreboardHelper.addScore("game_Properties", p, 1);
            ScoreboardHelper.removeScore("game_Money", p, gp.getBuyback());
            return true;
        } else {
            p.sendMessage(ChatColor.RED + "Please wait for your turn before buyback");
            return false;
        }

    }

}
