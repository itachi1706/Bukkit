package com.itachi1706.Bukkit.CheesecakeMinigameLobby.ModularCommands;

import com.itachi1706.Bukkit.CheesecakeMinigameLobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Kenneth on 17/7/2015.
 * for CheesecakeMinigameLobby in com.itachi1706.Bukkit.CheesecakeMinigameLobby.ModularCommands
 */

//Flings a player up into the air
public class FlingCmd implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public FlingCmd(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("fling")){
            if (!Main.commandFling){
                sender.sendMessage(ChatColor.RED + "This command has been disabled for this server!");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage("You must be a player in-game to use this command");
                return true;
            }
            if (!sender.hasPermission("cheesecakeminigamelobby.abilities.fling")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
                return true;
            }
            if (args.length > 1){
                sender.sendMessage(ChatColor.RED + "Usage: /fling [player]");
                return true;
            }
            Player p = (Player) sender;
            if (args.length == 0){
                //Flings yourself
                Player target = Bukkit.getServer().getPlayer(p.getName());
                target.getWorld().createExplosion(target.getLocation(), 0);
                target.getWorld().playEffect(target.getLocation(), Effect.SMOKE, 0);
                target.setVelocity(new Vector(0, 10, 0));
                target.sendMessage(ChatColor.DARK_PURPLE + "You were flung into the air!");
                p.sendMessage(ChatColor.GOLD + "Flung " + target.getDisplayName() + ChatColor.GOLD + " into the air!");
                return true;
            } else if (args.length == 1){
                //Flings a player
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null){
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                    return true;
                }
                target.getWorld().createExplosion(target.getLocation(), 0);
                target.getWorld().playEffect(target.getLocation(), Effect.SMOKE, 0);
                target.setVelocity(new Vector(0, 10, 0));
                target.sendMessage(ChatColor.DARK_PURPLE + "You were flung into the air!");
                p.sendMessage(ChatColor.GOLD + "Flung " + target.getDisplayName() + ChatColor.GOLD + " into the air!");
                return true;
            }
        }
        return false;

    }

}
