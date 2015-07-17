package com.itachi1706.Bukkit.StaffMember;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for StaffMember in com.itachi1706.Bukkit.StaffMember
 */
public class MojangStatus implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public MojangStatus(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mojang")) {
            if (args.length > 2 || args.length < 1){
                displayHelp(sender);
                return true;
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("status")){
                    getMojangStatus(sender);
                    return true;
                } else {
                    displayHelp(sender);
                    return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("premium")){
                    //URL = minecraft.net/haspaid.jsp?user=
                    getPremium(sender, args[1]);
                    return true;
                } else {
                    displayHelp(sender);
                    return true;
                }
            }
        }
        return false;
    }

    public void displayHelp(CommandSender sender){
        sender.sendMessage(ChatColor.RED + "Usage: /mojang status or /mojang premium <player>");
        if (sender.hasPermission("staffmember.admin")){
            sender.sendMessage(ChatColor.GREEN + "Do /staffmember help to see all the commands");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Do /staffmember commands to see all the commands");
        }
    }

    public void getMojangStatus(CommandSender sender){
        //Does /mojang status
        sender.sendMessage(ChatColor.GOLD + "==================================================");
        sender.sendMessage(ChatColor.BLUE + "                Mojang Server Status");
        sender.sendMessage(ChatColor.GOLD + "==================================================");
        for (MojangStatusChecker statusChecker : MojangStatusChecker.values()) {
            String service = statusChecker.getName();
            MojangStatusChecker.Status status = statusChecker.getStatus(false);

            sender.sendMessage(service + ": " + status.getColor() + status.getStatus() + " - " + status.getDescription());
        }
        sender.sendMessage(ChatColor.GOLD + "==================================================");
    }

    public void getPremium(CommandSender sender, String name){
        int returnCode = MojangPremiumPlayer.isPremium(name);
        if (returnCode == 1){
            sender.sendMessage(ChatColor.GOLD + name + ChatColor.DARK_PURPLE + " is a " + ChatColor.GREEN + "premium" + ChatColor.DARK_PURPLE + " status player!");
        } else if (returnCode == 0){
            sender.sendMessage(ChatColor.GOLD + name + ChatColor.DARK_PURPLE + " is a " + ChatColor.RED + "non-premium" + ChatColor.DARK_PURPLE + " status player!");
        } else if (returnCode == 2){
            sender.sendMessage(ChatColor.RED + "An error had occured. Check the console for details!");
        }
    }

}
