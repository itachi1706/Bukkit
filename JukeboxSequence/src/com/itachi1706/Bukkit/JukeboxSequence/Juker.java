package com.itachi1706.Bukkit.JukeboxSequence;

import org.bukkit.Location;

/**
 * Created by Kenneth on 17/7/2015.
 * for JukeboxSequence in com.itachi1706.Bukkit.JukeboxSequence
 */
public class Juker {

    private Location location;
    private boolean isPlaying;

    public Juker(Location loc)
    {
        location = loc;
        isPlaying = false;
    }

    public boolean getIsPlaying(){
        return isPlaying;
    }

    public void setIsPlaying(boolean playingStatus){
        isPlaying = playingStatus;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location loc){
        location = loc;
    }

}
