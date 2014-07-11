package io.github.itachi1706.CheesecakeMinigameLobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class SurvMapActions implements Listener{
	
	@EventHandler
	private void joinAdvMap(PlayerJoinEvent e){
		Player p = e.getPlayer();
		welcomeMessage(p);
	}
	
	private void welcomeMessage(Player p){
		String prefix = "";
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			//Use name
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser target = pex.getUser(p);
			if (target == null){
				Bukkit.getLogger().info("Player not found in PEX File, reverting down to fallback...");
			} else {
				String result = target.getPrefix();
				prefix = ChatColor.translateAlternateColorCodes('&', result);
			}
		}
		
		String playerList = getFullPlayerList();
		String time = getWorldTime(Bukkit.getServer().getWorld(p.getWorld().getName()).getTime());
		for (int i = 0; i < Main.survmsg.size(); i++){
			String welMsg = Main.survmsg.get(i);
			welMsg = welMsg.replaceAll("%prefix%", prefix);
			welMsg = welMsg.replaceAll("%playername%", p.getDisplayName());
			welMsg = welMsg.replaceAll("%time%", time);
			welMsg = welMsg.replaceAll("%world%", p.getWorld().getName());
			welMsg = welMsg.replaceAll("%online%", Integer.toString(Bukkit.getServer().getOnlinePlayers().length));
			welMsg = welMsg.replaceAll("%players%", playerList);
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', welMsg));
		}
	}
	
	private String getFullPlayerList(){
		String format = Main.playerlistformat;
		Player[] players = Bukkit.getServer().getOnlinePlayers();
		StringBuilder fullmsg = new StringBuilder();
		for (int i = 0; i < (players.length - 1); i++){
			Player p = players[i];
			String pref = getPPrefix(p);
			String tmp = format;
			tmp = tmp.replaceAll("%prefix%", pref);
			tmp = tmp.replaceAll("%playername%", p.getDisplayName());
			fullmsg.append(tmp + ChatColor.RESET + ", ");
		}
		Player p = players[players.length - 1];
		String pref = getPPrefix(p);
		String tmp = format;
		tmp = tmp.replaceAll("%prefix%", pref);
		tmp = tmp.replaceAll("%playername%", p.getDisplayName());
		fullmsg.append(tmp);
		return fullmsg.toString();
	}
	
	private String getPPrefix(Player p){
		String prefix = "";
		if (Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
			//Use name
			PermissionManager pex = PermissionsEx.getPermissionManager();
			PermissionUser target = pex.getUser(p);
			if (target == null){
				Bukkit.getLogger().info("Player not found in PEX File, reverting down to fallback...");
			} else {
				String result = target.getPrefix();
				prefix = ChatColor.translateAlternateColorCodes('&', result);
			}
		}
		return prefix;
	}
	
	private String getWorldTime(long time){
		long gameTime = time, hours = gameTime / 1000 + 6, minutes = (gameTime % 1000) * 60 / 1000; String ampm = "AM";
		if (hours >= 12) { hours -= 12; ampm = "PM"; } if (hours >= 12) { hours -= 12; ampm = "AM"; } if (hours == 0) hours = 12;
		String mm = "0" + minutes; mm = mm.substring(mm.length() - 2, mm.length());
		return hours + ":" + mm + " " + ampm;
	}
	
	

}
