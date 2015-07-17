package com.itachi1706.Bukkit.SpeedChallenge.Utilities;

import com.itachi1706.Bukkit.SpeedChallenge.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kenneth on 17/7/2015.
 * for SpeedChallenge in com.itachi1706.Bukkit.SpeedChallenge.Utilities
 */
public class ConfigTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> list = new ArrayList<String>();
        // Now, it's just like any other command.
        // Check if the sender is a player.
        if (sender instanceof Player) {
            // Check if the command is "something."
            if (cmd.getName().equalsIgnoreCase("scconfig")) {
                // If the player has not typed anything in
                if (args.length == 0) {
                    // Add a list of words that you'd like to show up
                    // when the player presses tab.
                    list.add("gamemode");
                    list.add("gametime");
                    list.add("pvp");
                    list.add("respawn");
                    list.add("list");
                    // Sort them alphabetically.
                    Collections.sort(list);
                    // return the list.
                    return list;
                    // If player has typed one word in.
                    // This > "/command hello " does not count as one
                    // argument because of the space after the hello.
                } else if (args.length == 1) {
                    list.add("gamemode");
                    list.add("gametime");
                    list.add("pvp");
                    list.add("respawn");
                    list.add("list");
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
                } else if (args.length == 2 && args[0].equalsIgnoreCase("gamemode")){
                    for (int i = 1; i <= Main.numberOfChallenges; i++){
                        list.add(i + "");
                    }
                    for (int i = 0; i < list.size(); i++){
                        String s = list.get(i);
                        // Since the player has already typed something in,
                        // we ant to complete the word for them so we check startsWith().
                        if (!s.toLowerCase().startsWith(args[1].toLowerCase())){
                            list.remove(i);
                            i = 0;
                        }
                    }
                    Collections.sort(list);
                    return list;
                } else if (args.length == 2 && args[0].equalsIgnoreCase("pvp")){
                    list.add("true");
                    list.add("false");
                    for (int i = 0; i < list.size(); i++){
                        String s = list.get(i);
                        // Since the player has already typed something in,
                        // we ant to complete the word for them so we check startsWith().
                        if (!s.toLowerCase().startsWith(args[1].toLowerCase())){
                            list.remove(i);
                            i = 0;
                        }
                    }
                    Collections.sort(list);
                    return list;
                } else if (args.length == 2 && args[0].equalsIgnoreCase("respawn")){
                    list.add("true");
                    list.add("false");
                    for (int i = 0; i < list.size(); i++){
                        String s = list.get(i);
                        // Since the player has already typed something in,
                        // we ant to complete the word for them so we check startsWith().
                        if (!s.toLowerCase().startsWith(args[1].toLowerCase())){
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
