package com.itachi1706.Bukkit.CheesecakeMinigameLobby.ModularCommands;

import com.itachi1706.Bukkit.CheesecakeMinigameLobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for CheesecakeMinigameLobby in com.itachi1706.Bukkit.CheesecakeMinigameLobby.ModularCommands
 */

//Makes a player fly
public class FlyCmd implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public FlyCmd(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("fly")){
            if (!Main.commandFly){
                sender.sendMessage(ChatColor.RED + "This command has been disabled for this server!");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage("You must be a player in-game to use this command");
                return true;
            }
            if (!sender.hasPermission("cheesecakeminigamelobby.abilities.fly")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
                return true;
            }
            if (args.length > 1){
                displayHelp(sender);
                return true;
            }
            Player p = (Player) sender;
            if (args.length == 0){
                //Does /fly
                if (p.getAllowFlight()){
                    p.setFlying(false);
                    p.setAllowFlight(false);
                    p.sendMessage(ChatColor.GOLD + "Fly mode has been " + ChatColor.RED + "disabled");
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage(ChatColor.GOLD + "Fly mode has been " + ChatColor.GREEN + "enabled");
                }
                return true;
            } else if (args.length == 1){
                //Does /fly on another player
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null){
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                    return true;
                }
                if (target.getAllowFlight()){
                    target.setFlying(false);
                    target.setAllowFlight(false);
                    target.sendMessage(ChatColor.GOLD + "Fly mode has been " + ChatColor.RED + "disabled");
                    p.sendMessage(ChatColor.RED + "Disabled " + ChatColor.GOLD + "Fly mode for " + target.getDisplayName());
                } else {
                    target.setAllowFlight(true);
                    target.setFlying(true);
                    target.sendMessage(ChatColor.GOLD + "Fly mode has been " + ChatColor.GREEN + "enabled");
                    p.sendMessage(ChatColor.GREEN + "Enabled " + ChatColor.GOLD + "Fly mode for " + target.getDisplayName());
                }
                return true;
            }
        }
        return false;
    }

    private void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.RED + "Usage: /fly [player]");
    }

}
