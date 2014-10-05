package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Gamemodes.AbbaRules;
import io.github.itachi1706.SpeedChallenge.Gamemodes.AbbaRulesRetardStyle;
import io.github.itachi1706.SpeedChallenge.Gamemodes.ModAbbaRules;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReequipCmd implements CommandExecutor{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ReequipCmd(Main plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reequip")){
			if (!(sender instanceof Player)){
				sender.sendMessage("You must be an in-game player to use this command!");
				return true;
			}
			for (int i = 0; i < Main.gamePlayerList.size(); i++){
				Player p = Main.gamePlayerList.get(i);
				if (p.getName().equals(sender.getName())){
					//Matches
					reequip((Player) sender);
					return true;
				}
			}
		}
		return false;
	}
	
	public static void reequip(Player p){
		switch (Main.gamemode){
		case 4: ModAbbaRules.checkWeapon(p);
				break;
		case 5: AbbaRules.checkWeapon(p);
				break;
		case 6: AbbaRulesRetardStyle.checkWeapon(p);
				break;
		default: p.sendMessage(ChatColor.RED + "Your current challenge does not require any equipments!");
				break;
		}
	}

}
