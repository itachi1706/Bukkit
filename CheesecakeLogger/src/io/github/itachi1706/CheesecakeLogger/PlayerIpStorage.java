package io.github.itachi1706.CheesecakeLogger;

import org.bukkit.entity.Player;

public class PlayerIpStorage {
	
	private Player player;
	private String ipAddr;
	
	public PlayerIpStorage(){}
	public PlayerIpStorage(Player p, String ip){
		player = p;
		ipAddr = ip;
	}
	
	public void setPlayerIP(String ip){
		ipAddr = ip;
	}
	
	public String getPlayerIP(){
		return ipAddr;
	}
	
	public void setPlayer(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}

}
