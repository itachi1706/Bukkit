package io.github.itachi1706.StaffMember;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	static File staffFile;
	static FileConfiguration staff;
	
	public static boolean debugPlugin = false;
	
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
		//Staff File Config
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
		getCommand("mojang").setExecutor(new MojangStatus(this));
		getCommand("serverproperties").setExecutor(new ServerStatsCommand(this));
		getCommand("mojang").setTabCompleter(new CmdTabComplete());
		getCommand("getuuid").setExecutor(new GetPlayerUUIDCmd(this));
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
	        /*staff.set("Admin", "");
	        staff.set("Mod", "");
	        staff.set("OP", "");
	        staff.set("Owner", "");
	        staff.set("YT", "");*/
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staffmember")){
			if (!sender.hasPermission("staffmember.admin")){
				if (args.length == 1 && args[0].equalsIgnoreCase("commands")){
					displayMenu(sender);
					return true;
				}
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
				return true;
			}
			if (args.length > 1 || args.length < 1){
				displayMenuAdmin(sender);
				return true;
			}
			if (args[0].equalsIgnoreCase("help")){
				displayMenuAdmin(sender);
				return true;
			} else if (args[0].equalsIgnoreCase("reload")){
				sender.sendMessage(ChatColor.GREEN + "This command is not needed for this plugin");
				return true;
			} else if (args[0].equalsIgnoreCase("commands")){
				displayMenu(sender);
				return true;
			} else {
				displayMenuAdmin(sender);
				return true;
			}
		}
		return false;
	}
	
	public void displayMenu(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------StaffMember Commands-----------");
		s.sendMessage(ChatColor.GOLD + "/staffmember commands: " + ChatColor.WHITE + "List all commands for normal players");
		s.sendMessage(ChatColor.GOLD + "/staffonline: " + ChatColor.WHITE + "List all staff currently online");
		s.sendMessage(ChatColor.GOLD + "/stafflist: " + ChatColor.WHITE + "List all staff (online/offline)");
		s.sendMessage(ChatColor.GOLD + "/mojang status: " + ChatColor.WHITE + "List the current status of Mojang Servers");
		s.sendMessage(ChatColor.GOLD + "/mojang premium <player>: " + ChatColor.WHITE + "Checks if a player is a premium user");
		s.sendMessage(ChatColor.GOLD + "/serverproperties: " + ChatColor.WHITE + "List the current server properties");
	}
	
	public void displayMenuAdmin(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------StaffMember Commands-----------");
		s.sendMessage(ChatColor.GOLD + "/staffonline: " + ChatColor.WHITE + "List all staff currently online");
		s.sendMessage(ChatColor.GOLD + "/staffmember commands: " + ChatColor.WHITE + "List all commands for normal players");
		s.sendMessage(ChatColor.GOLD + "/staffmember help: " + ChatColor.WHITE + "List all plugin commands (including admin commands)");
		s.sendMessage(ChatColor.GOLD + "/staffmember reload: " + ChatColor.WHITE + "Reloads plugin");
		s.sendMessage(ChatColor.GOLD + "/staffmember: " + ChatColor.WHITE + "Main plugin command");
		s.sendMessage(ChatColor.GOLD + "/stafflist: " + ChatColor.WHITE + "List all staff (online/offline)");
		s.sendMessage(ChatColor.GOLD + "/getuuid: " + ChatColor.WHITE + "Gets the UUID of an online player");
		s.sendMessage(ChatColor.GOLD + "/mojang status: " + ChatColor.WHITE + "List the current status of Mojang Servers");
		s.sendMessage(ChatColor.GOLD + "/mojang premium <player>: " + ChatColor.WHITE + "Checks if a player is a premium user");
		s.sendMessage(ChatColor.GOLD + "/serverproperties: " + ChatColor.WHITE + "List the current server properties");
	}

}
