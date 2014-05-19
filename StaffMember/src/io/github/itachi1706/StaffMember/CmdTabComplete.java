package io.github.itachi1706.StaffMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CmdTabComplete implements TabCompleter{
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> list = new ArrayList<String>();
		 // Now, it's just like any other command.
        // Check if the sender is a player.
        if (sender instanceof Player) {
        	// Check if the command is "something."
            if (cmd.getName().equalsIgnoreCase("mojang")){
            	// If the player has not typed anything in
                if (args.length == 0) {
                	// Add a list of words that you'd like to show up
                    // when the player presses tab.
                    list.add("status");
                    list.add("premium");
                    // Sort them alphabetically.
                    Collections.sort(list);
                    // return the list.
                    return list;
                // If player has typed one word in.
                // This > "/command hello " does not count as one
                // argument because of the space after the hello.
                } else if (args.length == 1) {
                    list.add("status");
                    list.add("premium");
                    for (int i = 0; i < list.size(); i++){
                    	String s = list.get(i);
                        // Since the player has already typed something in,
                        // we ant to complete the word for them so we check startsWith().
                        if (!s.toLowerCase().startsWith(args[0].toLowerCase())){
                        	list.remove(i);
                        	i = 0;
                        }
                    }
                    Collections.sort(list);
                    return list;
                }
            }
        }
		return null;
	}

}
