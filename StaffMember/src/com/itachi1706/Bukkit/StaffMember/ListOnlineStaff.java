package com.itachi1706.Bukkit.StaffMember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Kenneth on 17/7/2015.
 * for StaffMember in com.itachi1706.Bukkit.StaffMember
 */
public class ListOnlineStaff implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public ListOnlineStaff(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("staffonline")) {
            if (args.length >= 1){
                displayHelp(sender);
                return true;
            }

            getOnlineStaff(sender);
            return true;
        }
        return false;
    }

    public void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.RED + "Usage: /staffonline");
        if (s.hasPermission("staffmember.admin")){
            s.sendMessage(ChatColor.GREEN + "Do /staffmember help to see all the commands");
        } else {
            s.sendMessage(ChatColor.GREEN + "Do /staffmember commands to see all the commands");
        }
    }

    public void getOnlineStaff(CommandSender s){
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        Iterator<? extends Player> i = playerList.iterator();
        ArrayList<String> ops = new ArrayList<String>();
        ArrayList<String> admins = new ArrayList<String>();
        ArrayList<String> owners = new ArrayList<String>();
        ArrayList<String> mods = new ArrayList<String>();
        ArrayList<String> yts = new ArrayList<String>();
        String admin = "";
        String op = "";
        String owner = "";
        String mod = "";
        String yt = "";
        while (i.hasNext()){
            Player p = i.next();
            if (p.hasPermission("staffmember.isowner")){
                owners.add(p.getDisplayName());
            } else if (p.hasPermission("staffmember.isop")){
                ops.add(p.getDisplayName());
            } else if (p.hasPermission("staffmember.isadmin")){
                admins.add(p.getDisplayName());
            } else if (p.hasPermission("staffmember.ismod")){
                mods.add(p.getDisplayName());
            } else if (p.hasPermission("staffmember.isyt")){
                yts.add(p.getDisplayName());
            }
        }
        for (int i1 = 0; i1 < ops.size(); i1++){
            String tmp = ops.get(i1);
            op = op + tmp + " ";
        }
        for (int i1 = 0; i1 < admins.size(); i1++){
            String tmp = admins.get(i1);
            admin = admin + tmp + " ";
        }
        for (int i1 = 0; i1 < owners.size(); i1++){
            String tmp = owners.get(i1);
            owner = owner + tmp + " ";
        }
        for (int i1 = 0; i1 < mods.size(); i1++){
            String tmp = mods.get(i1);
            mod = mod + tmp + " ";
        }
        for (int i1 = 0; i1 < yts.size(); i1++){
            String tmp = yts.get(i1);
            yt = yt + tmp + " ";
        }
        if (admin.length() == 0){
            admin = "None is currently online";
        }
        if (op.length() == 0){
            op = "None is currently online";
        }
        if (owner.length() == 0){
            owner = "None is currently online";
        }
        if (mod.length() == 0){
            mod = "None is currently online";
        }
        if (yt.length() == 0){
            yt = "None is currently online";
        }

        s.sendMessage(ChatColor.GREEN + "Staff Members currently online:");
        s.sendMessage(ChatColor.DARK_RED + "Owners: " + ChatColor.WHITE + owner);
        s.sendMessage(ChatColor.DARK_BLUE + "OPs: " + ChatColor.WHITE + op);
        s.sendMessage(ChatColor.RED + "Admins: " + ChatColor.WHITE + admin);
        s.sendMessage(ChatColor.DARK_GREEN + "Mods: " + ChatColor.WHITE + mod);
        s.sendMessage(ChatColor.GOLD + "YouTubers: " + ChatColor.WHITE + yt);

    }

}
