package com.itachi1706.Bukkit.Monopoly.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.util
 */

//To invoke:
//TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, new String[] { /*(and in here you say which completions can be done on this specific argument number, say that you had 3 sub-commands called "abc", "abd" and "abe", you would do this:)*/ "abc", "abd", "abe" });
/**
 * Class to help with the TabCompletion for Bukkit.
 * <p>
 * Can be used by anybody, as long as you provide credit for it.
 *
 * @author D4rKDeagle
 */
public class TabCompletionHelper {

    public static List<String> getPossibleCompletionsForGivenArgs(String[] args, String[] possibilitiesOfCompletion)
    {
        String argumentToFindCompletionFor = args[args.length-1];

        List<String> listOfPossibleCompletions = new ArrayList<String>();

        for (int i = 0; i < possibilitiesOfCompletion.length; ++i)
        {
            String foundString = possibilitiesOfCompletion[i];

            if(foundString.regionMatches(true, 0, argumentToFindCompletionFor, 0, argumentToFindCompletionFor.length()))
            {
                listOfPossibleCompletions.add(foundString);
            }
        }

        return listOfPossibleCompletions;
    }

    public static String[] getOnlinePlayerNames() {
        Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        String[] onlinePlayerNames = new String[onlinePlayers.length];

        for(int i = 0; i < onlinePlayers.length; i++) {
            onlinePlayerNames[i] = onlinePlayers[i].getName();
        }

        return onlinePlayerNames;
    }

}
