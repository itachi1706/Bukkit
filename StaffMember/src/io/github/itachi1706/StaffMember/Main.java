package io.github.itachi1706.StaffMember;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	static File staffFile;
	static FileConfiguration staff;
	
	@Override
	public void onEnable(){
		getLogger().info("Enabling plugin...");
		getLogger().info("Checking dependencies...");
		if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
		    getLogger().info("Detected PermissionsEx! Enabling List Offline Staff!");
		    
		}
		if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
		    getLogger().info("Detected GroupManager!");
		}
		if (getServer().getPluginManager().getPlugin("bPermissions") != null) {
		    getLogger().info("Detected bPermissions!");
		}
		if (getServer().getPluginManager().getPlugin("zPermissions") != null) {
		    getLogger().info("Detected zPermissions!");
		}
		if (getServer().getPluginManager().getPlugin("NickNamer") != null) {
		    getLogger().info("Detected NickNamer!");
		}
		getLogger().info("Loading configs...");
		staffFile = new File(getDataFolder(), "staff.yml");
		//Nick File Config
				try {
					firstRun();
				} catch (Exception e){
					e.printStackTrace();
				}
				staff = new YamlConfiguration();
			    loadYamls();
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("staffonline").setExecutor(new ListOnlineStaff(this));
		getCommand("stafflist").setExecutor(new ListStaff(this));
		getLogger().info("Loaded!");
	}
	
	@Override
	public void onDisable(){
		getLogger().info("Disabling Plugin...");
		getLogger().info("Saving config file...");
		saveYamls();
	}
	
	private void firstRun() throws Exception{
		if (!staffFile.exists()){
			staffFile.getParentFile().mkdirs();
	        copy(getResource("staff.yml"), staffFile);
	        staff.set("Admin", "");
	        staff.set("Mod", "");
	        staff.set("OP", "");
	        staff.set("Owner", "");
	        staff.set("YT", "");
		}
	}
	
	private void copy(InputStream in, File file){
		try{
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len=in.read(buf))>0){
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void saveYamls() {
	    try {
	        staff.save(staffFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void loadYamls() {
	    try {
	    	staff.load(staffFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void checkRank(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (p.hasPermission("staffmember.isowner")){
			
		} else if (p.hasPermission("staffmember.isop")){
			
		} else if (p.hasPermission("staffmember.isadmin")){
			
		} else if (p.hasPermission("staffmember.ismod")){
			
		} else if (p.hasPermission("staffmember.isyt")){
			
		} else {
			
		}
	}

}
