package io.github.itachi1706.NickNamer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class Nick implements Listener {

	@EventHandler(priority=EventPriority.NORMAL)
	public void initNameTag(AsyncPlayerReceiveNameTagEvent e){
		if (e.getNamedPlayer().getName().equals(e.getNamedPlayer().getName())){
			if (!Main.nick.getBoolean(e.getNamedPlayer().getName() + ".disguised")){
			String format = checkFormatPerm(e.getNamedPlayer());
			e.setTag(format + Main.nick.getString(e.getNamedPlayer().getName() + ".nick"));
			} else {
				e.setTag(ChatColor.BLUE + Main.nick.getString(e.getNamedPlayer().getName() + ".nick") + ChatColor.WHITE);
			}
		}
	}
	
	public static void refreshNameTag(Player player){
		TagAPI.refreshPlayer(player);
		updateTabList(player);
		updateChatName(player);
	}
	
	public static void updateTabList(Player player){
		String nick = Main.nick.getString(player.getName() + ".nick");
		if (!Main.nick.getBoolean(player.getName() + ".disguised")){
			String format = checkFormatPerm(player);
			player.setPlayerListName(format + nick + ChatColor.WHITE);
		} else {
			player.setPlayerListName(ChatColor.BLUE + nick + ChatColor.WHITE);
		}
	}
	
	public static void updateChatName(Player player){
		String nick = Main.nick.getString(player.getName() + ".nick");
		if (!Main.nick.getBoolean(player.getName() + ".disguised")){
			String format = checkFormatPerm(player);
			player.setDisplayName(format + nick + ChatColor.WHITE);
		} else {
			player.setDisplayName(ChatColor.BLUE + nick + ChatColor.WHITE);
		}
	}
	
	public static String checkFormatPerm(Player player){
		String format = "";
		if (player.hasPermission("nicknamer.color.black")){
			format = format + ChatColor.BLACK;
		}
		if (player.hasPermission("nicknamer.color.dark_blue")){
			format = format + ChatColor.DARK_BLUE;
		}
		if (player.hasPermission("nicknamer.color.dark_green")){
			format = format + ChatColor.DARK_GREEN;
		}
		if (player.hasPermission("nicknamer.color.dark_aqua")){
			format = format + ChatColor.DARK_AQUA;
		}
		if (player.hasPermission("nicknamer.color.dark_red")){
			format = format + ChatColor.DARK_RED;
		}
		if (player.hasPermission("nicknamer.color.dark_purple")){
			format = format + ChatColor.DARK_PURPLE;
		}
		if (player.hasPermission("nicknamer.color.gold")){
			format = format + ChatColor.GOLD;
		}
		if (player.hasPermission("nicknamer.color.gray")){
			format = format + ChatColor.GRAY;
		}
		if (player.hasPermission("nicknamer.color.dark_gray")){
			format = format + ChatColor.DARK_GRAY;
		}
		if (player.hasPermission("nicknamer.color.blue")){
			format = format + ChatColor.BLUE;
		}
		if (player.hasPermission("nicknamer.color.green")){
			format = format + ChatColor.GREEN;
		}
		if (player.hasPermission("nicknamer.color.aqua")){
			format = format + ChatColor.AQUA;
		}
		if (player.hasPermission("nicknamer.color.red")){
			format = format + ChatColor.RED;
		}
		if (player.hasPermission("nicknamer.color.light_purple")){
			format = format + ChatColor.LIGHT_PURPLE;
		}
		if (player.hasPermission("nicknamer.color.yellow")){
			format = format + ChatColor.YELLOW;
		}
		if (player.hasPermission("nicknamer.color.white")){
			format = format + ChatColor.WHITE;
		}
		if (player.hasPermission("nicknamer.format.magic")){
			format = format + ChatColor.MAGIC;
		}
		if (player.hasPermission("nicknamer.format.bold")){
			format = format + ChatColor.BOLD;
		}
		if (player.hasPermission("nicknamer.format.strikethrough")){
			format = format + ChatColor.STRIKETHROUGH;
		}
		if (player.hasPermission("nicknamer.format.underline")){
			format = format + ChatColor.UNDERLINE;
		}
		if (player.hasPermission("nicknamer.format.italic")){
			format = format + ChatColor.ITALIC;
		}
		return format;
	}
}
