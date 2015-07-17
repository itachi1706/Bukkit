package com.itachi1706.Bukkit.PEXHelper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

/**
 * Created by Kenneth on 17/7/2015.
 * for PEXHelper in com.itachi1706.Bukkit.PEXHelper
 */
public class Main extends JavaPlugin implements Listener, PluginMessageListener {

    static double pluginVersion = 1;
    static File ackFile;
    static FileConfiguration acknowledgement;

    public static String commandBungeeMsg;
    public static boolean configWhitelist;

    @Override
    public void onEnable(){
        //Logic when plugin gets enabled
        getLogger().info("Enabling Plugin...");
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.saveConfig();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            getLogger().info("Detected PermissionsEx!");
        } else {
            getLogger().severe("PEX is not detected. This plugin will not work without PEX!");
            onDisable();
        }
        getServer().getPluginManager().registerEvents(this, this);
        ackFile = new File(getDataFolder(), "ack.yml");
        try {
            firstRun();
        } catch (Exception e){
            e.printStackTrace();
        }
        acknowledgement = new YamlConfiguration();
        configWhitelist = this.getConfig().getBoolean("whitelist");
        loadYamls();
        getCommand("acknowledge").setExecutor(new AckCmd(this));
        getCommand("grank").setExecutor(new GlobalRankCmd(this));
        getCommand("rank").setExecutor(new RankCmd(this));

    }

    private void firstRun() throws Exception{
        if (!ackFile.exists()){
            ackFile.getParentFile().mkdirs();
            copy(getResource("staff.yml"), ackFile);
        }
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
            acknowledgement.save(ackFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadYamls() {
        try {
            acknowledgement.load(ackFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
        //Logic when plugin gets disabled
        getLogger().info("Disabling Plugin...");
        this.saveConfig();
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Main Command
        if (cmd.getName().equalsIgnoreCase("pexhelper")){
            if (args.length < 1 || args.length > 1){
                displayHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                //pexhelper reload
                this.reloadConfig();
                configWhitelist = this.getConfig().getBoolean("whitelist");
                sender.sendMessage(ChatColor.GREEN + "Plugin configuration reloaded");
                sender.sendMessage(ChatColor.BLUE + "Whitelist update here: " + configWhitelist);
                return true;
            } else if (args[0].equalsIgnoreCase("help")){
                displayHelp(sender);
                return true;
            } else {
                displayHelp(sender);
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("perm")){
            sender.sendMessage(ChatColor.DARK_RED + "Feature coming soon");
            return true;
        } else if (cmd.getName().equalsIgnoreCase("gperm")){
            sender.sendMessage(ChatColor.DARK_RED + "Feature coming soon");
            return true;
        }
        return false;
    }

    public void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.GOLD + "-----------PEXHelper Commands-----------");
        s.sendMessage(ChatColor.GOLD + "/pexhelper reload: " + ChatColor.WHITE +  "Reload Config");
        s.sendMessage(ChatColor.GOLD + "/pexhelper help: " + ChatColor.WHITE +  "Get Help");
        s.sendMessage(ChatColor.GOLD + "/acknowledge <player>: " + ChatColor.WHITE +  "Acknowledges a player and auto whitelist (survival) and promote to member rank");
        s.sendMessage(ChatColor.GOLD + "/grank <player> <rank>: " + ChatColor.WHITE +  "Set rank across all servers connected");
        s.sendMessage(ChatColor.GOLD + "/rank <player> <rank>: " + ChatColor.WHITE +  "Set rank locally");
    }

    @SuppressWarnings("unused")
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        try {
            String subChannel = in.readUTF();
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            String somedata = msgin.readUTF(); // Read the data in the same way you wrote it
            short somenumber = msgin.readShort();
            if (subChannel.equals("PEXHelper")){
                if (!somedata.contains("whitelist")){
                    //Not whitelist command
                    getServer().dispatchCommand(getServer().getConsoleSender(), somedata);
                } else {
                    if (configWhitelist){
                        getServer().dispatchCommand(getServer().getConsoleSender(), somedata);
                    }
                }
            } else {
                return;
            }
        } catch (IOException e) {
            // There was an issue in creating the subchannel string
        }
    }

}
