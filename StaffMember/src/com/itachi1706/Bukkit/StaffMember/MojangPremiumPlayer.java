package com.itachi1706.Bukkit.StaffMember;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Kenneth on 17/7/2015.
 * for StaffMember in com.itachi1706.Bukkit.StaffMember
 */
public class MojangPremiumPlayer {

    //URL = minecraft.net/haspaid.jsp?user=<player name>
    public static int isPremium(String name){
        //return 0 for false, 1 for true, 2 for error
        try {
            URL url = new URL("https://minecraft.net/haspaid.jsp?user=" + name);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            if (Main.debugPlugin){
                Bukkit.getLogger().info(url.toString());
            }
            StringBuffer buffer = new StringBuffer();
            String check;
            if ((check = bufferedReader.readLine()) != null){
                buffer.append(check);
                if (Main.debugPlugin){
                    Bukkit.getLogger().info(check);
                }
                if (check.contains("true")){
                    Bukkit.getLogger().warning("Premium check for " + name + ": True");
                    return 1;
                } else if (check.contains("false")){
                    Bukkit.getLogger().warning("Premium check for " + name + ": False");
                    return 0;
                } else {
                    Bukkit.getLogger().warning("Error parsing text. (Neither true nor false)");
                    return 2;
                }
            } else {
                return 2;
            }
        } catch (IOException e){
            Bukkit.getLogger().severe("An exception occured (IOException), " + e.getLocalizedMessage());
            return 2;
        } catch (Exception e){
            Bukkit.getLogger().severe("An exception occured, " + e.getLocalizedMessage());
            e.printStackTrace();
            return 2;
        }
    }

}
