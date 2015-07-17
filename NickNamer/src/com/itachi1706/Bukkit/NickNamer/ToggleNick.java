package com.itachi1706.Bukkit.NickNamer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for NickNamer in com.itachi1706.Bukkit.NickNamer
 */
public class ToggleNick implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public ToggleNick(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("togglenick")) {
            if (args.length > 1){
                sender.sendMessage(ChatColor.RED + "Invalid usage!");
                return false;
            }
            if (!sender.hasPermission("nicknamer.nick.disguise")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return false;
            }
            if (args.length == 0){
                if (sender instanceof Player){
                    //Toggle nick of self
                    if (Main.nick.getBoolean(sender.getName() + ".disguised")){
                        //Already true nicked
                        Main.nick.set(sender.getName() + ".disguised", false);
                        sender.sendMessage(ChatColor.RED + "You are no longer disguised as an ordinary member!");
                        Main.saveYamls();
                        Player sen = (Player) sender;
                        Nick.refreshNameTag(sen);
                        Nick.updateChatName(sen);
                        Nick.updateTabList(sen);
                        return true;
                    } else {
                        //Not true nicked
                        Main.nick.set(sender.getName() + ".disguised", true);
                        sender.sendMessage(ChatColor.RED + "You are now disguised as an ordinary member!");
                        Main.saveYamls();
                        Player sen = (Player) sender;
                        Nick.refreshNameTag(sen);
                        Nick.updateChatName(sen);
                        Nick.updateTabList(sen);
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You must be a player in game to use this command!");
                    return false;
                }
            } else {
                //Toggle nick of others
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (!sender.hasPermission("nicknamer.nick.realname.other")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                    return false;
                }
                if (target == null){
                    sender.sendMessage(ChatColor.BLUE + "Player is not online or is currently nicked! Do /realname if it is a nick");
                    return false;
                } else {
                    if (Main.nick.getBoolean(target.getName() + ".disguised")){
                        //Currently true nicked
                        Main.nick.set(target.getName() + ".disguised", false);
                        sender.sendMessage(ChatColor.GOLD + target.getDisplayName() + ChatColor.RED +" is no longer disguised as an ordinary member!");
                        target.sendMessage(ChatColor.RED + "You are no longer disguised as an ordinary member!");
                        Main.saveYamls();
                        Nick.refreshNameTag(target);
                        Nick.updateChatName(target);
                        Nick.updateTabList(target);
                        return true;
                    } else {
                        //Not true nicked
                        Main.nick.set(target.getName() + ".disguised", true);
                        sender.sendMessage(ChatColor.GOLD + target.getDisplayName() + ChatColor.RED +" is now disguised as an ordinary member!");
                        target.sendMessage(ChatColor.RED + "You are now disguised as an ordinary member!");
                        Main.saveYamls();
                        Nick.refreshNameTag(target);
                        Nick.updateChatName(target);
                        Nick.updateTabList(target);
                        return true;
                    }

                }
            }
        }
        return false;
    }

}
