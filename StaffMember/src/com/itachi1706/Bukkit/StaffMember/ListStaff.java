package com.itachi1706.Bukkit.StaffMember;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kenneth on 17/7/2015.
 * for StaffMember in com.itachi1706.Bukkit.StaffMember
 */
public class ListStaff implements CommandExecutor {

    @SuppressWarnings("unused")
    private Main plugin;

    public ListStaff(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("stafflist")) {
            if (args.length >= 1){
                displayHelp(sender);
                return true;
            }
            if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
                PermissionManager pex = PermissionsEx.getPermissionManager();
                getStaff(sender, pex);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "PermissionsEx is not detected! This command cannot be used!");
                return true;
            }
        }
        return false;
    }

    public void getStaff(CommandSender s, PermissionManager pex){
        Set<PermissionUser> modd = pex.getUsers("Mod");
        Set<PermissionUser> admind = pex.getUsers("Admin");
        Set<PermissionUser> opd = pex.getUsers("gameOP");
        Set<PermissionUser> ownerd = pex.getUsers("Owner");
        Set<PermissionUser> hostd = pex.getUsers("Host");
        Set<PermissionUser> ytd = pex.getUsers("YouTuber");
        List<PermissionUser> mod = new ArrayList<PermissionUser>(modd);
        List<PermissionUser> admin = new ArrayList<PermissionUser>(admind);
        List<PermissionUser> op = new ArrayList<PermissionUser>(opd);
        List<PermissionUser> owner = new ArrayList<PermissionUser>(ownerd);
        List<PermissionUser> host = new ArrayList<PermissionUser>(hostd);
        List<PermissionUser> yt = new ArrayList<PermissionUser>(ytd);
        String admins = "";
        String ops = "";
        String owners = "";
        String mods = "";
        String yts = "";
        String hosts = "";
        for (int i = 0; i < mod.size(); i++){
            PermissionUser se = mod.get(i);
            String name = se.getName();
            mods = mods + name + " ";
        }
        for (int i = 0; i < admin.size(); i++){
            PermissionUser se = admin.get(i);
            String name = se.getName();
            admins = admins + name + " ";
        }
        for (int i = 0; i < op.size(); i++){
            PermissionUser se = op.get(i);
            String name = se.getName();
            ops = ops + name + " ";
        }
        for (int i = 0; i < owner.size(); i++){
            PermissionUser se = owner.get(i);
            String name = se.getName();
            owners = owners + name + " ";
        }
        for (int i = 0; i < host.size(); i++){
            PermissionUser se = host.get(i);
            String name = se.getName();
            hosts = hosts + name + " ";
        }
        for (int i = 0; i < yt.size(); i++){
            PermissionUser se = yt.get(i);
            String name = se.getName();
            yts = yts + name + " ";
        }
        if (admins.length() == 0){
            admins = "There are no players with the rank of ADMIN";
        }
        if (ops.length() == 0){
            ops = "There are no players with the rank of OP";
        }
        if (owners.length() == 0){
            owners = "There are no players with the rank of OWNER";
        }
        if (mods.length() == 0){
            mods = "There are no players with the rank of MOD";
        }
        if (yts.length() == 0){
            yts = "There are no players with the rank of YT";
        }
        if (hosts.length() == 0){
            hosts = "There are no players with the rank of HOST";
        }
        s.sendMessage(ChatColor.GREEN + "Staff Members of the server:");
        s.sendMessage(ChatColor.DARK_RED + "Owners: " + owners);
        s.sendMessage(ChatColor.DARK_BLUE + "OPs: " + ops);
        s.sendMessage(ChatColor.RED + "Admins: " + admins);
        s.sendMessage(ChatColor.DARK_GREEN + "Mods: " + mods);
        s.sendMessage(ChatColor.GOLD + "YouTubers: " + yts);
        s.sendMessage(ChatColor.DARK_AQUA + "Hosts: " + hosts);
    }

    public void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.RED + "Usage: /stafflist");
        if (s.hasPermission("staffmember.admin")){
            s.sendMessage(ChatColor.GREEN + "Do /staffmember help to see all the commands");
        } else {
            s.sendMessage(ChatColor.GREEN + "Do /staffmember commands to see all the commands");
        }
    }

}
