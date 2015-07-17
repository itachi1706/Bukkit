package com.itachi1706.Bukkit.NBPHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kenneth on 17/7/2015.
 * for NBPHelper in com.itachi1706.Bukkit.NBPHelper
 */
public class Main extends JavaPlugin implements Listener {

    public static ArrayList<String> songList = new ArrayList<String>();
    public static File folder = null;

    @Override
    public void onEnable(){
        getLogger().info("Enabling Plugin...");
        getServer().getPluginManager().registerEvents(this, this);
        try {
            folder = new File(this.getDataFolder().getParentFile() + "/NoteBlockPlayer");
        } catch (Exception ex){
            Bukkit.getServer().broadcastMessage("An error occured in NBPHelper. (" + ex.toString() + ")");
        }
        updateSongList();
    }

    @Override
    public void onDisable(){
        //Logic when plugin gets disabled
        getLogger().info("Disabling Plugin...");
    }

    public void updateSongList(){
        songList.clear();
        try {
            File[] listOfFiles = folder.listFiles();
            for (File f : listOfFiles){
                if (f.isFile()){
                    songList.add(f.getName());
                }
            }
        } catch (Exception ex){
            Bukkit.getServer().broadcastMessage("An error occured in NBPHelper. (" + ex.toString() + ")");
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("rescan")){
            sender.sendMessage(ChatColor.GREEN + "Doing a repopulation of the song list");
            updateSongList();
            sender.sendMessage(ChatColor.GREEN + "Song list repopulated!");
            return true;
        } else if (cmd.getName().equalsIgnoreCase("songlist") && args.length == 0){
            displaySongPage(1, sender);
            return true;
        } else if (cmd.getName().equalsIgnoreCase("songlist") && args.length == 1){
            int value;
            try {
                value = Integer.parseInt(args[0]);
                displaySongPage(value, sender);
            } catch (NumberFormatException ex){
                sender.sendMessage(ChatColor.RED + "Invalid Usage. Please specify only a number!");
                return true;
            }
            return true;
        } else if (cmd.getName().equals("songlist") && args.length > 1){
            sender.sendMessage(ChatColor.RED + "Invalid Usage! Correct Usage: /songlist <#>");
            return true;
        } else if (cmd.getName().equals("songplay")){
            if (args.length != 1){
                sender.sendMessage(ChatColor.RED + "Invalid Useage! Usage: /songplay <fullsongname>");
                sender.sendMessage(ChatColor.GREEN + "Do /songlist to view the song names");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage("You must be an ingame player to use this command!");
                return true;
            }
            Player p = (Player) sender;
            sender.sendMessage("[" + ChatColor.RED + "PlaySong" + ChatColor.RESET + "] Now playing " + ChatColor.AQUA + args[0]);
            playMusic(args[0], p);
            sendAdminMsg( ChatColor.translateAlternateColorCodes('&', "&7&o[" + sender.getName() + ": Played song " + args[0] + "]"), sender);
            return true;
        }
        return false;
    }

    private void displaySongPage(int arg, CommandSender sender){
        if (arg == 0){
            sender.sendMessage(ChatColor.RED + "Invalid Usage. Please specify a number above 0!");
            return;
        }
        int maxPossibleValue = songList.size();	//Max possible based on songlist
        int maxPossiblePage = (songList.size() / 10) + 1;
        if (maxPossiblePage < arg){
            sender.sendMessage(ChatColor.RED + "Max amount of pages is " + maxPossiblePage + ". Please specify a value within that range!");
            return;
        }
        //1 (0-9), 2 (10,19)...
        int minValue = (arg - 1) * 10;
        int maxValue = (arg * 10) - 1;
        if (maxValue > maxPossibleValue) {	//Exceeds
            sender.sendMessage(ChatColor.GOLD + "------------- Songs Available Page " + arg + " of " + maxPossiblePage + " -------------");
            for (int i = minValue; i < songList.size(); i++){
                sender.sendMessage(ChatColor.GOLD + "" + (i+1) + ": " + ChatColor.AQUA + songList.get(i).toString());
            }
            sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
            return;
        }
        sender.sendMessage(ChatColor.GOLD + "------------- Songs Available Page " + arg + " of " + maxPossiblePage + " -------------");
        for (int i = minValue; i <= maxValue; i++){
            sender.sendMessage(ChatColor.GOLD + "" + (i+1) + ": " + ChatColor.AQUA + songList.get(i).toString());
        }
        sender.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
        return;
    }

    private void playMusic(String songname, Player p){
        this.getServer().dispatchCommand(getServer().getConsoleSender(), "play " + p.getName() + " " + songname);
    }

    public static void sendAdminMsg(String msg, CommandSender sender){
        for (Player p : Bukkit.getServer().getOnlinePlayers()){
            if (!(p.getName().equalsIgnoreCase(sender.getName()))){
                if (p.hasPermission("bukkit.broadcast.admin")){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
    }

}
