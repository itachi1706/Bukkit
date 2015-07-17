package com.itachi1706.Bukkit.StaffMember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for StaffMember in com.itachi1706.Bukkit.StaffMember
 */
public class GetPlayerUUIDCmd implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public GetPlayerUUIDCmd(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("getuuid")){
            if (args.length > 1){
                sender.sendMessage(ChatColor.RED + "Usage: /getuuid [player]");
                displayHelp(sender);
                return true;
            }
            if (!sender.hasPermission("staffmember.getuuid")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
                return true;
            }
            if (args.length == 0){
                //Get own UUID
                if (!(sender instanceof Player)){
                    sender.sendMessage("CONSOLE does not have a UUID. You must be an in-game player to use this command!");
                    return true;
                }
                Player p = (Player) sender;
                p.sendMessage(ChatColor.GOLD + "UUID of " + p.getDisplayName() + ChatColor.GOLD + " is: " + p.getUniqueId());
                return true;
            } else if (args.length == 1){
                //Get another user's UUID
                String playername = args[0];
                Player target = Bukkit.getServer().getPlayer(playername);
                if (target == null){
                    //Attempt to get an offline player
                    OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(args[0]);
                    if (op.hasPlayedBefore()){
                        sender.sendMessage(ChatColor.GOLD + "UUID of " + ChatColor.GRAY + ChatColor.ITALIC + op.getName() + ChatColor.GOLD + " is: " + op.getUniqueId());
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "Unable to get " + playername + "'s UUID. Players must have joined at least once to get their UUID");
                    return true;
                }
                sender.sendMessage(ChatColor.GOLD + "UUID of " + target.getDisplayName() + ChatColor.GOLD + " is: " + target.getUniqueId());
                return true;
            }
        }
        return false;
    }

    public void displayHelp(CommandSender s){
        if (s.hasPermission("staffmember.admin")){
            s.sendMessage(ChatColor.GREEN + "Do /staffmember help to see all the commands");
        } else {
            s.sendMessage(ChatColor.GREEN + "Do /staffmember commands to see all the commands");
        }
    }

}
