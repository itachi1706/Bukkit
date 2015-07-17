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
public class SendingItems {

    @SuppressWarnings("deprecation")
    public static void sendGold(Player p, String[] args){
        Player target = Bukkit.getServer().getPlayer(args[1]);
        if (target == null){
            p.sendMessage(ChatColor.RED + "Player is not online");
        } else {
            if (ScoreboardHelper.getScore("game_isPlaying", target) == 0){
                p.sendMessage(ChatColor.RED + "Player is not in game");
            } else {
                int gold = Integer.parseInt(args[3]);
                if (MoneyHandling.checkIfMoney(gold, p)){
                    //Deducts
                    ScoreboardHelper.removeScore("game_Money", p, gold);
                    ScoreboardHelper.addScore("game_Money", target, gold);
                    p.sendMessage(ChatColor.AQUA + "" + gold + ChatColor.BLUE + "has been sent to " + ChatColor.GOLD + target.getDisplayName());
                    target.sendMessage(ChatColor.AQUA + "" + gold + ChatColor.BLUE + "has been recieved from " + ChatColor.GOLD + p.getDisplayName());
                } else {
                    p.sendMessage(ChatColor.BLUE + "You have insufficient gold to do this!");
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void sendProperty(Player p, String[] args){
        Player target = Bukkit.getServer().getPlayer(args[1]);
        if (target == null){
            p.sendMessage(ChatColor.RED + "Player is not online");
        } else {
            if (ScoreboardHelper.getScore("game_isPlaying", target) == 0){
                p.sendMessage(ChatColor.RED + "Player is not in game");
            } else {
                String property = "";
                for (int i = 3; i < args.length; i++){
                    property = property + args[i] + " ";
                }
                property = property.toLowerCase();
                GameProperties gp = null;
                int index = 0;
                for (int i = 0; i < MainGameLogic.propertyList.size(); i++){
                    gp = MainGameLogic.propertyList.get(i);
                    String temp = gp.getName().toLowerCase();
                    if (property.contains(temp)){
                        index = i;
                        break;
                    }
                }
                String owner = MainGameLogic.owned.get(index);
                if (!(gp == null)){
                    if (p.getName().contains(owner)){
                        MainGameLogic.owned.set(index, target.getName());
                        ScoreboardHelper.removeScore("game_Properties", p, 1);
                        ScoreboardHelper.addScore("game_Properties", target, 1);
                        p.sendMessage(ChatColor.AQUA + "" + gp.getName() + ChatColor.BLUE + "has been sent to " + ChatColor.GOLD + target.getDisplayName());
                        target.sendMessage(ChatColor.AQUA + "" + gp.getName() + ChatColor.BLUE + "has been recieved from " + ChatColor.GOLD + p.getDisplayName());
                    } else {
                        p.sendMessage(ChatColor.RED + "You do not own this property");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Unable to find this property");
                }
            }
        }
    }

}
