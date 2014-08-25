package io.github.itachi1706.CheesecakeLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import lib.PatPeter.SQLibrary.Database;

public class SQLiteHelper {
	
	private static Database sqlite = null;
	private static String exceptionMsg;
	
	@SuppressWarnings("deprecation")
	public static void checkTableExist() {
		sqlite = Main.sql;
		if (!sqlite.checkTable("LOGINS")){
			try {
				sqlite.query("CREATE TABLE LOGINS (id INTEGER PRIMARY KEY AUTOINCREMENT, NAME varchar(30) not null, TIME TEXT NOT NULL DEFAULT (DATETIME('now')), IP VARCHAR(20) NOT NULL, X VARCHAR(30) NOT NULL, Y VARCHAR(30) NOT NULL, Z VARCHAR(30) NOT NULL, WORLD TEXT NOT NULL, TYPE VARCHAR(10) NOT NULL);");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Name, IP, X, Y, Z, World
	public static void addLoginLog(String name, Location location, String ipAddress){
		String sqlQuery = "INSERT INTO LOGINS (NAME,IP,X,Y,Z,WORLD,TYPE) VALUES('" + name + "','" + ipAddress + "','" + (Math.round(location.getX() * 100.0) / 100.0) + "','" + (Math.round(location.getY() * 100.0) / 100.0) + "','" + (Math.round(location.getZ() * 100.0) / 100.0) + "','" + location.getWorld().getName() + "','LOGIN');";
		try {
			sqlite.query(sqlQuery);
			Bukkit.getLogger().info("Logged Login for " + name);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void addLogoutLog(String name, Location location, String ipAddress){
		String sqlQuery = "INSERT INTO LOGINS (NAME,IP,X,Y,Z,WORLD,TYPE) VALUES('" + name + "','" + ipAddress + "','" + (Math.round(location.getX() * 100.0) / 100.0) + "','" + (Math.round(location.getY() * 100.0) / 100.0) + "','" + (Math.round(location.getZ() * 100.0) / 100.0) + "','" + location.getWorld().getName() + "','LOGOUT');";
		try {
			sqlite.query(sqlQuery);
			Bukkit.getLogger().info("Logged Logout for " + name);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void checkLoginLogs(CommandSender p, String target, int no){
		ArrayList<String> loginHist = getFullPlayerLogs(target);
		if (loginHist == null){
			//Exception
			p.sendMessage(ChatColor.RED + "An Error Occured trying to get logs! (" + exceptionMsg + ")");
			exceptionMsg = new String();	//Reset exception message
		} else {
			parseMessages(loginHist, p, no, target);
		}
	}
	
	private static String getPexRank(String target){
		PermissionManager pex = PermissionsEx.getPermissionManager();
		PermissionUser user = pex.getUser(target);
		List<String> grouptmp = user.getParentIdentifiers();
		StringBuilder build = new StringBuilder();
		if (grouptmp.size() > 1){
			build.append("[ ");
			for (String tmp : grouptmp){
				build.append(tmp + " ");
			}
			build.append("]");
		} else {
			build.append(grouptmp.get(0));
		}
		return build.toString();
	}
	
	public static void checkLoginStats(CommandSender p, String target, UUID uuid, String firstPlayed, String lastPlayed){
		int logins = getLoginCount(target);
		String rank = "PermissionsEx is not installed";
		if (logins == -2){
			p.sendMessage(ChatColor.RED + "An Error Occured trying to convert login count! (" + exceptionMsg + ")");
			exceptionMsg = new String();	//Reset exception message
			return;
		} else if (logins == -1){
			p.sendMessage(ChatColor.RED + "An Error Occured trying to get stats! (" + exceptionMsg + ")");
			exceptionMsg = new String();	//Reset exception message
			return;
		}
		int logouts = getLogoutCount(target);
		if (logouts == -2){
			p.sendMessage(ChatColor.RED + "An Error Occured trying to convert logout count! (" + exceptionMsg + ")");
			exceptionMsg = new String();	//Reset exception message
			return;
		} else if (logouts == -1){
			p.sendMessage(ChatColor.RED + "An Error Occured trying to get stats! (" + exceptionMsg + ")");
			exceptionMsg = new String();	//Reset exception message
			return;
		}
		//Get PEX Rank if present
		if (Main.pexPresent){
			rank = getPexRank(target);
		}
		
		//Present them all out
		p.sendMessage(ChatColor.GOLD + "-------------------- Login Statistics -------------------");
		p.sendMessage(ChatColor.GOLD + "Player Name: " + ChatColor.RESET + target);
		p.sendMessage(ChatColor.GOLD + "Rank: " + ChatColor.RESET + rank);
		p.sendMessage(ChatColor.GOLD + "Player UUID: " + ChatColor.RESET + uuid);
		p.sendMessage(ChatColor.GOLD + "Logins: " + ChatColor.RESET + logins);
		p.sendMessage(ChatColor.GOLD + "Logouts: " + ChatColor.RESET + logouts);
		p.sendMessage(ChatColor.GOLD + "First Joined: " + ChatColor.RESET + firstPlayed);
		p.sendMessage(ChatColor.GOLD + "Last Played: " + ChatColor.RESET + lastPlayed);
		p.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
	}
	
	private static int getLoginCount(String target){
		int loginCount = 0;
		try {
			//get login count
			ResultSet rs = sqlite.query("SELECT COUNT(*) FROM LOGINS WHERE NAME='" + target + "' AND TYPE='LOGIN';");
			while (rs.next()){
				String tmp = rs.getString(1);
				if (tmp != null){
					loginCount=Integer.parseInt(tmp);
				}
			}
			rs.close();
			return loginCount;
		} catch (NumberFormatException e){
			exceptionMsg = e.toString();
			e.printStackTrace();
			return -2;
		}catch (Exception e) {
			exceptionMsg = e.toString();
			e.printStackTrace();
			return -1;
		}
	}
	
	private static int getLogoutCount(String target){
		int logoutCount = 0;
		try {
			//get logout count
			ResultSet rs = sqlite.query("SELECT COUNT(*) FROM LOGINS WHERE NAME='" + target + "' AND TYPE='LOGOUT';");
			while (rs.next()){
				String tmp = rs.getString(1);
				if (tmp != null){
					logoutCount=Integer.parseInt(tmp);
				}
			}
			rs.close();
			return logoutCount;
		} catch (NumberFormatException e){
			exceptionMsg = e.toString();
			e.printStackTrace();
			return -2;
		}catch (Exception e) {
			exceptionMsg = e.toString();
			e.printStackTrace();
			return -1;
		}
	}
	
	private static ArrayList<String> getFullPlayerLogs(String target){
		try {
			ResultSet rs = sqlite.query("SELECT NAME,TYPE,X,Y,Z,WORLD,TIME,IP FROM LOGINS WHERE NAME='" + target + "' ORDER BY TIME DESC;");
			//p.sendMessage(ChatColor.GOLD + "--------- Login History For " + target + " ---------");
			int i = 1;
			ArrayList<String> loginHist = new ArrayList<String>();
			while (rs.next()){
				if (rs.getString("TYPE").toString().equalsIgnoreCase("LOGIN")){
					//A login message
					loginHist.add(sendLogin(i, rs.getString("X"), rs.getString("Y"), rs.getString("Z"), rs.getString("WORLD"), rs.getString("TIME"), rs.getString("IP")));
				} else if (rs.getString("TYPE").equalsIgnoreCase("LOGOUT")){
					//A logout message
					loginHist.add(sendLogout(i, rs.getString("X"), rs.getString("Y"), rs.getString("Z"), rs.getString("WORLD"), rs.getString("TIME"), rs.getString("IP")));
				}
				i++;
			}
			rs.close();
			return loginHist;
		} catch (Exception e) {
			exceptionMsg = e.toString();
			e.printStackTrace();
			return null;
		}
			
	}
	
	private static void parseMessages(ArrayList<String> stringList, CommandSender p, int arg, String target){
		int maxPossibleValue = stringList.size();	//Max possible based on stringList
		int maxPossiblePage = (stringList.size() / 10) + 1;
		if (maxPossiblePage < arg){
			p.sendMessage(ChatColor.RED + "Max amount of pages is " + maxPossiblePage + ". Please specify a value within that range!");
			return;
		}
		//1 (0-9), 2 (10,19)...
		int minValue = (arg - 1) * 10;
		int maxValue = (arg * 10) - 1;
		if (maxValue > maxPossibleValue) {	//Exceeds
			p.sendMessage(ChatColor.GOLD + "------ Login History For " + target + " Page " + arg + " of " + maxPossiblePage + " ------");
			for (int i = minValue; i < stringList.size(); i++){
				p.sendMessage(stringList.get(i).toString());
			}
			p.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			return;
		}
		p.sendMessage(ChatColor.GOLD + "------ Login History For " + target + " Page " + arg + " of " + maxPossiblePage + " ------");
		for (int i = minValue; i <= maxValue; i++){
			p.sendMessage(stringList.get(i).toString());
		}
		p.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		return;
	}
	
	private static String sendLogin(int no, String x, String y, String z, String world, String datetime, String ip){
		//1. datetime Login at X: x, Y: y, Z:z, World: world with IP
		StringBuilder builder = new StringBuilder();
		builder.append(ChatColor.GOLD + "" + no + ". ");
		builder.append(ChatColor.RESET + "" + ChatColor.ITALIC + datetime + " " + ChatColor.RESET + "");
		builder.append(ChatColor.GREEN + "Login ");
		builder.append(ChatColor.RESET + "at ");
		builder.append(ChatColor.GOLD + "" + ChatColor.AQUA + world);
		builder.append(ChatColor.GOLD + "," + ChatColor.AQUA + x);
		builder.append(ChatColor.GOLD + "," + ChatColor.AQUA + y);
		builder.append(ChatColor.GOLD + "," + ChatColor.AQUA + z);
		builder.append(ChatColor.RESET + " at ");
		builder.append(ChatColor.LIGHT_PURPLE + ip);
		return builder.toString();
	}
	
	private static String sendLogout(int no, String x, String y, String z, String world, String datetime, String ip){
		//1. datetime Logout at X: x, Y: y, Z:z, World: world with IP
		StringBuilder builder = new StringBuilder();
		builder.append(ChatColor.GOLD + "" + no + ". ");
		builder.append(ChatColor.RESET + "" + ChatColor.ITALIC + datetime + " " + ChatColor.RESET + "");
		builder.append(ChatColor.RED + "Logout ");
		builder.append(ChatColor.RESET + "at ");
		builder.append(ChatColor.GOLD + "" + ChatColor.AQUA + world);
		builder.append(ChatColor.GOLD + "," + ChatColor.AQUA + x);
		builder.append(ChatColor.GOLD + "," + ChatColor.AQUA + y);
		builder.append(ChatColor.GOLD + "," + ChatColor.AQUA + z);
		builder.append(ChatColor.RESET + " at ");
		builder.append(ChatColor.LIGHT_PURPLE + ip);
		return builder.toString();
	}
	
	public static void deleteLogs(CommandSender p, String target){
		String sqlQuery = "DELETE FROM LOGINS WHERE NAME='" + target + "';";
		try{
			sqlite.query(sqlQuery);
			p.sendMessage(ChatColor.GREEN + target + " logs for login/logout deleted!");
		} catch (Exception e) {
			p.sendMessage(ChatColor.RED + "An Error Occured trying to delete logs! (" + e.toString() + ")");
			e.printStackTrace();
		}
	}

}
