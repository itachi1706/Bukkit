package com.itachi1706.Bukkit.FTBOpJoinAnnounce;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

/**
 * Created by Kenneth on 17/7/2015.
 * for FTBOPJoinAnnounce in com.itachi1706.Bukkit.FTBOpJoinAnnounce
 */
public class Main extends JavaPlugin implements Listener {

    static File configFile;
    static FileConfiguration config;
    public static String pluginPrefix = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "OPJoinAnnounce" + ChatColor.DARK_RED + "] " + ChatColor.WHITE;
    public static String opPrefix = ChatColor.DARK_RED + "[OP] ";
    public static String onJoinMsg = ChatColor.YELLOW + " joined the game";
    public static String onLeaveMsg = ChatColor.YELLOW + " left the game";
    public static String devPrefix = ChatColor.GOLD + "[" + ChatColor.DARK_RED + "D" + ChatColor.DARK_GREEN + "E" + ChatColor.DARK_BLUE + "V" + ChatColor.DARK_AQUA + "E"
            + ChatColor.DARK_PURPLE + "L" + ChatColor.AQUA + "O" + ChatColor.RED + "P" + ChatColor.BLUE + "E" + ChatColor.LIGHT_PURPLE + "R"
            + ChatColor.GOLD + "] ";
    public static String modPrefix = ChatColor.DARK_GREEN + "[MOD] ";
    public static String regPrefix = ChatColor.AQUA + "[TRUSTED] ";
    public static String opPrefix1 = ChatColor.DARK_RED + "[OP]";
    public static String modPrefix1 = ChatColor.DARK_GREEN + " [MOD]";
    public static String regPrefix1 = ChatColor.AQUA + " [TRUSTED]";

    @Override
    public void onEnable(){
        getLogger().info("Enabling Plugin...");
        configFile = new File(getDataFolder(), "config.yml");
        //Op File Config
        config = new YamlConfiguration();
        try {
            firstRun();
        } catch (Exception e){
            e.printStackTrace();
        }
        loadYamls();
        getLogger().info("Enabling listners...");
        init();
        getLogger().info("Listners enabled!");
        ScoreboardHelper.ensureTeamsAvailable();
        for (Player p : this.getServer().getOnlinePlayers()){
            //Check if player in config
            if (!(config.isBoolean("players." + p.getName()))){
                config.set("players." + p.getName(), false);
            }
        }
    }

    public void init(){
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Main Command
        if(cmd.getName().equalsIgnoreCase("opjoin")){
            if (args.length < 1 || args.length > 1){
                displayHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                //Reloads Plugin
                disableCommand(this);
                loadYamls();
                init();
                sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Plugin reloaded");
                return true;
            } else {
                //Error
                displayHelp(sender);
                return true;
            }
        }

        //Enable/Disable Admin Mode
        if (cmd.getName().equals("duty")){
            if (!sender.hasPermission("opjoinannounce.duty")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
                return true;
            }
            if (args.length > 1){
                sender.sendMessage(ChatColor.RED + "Enables/Disable admin duty mode. Usage: /duty [-s]");
                return true;
            }
            if (!(sender instanceof Player)){
                sender.sendMessage(ChatColor.DARK_RED + "You must be an administrator in game to use this command!");
                return true;
            }
            if (args.length == 0){
                if (config.getBoolean("players." + sender.getName())){
                    config.set("players." + sender.getName(), false);
                    sender.sendMessage(ChatColor.DARK_AQUA + "Administrator Mode: " + ChatColor.RED + "Disabled");
                } else {
                    config.set("players." + sender.getName(), true);
                    sender.sendMessage(ChatColor.DARK_AQUA + "Administrator Mode: " + ChatColor.GREEN + "Enabled");
                }
                saveYamls();
            } else if (args.length == 1 && args[0].equalsIgnoreCase("-a")){
                if (config.getBoolean("players." + sender.getName())){
                    config.set("players." + sender.getName(), false);
                    sendAdminMsg("&7&o[" + sender.getName() + "] Disabled Admin Mode", sender);
                    sender.sendMessage(ChatColor.DARK_AQUA + "Administrator Mode: " + ChatColor.RED + "Disabled");
                } else {
                    config.set("players." + sender.getName(), true);
                    sendAdminMsg("&7&o[" + sender.getName() + "] Enabled Admin Mode", sender);
                    sender.sendMessage(ChatColor.DARK_AQUA + "Administrator Mode: " + ChatColor.GREEN + "Enabled");
                }
                saveYamls();
            } else {
                sender.sendMessage(ChatColor.RED + "Enables/Disable admin duty mode. Usage: /duty [-s]");
                return true;
            }
            return true;
        }

        //Check Mojang Servers
        if (cmd.getName().equals("mojang")){
            if (args.length < 1 || args.length > 1){
                displayHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("status")){
                //Does /mojang status
                sender.sendMessage(ChatColor.GOLD + "==================================================");
                sender.sendMessage(ChatColor.BLUE + "                Mojang Server Status");
                sender.sendMessage(ChatColor.GOLD + "==================================================");
                for (MojangStatusChecker statusChecker : MojangStatusChecker.values()) {
                    String service = statusChecker.getName();
                    MojangStatusChecker.Status status = statusChecker.getStatus(false);

                    sender.sendMessage(service + ": " + status.getColor() + status.getStatus() + " - " + status.getDescription());
                }
                sender.sendMessage(ChatColor.GOLD + "==================================================");
                return true;
            } else {
                displayHelp(sender);
                return true;
            }
        }

        //Enable/Disable OP Announce on join/leave
        if (cmd.getName().equalsIgnoreCase("joinannounce")){
            if (args.length < 1 || args.length > 1){
                //Invalid
                invalidOpCommand(sender);
                return true;
            }
            if (!sender.hasPermission("opjoinannounce.edit")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have the permission to use this command");
                return true;
            } else {
                //Enables OP
                if (args[0].equalsIgnoreCase("op")){
                    if (config.getBoolean("opjoin") == true){
                        //Disable
                        config.set("opjoin", false);
                        sender.sendMessage(pluginPrefix + ChatColor.GOLD + "OP/Ranks Join Announcement " + ChatColor.RED + "disabled" + ChatColor.GOLD + "!");
                        return true;
                    } else {
                        //Enable
                        config.set("opjoin", true);
                        sender.sendMessage(pluginPrefix + ChatColor.GOLD + "OP/Ranks Join Announcement " + ChatColor.GREEN + "enabled" + ChatColor.GOLD + "!");
                        return true;
                    }
                }
                //Enables dev
                if (args[0].equalsIgnoreCase("dev")){
                    if (config.getBoolean("devjoin") == true){
                        //Disable
                        config.set("devjoin", false);
                        sender.sendMessage(pluginPrefix + ChatColor.GOLD + "Dev Join Announcement " + ChatColor.RED + "disabled" + ChatColor.GOLD + "!");
                        return true;
                    } else {
                        //Enable
                        config.set("devjoin", true);
                        sender.sendMessage(pluginPrefix + ChatColor.GOLD + "Dev Join Announcement " + ChatColor.GREEN + "enabled" + ChatColor.GOLD + "!");
                        return true;
                    }
                }
                //Enables status
                if (args[0].equalsIgnoreCase("status")){
                    sender.sendMessage(pluginPrefix + ChatColor.GOLD + "======================================");
                    sender.sendMessage(pluginPrefix + ChatColor.BLUE + "Getting status of Join Announcements...");
                    sender.sendMessage(pluginPrefix + ChatColor.GOLD + "======================================");
                    boolean statusDev, statusOp;
                    statusDev = config.getBoolean("devjoin");
                    statusOp = config.getBoolean("opjoin");

                    if (statusOp == true){
                        sender.sendMessage(pluginPrefix + "OP/Ranks: " + ChatColor.GREEN + "Enabled");
                    } else {
                        sender.sendMessage(pluginPrefix + "OP/Ranks: " + ChatColor.RED + "Disabled");
                    }

                    if (statusDev == true){
                        sender.sendMessage(pluginPrefix + "DEV: " + ChatColor.GREEN + "Enabled");
                    } else {
                        sender.sendMessage(pluginPrefix + "DEV: " + ChatColor.RED + "Disabled");
                    }

                    return true;
                }

                invalidOpCommand(sender);
                return true;
            }
        }
        return false;
    }

    public void invalidOpCommand(CommandSender s){
        s.sendMessage(pluginPrefix + ChatColor.RED + "Invalid Usage! Correct Usage:");
        s.sendMessage(ChatColor.GOLD + "/joinannounce op: " + ChatColor.WHITE + "Enables/Disables OP/Ranks (" + opPrefix1 + "," + modPrefix1 + "," + regPrefix1 + ChatColor.WHITE + ") Join Announcement");
        s.sendMessage(ChatColor.GOLD + "/joinannounce dev: " + ChatColor.WHITE + "Enables/Disables Dev Join Announcement");
        s.sendMessage(ChatColor.GOLD + "/joinannounce status: " + ChatColor.WHITE +  "Check current status of join announcements");
    }

    private void disableCommand(Plugin plugin){
        HandlerList.unregisterAll(plugin);
    }

    public void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.GOLD + "-----------FTBOpJoin Commands-----------");
        s.sendMessage(ChatColor.GOLD + "/opjoin: " + ChatColor.WHITE + "Main plugin command");
        s.sendMessage(ChatColor.GOLD + "/opjoin reload: " + ChatColor.WHITE + "Reloads plugin config");
        s.sendMessage(ChatColor.GOLD + "/joinannounce op: " + ChatColor.WHITE + "Enables/Disables OP/Ranks (" + opPrefix1 + "," + modPrefix1 + "," + regPrefix1 + ChatColor.WHITE + ") Join Announcement");
        s.sendMessage(ChatColor.GOLD + "/joinannounce dev: " + ChatColor.WHITE + "Enables/Disables Dev Join Announcement");
        s.sendMessage(ChatColor.GOLD + "/joinannounce status: " + ChatColor.WHITE +  "Check current status of join announcements");
        s.sendMessage(ChatColor.GOLD + "/mojang status: " + ChatColor.WHITE + "Gets the status of all Mojang Servers");
        s.sendMessage(ChatColor.GOLD + "/duty [-a]: " + ChatColor.WHITE + "Enable/Disable Administrative Mode (with announcement)");
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabling Plugin...");
        disableCommand(this);
        saveYamls();
    }

    private void firstRun() throws Exception{
        if (!configFile.exists()){
            configFile.getParentFile().mkdirs();
            copy(getResource("config.yml"), configFile);
            config.set("opjoin", true);
            config.set("devjoin", true);
            saveYamls();
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

    @EventHandler(priority=EventPriority.HIGHEST)
    public static void adminTalk(final AsyncPlayerChatEvent e){
        if (config.getBoolean("players." + e.getPlayer().getName()) == true){
            String msg = e.getFormat();
            msg = ChatColor.DARK_RED + "[SYSTEM-ADMIN] " + ChatColor.RESET + msg;
            e.setFormat(msg);
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public static void newOnJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        //Check for OPs
        if (config.getBoolean("opjoin") == true){
            if (p.isOp()){
                //Is an OP
                //OPs
                e.setJoinMessage(opPrefix + p.getDisplayName() + onJoinMsg);
            } else {
                //Not an OP
                boolean mod = ScoreboardHelper.checkIfMod(p);
                boolean reg = ScoreboardHelper.checkIfRegular(p);
                if (mod == true){
                    //Moderators
                    e.setJoinMessage(modPrefix + p.getDisplayName() + onJoinMsg);
                } else if (reg == true){
                    //Regulars
                    e.setJoinMessage(regPrefix + p.getDisplayName() + onJoinMsg);
                } else {
                    //Normal
                    e.setJoinMessage(ChatColor.YELLOW + p.getDisplayName() + onJoinMsg);
                }
            }
        } else {
            //Do not do a check for OP
            //Normal
            e.setJoinMessage(ChatColor.YELLOW + p.getDisplayName() + onJoinMsg);
        }

        //Check for DEVs
        if (p.getName().equalsIgnoreCase("itachi1706")){
            //Check if Dev Join is announced
            if (config.getBoolean("devjoin") == true){
                //Dev Join enabled
                //Check if OP msg enabled
                if (p.isOp() == true && config.getBoolean("opjoin") == true){
                    //OP + DEV
                    e.setJoinMessage(opPrefix + devPrefix + p.getDisplayName() + onJoinMsg);
                } else {
                    //DEV
                    e.setJoinMessage(devPrefix + p.getDisplayName() + onJoinMsg);
                }
            } else {
                //Check if OP join is valid
                if (p.isOp() == true && config.getBoolean("opjoin") == true){
                    //OP
                    e.setJoinMessage(opPrefix + p.getDisplayName() + onJoinMsg);
                }
            }
        }

        //Check if player in config
        if (!(config.isBoolean("players." + e.getPlayer().getName()))){
            config.set("players." + e.getPlayer().getName(), false);
            saveYamls();
        }
    }

    /*
     * Is both OP and Dev
     * Is OP only
     * Is Dev only
     * Is not OP or Dev
     */
    @EventHandler(priority=EventPriority.HIGH)
    public static void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        //Check for OPs
        if (config.getBoolean("opjoin") == true){
            if (p.isOp()){
                //Is an OP
                //OPs
                e.setQuitMessage(opPrefix + p.getDisplayName() + onLeaveMsg);
            } else {
                //Not an OP
                boolean mod = ScoreboardHelper.checkIfMod(p);
                boolean reg = ScoreboardHelper.checkIfRegular(p);
                if (mod == true){
                    //Moderators
                    e.setQuitMessage(modPrefix + p.getDisplayName() + onLeaveMsg);
                } else if (reg == true){
                    //Regulars
                    e.setQuitMessage(regPrefix + p.getDisplayName() + onLeaveMsg);
                } else {
                    //Normal
                    e.setQuitMessage(ChatColor.YELLOW + p.getDisplayName() + onLeaveMsg);
                }
            }
        } else {
            //Do not do a check for OP
            //Normal
            e.setQuitMessage(ChatColor.YELLOW + p.getDisplayName() + onLeaveMsg);
        }

        //Check for DEVs
        if (p.getName().equalsIgnoreCase("itachi1706")){
            //Check if Dev Join is announced
            if (config.getBoolean("devjoin") == true){
                //Dev Join enabled
                //Check if OP msg enabled
                if (p.isOp() == true && config.getBoolean("opjoin") == true){
                    //OP + DEV
                    e.setQuitMessage(opPrefix + devPrefix + p.getDisplayName() + onLeaveMsg);
                } else {
                    //DEV
                    e.setQuitMessage(devPrefix + p.getDisplayName() + onLeaveMsg);
                }
            } else {
                //Check if OP join is valid
                if (p.isOp() == true && config.getBoolean("opjoin") == true){
                    //OP
                    e.setQuitMessage(opPrefix + p.getDisplayName() + onLeaveMsg);
                }
            }
        }
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void dead(PlayerDeathEvent e){
        Player player = e.getEntity();
        String msgD = e.getDeathMessage();
        EntityDamageEvent.DamageCause dc = e.getEntity().getLastDamageCause().getCause();
        if (player.getKiller() != null){
            Player killer = player.getKiller();
            String weapon = "";
            if (killer.getItemInHand().getItemMeta().hasDisplayName()){
                weapon = killer.getItemInHand().getItemMeta().getDisplayName();
            } else {
                weapon = WordUtils.capitalize(killer.getItemInHand().getType().name().replaceAll("_", " ").replaceAll("AIR", "FIST").toLowerCase());
            }
            String[] msgS = msgD.split(" ", 2);
            String msg = "";
            for (int i = 1; i < msgS.length; i++){
                msg = msg + msgS[i] + " ";
            }
            if (dc == EntityDamageEvent.DamageCause.LAVA){
                //Lava Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " tried to swim in lava to escape " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            } else if (dc == EntityDamageEvent.DamageCause.FIRE || dc == EntityDamageEvent.DamageCause.FIRE_TICK){
                //Fire Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was burnt to a crisp whilst fighting " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            } else if (dc == EntityDamageEvent.DamageCause.CONTACT){
                //Cactus Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " walked into a cactus whilst trying to escape " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            } else if (dc == EntityDamageEvent.DamageCause.DROWNING){
                //Drowning Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " drowned whilst trying to escape " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            } else if (dc == EntityDamageEvent.DamageCause.THORNS){
                //Thorns Enchant Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed trying to hurt " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            } else if (dc == EntityDamageEvent.DamageCause.FALL){
                //Fall Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was doomed to fall by " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            } else if (dc == EntityDamageEvent.DamageCause.MAGIC){
                //Potion Damage
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed using magic by " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
            }else {
                e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed by " + killer.getDisplayName() + " with " + ChatColor.AQUA + weapon);
            }
        } else {
            String[] msgS = msgD.split(" ", 2);
            String msg = "";
            for (int i = 1; i < msgS.length; i++){
                msg = msg + msgS[i] + " ";
            }
            e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " " + msg);
        }
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
