package com.itachi1706.Bukkit.MemberSigns;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;

/**
 * Created by Kenneth on 17/7/2015.
 * for MemberSigns in com.itachi1706.Bukkit.MemberSigns
 */
public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable(){
        //Logic when plugin gets enabled
        getLogger().info("Enabling Plugin...");
        getServer().getPluginManager().registerEvents(this, this);
        if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            getLogger().info("Detected PermissionsEx! Enabling AutoObtaining Ranks!");

        }
        getLogger().info("Plugin is running!");
    }

    @Override
    public void onDisable(){
        //Logic when plugin gets disabled
        getLogger().info("Disabling Plugin...");
    }

    @EventHandler
    public void memberSignSet(final SignChangeEvent e){
        if (!(e.getPlayer().hasPermission("membersigns.admin"))){
            return;
        }
        if (e.isCancelled())
        {
            return;
        }
        if (!(ChatColor.stripColor(e.getLine(0)).equalsIgnoreCase("[member]")))
        {
            if (!(ChatColor.stripColor(e.getLine(0)).equalsIgnoreCase("[staff]"))){
                return;
            } else {
                //Staff (Famous etc)
                getLogger().info("Getting staff value...");
                String member = ChatColor.stripColor(e.getLine(1));
                String title = ChatColor.stripColor(e.getLine(2));
                ArrayList<String> ranksd = position(title);
                World world = e.getPlayer().getWorld();
                getLogger().info("Getting sign location...");
                Sign sign = (Sign) e.getBlock().getState();
                int x = sign.getX();
                int y = sign.getY();
                int z = sign.getZ();
                e.setLine(0, ChatColor.GREEN + member);	//Name
                e.setLine(1, "X----X----X");			//Design
                e.setLine(2, ranksd.get(0));				//Title 1
                e.setLine(3, ranksd.get(1));				//Title 2
                sign.update();
                getLogger().info("Sign updated!");
                getLogger().info("Setting head...");
                setHead(member, ranksd, x, (y+1), z, world);
                getLogger().info("Head set!");
                return;
            }
        }
        //Member
        getLogger().info("Getting value...");
        String member = ChatColor.stripColor(e.getLine(1));
        String rank1 = ChatColor.stripColor(e.getLine(2));
        String ranker = ChatColor.stripColor(e.getLine(3));
        String ranked = "";
        if (ranker.equals("")){
            ranked = getRank(ranker, e.getLine(1));
        } else {
            ranked = getRank(ranker);
        }
        ArrayList<String> rank = rankings(rank1);
        World world = e.getPlayer().getWorld();
        getLogger().info("Getting sign location...");
        Sign sign = (Sign) e.getBlock().getState();
        int x = sign.getX();
        int y = sign.getY();
        int z = sign.getZ();
        e.setLine(0, ChatColor.GREEN + member);	//Name
        e.setLine(1, "X----X----X");			//Design
        e.setLine(2, rank.get(0));				//Title 1
        e.setLine(3, rank.get(1));				//Title 2
        sign.update();
        getLogger().info("Sign updated!");
        getLogger().info("Setting head...");
        changeSign(member, rank, ranked, x, y, z, sign, world);
        getLogger().info("Head set!");
        return;
    }

    public void setHead(String name, ArrayList<String> rank, int x, int y, int z, World w){
        Block b = w.getBlockAt(x,y,z);
        if(b.getType().equals(Material.SKULL))		//Change Player head
        {
            Skull skull = (Skull)b.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(name);
            skull.update(true);
            getLogger().info("Skull updated.");
        }
    }

    public void changeSign(String name, ArrayList<String> rank, String ranking, int x, int y, int z, Sign b, World w){
        Block blo = (Block) w.getBlockAt(x, y+1, z);
        Block sg = w.getBlockAt(x, y+2, z);	//Sign to display rank
        Location loc = sg.getLocation();
        if (sg.getType().equals(Material.WALL_SIGN)){	//Rank
            getLogger().info("Getting sign state.");
            Sign s = (Sign) sg.getState();
            getLogger().info("Changing text on the thing");
            s.setLine(1, ranking);
            getLogger().info("Updating sign...");
            s.update();
            loc.getBlock().setType(Material.WALL_SIGN);
            getLogger().info("Top Wall Sign updated.");
        }
        getLogger().info("Sign Updated.");
        if(blo.getType().equals(Material.SKULL))		//Change Player head
        {
            Skull skull = (Skull)blo.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(name);
            skull.update(true);
            getLogger().info("Skull updated.");
        }

    }

    public String getRank(String r,String n){
        if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            //Use name
            PermissionManager pex = PermissionsEx.getPermissionManager();
            PermissionUser target = pex.getUser(n);
            if (target == null){
                getLogger().info("Player not found in PEX File, reverting down to fallback...");
            } else {
                String result = target.getPrefix();
                result = ChatColor.translateAlternateColorCodes('&', result);
                return result;
            }
        }
        //Use rank written or fallbacking
        String result;
        if (r.equalsIgnoreCase("owner")){
            result = ChatColor.DARK_RED + "[OWNER]";
        } else if (r.equalsIgnoreCase("host")){
            result = ChatColor.DARK_AQUA + "[HOST]";
        } else if (r.equalsIgnoreCase("gameOP")){
            result = ChatColor.GOLD + "[OP]";
        } else if (r.equalsIgnoreCase("mod")){
            result = ChatColor.DARK_GREEN + "[MOD]";
        } else if (r.equalsIgnoreCase("admin")){
            result = ChatColor.RED + "[ADMIN]";
        } else if (r.equalsIgnoreCase("member")){
            result = ChatColor.BLUE + "[MEMBER]";
        } else if (r.equalsIgnoreCase("guest")){
            result = ChatColor.LIGHT_PURPLE + "[GUEST]";
        } else if (r.equalsIgnoreCase("youtuber")){
            result = ChatColor.DARK_PURPLE + "[YOUTUBER]";
        } else if (r.equalsIgnoreCase("donator")){
            result = ChatColor.GREEN + "[DONATOR]";
        } else {
            result = ChatColor.MAGIC + "ERROR";
        }
        return result;
    }

    public String getRank(String r){
        String result;
        if (r.equalsIgnoreCase("owner")){
            result = ChatColor.DARK_RED + "[OWNER]";
        } else if (r.equalsIgnoreCase("host")){
            result = ChatColor.DARK_AQUA + "[HOST]";
        } else if (r.equalsIgnoreCase("gameOP")){
            result = ChatColor.GOLD + "[OP]";
        } else if (r.equalsIgnoreCase("mod")){
            result = ChatColor.DARK_GREEN + "[MOD]";
        } else if (r.equalsIgnoreCase("admin")){
            result = ChatColor.RED + "[ADMIN]";
        } else if (r.equalsIgnoreCase("member")){
            result = ChatColor.BLUE + "[MEMBER]";
        } else if (r.equalsIgnoreCase("guest")){
            result = ChatColor.LIGHT_PURPLE + "[GUEST]";
        } else if (r.equalsIgnoreCase("youtuber")){
            result = ChatColor.DARK_PURPLE + "[YOUTUBER]";
        } else if (r.equalsIgnoreCase("donator")){
            result = ChatColor.GREEN + "[DONATOR]";
        } else {
            result = ChatColor.MAGIC + "ERROR";
        }
        return result;
    }

    public ArrayList<String> rankings(String rank){
        ArrayList<String> result = new ArrayList<String>();
        if (rank.equalsIgnoreCase("[DEV]")){
            result.add("Server");
            result.add("Developer");
        } else if (rank.equalsIgnoreCase("[ADMIN]")){
            result.add("Server");
            result.add("Administrator");
        } else if (rank.equalsIgnoreCase("[OWNER]")){
            result.add("Server");
            result.add("Owner");
        } else if (rank.equalsIgnoreCase("[MOD]")){
            result.add("Server");
            result.add("Moderator");
        } else if (rank.equalsIgnoreCase("[YT]")){
            result.add("Server");
            result.add("YouTuber");
        } else if (rank.equalsIgnoreCase("[MEMBER]")){
            result.add("Server");
            result.add("Member");
        } else if (rank.equalsIgnoreCase("[GUEST]")){
            result.add("Server");
            result.add("Guest");
        } else if (rank.equalsIgnoreCase("[OP]")){
            result.add("Server");
            result.add("Operator");
        } else if (rank.equalsIgnoreCase("[OLD]")){
            result.add("Former");
            result.add("Member");
        } else if (rank.equalsIgnoreCase("[WHITELISTED]")){
            result.add("Whitelisted");
            result.add("Member");
        } else if (rank.equalsIgnoreCase("[MOD/OP]")){
            result.add("Server");
            result.add("Moderator/OP");
        } else if (rank.equalsIgnoreCase("[ADMIN/OP]")){
            result.add("Server");
            result.add("Operator/Admin");
        } else if (rank.equalsIgnoreCase("[TEST]")){
            result.add("Server");
            result.add("Test Acct");
        } else if (rank.equalsIgnoreCase("[PLUGIN]")){
            result.add("Server Plugins");
            result.add("Tester");
        } else if (rank.equalsIgnoreCase("[DONATOR]")){
            result.add("Server");
            result.add("Donator");
        } else{
            result.add("ERROR");
            result.add("ERROR");
        }
        return result;

    }

    public ArrayList<String> position(String rank){
        ArrayList<String> result = new ArrayList<String>();
        if (rank.equalsIgnoreCase("[MC]")){
            result.add(ChatColor.GOLD + "MindCracker");
            result.add("");
        } else if (rank.equalsIgnoreCase("[MCO]")){
            result.add(ChatColor.RED + "Opped");
            result.add(ChatColor.GOLD + "MindCracker");
        } else if (rank.equalsIgnoreCase("[MCF]")){
            result.add(ChatColor.GOLD + "MindCracker");
            result.add(ChatColor.DARK_GREEN + "Founder & OP");
        } else if (rank.equalsIgnoreCase("[FMC]")){
            result.add(ChatColor.AQUA + "Former");
            result.add(ChatColor.AQUA + "MindCracker");
        } else if (rank.equalsIgnoreCase("[MCW]")){
            result.add("Whitelisted");
            result.add("MindCracker");
        } else if (rank.equalsIgnoreCase("[MCGCA]")){
            result.add("Guude Camera");
            result.add("Account");
        } else if (rank.equalsIgnoreCase("[MCZCA]")){
            result.add("Zisteau Camera");
            result.add("Account");
        } else if (rank.equalsIgnoreCase("[MCBCA]")){
            result.add("BlameTC Camera");
            result.add("Account");
        } else if (rank.equalsIgnoreCase("[MCWH]")){
            result.add("MCN Weekly");
            result.add("Recap Host");
        } else if (rank.equalsIgnoreCase("[MCVCA]")){
            result.add("Vechs_ Camera");
            result.add("Account");
        } else if (rank.equalsIgnoreCase("[MCOCA]")){
            result.add("BDoubleO100");
            result.add("Camera Account");
        } else if (rank.equalsIgnoreCase("[MCACA]")){
            result.add("Arkas Camera");
            result.add("Account");
        } else if (rank.equalsIgnoreCase("[EvilSeph]")){
            result.add(ChatColor.DARK_PURPLE + "Bukkit Team");
            result.add("Former" + ChatColor.GOLD + " Mojang");
        } else if (rank.equalsIgnoreCase("[marc]")){
            result.add(ChatColor.BLUE + "Customer");
            result.add(ChatColor.BLUE + "Support Head");
        } else if (rank.equalsIgnoreCase("[xlson]")){
            result.add(ChatColor.GOLD + "Mojang " + ChatColor.BLUE + "Web");
            result.add(ChatColor.BLUE + "Developer");
        } else if (rank.equalsIgnoreCase("[c418]")){
            result.add(ChatColor.BLUE + "Minecraft Music");
            result.add(ChatColor.BLUE + "Maker");
        } else if (rank.equalsIgnoreCase("[dinnerbone]")){
            result.add(ChatColor.BLUE + "Minecraft");
            result.add(ChatColor.BLUE + "Developer");
        } else if (rank.equalsIgnoreCase("[notch]")){
            result.add(ChatColor.GOLD + "Minecraft");
            result.add(ChatColor.GOLD + "Founder");
        } else if (rank.equalsIgnoreCase("[jeb]")){
            result.add(ChatColor.BLUE + "MC Lead");
            result.add(ChatColor.BLUE + "Developer");
        } else if (rank.equalsIgnoreCase("[MCC]")){
            result.add(ChatColor.BLUE + "Director");
            result.add(ChatColor.BLUE + "of Fun");
        } else if (rank.equalsIgnoreCase("[HYA]")){
            result.add(ChatColor.RED + "Hypixel");
            result.add(ChatColor.RED + "Administrator");
        } else if (rank.equalsIgnoreCase("[HYO]")){
            result.add(ChatColor.RED + "Hypixel");
            result.add(ChatColor.RED + "Owner");
        } else if (rank.equalsIgnoreCase("[HYD]")){
            result.add(ChatColor.RED + "Hypixel");
            result.add(ChatColor.RED + "Developer");
        } else if (rank.equalsIgnoreCase("[HYM]")){
            result.add(ChatColor.RED + "Hypixel");
            result.add(ChatColor.DARK_GREEN + "Moderator");
        } else if (rank.equalsIgnoreCase("[HYH]")){
            result.add(ChatColor.RED + "Hypixel");
            result.add(ChatColor.BLUE + "Helper");
        } else if (rank.equalsIgnoreCase("[OWNER]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "Owner");
        } else if (rank.equalsIgnoreCase("[OP]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "Operator");
        } else if (rank.equalsIgnoreCase("[ADMIN/OP]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "OP/Admin");
        } else if (rank.equalsIgnoreCase("[MOD/OP]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "Moderator/OP");
        } else if (rank.equalsIgnoreCase("[MOD]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "Moderator");
        } else if (rank.equalsIgnoreCase("[DEV]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "Developer");
        } else if (rank.equalsIgnoreCase("[ADMIN]")){
            result.add(ChatColor.DARK_RED + "Server");
            result.add(ChatColor.DARK_RED + "Administrator");
        } else if (rank.equalsIgnoreCase("[TEST]")){
            result.add("Server");
            result.add("Test Acct");
        } else if (rank.equalsIgnoreCase("[PLUGIN]")){
            result.add("Server Plugins");
            result.add("Tester");
        } else{
            result.add(ChatColor.GOLD + "UNRECGONIZED");
            result.add(ChatColor.GOLD + "UPDATE PLUGIN");
        }
        return result;
    }

}
