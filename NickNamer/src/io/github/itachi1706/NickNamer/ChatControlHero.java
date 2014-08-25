package io.github.itachi1706.NickNamer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.dthielke.herochat.Channel;
import com.dthielke.herochat.ChannelChatEvent;
import com.dthielke.herochat.Chatter;

public class ChatControlHero implements Listener{
	
	@EventHandler(priority=EventPriority.HIGH)
	public void trueNickChat(ChannelChatEvent e){
		Chatter chat = e.getSender();
		Channel c = e.getChannel();
		String channel = c.getName();
		if (channel.equalsIgnoreCase("staff")){
			Player staffer = chat.getPlayer();
			if (Main.nick.getBoolean(staffer.getName() + ".disguised")){
				//Do true nicked chat hiding
				String msg = e.getMessage();
				String dname = staffer.getDisplayName();
				String herochatFor = "{color}[{nick}] " + ChatColor.BLUE + "[MEMBER] " + staffer.getDisplayName() + "{color}: " + e.getMessage();
				String format = ChatColor.BLUE + "[MEMBER] <player>: <message>";
				format.replace("<player>", dname);
				format.replace("<message>", msg);
				e.setBukkitFormat(format);
				e.setFormat(herochatFor);
				e.setMessage(msg.replaceAll("&", "§"));
		} 
		}else if(channel.equalsIgnoreCase("global")){
		Player player = chat.getPlayer();
		if (Main.nick.getBoolean(player.getName() + ".disguised")){
			//Do true nicked chat hiding
			String msg = e.getMessage();
			String dname = player.getDisplayName();
			String herochatFor = ChatColor.BLUE + "[MEMBER] " + player.getDisplayName() + "{color}: " + e.getMessage();
			String format = ChatColor.BLUE + "[MEMBER] <player>: <message>";
			format.replace("<player>", dname);
			format.replace("<message>", msg);
			e.setBukkitFormat(format);
			e.setFormat(herochatFor);
			e.setMessage(msg.replaceAll("&", "§"));
		}
		} else {
			String checkMsg = "";
			for (int i = 0; i < 5; i++){
				char wd = channel.charAt(i);
				checkMsg = checkMsg + wd;
			}
			if (checkMsg.equals("convo")){
				String herochatFor = ChatColor.DARK_PURPLE + "[{convoaddress} -> {convopartner}" + ChatColor.DARK_PURPLE + "]" + ChatColor.GOLD + " {msg}";
				e.setFormat(herochatFor);
				e.setMessage(e.getMessage().replaceAll("&", "§"));
			}
		}
	}
}

