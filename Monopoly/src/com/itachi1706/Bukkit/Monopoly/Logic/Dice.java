package com.itachi1706.Bukkit.Monopoly.Logic;

import com.itachi1706.Bukkit.Monopoly.Objects.GameProperties;
import com.itachi1706.Bukkit.Monopoly.util.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class Dice {

    public static int diceTotal;

    //Rolls the dice for a player
    public static void rollDice(Player p){
        //Check if player can roll dice
        if(PlayerTurns.checkTurn(p) == true){
            //Rolls Dice
            if(checkDiceRoll(p) == true){
                int diceOne = 1 + (int) (Math.random() * ((6-1)+1));
                int diceTwo = 1 + (int) (Math.random() * ((6-1)+1));
                diceTotal = diceOne + diceTwo;
                if (diceOne == diceTwo){
                    //Doubles
                    p.sendMessage(ChatColor.AQUA + "You have rolled a double!");
                    if (ScoreboardHelper.getScore("game_inJail", p) > 0){
                        //In Jail get out
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has rolled a double " + ChatColor.AQUA + diceOne + ChatColor.BLUE + " ,got out of Jail and moved " + ChatColor.AQUA + (diceOne + diceTwo) + ChatColor.BLUE + " spaces!");
                    } else {
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has rolled a double " + ChatColor.AQUA + diceOne + ChatColor.BLUE + " and moved " + ChatColor.AQUA + (diceOne + diceTwo) + ChatColor.BLUE + " spaces!");
                    }
                    int newLocation = PlayerLocation.moveToLocationDice(p, diceOne + diceTwo);
                    GameProperties gp = MainGameLogic.propertyList.get(newLocation);
                    p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
                    postProcess(gp, p);
                } else {
                    if (ScoreboardHelper.getScore("game_inJail", p) > 0){
                        //In Jail cannot go out
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has rolled a " + ChatColor.AQUA + diceOne + ChatColor.BLUE + " and " + ChatColor.AQUA + diceTwo + ChatColor.BLUE + " in jail!");
                        Jailing.inJail(p, (diceOne + diceTwo));
                        endDiceRoll(p);
                    } else {
                        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " has rolled a " + ChatColor.AQUA + diceOne + ChatColor.BLUE + " and " + ChatColor.AQUA + diceTwo + ChatColor.BLUE + " and moved " + ChatColor.AQUA + (diceOne + diceTwo) + ChatColor.BLUE + " spaces!");
                        int newLocation = PlayerLocation.moveToLocationDice(p, diceOne + diceTwo);
                        GameProperties gp = MainGameLogic.propertyList.get(newLocation);
                        p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.GOLD + gp.getName());
                        postProcess(gp, p);
                        endDiceRoll(p);
                    }
                }
            } else {
                p.sendMessage(ChatColor.BLUE + "You have rolled already. Either handle your properties or end your turn with " + ChatColor.GOLD + "end turn");
            }
        } else {
            p.sendMessage(ChatColor.BLUE + "It is not your turn yet.");
        }
    }

    //Check if theres a Dice Roll
    public static boolean checkDiceRoll(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective("game_diceRoll");
        Score score = objective.getScore(p);
        if (score.getScore() == 1){
            return true;
        } else
            return false;
    }

    //Does the end dice roll method
    public static void endDiceRoll(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objective = board.getObjective("game_diceRoll");
        Score score = objective.getScore(p);
        score.setScore(0);
    }

    public static int diceRoll(){
        return diceTotal;
    }

    public static void postProcess(GameProperties gp, Player p){
        if (gp.getType().contains("Null")){
            //Null properties
            if (gp.getId() == 0){
                //Touch on GO, DOUBLES
                PlayerLocation.touchGo(p);
            } else if (gp.getId() == 4){
                //Income tax
                if (MoneyHandling.checkIfMoney(gp.getCost(), p)){
                    p.sendMessage(ChatColor.AQUA + "100" + ChatColor.BLUE + " has been taken from you due to tax!");
                    MoneyHandling.deductsMoney(gp.getCost(), p);
                } else {
                    p.sendMessage(ChatColor.AQUA + "100" + ChatColor.BLUE + " has been taken from you due to tax!");
                    MoneyHandling.deductsMoney(gp.getCost(), p);
                    if(MoneyHandling.checkProperties(p) == true){
                        p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
                    } else {
                        MainGameLogic.surrender(p);
                    }

                }
            } else if (gp.getId() == 30){
                //Go to Jail
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_BLUE + " has been sent to jail!");
                p.sendMessage(ChatColor.BLUE + "Move to " + ChatColor.DARK_BLUE + "Jail");
                Jailing.toJail(p);
            } else if (gp.getId() == 38){
                //Super Tax
                if (MoneyHandling.checkIfMoney(gp.getCost(), p)){
                    //Deducts
                    p.sendMessage(ChatColor.AQUA + "75" + ChatColor.BLUE + " has been taken from you due to tax!");
                    MoneyHandling.deductsMoney(gp.getCost(), p);
                } else {
                    p.sendMessage(ChatColor.AQUA + "75" + ChatColor.BLUE + " has been taken from you due to tax!");
                    MoneyHandling.deductsMoney(gp.getCost(), p);
                    if(MoneyHandling.checkProperties(p) == true){
                        p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
                    } else {
                        MainGameLogic.surrender(p);
                    }

                }
            }
        } else if (gp.getType().contains("Chance")){
            //Chance card draw
            ChanceDraw.drawChanceCard(p);
        } else if (gp.getType().contains("CC")){
            //Community Chest Card draw
            CommChest.drawCommChestCard(p);
        } else {
            if (PropertiesStuff.checkPropertyPurchased(gp) == true){
                //Pay rent
                if (gp.getType().contains("Property")){
                    PropertiesStuff.payPropertyRent(p, gp);
                } else if (gp.getType().contains("Train")){
                    PropertiesStuff.payTrainRent(p, gp);
                } else if (gp.getType().contains("Utility")){
                    PropertiesStuff.payUtilityRent(p, gp);
                }
            } else {
                //Option to buy property
                p.sendMessage(ChatColor.GOLD + gp.getName() + ChatColor.BLUE + " costs " + ChatColor.AQUA + gp.getCost() + ChatColor.BLUE + ". Reply 'buy' or 'dont buy'");
                ScoreboardManager sm = Bukkit.getServer().getScoreboardManager();
                Scoreboard sb = sm.getMainScoreboard();
                Objective o = sb.getObjective("game_askRent");
                Score sc = o.getScore(p);
                sc.setScore(1);
            }
        }
    }

}
