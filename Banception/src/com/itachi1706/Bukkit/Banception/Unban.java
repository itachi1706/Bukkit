package com.itachi1706.Bukkit.Banception;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for Banception in com.itachi1706.Bukkit.Banception
 */
public class Unban implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public Unban(Main plugin){
        this.plugin = plugin;
    }


    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Commands
        if (cmd.getName().equalsIgnoreCase("unban")){
            //Unbans a player
            if (args.length < 1){
                //Not enough arguments
                sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
                return true;
            }
            if (!sender.hasPermission("banception.ban.unban")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }
            OfflinePlayer target = (Bukkit.getServer().getOfflinePlayer(args[0]));
            if (!Main.ban.getBoolean(target.getName() + ".ban")){	//Player wasn't banned
                sender.sendMessage(ChatColor.RED + "Player has not been banned.");
                return true;
            } else {	//Unban player
                target.setBanned(false);
                if (args.length == 1 ){
                    sender.sendMessage(ChatColor.RED + target.getName() + " is unbanned. " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + "No reason");
                    Main.ban.set(target.getName() + ".ban", false);
                    Main.ban.set(target.getName() + ".perm", false);
                    Main.ban.set(target.getName() + ".timeleft", "");
                    Main.ban.set(target.getName() + ".time", 0);
                    Main.ban.set(target.getName() + ".timeStart", 0);
                    Main.ban.set(target.getName() + ".timeEnd", 0);
                    Main.ban.set(target.getName() + ".reason", "");
                    Main.saveBan();
                    return true;
                } else {
                    String unbanReason = "";
                    for (int i = 1; i < args.length; i++){
                        unbanReason = unbanReason + args[i] + " ";
                    }
                    sender.sendMessage(ChatColor.RED + target.getName() + " is unbanned. " + ChatColor.BLUE + "Reason: " + ChatColor.DARK_RED + unbanReason);
                    Main.ban.set(target.getName() + ".ban", false);
                    Main.ban.set(target.getName() + ".perm", false);
                    Main.ban.set(target.getName() + ".timeleft", "");
                    Main.ban.set(target.getName() + ".time", 0);
                    Main.ban.set(target.getName() + ".timeStart", 0);
                    Main.ban.set(target.getName() + ".timeEnd", 0);
                    Main.ban.set(target.getName() + ".reason", unbanReason);
                    Main.saveBan();
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static void unbanned(OfflinePlayer player){
        player.setBanned(false);
        Main.ban.set(player.getName() + ".ban", false);
        Main.ban.set(player.getName() + ".perm", false);
        Main.ban.set(player.getName() + ".timeleft", "");
        Main.ban.set(player.getName() + ".time", 0);
        Main.ban.set(player.getName() + ".timeStart", 0);
        Main.ban.set(player.getName() + ".timeEnd", 0);
        Main.ban.set(player.getName() + ".reason", "Tempban over");
        Main.saveBan();
    }

}
