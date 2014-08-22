package io.github.itachi1706.Banception;

import java.sql.*;
import java.util.ArrayList;

import lib.PatPeter.SQLibrary.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SQLiteHelper {
	
	@SuppressWarnings("deprecation")
	public static void checkTableExist() {
		Database sqlite = Main.sql;
		if (sqlite.checkTable("BAN")){
			
		}else{
			try {
				sqlite.query("CREATE TABLE BAN (id INT PRIMARY KEY, NAME varchar(30) not null, TIME var NOT NULL, REASON TEXT NOT NULL, BANNER VARCHAR(30) NOT NULL, TYPE VARCHAR(30) NOT NULL);");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sqlite.checkTable("LOGINS")){
		} else {
			try {
				sqlite.query("DROP TABLE IF EXIST LOGINS;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sqlite.checkTable("mainTabl")){
		} else {
			try {
				sqlite.query("CREATE TABLE mainTabl (VERSION int not null);");
				sqlite.query("ALTER TABLE BAN ADD COLUMN UUID varchar(40) null;");
				sqlite.query("INSERT INTO mainTabl VALUES (1);");
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void updateBanMuteLogs(Database sqlite, String playername, String playerUUID){
		String banUpdate = "UPDATE BAN SET UUID=" + playerUUID + " WHERE NAME=" + playername + ";";
		try {
			sqlite.query(banUpdate);
		} catch (SQLException e){
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void addBanLog(Database sqlite, String n, String t, String r, String b){
		String banlogadd = "INSERT INTO BAN (NAME,TIME,REASON,BANNER,TYPE) VALUES('" + n + "','" + t + "','" + r + "','" + b + "','BAN');";
		try {
			sqlite.query(banlogadd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void addKickLog(Database sqlite, String n, String r, String b){
		String banlogadd = "INSERT INTO BAN (NAME,TIME,REASON,BANNER,TYPE) VALUES('" + n + "','0','" + r + "','" + b + "','KICK');";
		try {
			sqlite.query(banlogadd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void addMuteLog(Database sqlite, String n, String t, String r, String b){
		String mutelogadd = "INSERT INTO BAN (NAME,TIME,REASON,BANNER,TYPE) VALUES('" + n + "','" + t + "','" + r + "','" + b + "','MUTE');";
		try {
			sqlite.query(mutelogadd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void getPlayerBans(Database sqlite, CommandSender p, String target){
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> time = new ArrayList<String>();
		ArrayList<String> reason = new ArrayList<String>();
		ArrayList<String> banner = new ArrayList<String>();
		ArrayList<String> type = new ArrayList<String>();
		try {
			ResultSet rs = sqlite.query(" SELECT NAME,TIME,REASON,BANNER,TYPE FROM BAN; ");
			while (rs.next()){
				name.add(rs.getString("NAME"));
				time.add(rs.getString("TIME"));
				reason.add(rs.getString("REASON"));
				banner.add(rs.getString("BANNER"));
				type.add(rs.getString("TYPE"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Print statement
		p.sendMessage(ChatColor.GOLD + "--------- Bans For " + target + " ---------");
		for (int i = 0; i < name.size(); i++){
			String names = name.get(i);
			String types = type.get(i);
			if (names.equalsIgnoreCase(target) && types.equalsIgnoreCase("BAN")) {
				String times = time.get(i);
				String reasons = reason.get(i);
				String banners = banner.get(i);
				p.sendMessage(ChatColor.DARK_GREEN + banners + ChatColor.WHITE + " banned " + target + " for " + ChatColor.LIGHT_PURPLE + reasons + ChatColor.WHITE +" Duration: " + ChatColor.AQUA + times);
			}
		}
		
	}
	
	public static void getPlayerKicks(Database sqlite, CommandSender p, String target){
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> time = new ArrayList<String>();
		ArrayList<String> reason = new ArrayList<String>();
		ArrayList<String> banner = new ArrayList<String>();
		ArrayList<String> type = new ArrayList<String>();
		try {
			ResultSet rs = sqlite.query(" SELECT NAME,TIME,REASON,BANNER,TYPE FROM BAN; ");
			while (rs.next()){
				name.add(rs.getString("NAME"));
				time.add(rs.getString("TIME"));
				reason.add(rs.getString("REASON"));
				banner.add(rs.getString("BANNER"));
				type.add(rs.getString("TYPE"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Print statement
		p.sendMessage(ChatColor.GOLD + "--------- Kicks For " + target + " ---------");
		for (int i = 0; i < name.size(); i++){
			String names = name.get(i);
			String types = type.get(i);
			if (names.equalsIgnoreCase(target) && types.equalsIgnoreCase("KICK")) {
				String reasons = reason.get(i);
				String banners = banner.get(i);
				p.sendMessage(ChatColor.DARK_GREEN + banners + ChatColor.WHITE + " kicked " + target + " for " + ChatColor.LIGHT_PURPLE + reasons);
			}
		}
		
	}
	
	public static void getPlayerMutes(Database sqlite, CommandSender p, String target){
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> time = new ArrayList<String>();
		ArrayList<String> reason = new ArrayList<String>();
		ArrayList<String> banner = new ArrayList<String>();
		ArrayList<String> type = new ArrayList<String>();
		try {
			ResultSet rs = sqlite.query(" SELECT NAME,TIME,REASON,BANNER,TYPE FROM BAN; ");
			while (rs.next()){
				name.add(rs.getString("NAME"));
				time.add(rs.getString("TIME"));
				reason.add(rs.getString("REASON"));
				banner.add(rs.getString("BANNER"));
				type.add(rs.getString("TYPE"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Print statement
		p.sendMessage(ChatColor.GOLD + "--------- Mutes For " + target + " ---------");
		for (int i = 0; i < name.size(); i++){
			String names = name.get(i);
			String types = type.get(i);
			if (names.equalsIgnoreCase(target) && types.equalsIgnoreCase("MUTE")) {
				String times = time.get(i);
				String reasons = reason.get(i);
				String banners = banner.get(i);
				p.sendMessage(ChatColor.DARK_GREEN + banners + ChatColor.WHITE + " muted " + target + " for " + ChatColor.LIGHT_PURPLE + reasons + ChatColor.WHITE +" Duration: " + ChatColor.AQUA + times);
			}
		}
		
	}

}
