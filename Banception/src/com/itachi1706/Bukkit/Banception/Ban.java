package com.itachi1706.Bukkit.Banception;

import lib.PatPeter.SQLibrary.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for Banception in com.itachi1706.Bukkit.Banception
 */
public class Ban implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public Ban(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Commands
        if (cmd.getName().equalsIgnoreCase("ban")){
            //Permanently Bans a Player
            if (args.length < 1){
                //Not enough arguments
                sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
                return true;
            }
            if (!sender.hasPermission("banception.ban.perm")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }
            Player target = (Bukkit.getServer().getPlayer(args[0]));
            Database sql = Main.sql;
            if (target == null){
                //Offline Player process
                OfflinePlayer offvictim = Bukkit.getServer().getOfflinePlayer(args[0]);
                if (offvictim.isBanned() == true){	//Player was banned
                    sender.sendMessage(ChatColor.RED + "Player has already been banned.");
                    return true;
                } else {	//Ban offline player
                    offvictim.setBanned(true);
                    if (args.length == 1){
                        sender.sendMessage(ChatColor.RED + offvictim.getName() + " is banned permenantly while he is offline. " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + "No reason");
                        Main.ban.set(offvictim.getName() + ".ban", true);
                        Main.ban.set(offvictim.getName() + ".perm", true);
                        Main.ban.set(offvictim.getName() + ".timeleft", "permenantly");
                        Main.ban.set(offvictim.getName() + ".time", -1);
                        Main.ban.set(offvictim.getName() + ".reason", "The ban hammer has spoken!");
                        Main.ban.set(offvictim.getName() + ".isBannedBy", sender.getName());
                        SQLiteHelper.addBanLog(sql, offvictim.getName(), "permenantly", "The ban hammer has spoken!", sender.getName());
                        Main.saveBan();
                        return true;
                    } else {
                        String banReason = "";
                        for (int i = 1; i < args.length; i++){
                            banReason = banReason + args[i] + " ";
                        }
                        sender.sendMessage(ChatColor.RED + offvictim.getName() + " is banned permenantly while he is offline. " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + banReason);
                        Main.ban.set(offvictim.getName() + ".ban", true);
                        Main.ban.set(offvictim.getName() + ".perm", true);
                        Main.ban.set(offvictim.getName() + ".timeleft", "permenantly");
                        Main.ban.set(offvictim.getName() + ".time", -1);
                        Main.ban.set(offvictim.getName() + ".reason", banReason);
                        Main.ban.set(offvictim.getName() + ".isBannedBy", sender.getName());
                        SQLiteHelper.addBanLog(sql, offvictim.getName(), "permenantly", banReason, sender.getName());
                        Main.saveBan();
                        return true;
                    }
                }
            } else {
                //Online Player process
                Player victim = Bukkit.getServer().getPlayerExact(args[0]);
                if (args.length == 1) {
                    victim.kickPlayer("You have been permenantly banned from the server! Reason: " + ChatColor.DARK_RED + " The ban hammer has spoken!");
                    victim.setBanned(true);
                    sender.sendMessage(ChatColor.RED + victim.getName() + " is banned permenantly. " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + "No reason");
                    Main.ban.set(victim.getName() + ".ban", true);
                    Main.ban.set(victim.getName() + ".perm", true);
                    Main.ban.set(victim.getName() + ".timeleft", "permenantly");
                    Main.ban.set(victim.getName() + ".time", -1);
                    Main.ban.set(victim.getName() + ".reason", "The ban hammer has spoken!");
                    Main.ban.set(victim.getName() + ".isBannedBy", sender.getName());
                    SQLiteHelper.addBanLog(sql, victim.getName(), "permenantly", "The ban hammer has spoken!", sender.getName());
                    Main.saveBan();
                    return true;
                } else {
                    String banReason = "";
                    for (int i = 1; i < args.length; i++){
                        banReason = banReason + args[i] + " ";
                    }
                    victim.kickPlayer("You have been permenantly banned from the server! Reason: "  + ChatColor.DARK_RED + banReason);
                    victim.setBanned(true);
                    sender.sendMessage(ChatColor.RED + victim.getName() + " is banned permenantly. " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + banReason);
                    Main.ban.set(victim.getName() + ".ban", true);
                    Main.ban.set(victim.getName() + ".perm", true);
                    Main.ban.set(victim.getName() + ".timeleft", "permenantly");
                    Main.ban.set(victim.getName() + ".time", -1);
                    Main.ban.set(victim.getName() + ".reason", banReason);
                    Main.ban.set(victim.getName() + ".isBannedBy", sender.getName());
                    SQLiteHelper.addBanLog(sql, victim.getName(), "permenantly", banReason, sender.getName());
                    Main.saveBan();
                    return true;
                }

            }
        }
        return false;
    }

}
