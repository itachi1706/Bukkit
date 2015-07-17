package com.itachi1706.Bukkit.Monopoly;

import com.itachi1706.Bukkit.Monopoly.Logic.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly
 */
public class ChatCommandControl implements Listener {

    private static ScoreboardManager sm = Bukkit.getServer().getScoreboardManager();
    private static Scoreboard sb = sm.getMainScoreboard();

    @EventHandler(priority= EventPriority.HIGHEST)
    public void chatCommands(AsyncPlayerChatEvent e){
        if (Monopoly.configGame.getBoolean("start") == true){
            Player sender = e.getPlayer();
            String msg = e.getMessage().toLowerCase();
            if (msg.equals("roll dice")){
                //Rolls dice
                Dice.rollDice(sender);
            } else if (msg.equals("pay")){
                e.setCancelled(true);
                Objective o = sb.getObjective("game_pay");
                Score sc = o.getScore(sender);
                if (sc.getScore() == 1){
                    //Pays to get out of jail free
                    Jailing.payFine(sender);
                } else {
                    e.setCancelled(true);
                    sender.sendMessage(ChatColor.RED + "You are not allowed to use this command now");
                }
            } else if (msg.equals("buy")){
                //Buys property
                e.setCancelled(true);
                Objective o = sb.getObjective("game_askRent");
                Score sc = o.getScore(sender);
                if (sc.getScore() == 1){
                    //Buy property
                    boolean check = PropertiesStuff.buyProperty(sender);
                    if (check == true){
                        sc.setScore(0);
                    }
                } else {
                    e.setCancelled(true);
                    sender.sendMessage(ChatColor.RED + "You are not allowed to use this command now");
                }
            } else if (msg.equals("dont buy")){
                //Dont buy property
                e.setCancelled(true);
                Objective o = sb.getObjective("game_askRent");
                Score sc = o.getScore(sender);
                if (sc.getScore() == 1){
                    //Don't Buy property
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "You did not buy the property.");
                    sc.setScore(0);
                } else {
                    e.setCancelled(true);
                    sender.sendMessage(ChatColor.RED + "You are not allowed to use this command now");
                }
            } else if (msg.equals("list property") && msg.length() == 13){
                //List own property
                PropertiesStuff.listOwnProperty(sender);
                e.setCancelled(true);
            } else if (msg.contains("list property")){
                //List specific player property
                String[] args = msg.split(" ");
                PropertiesStuff.listProperty(sender, args);
                e.setCancelled(true);
            } else if (msg.contains("list rent")){
                //List rent of property
                String[] args = msg.split(" ");
                PropertiesStuff.listRent(sender, args);
                e.setCancelled(true);
            } else if (msg.contains("send") && msg.contains("gold")){
                //Sends gold to player
                String[] args = msg.split(" ");
                SendingItems.sendGold(sender, args);
                e.setCancelled(true);
            } else if (msg.contains("send") && msg.contains("property")){
                //Sends property to player
                String[] args = msg.split(" ");
                SendingItems.sendProperty(sender, args);
                e.setCancelled(true);
            } else if (msg.equals("use jail card")){
                e.setCancelled(true);
                Objective o = sb.getObjective("game_useCard");
                Score sc = o.getScore(sender);
                if (sc.getScore() == 1){
                    //Use Get Out of Jail free card
                    Jailing.useCard(sender);
                } else {
                    e.setCancelled(true);
                    sender.sendMessage(ChatColor.RED + "You are not allowed to use this command now");
                }
            } else if (msg.equals("yes")){
                //Do Yes action and validation
                e.setCancelled(true);
                Objective o = sb.getObjective("game_mortgage");
                Score sc = o.getScore(sender);
                Objective o2 = sb.getObjective("game_buyback");
                Score sc2 = o2.getScore(sender);
                if (sc.getScore() == 1){
                    //mortgage
                    boolean check = Mortgaging.confirmMortgage(sender);;
                    if (check == true){
                        sc.setScore(0);
                    }
                } else if (sc2.getScore() == 1) {
                    //buyback
                    boolean check = Unmortgaging.confirmUnMortgage(sender);
                    if (check == true){
                        sc2.setScore(0);
                    }
                } else {
                    e.setCancelled(true);
                    sender.sendMessage(ChatColor.RED + "You are not allowed to use this command now");
                }
            } else if (msg.equals("no")){
                //Do no action and validation
                e.setCancelled(true);
                Objective o = sb.getObjective("game_mortgage");
                Score sc = o.getScore(sender);
                Objective o2 = sb.getObjective("game_buyback");
                Score sc2 = o2.getScore(sender);
                if (sc.getScore() == 1){
                    //Dont mortgage
                    sc.setScore(0);
                } else if (sc2.getScore() == 1) {
                    //Dont buyback
                    sc2.setScore(0);
                } else {
                    e.setCancelled(true);
                    sender.sendMessage(ChatColor.RED + "You are not allowed to use this command now");
                }
            } else if (msg.equals("end turn")){
                //Do end turn sequence
                PlayerTurns.endTurn(sender);
            } else if (msg.contains("mortgage")){
                //Mortgages property
                String[] args = msg.split(" ");
                Mortgaging.askMortgage(sender, args);
                e.setCancelled(true);
            } else if (msg.contains("buyback")){
                //Buys back property
                String[] args = msg.split(" ");
                Unmortgaging.askUnMortgage(sender, args);
                e.setCancelled(true);
            }

        }
    }

}
