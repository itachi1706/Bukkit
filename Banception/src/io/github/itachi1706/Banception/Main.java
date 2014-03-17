package io.github.itachi1706.Banception;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Main extends JavaPlugin implements Listener {
	
	static File banFile;
	static File muteFile;
	static File gmFile;
	static File banceptionDb;
    static FileConfiguration ban;
    static FileConfiguration mute;
    static FileConfiguration gm;
    
    public static Database sql;
	
	@Override
	public void onEnable(){
		//Logic when plugin gets enabled
		getLogger().info("Enabling Plugin...");
		banFile = new File(getDataFolder(), "ban.yml");
		muteFile = new File(getDataFolder(), "mute.yml");
		gmFile = new File(getDataFolder(), "gm.yml");
		getLogger().info("Setting up database file...");;
		sql = new SQLite(getLogger(), "[Banception] " , this.getDataFolder().getAbsolutePath(), "banception", ".sqlite");
		if (!sql.isOpen()) {
		    sql.open();
		}
		if (sql.open()){
			//Do SQL stuff
			SQLiteHelper.checkTableExist();
		}
		getLogger().info("Database loaded! Loading YAML Files...");
		//Ban/Mute File Config
		try {
			firstRun();
		} catch (Exception e){
			e.printStackTrace();
		}
		ban = new YamlConfiguration();
		mute = new YamlConfiguration();
		gm = new YamlConfiguration();
	    loadYamls();
		// This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!
	    getLogger().info("Enabling commands...");
		getCommand("ban").setExecutor(new Ban(this));
		getCommand("unban").setExecutor(new Unban(this));
		getCommand("mute").setExecutor(new Permmute(this));
		getCommand("unmute").setExecutor(new Unmute(this));
		getCommand("tempban").setExecutor(new Tempban(this));
		getCommand("tempmute").setExecutor(new Tempmute(this));
		getCommand("bc").setExecutor(new Reload(this));
		getCommand("shutup").setExecutor(new GlobalMute(this));
		getCommand("bans").setExecutor(new ListBans(this));
		getCommand("mutes").setExecutor(new ListBans(this));
		getCommand("kick").setExecutor(new Kick(this));
		getCommand("kicks").setExecutor(new ListBans(this));
		//Plugin Manager
		getLogger().info("Enabling Event Listners...");
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Plugin loaded!");
	}
	
	@Override
	public void onDisable(){
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
		saveYamls();
	}
	
	private void firstRun() throws Exception{
		if (!banFile.exists()){
			banFile.getParentFile().mkdirs();
	        copy(getResource("ban.yml"), banFile);
		}
		if (!muteFile.exists()){
			muteFile.getParentFile().mkdirs();
	        copy(getResource("mute.yml"), muteFile);
		}
		if (!gmFile.exists()){
			gmFile.getParentFile().mkdirs();
			copy(getResource("gm.yml"), gmFile);
			gm.set("mute", false);
			gm.set("time", 0);
			gm.set("timeStart", 0);
			gm.set("timeEnd", 0);
			gm.set("isMutedBy", "");
			gm.set("timeleft", "");
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
	
	public static void saveBan(){
		try{
			ban.save(banFile);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void saveGm(){
		try {
			gm.save(gmFile);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void saveMute(){
		try{
			mute.save(muteFile);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void saveYamls() {
	    try {
	        ban.save(banFile);
	        mute.save(muteFile);
	        gm.save(gmFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void loadYamls() {
	    try {
	    	ban.load(banFile);
	        mute.load(muteFile);
	        gm.load(gmFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void muteChat(AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		if (mute.getBoolean(player.getName() + ".mute")){
			if (!mute.getBoolean(player.getName() + ".perm")){
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				if (currentTime > mute.getInt(player.getName() + ".timeEnd")){
					Unmute.unmute(player);
					e.setCancelled(false);
				} else {
					int duration = mute.getInt(player.getName() + ".timeEnd") - currentTime;
					String durationMsg = TimeCalc.calcTimeMsg(currentTime, mute.getInt(player.getName() + ".timeEnd"));
					mute.set(player.getName() + ".timeleft", durationMsg);
					mute.set(player.getName() + ".time", duration);
					saveMute();
					e.setCancelled(true);
					player.sendMessage(ChatColor.RED + "You have been muted by " + ChatColor.GREEN + mute.getString(player.getName() + ".isMutedBy") + ChatColor.RED + ". " + ChatColor.AQUA + "Duration Left: " + ChatColor.DARK_AQUA + mute.getString(player.getName() + ".timeleft") + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + mute.getString(player.getName() + ".reason"));
		}
	} else {
		e.setCancelled(true);
		player.sendMessage(ChatColor.RED + "You have been muted by " + ChatColor.GREEN + mute.getString(player.getName() + ".isMutedBy") + ChatColor.RED + ". " + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + "permenantly" + ChatColor.BLUE + " Reason: " + ChatColor.DARK_RED + mute.getString(player.getName() + ".reason"));
	}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void gmChat(AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		if (gm.getBoolean("mute")){
			//Global Mute initialized
			int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
			if (currentTime > gm.getInt("timeEnd")){
				//Disabled Global Mute
				GlobalMute.autoUnmute();
				e.setCancelled(false);
			} else {
				//Global Mute active
				int duration = gm.getInt("timeEnd") - currentTime;
				String durationMsg = TimeCalc.calcTimeMsg(currentTime, gm.getInt("timeEnd"));
				gm.set("timeleft", durationMsg);
				gm.set("time", duration);
				saveGm();
				if (player.hasPermission("banception.gm.bypass")){
					e.setCancelled(false);
				} else {
					e.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Global mute was inititated by " + ChatColor.GREEN + gm.getString("isMutedBy") + ChatColor.RED + ". " + ChatColor.AQUA + "Duration Left: " + ChatColor.DARK_AQUA + gm.getString("timeleft"));
				}
			}
		} else {
			muteChat(e);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void checkIsBanned(PlayerLoginEvent e){
		Player player = e.getPlayer();
		if (ban.getBoolean(player.getName() + ".ban")){
			if (!ban.getBoolean(player.getName() + ".perm")){
				int currentTime = (int) (System.currentTimeMillis() / 1000);	//Current time in seconds
				if (currentTime > ban.getInt(player.getName() + ".timeEnd")){
					Unban.unbanned(player);
					e.allow();
				} else {
					int duration = ban.getInt(player.getName() + ".timeEnd") - currentTime;
					String durationMsg = TimeCalc.calcTimeMsg(currentTime, ban.getInt(player.getName() + ".timeEnd"));
					ban.set(player.getName() + ".timeleft", durationMsg);
					ban.set(player.getName() + ".time", duration);
					saveBan();
					e.disallow(Result.KICK_BANNED, "You have been temporarily banned from the server! "  + ChatColor.AQUA + "Duration: " + ChatColor.DARK_AQUA + durationMsg + ChatColor.WHITE + " Reason: "  + ChatColor.DARK_RED + ban.getString(player.getName() + ".reason"));
				}
			} else {
			e.disallow(Result.KICK_BANNED, "You have been banned from the server! Reason: "  + ChatColor.DARK_RED + ban.getString(player.getName() + ".reason"));
			}
		}
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		if (ban.getString(player.getName()) == null){
		getLogger().info("Adding new record to new player");
		ban.set(player.getName() + ".ban", false);
		ban.set(player.getName() + ".timeleft", "");
		ban.set(player.getName() + ".time", 0);
		ban.set(player.getName() + ".timeStart", 0);
		ban.set(player.getName() + ".timeEnd", 0);
		ban.set(player.getName() + ".reason", "");
		ban.set(player.getName() + ".isBannedBy", "");
		ban.set(player.getName() + ".perm", "");
		mute.set(player.getName() + ".mute", false);
		mute.set(player.getName() + ".timeleft", "");
		mute.set(player.getName() + ".time", 0);
		mute.set(player.getName() + ".reason", "");
		mute.set(player.getName() + ".isMutedBy", "");
		mute.set(player.getName() + ".timeStart", 0);
		mute.set(player.getName() + ".timeEnd", 0);
		mute.set(player.getName() + ".perm", "");
		Main.saveYamls();
		}
		}
	}
	
