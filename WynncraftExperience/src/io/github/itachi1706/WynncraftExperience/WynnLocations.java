package io.github.itachi1706.WynncraftExperience;

public class WynnLocations {
	
	private String LocationName, LocationType;
	private double LocX,LocY,LocZ;
	private int LocatedWorld;
	private float LocPitch, LocYaw;
	
	public WynnLocations(String name, double x, double y, double z){
		LocationName = name;
		LocX = x;
		LocY = y;
		LocZ = z;
	}
	
	public WynnLocations(String name, double x, double y, double z, float yaw, float pitch, String type){
		LocationName = name;
		LocX = x;
		LocY = y;
		LocZ = z;
		LocPitch = pitch;
		LocYaw = yaw;
		LocationType = type;
	}
	
	public WynnLocations(String name, double x, double y, double z, float yaw, float pitch){
		LocationName = name;
		LocX = x;
		LocY = y;
		LocZ = z;
		LocPitch = pitch;
		LocYaw = yaw;
		LocationType = "";
	}
	
	public WynnLocations(String name, double x, double y, double z, int world){
		LocationName = name;
		LocX = x;
		LocY = y;
		LocZ = z;
		LocatedWorld = world;
	}
	
	public void setName(String name){
		LocationName = name;
	}
	
	public String getName(){
		return LocationName;
	}
	
	public void setX(double x){
		LocX = x;
	}
	
	public double getX(){
		return LocX;
	}
	
	public void setY(double y){
		LocY = y;
	}
	
	public double getY(){
		return LocY;
	}
	
	public void setZ(double z){
		LocZ = z;
	}
	
	public double getZ(){
		return LocZ;
	}
	
	public float getPitch(){
		return LocPitch;
	}
	
	public void setPitch(float pitch){
		LocPitch = pitch;
	}
	
	public float getYaw(){
		return LocYaw;
	}
	
	public void setYaw(float yaw){
		LocYaw = yaw;
	}
	
	public void setWorld(int world){
		LocatedWorld = world;
	}
	
	public int getWorld(){
		return LocatedWorld;
	}
	
	public void setType(String type){
		LocationType = type;
	}
	
	public String getType(){
		return LocationType;
	}

}
