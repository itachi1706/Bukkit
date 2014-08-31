package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Utilities.ConfigTabCompleter;
import io.github.itachi1706.SpeedChallenge.Utilities.InventoriesPreGame;
import io.github.itachi1706.SpeedChallenge.Utilities.ScoreboardHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin implements Listener{

	/*
	 * Modify PreGameRunnable's 4 switch case modules and the variable in the main class when
	 * adding new gamemodes.
	 * Modify ReequipCmd if needed and listGamemode method in ConfigCmd
	 * Edit ConfigTabCompleter to add in the extra challenge numbers
	 * Edit InventoriesPreGame too to include the stuff
	 */
	
	//User editable variables
	public static int numberOfChallenges = 6;
	
	public static Plugin plugin;
	
	//Plugin variables (DO NOT TOUCH)
	public static int players = 0;	//Number of players
	public static boolean gameStart = false;	//Whether game started alr
	public static boolean initGame = false;		//Whether game initialization is done
	public static int gamemode = 0;	//What type of speed challenge to do (0 = random, 1,2... = specific gamemodes)
	public static int pvp = 0;	//PVP enabled or not (0 is random, 1 is on, 2 is off)
	public static int respawn = 0; //Whether or not to respawn players if dead (0 random, 1 yes, 2 no)
	public static int countdown = 90;	//Countdown timer
	public static ArrayList<Player> gamePlayerList = new ArrayList<Player>();	//Players
	public static ArrayList<Player> spectators = new ArrayList<Player>();		//Spectators
	public static boolean invulnerable = true;		//Invulnerable
	public static boolean serverstarted = false;	//Server has started or not
	public static int customGameTime = -1;
	
	//Countdown timers
	public static int countDownTimer = 1;
	public static int countDownTimer2 = 2;
	public static int countDownTimer3 = 3;
	public static int countDownTimer4 = 4;
	
	@Override
	public void onEnable(){
		getLogger().info("Enabling Plugin...");
		getServer().dispatchCommand(Bukkit.getConsoleSender(), "s [SpeedChallenge] server starting...");
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new GameListeners(), this);
		this.saveDefaultConfig(); 
		getCommand("scconfig").setExecutor(new ConfigCmd(this));
		getCommand("spectate").setExecutor(new SpecCmd(this));
		getCommand("listobjectives").setExecutor(new ListObjectives(this));
		getCommand("reequip").setExecutor(new ReequipCmd(this));
		getCommand("scadmin").setExecutor(new AdminCmd(this));
		getCommand("scconfig").setTabCompleter(new ConfigTabCompleter());
		getLogger().info("Deleting previous stats of player in case its not deleted");
		deletePlayerStats();
		InventoriesPreGame.initSelectionStuff();
		getLogger().info("Deleting and regenerating worlds after 10 seconds...");
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run(){
				WorldGen.deleteSCWorlds();
				WorldGen.generateSCWorld();
				getLogger().info("Worlds regenerated!");
			}
		}, 200L);
		plugin = this;
		serverstarted = false;
		getServer().dispatchCommand(Bukkit.getConsoleSender(), "s [SpeedChallenge] server started...");
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//Main Command
    	if(cmd.getName().equalsIgnoreCase("sc")){
    		if (args.length < 1 || args.length > 1){
    			displayHelp(sender);
				return true;
    		}
    		if (args[0].equalsIgnoreCase("reset")){
    			//Reloads Plugin
    			sender.sendMessage("Resetting game...");
    			resetGame();
    			return true;
    		} else {
    			//Error
    			displayHelp(sender);
    			return true;
    		}
		}
    	return false;
	}
	
	public void displayHelp(CommandSender s){
		s.sendMessage(ChatColor.GOLD + "-----------SpeedChallenge Commands-----------");
    	s.sendMessage(ChatColor.GOLD + "/sc reset: " + ChatColor.WHITE +  "Resets game");
	}
	
	@Override
	public void onDisable(){
		Plugin plugin = this;
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
		HandlerList.unregisterAll(plugin);
	}
	
	private void reinitializeListeners(){
		HandlerList.unregisterAll(plugin);
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new GameListeners(), this);
	}
	
	private void startCountDown(){
		String finalCountDown2 = "&b[SpeedChallenge] &6&lGame is starting in 1 and a half minute!";
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', finalCountDown2));
		countDownTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new InitGame(this), 20L, 20L);
	}
	
	public void resetGame(){
		Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
		Iterator<? extends Player> i = playerList.iterator();
		World w = Bukkit.getWorld("world");
		Location l = new Location(Bukkit.getServer().getWorld("world"), Bukkit.getServer().getWorld("world").getSpawnLocation().getX(), Bukkit.getServer().getWorld("world").getSpawnLocation().getY(), Bukkit.getServer().getWorld("world").getSpawnLocation().getZ());
		while (i.hasNext()){
			Player p = i.next();
			if (!p.getWorld().equals(w)){
				p.teleport(l);
				p.sendMessage("You were teleported back to the main world!");
			}
		}
		if (initGame){
			WorldGen.deleteSCWorlds();
			reinitializeListeners();
		}
		if (initGame == false){
			Bukkit.getLogger().info("Resetting countdown timer!");
			Bukkit.getServer().getScheduler().cancelTask(countDownTimer);
		}
		if (gameStart == false && initGame == true){
			Bukkit.getLogger().info("Resetting countdown timer 2!");
			Bukkit.getServer().getScheduler().cancelTask(countDownTimer2);
		}
		if (gameStart == true){
			Bukkit.getLogger().info("Resetting countdown timer 3!");
			Bukkit.getServer().getScheduler().cancelTask(countDownTimer3);
		}
		
		for (int j = 0; j < spectators.size(); j++){
			Player spec = spectators.get(j);
			spec.setFlying(false);
			spec.setCanPickupItems(true);
		}
		
		players = 0;
		gameStart = false;
		countdown = 90;
		PreGameRunnable.countdown = 30;
		gamemode = 0;
		pvp = 0;
		respawn = 0;
		gamePlayerList.clear();
		spectators.clear();
		serverstarted = false;
		customGameTime = -1;
		
		ScoreboardHelper.resetScoreboard();
		i = playerList.iterator();
		while (i.hasNext()){
			i.next().kickPlayer("Game is restarting");
		}
		if (initGame){
			Bukkit.getLogger().info("Regenerating worlds...");
			WorldGen.generateSCWorld();
		}
		initGame = false;
	}
	
	@EventHandler
	public void checkIfWorldReset(PlayerLoginEvent e){
		if (WorldGen.resettingWorlds){
			e.getPlayer().kickPlayer("The world is still being resetted! Wait a while before rejoining!");
			return;
		}
		if (this.getServer().hasWhitelist()){
			if (!e.getPlayer().isWhitelisted()){
				e.getPlayer().kickPlayer("You are not whitelisted on the server!");
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		for (Player players : Bukkit.getOnlinePlayers()){
			players.showPlayer(e.getPlayer());
		}
		String prefix = "";
		InventoriesPreGame.giveItemToPlayer(e.getPlayer());
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser target = pex.getUser(e.getPlayer());
			String result = target.getPrefix();
			prefix = ChatColor.translateAlternateColorCodes('&', result);
		}
		World w = Bukkit.getWorld("world");
		Location l = new Location(Bukkit.getServer().getWorld("world"), Bukkit.getServer().getWorld("world").getSpawnLocation().getX(), Bukkit.getServer().getWorld("world").getSpawnLocation().getY(), Bukkit.getServer().getWorld("world").getSpawnLocation().getZ());
		if (!e.getPlayer().getWorld().equals(w)){
			e.getPlayer().teleport(l);
			e.getPlayer().sendMessage("You were teleported back to the main world!");
		}
		Bukkit.getServer().broadcastMessage(prefix + " " + e.getPlayer().getDisplayName() + " joined the game!");
		if (!initGame){
			/*ChatColor.DARK_GREEN + "Select a gamemode with " + ChatColor.GOLD + "/scconfig gamemode <number>",
			ChatColor.DARK_GREEN + "Then change the other configuration (respawn, pvp) with" + ChatColor.GOLD + " /scconfig <config> <true/false>",
			ChatColor.DARK_GREEN + "Do " + ChatColor.GOLD + "/scconfig" + ChatColor.DARK_GREEN + " for more details",
			String[] welMsg = {ChatColor.GOLD + "==================================================" ,
					ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "                 Speed Challenge" ,
					ChatColor.GOLD + "==================================================" ,
					ChatColor.DARK_GREEN + "Use the items in your first 3 slots of your inventory to configure this game!",
					ChatColor.GOLD + "==================================================" ,
					ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Do /hub to return back to the lobby!" ,
					ChatColor.GOLD + "=================================================="};*/
			e.getPlayer().sendMessage(ChatColor.GOLD + "==================================================");
			e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "                 Speed Challenge");
			e.getPlayer().sendMessage(ChatColor.GOLD + "==================================================");
			if (gamePlayerList.size() == 0){
				e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Use the items in your first 3 slots of your inventory to configure this game!");
			} else {
				e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Wait for the game to start!");
				if (e.getPlayer().hasPermission("sc.override")){
					e.getPlayer().sendMessage(ChatColor.DARK_RED + "You are able to configure this game as well due to your Staff status");
					e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Use the items in your first 3 slots of your inventory to configure");
				}
			}
			e.getPlayer().sendMessage(ChatColor.GOLD + "==================================================");
			e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Do /hub to return back to the lobby!");
			e.getPlayer().sendMessage(ChatColor.GOLD + "==================================================");
			e.getPlayer().setScoreboard(ScoreboardHelper.sb);
			players++;
			gamePlayerList.add(e.getPlayer());
			getLogger().info(players + " player(s)");
			if (players == 1 && !gameStart){
				ScoreboardHelper.initPlayersCounter();
			}
			if (countdown == 90 && !serverstarted){
				serverstarted = true;
				startCountDown();
			}
			ScoreboardHelper.updatePlayers();
		} else {
			String[] welMsg = {ChatColor.GOLD + "==================================================" ,
					ChatColor.DARK_GREEN + "Unfortunately, the game has already started. You can spectate the match if you want with" +
					ChatColor.GOLD + " /spectate",ChatColor.DARK_GREEN + "You can also teleport to current players with " + ChatColor.GOLD + "/spectate <name of player>",
					ChatColor.GOLD + "==================================================" ,
					ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Do /hub to return back to the lobby!" ,
					ChatColor.GOLD + "=================================================="};
			e.getPlayer().sendMessage(welMsg);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		String prefix = "";
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser target = pex.getUser(e.getPlayer());
			String result = target.getPrefix();
			prefix = ChatColor.translateAlternateColorCodes('&', result);
		}
		Bukkit.getServer().broadcastMessage(prefix + " " + e.getPlayer().getDisplayName() + " left the game!");
		for (int i = 0; i < gamePlayerList.size(); i++){
			Player pla = gamePlayerList.get(i);
			if (pla.equals(e.getPlayer())){
				gamePlayerList.remove(i);
				players--;
				break;
			}
		}
		getLogger().info(players + " player(s)");
		if (players <= 0){
			getLogger().info("Last Player Left. Resetting game...");
			resetGame();
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				public void run(){
					File dir = new File("world/stats");
					if (dir.isDirectory()){
						for (String pStats : dir.list()){
							File statFile = new File(dir, pStats);
							statFile.delete();
						}
					}
					getLogger().info("Files deleted");
				}
			}, 200L);
		} else {
			if (!gameStart){
				ScoreboardHelper.updatePlayers();
			}
		}
	}
	
	private void deletePlayerStats(){
		File dir = new File("world/stats");
		if (dir.isDirectory()){
			for (String pStats : dir.list()){
				File statFile = new File(dir, pStats);
				statFile.delete();
			}
		}
		getLogger().info("Files deleted");
	}

}
