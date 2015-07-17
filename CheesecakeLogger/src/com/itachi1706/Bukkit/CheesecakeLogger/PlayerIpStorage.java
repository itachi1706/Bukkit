package com.itachi1706.Bukkit.CheesecakeLogger;

import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for CheesecakeLogger in com.itachi1706.Bukkit.CheesecakeLogger
 */
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
