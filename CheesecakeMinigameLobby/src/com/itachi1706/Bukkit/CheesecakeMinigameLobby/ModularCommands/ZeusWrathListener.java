package com.itachi1706.Bukkit.CheesecakeMinigameLobby.ModularCommands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Kenneth on 17/7/2015.
 * for CheesecakeMinigameLobby in com.itachi1706.Bukkit.CheesecakeMinigameLobby.ModularCommands
 */
public class ZeusWrathListener implements Listener {

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        boolean check = false;
        for (int i = 0; i < ZeusWrathCmd.playerListZeused.size(); i++){
            Player pla = ZeusWrathCmd.playerListZeused.get(i);
            if (pla == p){
                check = true;
                ZeusWrathCmd.playerListZeused.remove(i);
                break;
            }
        }
        if (check){
            e.setDeathMessage(p.getDisplayName() + ChatColor.WHITE + " suffered the Wrath of Zeus!");
        }
    }

}
