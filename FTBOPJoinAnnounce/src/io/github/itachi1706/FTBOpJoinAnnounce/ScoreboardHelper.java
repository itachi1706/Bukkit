package io.github.itachi1706.FTBOpJoinAnnounce;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardHelper {

	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	static Scoreboard board = manager.getMainScoreboard();
	static Team regular = board.getTeam("Regulars");
	static Team mod = board.getTeam("Moderators");
	
	public static void ensureTeamsAvailable(){
		if (board.getTeam("Moderators") == null){
			board.registerNewTeam("Moderators");
		}
		
		if (board.getTeam("Regulars") == null){
			board.registerNewTeam("Regulars");
		}
	}
	
	public static boolean checkIfRegular(Player p){
		if (regular.getSize() == 0){
			return false;
		}
		if (regular.hasPlayer(p.getPlayer())){
			return true;
		}
		return false;
	}
	
	public static boolean checkIfMod(Player p){
		if (mod.getSize() == 0){
			return false;
		}
		if (mod.hasPlayer(p.getPlayer())){
			return true;
		}
		return false;
	}
}
