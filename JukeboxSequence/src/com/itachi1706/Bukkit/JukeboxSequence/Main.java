package com.itachi1706.Bukkit.JukeboxSequence;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kenneth on 17/7/2015.
 * for JukeboxSequence in com.itachi1706.Bukkit.JukeboxSequence
 */
public class Main extends JavaPlugin implements Listener {

    public static int dur1,dur2,dur3,dur4,dur5,dur6,dur7,dur8,dur9,dur10,dur11,dur12;
    public static String des1,des2,des3,des4,des5,des6,des7,des8,des9,des10,des11,des12;
    public static String currentDisc;
    public static long lastTime;
    public static List<String> enabledDiscs  = new ArrayList<String>();
    private static List<String> tmpLocJb = new ArrayList<String>();
    public static List<Juker> jukeboxLocation = new ArrayList<Juker>();
    private int runnableId;

    @Override
    public void onEnable(){
        //Logic when plugin gets enabled
        getLogger().info("Enabling Plugin...");
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.saveConfig();
        initializeConfig();
        getLogger().info("Enabling Plugin listeners...");
        loadListeners();
        runRunnable();
    }

    @Override
    public void onDisable(){
        //Logic when plugin gets disabled
        getLogger().info("Disabling Plugin...");
        Bukkit.getScheduler().cancelTask(runnableId);
        HandlerList.unregisterAll((Plugin) this);
        this.saveConfig();
    }

    private void initializeConfig(){
        dur1 = this.getConfig().getInt("disc.2256");
        dur2 = this.getConfig().getInt("disc.2257");
        dur3 = this.getConfig().getInt("disc.2258");
        dur4 = this.getConfig().getInt("disc.2259");
        dur5 = this.getConfig().getInt("disc.2260");
        dur6 = this.getConfig().getInt("disc.2261");
        dur7 = this.getConfig().getInt("disc.2262");
        dur8 = this.getConfig().getInt("disc.2263");
        dur9 = this.getConfig().getInt("disc.2264");
        dur10 = this.getConfig().getInt("disc.2265");
        dur11 = this.getConfig().getInt("disc.2266");
        dur12 = this.getConfig().getInt("disc.2267");
        des1 = this.getConfig().getString("disctext.2256");
        des2 = this.getConfig().getString("disctext.2257");
        des3 = this.getConfig().getString("disctext.2258");
        des4 = this.getConfig().getString("disctext.2259");
        des5 = this.getConfig().getString("disctext.2260");
        des6 = this.getConfig().getString("disctext.2261");
        des7 = this.getConfig().getString("disctext.2262");
        des8 = this.getConfig().getString("disctext.2263");
        des9 = this.getConfig().getString("disctext.2264");
        des10 = this.getConfig().getString("disctext.2265");
        des11 = this.getConfig().getString("disctext.2266");
        des12 = this.getConfig().getString("disctext.2267");
        lastTime = Calendar.getInstance().getTimeInMillis();
        enabledDiscs.clear();
        enabledDiscs = this.getConfig().getStringList("enabled-disc");
        if (this.getConfig().getString("current-disc") != null) {
            currentDisc = this.getConfig().getString("current-disc");
        } else {
            currentDisc = enabledDiscs.get(0).toString();
        }
        tmpLocJb.clear();
        tmpLocJb = this.getConfig().getStringList("jukebox-location");
        updateJukeboxLocation();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("js")){
            if (args.length != 1){
                sender.sendMessage(ChatColor.RED + "Usage: /js reload/stop/start/debug/status");
                return true;
            }
            if (!sender.hasPermission("jukeboxsequence.staff")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                reloadConfigServer();
                sender.sendMessage(ChatColor.GREEN + "Plugin config reloaded!");

                return true;
            } else if (args[0].equalsIgnoreCase("stop")){
                if (!this.getConfig().getBoolean("enabled")){
                    sender.sendMessage(ChatColor.RED + "Jukebox song has already been stopped globally. To start it, do "
                            + ChatColor.GOLD + "/js start");
                    return true;
                }
                this.getConfig().set("enabled", false);
                sender.sendMessage(ChatColor.RED + "Songs stopped");
                return true;
            } else if (args[0].equalsIgnoreCase("start")){
                if (this.getConfig().getBoolean("enabled")){
                    sender.sendMessage(ChatColor.RED + "Jukebox song has already been started globally. To stop it, do "
                            + ChatColor.GOLD + "/js stop");
                    return true;
                }
                this.getConfig().set("enabled", true);
                lastTime = Calendar.getInstance().getTimeInMillis();
                sender.sendMessage(ChatColor.GREEN + "Songs started");
                return true;
            } else if (args[0].equalsIgnoreCase("debug")){
                if (this.getConfig().getBoolean("debug")){
                    //disable
                    this.getConfig().set("debug", false);
                    sender.sendMessage("Debug Mode " + ChatColor.RED + "disabled");
                    return true;
                } else {
                    //enable
                    this.getConfig().set("debug", true);
                    sender.sendMessage("Debug Mode " + ChatColor.GREEN + "enabled");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("status")){
                getStatus(sender);
                return true;
            }
            else {
                sender.sendMessage(ChatColor.RED + "Usage: /js reload/stop/start/debug/status");
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("nowplaying")){
            if (!this.getConfig().getBoolean("enabled")){
                sender.sendMessage(ChatColor.GREEN + "Now Playing on the server: " + ChatColor.RED + "NONE");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Now Playing on the server: " + ChatColor.GOLD + getCurrentDiscName());
            return true;
        } else if (cmd.getName().equalsIgnoreCase("fixsong")){
            lastTime = Calendar.getInstance().getTimeInMillis();
            sender.sendMessage(ChatColor.GREEN + "Skipping and resynchronizing songs");
            return true;
        }
        return false;
    }

    private void getStatus(CommandSender sender){
        sender.sendMessage(ChatColor.GOLD + "=========== JukeboxSequence Status ===========");
        sender.sendMessage("Current Disc Loaded: " + ChatColor.AQUA + currentDisc);
        if (this.getConfig().getBoolean("debug")){
            sender.sendMessage("Debug Mode: " + ChatColor.GREEN + "true");
        } else {
            sender.sendMessage("Debug Mode: " + ChatColor.RED + "false");
        }
        if (this.getConfig().getBoolean("enabled")){
            sender.sendMessage("Enabled: " + ChatColor.GREEN + "true");
        } else {
            sender.sendMessage("Enabled: " + ChatColor.RED + "false");
        }
        sender.sendMessage("Enabled Disc IDs: " + ChatColor.AQUA + enabledDiscs.toString());
        sender.sendMessage(ChatColor.GOLD + "============================================");
    }

    private void updateJukeboxLocation(){
        jukeboxLocation.clear();
        for (int i = 0; i < tmpLocJb.size(); i++){
            String locationString = tmpLocJb.get(i);
            String[] location = locationString.split(" ");
            int x,y,z;
            Location loc = new Location(Bukkit.getServer().getWorlds().get(0),0,0,0);
            try {
                x = Integer.parseInt(location[1]);
                y = Integer.parseInt(location[2]);
                z = Integer.parseInt(location[3]);
                if (location.length == 4 && Bukkit.getServer().getWorld(location[0]) != null){
                    loc.setWorld(Bukkit.getServer().getWorld(location[0]));
                    loc.setX(x);
                    loc.setY(y);
                    loc.setZ(z);
                }
                jukeboxLocation.add(new Juker(loc));
            } catch (NumberFormatException ex){
                getLogger().severe("An error occured parsing location of jukebox " + (i + 1));
                ex.printStackTrace();
            }

        }
    }

    private void runRunnable(){
        runnableId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PlayJuke(this), 20L, 20L);
    }

    private void reloadConfigServer(){
        this.reloadConfig();
        Bukkit.getScheduler().cancelTask(runnableId);
        initializeConfig();
        HandlerList.unregisterAll((Plugin) this);
        loadListeners();
        runRunnable();
    }

    private String getCurrentDiscName(){
        String name = Main.currentDisc;
        switch (name){
            case "2256": return des1;
            case "2257": return des2;
            case "2258": return des3;
            case "2259": return des4;
            case "2260": return des5;
            case "2261": return des6;
            case "2262": return des7;
            case "2263": return des8;
            case "2264": return des9;
            case "2265": return des10;
            case "2266": return des11;
            case "2267": return des12;
            default: return des1;
        }
    }

    private void loadListeners(){

    }

    public static void sendAdminMsg(String msg){
        for (Player p : Bukkit.getServer().getOnlinePlayers()){
            if (p.hasPermission("bukkit.broadcast.admin")){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }

}
