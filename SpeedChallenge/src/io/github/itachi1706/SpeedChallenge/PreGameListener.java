package io.github.itachi1706.SpeedChallenge;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PreGameListener implements Listener{
	
	@EventHandler
	public void freezePlayer(PlayerMoveEvent e){
		if (!Main.gameStart){
			if (Main.initGame){
				Player p = e.getPlayer();
				for (int i = 0; i < Main.playerList.size(); i++){
					Player pla = Main.playerList.get(i);
					if (p.getName().equals(pla.getName())){
						//In game, freeze player
						Location lfrom = e.getFrom(), lTo = e.getTo();
						double fx = lfrom.getX(), fz = lfrom.getZ(), tx = lTo.getX(), tz = lTo.getZ();
						if (fx != tx && fz != tz){
							p.teleport(lfrom);
						}
						
					}
				}
			}	
		}
	}
	
	@EventHandler
	public void damagePlayer(EntityDamageEvent e){
		if (!Main.gameStart){
			e.setCancelled(true);
		}
	}

}
