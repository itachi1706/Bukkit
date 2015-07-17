package com.itachi1706.Bukkit.Monopoly.Logic;

import com.itachi1706.Bukkit.Monopoly.Objects.CommunityChest;
import com.itachi1706.Bukkit.Monopoly.Objects.GameProperties;
import com.itachi1706.Bukkit.Monopoly.util.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class CommChest {

    public static void drawCommChestCard(Player p){
        int draw = (int) (Math.random()*15);
        CommunityChest card = MainGameLogic.communityChestList.get(draw);
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " have drawn a Community Chest card! " + ChatColor.AQUA + card.getName());
        switch (draw){
            case 0:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has moved to " + ChatColor.AQUA + "Pass Go!");
                goToLocation(p, 0);
                break;
            case 1:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "200!");
                getMoney(p, 200);
                break;
            case 2:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has paid " + ChatColor.AQUA + "50!");
                takeMoney(p, 50);
                break;
            case 3:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "50!");
                getMoney(p, 50);
                break;
            case 4:
                Jailing.getJailCard(p);
                break;
            case 5:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been sent to " + ChatColor.AQUA + "JAIL!");
                Jailing.toJail(p);
                break;
            case 6:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "100!");
                getMoney(p, 100);
                break;
            case 7:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "20!");
                getMoney(p, 20);
                break;
            case 8:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "100!");
                getMoney(p, 100);
                break;
            case 9:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has paid " + ChatColor.AQUA + "100!");
                takeMoney(p, 100);
                break;
            case 10:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has paid " + ChatColor.AQUA + "150!");
                takeMoney(p, 150);
                break;
            case 11:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "25!");
                getMoney(p, 25);
                break;
            case 12:
                int cost = checkHouses(p);
                takeMoney(p, cost);
                break;
            case 13:
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has been given " + ChatColor.AQUA + "10!");
                getMoney(p, 10);
                break;
            case 14:
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
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has " + ChatColor.AQUA + house + ChatColor.BLUE + " houses and " + ChatColor.AQUA + hotel + ChatColor.BLUE + " hotels and will be charged " + ChatColor.DARK_GREEN + ((house * 40) + (hotel * 100)));
        return ((house * 40) + (hotel * 100));
    }

}
