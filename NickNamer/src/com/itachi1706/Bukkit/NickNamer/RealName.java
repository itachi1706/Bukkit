package com.itachi1706.Bukkit.NickNamer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Kenneth on 17/7/2015.
 * for NickNamer in com.itachi1706.Bukkit.NickNamer
 */
public class RealName implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public RealName(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("realname")){
            if (!(args.length == 1)){
                sender.sendMessage(ChatColor.RED + "Invalid usage.");
                return true;
            }
            if (!sender.hasPermission("nicknamer.nick.realname")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            }
            String target = args[0];
            Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
            Iterator<? extends Player> i = playerList.iterator();
            Player targeted = null;
            while (i.hasNext()){
                Player p = i.next();
                String ds = Main.nick.getString(p.getName() + ".nick");
                if (ds.equalsIgnoreCase(target)){
                    targeted = p;
                    break;
                }
            }
            if (targeted == null){
                sender.sendMessage(ChatColor.GOLD + args[0] + ChatColor.BLUE + " is not a valid nick of a player online!");
                return true;
            } else {
                sender.sendMessage(ChatColor.GOLD + args[0] + ChatColor.RED + " is the nickname of " + ChatColor.AQUA + targeted.getName());
                return true;
            }
        }
        return false;
    }

}
