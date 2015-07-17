package com.itachi1706.Bukkit.Banception;

import java.util.concurrent.TimeUnit;

/**
 * Created by Kenneth on 17/7/2015.
 * for Banception in com.itachi1706.Bukkit.Banception
 */
public class TimeCalc {

    public static int initCalc(String timeToBan){
        int strLength = timeToBan.length();	//Length of string
        int currentPos = 0;					//Current Pos of string
        int lastPos = 0;					//Last Pos of string
        int totalTime = 0;					//Total Time to ban/mute
        int timeSec = 0, timeMin = 0, timeHr = 0, timeDay = 0; //Calc timers
        char c = timeToBan.charAt(0);
        while (currentPos + 1 < strLength){
            for (int i = lastPos; i < timeToBan.length(); i++){
                if (Character.isAlphabetic(timeToBan.charAt(i))){
                    c = timeToBan.charAt(i);
                    currentPos = i;
                    break;
                }
            }
            if (c == 's'){
                String tmp = "";
                for (int i = lastPos; i < currentPos; i++){
                    tmp = tmp + timeToBan.charAt(i);
                }
                lastPos = currentPos + 1;
                timeSec = Integer.parseInt(tmp);	//time in seconds
            } else if (c=='m'){
                String tmp = "";
                for (int i = lastPos; i < currentPos; i++){
                    tmp = tmp + timeToBan.charAt(i);
                }
                lastPos = currentPos + 1;
                timeMin = Integer.parseInt(tmp);	//time in minutes
            } else if (c=='h'){
                String tmp = "";
                for (int i = lastPos; i < currentPos; i++){
                    tmp = tmp + timeToBan.charAt(i);
                }
                lastPos = currentPos + 1;
                timeHr = Integer.parseInt(tmp);	//time in hours
            } else if (c=='d'){
                String tmp = "";
                for (int i = lastPos; i < currentPos; i++){
                    tmp = tmp + timeToBan.charAt(i);
                }
                lastPos = currentPos + 1;
                timeDay = Integer.parseInt(tmp);	//time in day
            } else {
                return -1;
            }

        }
        totalTime = timeSec + (timeMin * 60) + (timeHr * 60 * 60) + (timeDay * 24 * 60 * 60);	//Total time in seconds
        return totalTime;
    }

    public static String calcTimeMsg(int start, int end){
        int duration = end - start;
        String result = "";
        long day = 0, hr = 0, min = 0, sec = 0;
        day = TimeUnit.SECONDS.toDays(duration);
        hr = TimeUnit.SECONDS.toHours(duration) - (day * 24);
        min = TimeUnit.SECONDS.toMinutes(duration) - (TimeUnit.SECONDS.toHours(duration) * 60);
        sec = TimeUnit.SECONDS.toSeconds(duration) - (TimeUnit.SECONDS.toMinutes(duration) * 60);
        if (day > 0)
            result = result + day + " days ";
        if (hr > 0)
            result = result + hr + " hours ";
        if (min > 0)
            result = result + min + " minutes ";
        if (sec > 0)
            result = result + sec + " seconds";
        return result;
    }

}
