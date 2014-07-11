package io.github.itachi1706.JukeboxSequence;

import org.bukkit.Location;

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
