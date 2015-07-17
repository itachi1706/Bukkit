package com.itachi1706.Bukkit.CheesecakeMinigameLobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Created by Kenneth on 17/7/2015.
 * for CheesecakeMinigameLobby in com.itachi1706.Bukkit.CheesecakeMinigameLobby
 */
public class AdvMapActions implements Listener {

    @EventHandler
    private void joinAdvMap(PlayerJoinEvent e){
        Player p = e.getPlayer();
        welcomeMessage(p);
    }

    private void welcomeMessage(Player p){
        String prefix = "";
        if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            //Use name
            PermissionManager pex = PermissionsEx.getPermissionManager();
            PermissionUser target = pex.getUser(p);
            if (target == null){
                Bukkit.getLogger().info("Player not found in PEX File, reverting down to fallback...");
            } else {
                String result = target.getPrefix();
                prefix = ChatColor.translateAlternateColorCodes('&', result);
            }
        }
        for (int i = 0; i < Main.advmsg.size(); i++){
            String welMsg = Main.advmsg.get(i);
            welMsg = welMsg.replaceAll("%prefix%", prefix);
            welMsg = welMsg.replaceAll("%playername%", p.getDisplayName());
            welMsg = welMsg.replaceAll("%mapauthor%", Main.advMapAuthor);
            welMsg = welMsg.replaceAll("%mapname%", Main.advMapName);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', welMsg));
        }
    }


}
