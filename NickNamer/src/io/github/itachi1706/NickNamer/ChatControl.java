package io.github.itachi1706.NickNamer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatControl implements Listener{
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void trueNickChat(AsyncPlayerChatEvent e){
		Player player = e.getPlayer();
		if (Main.nick.getBoolean(player.getName() + ".disguised")){
			//Do true nicked chat hiding
			e.isCancelled();
			String msg = e.getMessage();
			String dname = player.getDisplayName();
			String format = ChatColor.BLUE + "<prefix> <player>: <message>";
			format.replace("<player>", dname);
			format.replace("<format>", "[MEMBER]");
			format.replace("<message>", msg);
			e.setFormat(format);
			e.setMessage(msg.replaceAll("&", "§"));
		}
	}

}
