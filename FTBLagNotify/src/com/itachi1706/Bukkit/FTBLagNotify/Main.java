package com.itachi1706.Bukkit.FTBLagNotify;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Created by Kenneth on 17/7/2015.
 * for FTBLagNotify in com.itachi1706.Bukkit.FTBLagNotify
 */
public class Main extends JavaPlugin {

    static File configFile;
    static FileConfiguration config;
    static int versionConfig = 2;
    static String pluginPrefix = "[" + ChatColor.DARK_RED + "LagNotify" + ChatColor.WHITE + "] ";

    @Override
    public void onEnable(){
        getLogger().info("Enabling Plugin...");
        configFile = new File(getDataFolder(), "config.yml");
        //Lag File Config
        config = new YamlConfiguration();
        try {
            firstRun();
        } catch (Exception e){
            e.printStackTrace();
        }
        loadYamls();
        if (config.getInt("version") != versionConfig) {
            updateConfig();
        }

        getCommand("warnlag").setTabCompleter(new CmdTabComplete());
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);	//Get TPS

        //Warning notify
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run(){
                //Command that sends out warning to all users
                double tps = Lag.getTPS();
                double tpsLimit = config.getDouble("warntps");
                if ((config.getBoolean("startwarn") == true)){
                    getLogger().info("Showing default message.");
                    getServer().broadcastMessage(pluginPrefix + ChatColor.RED + config.getString("warn-msg"));
                } else if (config.getBoolean("startwarn2") == true){
                    getLogger().info("Showing message 2.");
                    getServer().broadcastMessage(pluginPrefix + ChatColor.RED + config.getString("warn-msg2"));
                } else if (tps <= tpsLimit && config.getBoolean("warn-msg-low-tps") == true){
                    getLogger().info("Showing low tps message.");
                    getServer().broadcastMessage(pluginPrefix + ChatColor.RED + config.getString("warn-msg"));
                }
            }
        }, 100L, 600L);

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run(){
                //Command that sends out warning to all users
                double tps = Lag.getTPS();
                getLogger().info("Showing tps message.");
                double tpses = Lag.getTPS();
                double lagser = Math.round((1.0D - tps / 20.0D) * 100.0D);
                if (config.getBoolean("hourlyTPS") == true){
                    getServer().broadcastMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Current Server TPS: " + ChatColor.GOLD + tpses);
                    getServer().broadcastMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Current lag on Server: " + ChatColor.GOLD + lagser);
                }
            }
        }, 100L, 18000L);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("warnlag")){ // If the player typed /warnlag then do the following...
            // Start warning about lag.
            if (args.length > 1){
                //Invalid usage
                displayHelp(sender);
                return true;
            } else if (!sender.hasPermission("lagnotify.warn")){
                sender.sendMessage(ChatColor.DARK_RED + "Insufficent permissions to use this command.");
                return true;
            } else if (args.length == 0){
                //Do /warnlag stuff (Default to warn-msg)
                if (config.getBoolean("startwarn") == false) {
                    //Enable
                    getLogger().info("Default message displayed.");
                    config.set("startwarn", true);
                    sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 1 started. To stop it, do /warnlag");
                    getServer().broadcastMessage(pluginPrefix + ChatColor.RED + config.getString("warn-msg"));
                    saveYamls();
                    return true;
                } else {
                    //Disable
                    getLogger().info("Default message disabled.");
                    config.set("startwarn", false);
                    sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 1 stopped");
                    saveYamls();
                    return true;
                }
            } else if (args.length == 1){
                //Do /warnlag stuff (Selection of 1 or 2 or reload)
                if (args[0].equalsIgnoreCase("reload")){
                    sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Plugin reloaded");
                    loadYamls();
                } else if(args[0].equalsIgnoreCase("1")){
                    //Select warn-msg
                    if (config.getBoolean("startwarn") == false) {
                        //Enable
                        getLogger().info("Message 1 displayed.");
                        config.set("startwarn", true);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 1 started. To stop it, do /warnlag 1");
                        getServer().broadcastMessage(pluginPrefix + ChatColor.RED + config.getString("warn-msg"));
                        saveYamls();
                    } else {
                        //Disable
                        getLogger().info("Message 1 disabled.");
                        config.set("startwarn", false);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 1 stopped");
                        saveYamls();
                    }
                } else if (args[0].equalsIgnoreCase("2")){
                    //Select warn-msg2
                    if (config.getBoolean("startwarn2") == false) {
                        //Enable
                        getLogger().info("Message 2 displayed.");
                        config.set("startwarn2", true);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 2 started. To stop it, do /warnlag 2");
                        getServer().broadcastMessage(pluginPrefix + ChatColor.RED + config.getString("warn-msg2"));
                        saveYamls();
                    } else {
                        //Disable
                        getLogger().info("Message 2 disabled.");
                        config.set("startwarn2", false);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 2 stopped");
                        saveYamls();
                    }
                } else if (args[0].equalsIgnoreCase("list")){
                    //List Messages
                    sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 1: " + ChatColor.BLUE + config.getString("warn-msg"));
                    sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning 2: " + ChatColor.BLUE + config.getString("warn-msg2"));
                } else if (args[0].equalsIgnoreCase("status")) {
                    serverStatus(sender);
                } else if (args[0].equalsIgnoreCase("hourly")){
                    if (config.getBoolean("hourlyTPS") == false) {
                        //Enable
                        getLogger().info("Message 1 displayed.");
                        config.set("hourlyTPS", true);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Notifying players of tps hourly" + ChatColor.GREEN + " enabled");
                        saveYamls();
                    } else {
                        //Disable
                        getLogger().info("Message 1 disabled.");
                        config.set("hourlyTPS", false);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Notifying players of tps hourly" + ChatColor.RED + " disabled");
                        saveYamls();
                    }
                } else if (args[0].equalsIgnoreCase("lowtpsmsg")){
                    if (config.getBoolean("warn-msg-low-tps") == false) {
                        //Enable
                        getLogger().info("Message 1 displayed.");
                        config.set("warn-msg-low-tps", true);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning user on low tps" + ChatColor.GREEN + " enabled");
                        saveYamls();
                    } else {
                        //Disable
                        getLogger().info("Message 1 disabled.");
                        config.set("warn-msg-low-tps", false);
                        sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Warning user on low tps" + ChatColor.RED + " disabled");
                        saveYamls();
                    }
                } else {
                    //Invalid Usage
                    displayHelp(sender);
                }
                return true;
            }
            return false;
        } else if (cmd.getName().equalsIgnoreCase("gettps")){
            //Get TPS and Lag %
            if (args.length > 0){
                //Invalid Usage
                displayHelp(sender);
                return true;
            } else {
                //Do /gettps
                double tps = Lag.getTPS();
                double lagser = Math.round((1.0D - tps / 20.0D) * 100.0D);
                sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Server TPS: " + ChatColor.GOLD + tps);
                sender.sendMessage(pluginPrefix + ChatColor.LIGHT_PURPLE + "Server Lag: " + ChatColor.GOLD + lagser);
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("lagnotify")){
            //Show help
            displayHelp(sender);
            return true;
        }
        // If this hasn't happened the a value of false will be returned.
        return false;
    }

    //Server Status info
    public void serverStatus(CommandSender s){
        s.sendMessage(pluginPrefix + ChatColor.BLUE + "Getting plugin status");
        boolean wm1 = config.getBoolean("startwarn");
        boolean wm2 = config.getBoolean("startwarn2");
        boolean hrTPS = config.getBoolean("hourlyTPS");
        boolean lowTPSMsg = config.getBoolean("warn-msg-low-tps");
        int warnTPSLimit = config.getInt("warntps");
        String warnmsg1 = config.getString("warn-msg");
        String warnmsg2 = config.getString("warn-msg2");
        int pluginConfigVersion = config.getInt("version");
        if (wm1 == true){
            s.sendMessage(pluginPrefix + "Display 1st warning message: " + ChatColor.GREEN + "ON");
        } else {
            s.sendMessage(pluginPrefix + "Display 1st warning message: " + ChatColor.RED + "OFF");
        }
        if (wm2 == true){
            s.sendMessage(pluginPrefix + "Display 2nd warning message: " + ChatColor.GREEN + "ON");
        } else {
            s.sendMessage(pluginPrefix + "Display 2nd warning message: " + ChatColor.RED + "OFF");
        }
        if (hrTPS == true){
            s.sendMessage(pluginPrefix + "Display hourly TPS Message: " + ChatColor.GREEN + "ON");
        } else {
            s.sendMessage(pluginPrefix + "Display hourly TPS Message: " + ChatColor.RED + "OFF");
        }
        if (lowTPSMsg == true){
            s.sendMessage(pluginPrefix + "Display TPS Message on low TPS: " + ChatColor.GREEN + "ON");
        } else {
            s.sendMessage(pluginPrefix + "Display TPS Message on low TPS: " + ChatColor.RED + "OFF");
        }
        s.sendMessage(pluginPrefix + "Plugin Configuration Version: " + ChatColor.AQUA + pluginConfigVersion);
        s.sendMessage(pluginPrefix + "TPS Limit to show Low TPS Message: " + ChatColor.AQUA + warnTPSLimit);
        s.sendMessage(pluginPrefix + "1st warning message: " + ChatColor.LIGHT_PURPLE + warnmsg1);
        s.sendMessage(pluginPrefix + "2nd warning message: " + ChatColor.LIGHT_PURPLE + warnmsg2);
    }

    public void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.GOLD + "-----------FTBLagNotify Commands-----------");
        s.sendMessage(ChatColor.GOLD + "/lagnotify: " + ChatColor.WHITE + "Main plugin command");
        s.sendMessage(ChatColor.GOLD + "/warnlag [1/2]: " + ChatColor.WHITE + "Activate/Deactivate Lag warnings (defaults to msg 1)");
        s.sendMessage(ChatColor.GOLD + "/warnlag list: " + ChatColor.WHITE + "List warn messages");
        s.sendMessage(ChatColor.GOLD + "/warnlag reload: " + ChatColor.WHITE + "Reloads plugin");
        s.sendMessage(ChatColor.GOLD + "/warnlag hourly: " + ChatColor.WHITE + "Activate/Deactivate display of TPS hourly");
        s.sendMessage(ChatColor.GOLD + "/warnlag lowtpsmsg: " + ChatColor.WHITE + "Activate/Deactivate Low TPS messages to users");
        s.sendMessage(ChatColor.GOLD + "/warnlag status: " + ChatColor.WHITE + "Display plugin status");
        s.sendMessage(ChatColor.GOLD + "/gettps: " + ChatColor.WHITE + "Get current server tick");
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabling Plugin...");
        saveYamls();
    }

    private void firstRun() throws Exception{
        if (!configFile.exists()){
            configFile.getParentFile().mkdirs();
            copy(getResource("config.yml"), configFile);
            config.set("warn-msg", "The server currently has extremely low server ticks and is lagging. The Admins has been notified and are currently looking into the issue.");
            config.set("warntps", 12);
            config.set("startwarn", false);				//Display first warning message
            config.set("startwarn2", false);			//Display second warning message
            config.set("hourlyTPS", true);				//Display the TPS message hourly
            config.set("version", 2);					//Config file version number
            config.set("warn-msg-low-tps", true);		//Showing warn-msg on low tps
            config.set("warn-msg2", "There might be a little bit of lag occurring, please do not panic.");
            saveYamls();
        }
    }

    private void updateConfig(){
        config.set("hourlyTPS", true);	//Display the TPS message hourly
        config.set("version", 2);	//Config file version number
        config.set("warn-msg-low-tps", true);		//Showing warn-msg on low tps
    }

    private void copy(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveYamls() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadYamls() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
