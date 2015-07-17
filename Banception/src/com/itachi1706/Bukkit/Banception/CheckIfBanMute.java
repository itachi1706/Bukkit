package com.itachi1706.Bukkit.Banception;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for Banception in com.itachi1706.Bukkit.Banception
 */
public class CheckIfBanMute implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public CheckIfBanMute(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Commands
        if (cmd.getName().equalsIgnoreCase("check")){
            if (args.length<1 || args.length>1){
                //Invalid
                sender.sendMessage(ChatColor.RED + "Please specify a player's name");	//Notifies Player
                return true;
            }
            if (!sender.hasPermission("banception.ban.check")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }
            Player target = (Bukkit.getServer().getPlayer(args[0]));
            if (target == null){
                //Offline Player process
                //OfflinePlayer offvictim = Bukkit.getServer().getOfflinePlayer(args[0]);
            }
        }
        return false;
    }

}
