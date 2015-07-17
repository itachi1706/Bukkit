package com.itachi1706.Bukkit.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Kenneth on 17/7/2015.
 * for WynncraftExperience in com.itachi1706.Bukkit.WynncraftExperience
 */
public class SpeedMode implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public SpeedMode(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("speedmode")){
            if (!sender.hasPermission("wynncraft.default")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command!");
                return true;
            }
            if (args.length > 1){
                sender.sendMessage(ChatColor.RED + "Usage: /speedmode");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage("You must be an in-game player to use this command!");
                sender.sendMessage("Support for CONSOLE coming soon");
                return true;
            }
            Player p = (Player) sender;
            if (args.length == 0 || (args.length == 1 && !(sender.hasPermission("wynncraft.admin")))){
                if (p.getWalkSpeed() == 0.2f){
                    p.setWalkSpeed(1f);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 5, true), true);
                    String finishMsg = Main.pluginPrefix + "&aYou now have superspeed! Go fast! :D";
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
                } else {
                    p.setWalkSpeed(0.2f);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 0, 5, true), true);
                    String finishMsg = Main.pluginPrefix + "&cYou no longer have superspeed D:";
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
                }
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null){
                sender.sendMessage(ChatColor.RED + args[0] + " is not online");
                return true;
            }
            if (target.getWalkSpeed() == 0.2f){
                target.setWalkSpeed(1f);
                target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 5, true), true);
                String finishMsg = Main.pluginPrefix + "&aYou now have superspeed! Go fast! :D";
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
                sender.sendMessage(ChatColor.GREEN + "Set superspeed for " + target.getDisplayName() + ChatColor.GREEN + ": true");
            } else {
                target.setWalkSpeed(0.2f);
                target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 0, 5, true), true);
                String finishMsg = Main.pluginPrefix + "&cYou no longer have superspeed D:";
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', finishMsg));
                sender.sendMessage(ChatColor.RED + "Set superspeed for " + target.getDisplayName() + ChatColor.RED + ": false");
            }
            return true;
        }
        return false;
    }

}
