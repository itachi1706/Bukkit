package io.github.itachi1706.NickNamer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	static File nickFile;
    static FileConfiguration nick;
    
	@Override
	public void onEnable(){
		//Logic when plugin gets enabled
		getLogger().info("Enabling Plugin...");
		nickFile = new File(getDataFolder(), "nick.yml");
		//Nick File Config
				try {
					firstRun();
				} catch (Exception e){
					e.printStackTrace();
				}
				nick = new YamlConfiguration();
			    loadYamls();
		//Plugin Manager
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new Nick(), this);
		if (getServer().getPluginManager().getPlugin("Herochat") != null) {
		    getLogger().info("Detected Herochat! Hooking into Herochat Channel API!");
		    getServer().getPluginManager().registerEvents(new ChatControlHero(), this);
		} else {
		getServer().getPluginManager().registerEvents(new ChatControl(), this);
		}
		if (getServer().getPluginManager().getPlugin("TagAPI") == null) {
            getLogger().warning("\n" +
                            "+--------------------------------------------------------------+\n" +
                            "|                         TagAPI                               |\n" +
                            "|     TagAPI is what makes some features of NickNamer work     |\n" +
                            "|      DOWNLOAD - http://dev.bukkit.org/bukkit-plugins/tag/    |\n" +
                            "+--------------------------------------------------------------+");
        }
		if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
		    getLogger().info("Detected PermissionsEx!");
		}
		getCommand("nick").setExecutor(new NickCmd(this));
		getCommand("nickreset").setExecutor(new NickCmd(this));
		getCommand("nn").setExecutor(new Reload(this));
		getCommand("realname").setExecutor(new RealName(this));
		getCommand("togglenick").setExecutor(new ToggleNick(this));
		Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
		Iterator<? extends Player> i = playerList.iterator();
		while(i.hasNext()){
			Nick.refreshNameTag(i.next());
			
		}
		/*this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run(){
				Player[] playerList = Bukkit.getServer().getOnlinePlayers();
				for (int i=0; i < playerList.length; i++){
					Nick.refreshNameTag(playerList[i]);
			}
			}
		}, 600L, 1200L);*/
	}
	
	@Override
	public void onDisable(){
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
		saveYamls();
	}
	
	private void firstRun() throws Exception{
		if (!nickFile.exists()){
			nickFile.getParentFile().mkdirs();
	        copy(getResource("nick.yml"), nickFile);
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
	        nick.save(nickFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void loadYamls() {
	    try {
	    	nick.load(nickFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		if (nick.getString(player.getName()) == null){
			getLogger().info("Adding new nick record to new player");
			nick.set(player.getName() + ".disguised", false);
			nick.set(player.getName() + ".nick", player.getName());
			saveYamls();
		}
		player.setDisplayName(nick.getString(player.getName()  + ".nick"));
		if ((!player.isOp()) && player.hasPermission("nicknamer.nick.isstaff") && (!nick.getBoolean(player.getName() + ".disguised"))){
			e.setJoinMessage(ChatColor.AQUA + "[STAFF] " + player.getDisplayName() + ChatColor.YELLOW + " joined the game");
		} else if ((!player.isOp()) && player.hasPermission("nicknamer.nick.isyt") && (!nick.getBoolean(player.getName() + ".disguised"))){
			e.setJoinMessage(ChatColor.GOLD + "[YouTuber] " + player.getDisplayName() + ChatColor.YELLOW + " joined the game");
		} else
		if (player.isOp() == true && (!nick.getBoolean(player.getName() + ".disguised"))){
			e.setJoinMessage(ChatColor.DARK_RED + "[OP] " + player.getDisplayName() + ChatColor.YELLOW + " joined the game");
		}	else {
		e.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + " joined the game");
		}
		Nick.updateChatName(player);
		Nick.updateTabList(player);
		}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void die(PlayerDeathEvent e){
		Player player = e.getEntity();
		String msgD = e.getDeathMessage();
		DamageCause dc;
		if (player.getLastDamageCause() != null){
			dc = player.getLastDamageCause().getCause();
		}
		else {
			dc = DamageCause.CUSTOM;
		}
		if (player.getKiller() != null){
			Player killer = player.getKiller();
			String weapon = "";
			if (killer.getItemInHand().getItemMeta().hasDisplayName()){
				weapon = killer.getItemInHand().getItemMeta().getDisplayName();
			} else {
				weapon = WordUtils.capitalize(killer.getItemInHand().getType().name().replaceAll("_", " ").replaceAll("AIR", "FIST").toLowerCase());
			}
			String[] msgS = msgD.split(" ", 2);
			String msg = "";
			for (int i = 1; i < msgS.length; i++){
				msg = msg + msgS[i] + " ";
			}
			if (dc == DamageCause.LAVA){
				//Lava Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " tried to swim in lava to escape " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.FIRE || dc == DamageCause.FIRE_TICK){
				//Fire Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was burnt to a crisp whilst fighting " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.CONTACT){
				//Cactus Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " walked into a cactus whilst trying to escape " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.DROWNING){
				//Drowning Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " drowned whilst trying to escape " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.THORNS){
				//Thorns Enchant Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed trying to hurt " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.FALL){
				//Fall Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was doomed to fall by " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.MAGIC){
				//Potion Damage
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed using magic by " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else if (dc == DamageCause.CUSTOM){
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed with unknown means by " + killer.getDisplayName() + ChatColor.WHITE + " who is armed with " + ChatColor.AQUA + weapon);
			} else {
				e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " was killed by " + killer.getDisplayName() + " with " + ChatColor.AQUA + weapon);
			}
		} else {
			String[] msgS = msgD.split(" ", 2);
			String msg = "";
			for (int i = 1; i < msgS.length; i++){
				msg = msg + msgS[i] + " ";
			}
			e.setDeathMessage(player.getDisplayName() + ChatColor.WHITE + " " + msg);
			}
		}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent e){
		Player player = e.getPlayer();
		player.setDisplayName(nick.getString(player.getName()  + ".nick"));
		if ((!player.isOp()) && player.hasPermission("nicknamer.nick.isstaff") && (!nick.getBoolean(player.getName() + ".disguised"))){
			e.setQuitMessage(ChatColor.AQUA + "[STAFF] " + player.getDisplayName() + ChatColor.YELLOW + " left the game");
		} else if ((!player.isOp()) && player.hasPermission("nicknamer.nick.isyt") && (!nick.getBoolean(player.getName() + ".disguised"))){
			e.setQuitMessage(ChatColor.GOLD + "[YouTuber] " + player.getDisplayName() + ChatColor.YELLOW + " left the game");
		} else 
		if (player.isOp() == true && (!nick.getBoolean(player.getName() + ".disguised"))){
			e.setQuitMessage(ChatColor.DARK_RED + "[OP] " + player.getDisplayName() + ChatColor.YELLOW + " left the game");
		}	else {
		e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName() + " left the game");
		}
		}
	}

