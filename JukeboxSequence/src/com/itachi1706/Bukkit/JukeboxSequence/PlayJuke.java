package com.itachi1706.Bukkit.JukeboxSequence;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;

import java.util.Calendar;

/**
 * Created by Kenneth on 17/7/2015.
 * for JukeboxSequence in com.itachi1706.Bukkit.JukeboxSequence
 */
public class PlayJuke implements Runnable {

    private Main plugin;
    private boolean debugMode;

    public PlayJuke(Main plugin){
        this.plugin = plugin;
    }

    public void run(){
        //Check if time up
        debugMode = plugin.getConfig().getBoolean("debug");
        long time = Calendar.getInstance().getTimeInMillis();
        long prevTime = Main.lastTime;
        //Bukkit.getLogger().info(time + " : " + prevTime);
        if (!plugin.getConfig().getBoolean("enabled")){
            for (int i = 0; i < Main.jukeboxLocation.size(); i++){
                if (Main.jukeboxLocation.get(i).getIsPlaying() == true){
                    Location jbLoc = Main.jukeboxLocation.get(i).getLocation();
                    Chunk c = jbLoc.getChunk();
                    if (!c.isLoaded()){
                        c.load();
                    }
                    Block b = jbLoc.getBlock();
                    if (b.getType().equals(Material.JUKEBOX)){
                        Jukebox j = (Jukebox) b.getState();
                        j.setPlaying(Material.AIR);
                        Main.jukeboxLocation.get(i).setIsPlaying(false);
                    } else {
                        if (b.isEmpty()){
                            b.setType(Material.JUKEBOX);
                            Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[NOTICE]: Placed Jukebox at " + jbLoc.getX() + " " + jbLoc.getY() + " " + jbLoc.getZ() + "]"));
                        } else {
                            Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[SEVERE]: Invalid Jukebox at " + jbLoc.getX() + " " + jbLoc.getY() + " " + jbLoc.getZ() + "]"));
                        }
                    }
                }
            }
        } else {
            if (time < prevTime){
                //If time not up ignore
                for (int i = 0; i < Main.jukeboxLocation.size(); i++){
                    if (Main.jukeboxLocation.get(i).getIsPlaying() == false){
                        Material m = getCurrentDisc();
                        Location jbLoc = Main.jukeboxLocation.get(i).getLocation();
                        Chunk c = jbLoc.getChunk();
                        if (!c.isLoaded()){
                            c.load();
                        }
                        Block b = jbLoc.getBlock();
                        if (b.getType().equals(Material.JUKEBOX)){
                            if (playersNearby(b)){
                                Jukebox j = (Jukebox) b.getState();
                                j.setPlaying(m);
                                Main.jukeboxLocation.get(i).setIsPlaying(true);
                            }
                        } else {
                            if (b.isEmpty()){
                                b.setType(Material.JUKEBOX);
                                Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[NOTICE]: Placed Jukebox at " + jbLoc.getX() + " " + jbLoc.getY() + " " + jbLoc.getZ() + "]"));
                            } else {
                                Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[SEVERE]: Invalid Jukebox at " + jbLoc.getX() + " " + jbLoc.getY() + " " + jbLoc.getZ() + "]"));
                            }
                        }
                    }

                }
            } else {
                //Else switch disc
                if (debugMode){
                    Bukkit.getLogger().info("Switching disc");
                }
                boolean chk = false;
                for (int i = 0; i < Main.enabledDiscs.size(); i++){
                    String fD = Main.enabledDiscs.get(i);
                    String cD = Main.currentDisc;
                    if (cD.equals(fD)){
                        if (i == (Main.enabledDiscs.size() - 1)){
                            //Restart from 0
                            Main.currentDisc = Main.enabledDiscs.get(0);
                        } else {
                            Main.currentDisc = Main.enabledDiscs.get(i+1);
                        }
                        plugin.getConfig().set("current-disc", Main.currentDisc);
                        updateNewTime(Main.currentDisc);
                        chk = true;
                        break;
                    }
                }
                if (chk == false){
                    if (Main.enabledDiscs.size() > 0){
                        Main.currentDisc = Main.enabledDiscs.get(0);
                    } else {
                        Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[SEVERE]: Enabled Disc List is empty!]"));
                    }
                }
                for (int i = 0; i < Main.jukeboxLocation.size(); i++){
                    Main.jukeboxLocation.get(i).setIsPlaying(false);
                }
                if (debugMode) {
                    Bukkit.getLogger().info("Disc switched to " + Main.currentDisc);
                }
                //Play sound
                for (int i = 0; i < Main.jukeboxLocation.size(); i++){
                    Material m = getCurrentDisc();
                    Location jbLoc = Main.jukeboxLocation.get(i).getLocation();
                    Chunk c = jbLoc.getChunk();
                    if (!c.isLoaded()){
                        c.load();
                    }
                    Block b = jbLoc.getBlock();
                    if (b.getType().equals(Material.JUKEBOX)){
                        if (playersNearby(b) && Main.jukeboxLocation.get(i).getIsPlaying() == false){
                            Jukebox j = (Jukebox) b.getState();
                            j.setPlaying(m);
                            Main.jukeboxLocation.get(i).setIsPlaying(true);
                        }
                    } else {
                        if (b.isEmpty()){
                            b.setType(Material.JUKEBOX);
                            Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[NOTICE]: Placed Jukebox at " + jbLoc.getX() + " " + jbLoc.getY() + " " + jbLoc.getZ() + "]"));
                        } else {
                            Main.sendAdminMsg(ChatColor.translateAlternateColorCodes('&', "&7&o[SEVERE]: Invalid Jukebox at " + jbLoc.getX() + " " + jbLoc.getY() + " " + jbLoc.getZ() + "]"));
                        }
                    }

                }
            }
        }
    }

    private Material getCurrentDisc(){
        String name = Main.currentDisc;
        switch (name){
            case "2256": return Material.GOLD_RECORD;
            case "2257": return Material.GREEN_RECORD;
            case "2258": return Material.RECORD_3;
            case "2259": return Material.RECORD_4;
            case "2260": return Material.RECORD_5;
            case "2261": return Material.RECORD_6;
            case "2262": return Material.RECORD_7;
            case "2263": return Material.RECORD_8;
            case "2264": return Material.RECORD_9;
            case "2265": return Material.RECORD_10;
            case "2266": return Material.RECORD_11;
            case "2267": return Material.RECORD_12;
            default: return Material.GOLD_RECORD;
        }
    }



    private void updateNewTime(String input){
        int duration = 0;
        switch (input){
            case "2256": duration = Main.dur1;
                break;
            case "2257": duration = Main.dur2;
                break;
            case "2258": duration = Main.dur3;
                break;
            case "2259": duration = Main.dur4;
                break;
            case "2260": duration = Main.dur5;
                break;
            case "2261": duration = Main.dur6;
                break;
            case "2262": duration = Main.dur7;
                break;
            case "2263": duration = Main.dur8;
                break;
            case "2264": duration = Main.dur9;
                break;
            case "2265": duration = Main.dur10;
                break;
            case "2266": duration = Main.dur11;
                break;
            case "2267": duration = Main.dur12;
                break;
            default: break;
        }

        //Convert to long and add to new time
        duration = duration * 1000;
        long newDur = (long) duration;
        long oldDur = Main.lastTime;
        newDur = newDur + oldDur;
        Main.lastTime = newDur;
    }

    private boolean playersNearby(Block b) {
        double dist;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            try {
                dist = b.getLocation().distance(p.getLocation());
                if (dist <= 64) {
                    return true;
                }
            } catch (IllegalArgumentException ex) { // cross-world.
            }
        }

        return false;
    }

}
