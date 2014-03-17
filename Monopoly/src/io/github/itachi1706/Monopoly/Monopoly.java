package io.github.itachi1706.Monopoly;

import io.github.itachi1706.Monopoly.Logic.PlayerTeams;
import io.github.itachi1706.Monopoly.util.Book;
import io.github.itachi1706.Monopoly.util.SQLiteHelper;
import io.github.itachi1706.Monopoly.util.ScoreboardHelper;
import io.github.itachi1706.Monopoly.util.TabCompletionHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Monopoly extends JavaPlugin implements Listener{

	static File configGameFile;
	static File playerPropertiesFile;
    public static FileConfiguration configGame;
    public static FileConfiguration playerProperties;
    
    public static Database sql;
	
	@Override
	public void onEnable(){
		//Logic when plugin gets enabled
		getLogger().info("Enabling Plugin...");
		getLogger().info("Setting up database...");
		sql = new SQLite(getLogger(), "[Monopoly] " , this.getDataFolder().getAbsolutePath(), "Monopoly", ".sqlite");
		if (!sql.isOpen()) {
		    sql.open();
		}
		if (sql.open()){
			//Do SQL stuff
			SQLiteHelper.checkTableExist();
		}
		getLogger().info("Database loaded! Loading YAML Files...");
		configGameFile = new File(getDataFolder(), "configGame.yml");
		playerPropertiesFile = new File(getDataFolder(), "playerProperties.yml");
		try {
			firstRun();
		} catch (Exception e){
			e.printStackTrace();
		}
		configGame = new YamlConfiguration();
		playerProperties = new YamlConfiguration();
		if (configGame.getString("start") == null){
	        getLogger().info("Inserting values into config file...");
			initConfigFile();
		}
		if (playerProperties.getString("Pass Go") == null){
	        getLogger().info("Inserting values into player properties file...");
	        initPropertiesFile();
		}
		loadYamls();
		getLogger().info("YAML Files loaded! Setting up scoreboards system....");
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard board = sm.getMainScoreboard();
		if ((board.getObjective("game_isTurn") == null)){
			getLogger().info("Setting up scoreboard system...");
			ScoreboardHelper.initScoreboard();
		}
		getLogger().info("Scoreboard set up!");
		getLogger().info("Enabling Plugin listeners...");
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new Book(), this);
		getServer().getPluginManager().registerEvents(new ChatCommandControl(), this);
		getServer().getPluginManager().registerEvents(new PlayerTeams(), this);
		//getServer().getPluginManager().registerEvents(new Blocker(), this);
		getCommand("monopoly").setExecutor(new CommandsControl(this));
	}
	
	@Override
	public void onDisable(){
		//Logic when plugin gets disabled
		getLogger().info("Disabling Plugin...");
		getLogger().info("Saving YAML Files...");
		saveYamls();
		getLogger().info("Closing database connection.");
		sql.close();
	}
	
	private void firstRun() throws Exception{
		if (!configGameFile.exists()){
			configGameFile.getParentFile().mkdirs();
	        copy(getResource("configGame.yml"), configGameFile);
		}
		if (!playerPropertiesFile.exists()){
			playerPropertiesFile.getParentFile().mkdirs();
	        copy(getResource("playerProperties.yml"), playerPropertiesFile);
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
	    	configGame.save(configGameFile);
	    	playerProperties.save(playerPropertiesFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static void loadYamls() {
	    try {
	    	configGame.load(configGameFile);
	    	playerProperties.load(playerPropertiesFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void savePlayerProperties(){
		try{
			playerProperties.save(playerPropertiesFile);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void initConfigFile(){
		configGame.set("start", false);
		configGame.set("pause", false);
		configGame.set("auction", false);
		configGame.set("passGo", 200);
		configGame.set("initialGold", 2000);
		configGame.set("freeParking", 0);
		saveYamls();
	}
	
	public static void initPropertiesFile(){
		ArrayList<String> sqlData = new ArrayList<String>();
		try {
			ResultSet results = sql.query("SELECT name FROM Properties");
			while (results.next()){
				sqlData.add(results.getString("name"));
			}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < sqlData.size(); i++){
			String s = sqlData.get(i);
			playerProperties.set(s + ".Mortgaged", false);
			playerProperties.set(s + ".Owned", "Nobody");
			playerProperties.set(s + ".House", 0);
			playerProperties.set(s + ".Hotel", 0);
			playerProperties.set(s + ".fullSet", false);
		}
		saveYamls();
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {	
		if(cmd.getName().equalsIgnoreCase("monopoly")){
			if (args.length == 0){
				return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, new String[] {"start", "pause" , "reset", "join", "leave", "give", "take", "reload", "dice", "where", "chatcmd", "region"});
			}
			if (args.length == 1 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take"))){
				return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, new String[] {"gold", "property"});
			}
			if (args.length == 1){
				return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, TabCompletionHelper.getOnlinePlayerNames());
			}
			if (args.length == 2 && (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take"))){
				return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, TabCompletionHelper.getOnlinePlayerNames());
			}
		}
		return null;
		
	}
}
