package com.itachi1706.Bukkit.Banception;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Kenneth on 17/7/2015.
 * for Banception in com.itachi1706.Bukkit.Banception
 */
public class GlobalMute implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public GlobalMute(Main plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Commands
        if (cmd.getName().equalsIgnoreCase("shutup")){
            if (args.length > 1){
                sender.sendMessage(ChatColor.RED + "Correct Usage: /shutup [time (s/m/h/d)]");
                return true;
            }
            if (Main.gm.getBoolean("mute") == true){
                //Disable GM
                if (sender.hasPermission("banception.gm.unmute"))
                {
                    unmutted(sender);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to enable chat.");
                    return true;
                }
            } else {
                if (sender.hasPermission("banception.gm.mute")){
                    if (args.length == 0){
                        //15 minutes
                        int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
                        int endTime = currentTime + 900;
                        executeMute(sender, currentTime, endTime);
                        return true;
                    } else {
                        //Custom time
                        int time = TimeCalc.initCalc(args[0].toLowerCase());	//Length of Mute in seconds
                        if (time == -1){
                            sender.sendMessage(ChatColor.RED + "Please input a valid time format.");
                            return false;
                        }
                        int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
                        int endTime = currentTime + time;	//Time when ban ends
                        executeMute(sender, currentTime, endTime);
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to disable chat");
                    return true;
                }
            }
        }
        return false;
    }

    public static void executeMute(CommandSender sender, int start, int end){
        String durationMsg = TimeCalc.calcTimeMsg(start, end);
        sender.sendMessage(ChatColor.RED + "You have disabled chat for " + ChatColor.AQUA + durationMsg);
        Main.gm.set("mute", true);
        Main.gm.set("isMutedBy", sender.getName());
        Main.gm.set("timeleft", durationMsg);
        Main.gm.set("time", start - end);
        Main.gm.set("timeStart", start);
        Main.gm.set("timeEnd", end);
        Main.saveGm();
        Bukkit.getServer().broadcastMessage(ChatColor.RED + "Global mute was inititated by " + ChatColor.GREEN + Main.gm.getString("isMutedBy") + ChatColor.RED + " for " + ChatColor.DARK_AQUA + Main.gm.getString("timeleft"));
    }

    public static void autoUnmute(){
        Main.gm.set("mute", false);
        Main.gm.set("timeLeft", "");
        Main.gm.set("time", 0);
        Main.gm.set("timeStart", 0);
        Main.gm.set("timeEnd", 0);
        Main.saveGm();
    }

    public static void unmutted(CommandSender sender){
        autoUnmute();
        sender.sendMessage(ChatColor.GREEN + "You have reenabled chat.");
    }

}
