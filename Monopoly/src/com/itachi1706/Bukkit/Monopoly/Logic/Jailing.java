package com.itachi1706.Bukkit.Monopoly.Logic;

import com.itachi1706.Bukkit.Monopoly.Objects.GameProperties;
import com.itachi1706.Bukkit.Monopoly.util.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class Jailing {

    private static int diceRoll = 0;

    @SuppressWarnings("deprecation")
    public static void goToJail(Player p, String args){
        Player target = Bukkit.getServer().getPlayer(args);
        if (target == null){
            p.sendMessage(ChatColor.RED + "Player is not online");
        } else {
            target.sendMessage(ChatColor.RED + "You have been sent to jail!");
            ScoreboardHelper.addScore("game_inJail", target, 1);
            PlayerLocation.setNewLocation(target, 10);
        }
    }

    public static void toJail(Player p){
        p.sendMessage(ChatColor.RED + "You have been sent to jail!");
        ScoreboardHelper.addScore("game_inJail", p, 1);
        PlayerLocation.setNewLocation(p, 10);
    }

    public static void inJail(Player p, int i){
        ScoreboardHelper.addScore("game_jail", p, 1);
        diceRoll = i;
        int jail = ScoreboardHelper.getScore("game_jail", p);
        if (jail > 2){
            //Exceeded jail term
            p.sendMessage(ChatColor.RED + "You have been in jail for too long! Force paying " + ChatColor.AQUA + "50 " + ChatColor.RED + "gold!");
            ScoreboardHelper.removeScore("game_Money", p, 50);
            ScoreboardHelper.setScore("game_inJail", p, 0);
            ScoreboardHelper.setScore("game_jail", p, 0);
            int newLocation = PlayerLocation.moveToLocationDice(p, i);
            GameProperties gp = MainGameLogic.propertyList.get(newLocation);
            p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
            Dice.postProcess(gp, p);
            Dice.endDiceRoll(p);
        } else {
            //Do you want to pay $50 fine or use jail card
            int jailCard = ScoreboardHelper.getScore("game_card", p);
            if (jailCard > 0){
                //Has Jail Card
                p.sendMessage(ChatColor.BLUE + "Do you want to use a Get Out of Jail Free card or pay 50 gold? Do 'use jail card' or 'pay' if you wish to. Else just 'end turn'");
                ScoreboardHelper.setScore("game_useCard", p, 1);
                ScoreboardHelper.setScore("game_pay", p, 1);
            } else {
                //No Jail card
                p.sendMessage(ChatColor.BLUE + "Do you want to pay 50 gold? Do 'pay' if you wish to. Else just 'end turn'");
                ScoreboardHelper.setScore("game_pay", p, 1);
            }
        }
    }

    public static void useCard(Player p){
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + p.getDisplayName() + ChatColor.GOLD + " used a Get Out of Jail Free Card and moved " + ChatColor.GREEN + diceRoll + ChatColor.GOLD + " spaces!");
        ScoreboardHelper.removeScore("game_Card", p, 1);
        ScoreboardHelper.setScore("game_inJail", p, 0);
        ScoreboardHelper.setScore("game_jail", p, 0);
        int newLocation = PlayerLocation.moveToLocationDice(p, diceRoll);
        GameProperties gp = MainGameLogic.propertyList.get(newLocation);
        p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
        Dice.postProcess(gp, p);
        Dice.endDiceRoll(p);
    }

    public static void payFine(Player p){
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + p.getDisplayName() + ChatColor.GOLD + " paid 50 gold and moved " + ChatColor.GREEN + diceRoll + ChatColor.GOLD + " spaces!");
        ScoreboardHelper.removeScore("game_Money", p, 50);
        ScoreboardHelper.setScore("game_inJail", p, 0);
        ScoreboardHelper.setScore("game_jail", p, 0);
        int newLocation = PlayerLocation.moveToLocationDice(p, diceRoll);
        GameProperties gp = MainGameLogic.propertyList.get(newLocation);
        p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
        Dice.postProcess(gp, p);
        Dice.endDiceRoll(p);
    }

    public static void getJailCard(Player p){
        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + p.getDisplayName() + ChatColor.GOLD + " has been given a Get Out of Jail Free card!");
        ScoreboardHelper.addScore("game_Card", p, 1);
    }

}
