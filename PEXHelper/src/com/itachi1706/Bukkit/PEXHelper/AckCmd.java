package com.itachi1706.Bukkit.PEXHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Kenneth on 17/7/2015.
 * for PEXHelper in com.itachi1706.Bukkit.PEXHelper
 */
public class AckCmd implements CommandExecutor {

    private Main plugin;

    public AckCmd(Main plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("acknowledge")){
            if (args.length > 1 || args.length < 1){
                sender.sendMessage(ChatColor.RED + "Usage: /acknowledge <player>");
                return true;
            }
            if (!sender.hasPermission("pexhelper.default")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
                return true;
            }
            PermissionManager pex = PermissionsEx.getPermissionManager();
            PermissionUser user = pex.getUser(args[0]);
            PermissionGroup[] grp = user.getGroups();
            boolean isNotGuest = false;
            for (PermissionGroup gr : grp){
                if (gr.getName().equalsIgnoreCase("guest")){
                    isNotGuest = true;
                    break;
                }
            }
            if (!isNotGuest){
                sender.sendMessage(ChatColor.RED + "Player is not a guest, and hence the rank cannot be edited!");
                return true;
            }
            String[] ack = {"Member"};
            user.setGroups(ack);
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            sender.sendMessage(ChatColor.GREEN + "Setting user group for " + ChatColor.GOLD + args[0]);
            try {
                out.writeUTF("Forward");
                out.writeUTF("ALL");
                out.writeUTF("PEXHelper");	//Channel name

                ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
                DataOutputStream msgout = new DataOutputStream(msgbytes);
                msgout.writeUTF("pex user " + args[0] + " group set Member"); // You can do anything you want with msgout
                msgout.writeShort(123);

                out.writeShort(msgbytes.toByteArray().length);
                out.write(msgbytes.toByteArray());
                Bukkit.getServer().sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                sender.sendMessage(ChatColor.RED + "An exception occured trying to send data out to the network");
                sender.sendMessage(ChatColor.RED + "Doing only local server now");
            }
            try {
                out.writeUTF("Forward");
                out.writeUTF("ALL");
                out.writeUTF("PEXHelper");	//Channel name

                ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
                DataOutputStream msgout = new DataOutputStream(msgbytes);
                msgout.writeUTF("whitelist add " + args[0]); // You can do anything you want with msgout
                msgout.writeShort(123);

                out.writeShort(msgbytes.toByteArray().length);
                out.write(msgbytes.toByteArray());
                Bukkit.getServer().sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                sender.sendMessage(ChatColor.RED + "An exception occured trying to send data out to the network");
                sender.sendMessage(ChatColor.RED + "Doing only local server now");
            }

            OfflinePlayer pla = Bukkit.getServer().getOfflinePlayer(args[0]);
            if (Main.configWhitelist){
                pla.setWhitelisted(true);
            }
            Main.acknowledgement.set(pla.getName() + ".acknowledged", sender.getName());
            sender.sendMessage(ChatColor.GOLD + pla.getName() + ChatColor.GREEN + " has been successfully acknowledged!");
            Main.saveYamls();
            return true;
        }
        return false;
    }

}
