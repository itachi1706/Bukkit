package com.itachi1706.Bukkit.Monopoly.Logic;

import com.itachi1706.Bukkit.Monopoly.Monopoly;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class PlayerTeams implements Listener {

    public static void giveTeamChooseOption(Player p){
        ItemStack zomb = new ItemStack(Material.WOOL, 1, (byte)5);
        ItemStack playe = new ItemStack(Material.WOOL, 1, (byte)7);
        ItemStack skele = new ItemStack(Material.WOOL, 1, (byte)8);
        ItemStack creepe = new ItemStack(Material.WOOL, 1, (byte)1);
        //Zombie Custom name
        ItemMeta temp = zomb.getItemMeta();
        temp.setDisplayName("Zombie");
        zomb.setItemMeta(temp);
        //player Custom name
        temp = playe.getItemMeta();
        temp.setDisplayName("Player");
        playe.setItemMeta(temp);
        //Skele Custom name
        temp = skele.getItemMeta();
        temp.setDisplayName("Skeleton");
        skele.setItemMeta(temp);
        //Creeper custom name
        temp = creepe.getItemMeta();
        temp.setDisplayName("Creeper");
        creepe.setItemMeta(temp);

        //Give items
        p.getInventory().setItem(0, zomb);
        p.getInventory().setItem(1, playe);
        p.getInventory().setItem(2, skele);
        p.getInventory().setItem(3, creepe);
        p.sendMessage(ChatColor.GOLD + "Select a token by left clicking a wool!");
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void checkTeamValidity(PlayerAnimationEvent e){
        Player p = (Player) e.getPlayer();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        ItemStack is = p.getItemInHand();
        ItemMeta im = is.getItemMeta();
        if (p.getItemInHand().getType().equals(Material.WOOL)){
            String te = im.getDisplayName();
            if (te.equals("Zombie")){
                //Selected Zombie
                Team tea = board.getTeam("Zombie");
                if (tea.getSize() == 0){
                    //Adds to team
                    tea.addPlayer(p);
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)2);
                    confirmedTeam(p);
                    p.getInventory().setItem(0, head);
                    p.getInventory().setHelmet(head);
                    p.sendMessage(ChatColor.GOLD + "Selected Zombie token!");
                } else {
                    //Has player in team
                    p.sendMessage(ChatColor.BLUE + "Token already chosen. Please choose another token.");
                }
            } else if (te.equals("Player")){
                //Selected Player
                Team tea = board.getTeam("Player");
                if (tea.getSize() == 0){
                    //Adds to team
                    tea.addPlayer(p);
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                    confirmedTeam(p);
                    p.getInventory().setItem(0, head);
                    p.getInventory().setHelmet(head);
                    p.sendMessage(ChatColor.GOLD + "Selected Player token!");
                } else {
                    //Has player in team
                    p.sendMessage(ChatColor.BLUE + "Token already chosen. Please choose another token.");
                }
            } else if (te.equals("Skeleton")){
                //Selected Skeleton
                Team tea = board.getTeam("Skeleton");
                if (tea.getSize() == 0){
                    //Adds to team
                    tea.addPlayer(p);
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)1);
                    confirmedTeam(p);
                    p.getInventory().setItem(0, head);
                    p.getInventory().setHelmet(head);
                    p.sendMessage(ChatColor.GOLD + "Selected Skeleton token!");
                } else {
                    //Has player in team
                    p.sendMessage(ChatColor.BLUE + "Token already chosen. Please choose another token.");
                }
            } else if (te.equals("Creeper")){
                //Selected Creeper
                Team tea = board.getTeam("Creeper");
                if (tea.getSize() == 0){
                    //Adds to team
                    tea.addPlayer(p);
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)4);
                    confirmedTeam(p);
                    p.getInventory().setItem(0, head);
                    p.getInventory().setHelmet(head);
                    p.sendMessage(ChatColor.GOLD + "Selected Creeper token!");
                } else {
                    //Has player in team
                    p.sendMessage(ChatColor.BLUE + "Token already chosen. Please choose another token.");
                }
            }
        }
    }

    public void confirmedTeam(Player p){
        p.getInventory().remove(Material.WOOL);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective objec = board.getObjective("game_isPlaying");
        Score sc = objec.getScore(p);
        sc.setScore(1);
        Objective o = board.getObjective("game_Money");
        Score sc1 = o.getScore(p);
        sc1.setScore(Monopoly.configGame.getInt("initialGold"));
        p.setGameMode(GameMode.CREATIVE);
    }

    public static void leaveGame(Player p){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Objective o = board.getObjective("game_isPlaying");
        Score sc = o.getScore(p);
        sc.setScore(0);
        p.setGameMode(GameMode.ADVENTURE);
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + " left the game! If the game is in progress, please restart the game to prevent confict and problems appearing.");
    }

}
