package io.github.itachi1706.SpeedChallenge;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Spec {
	
	public static void addSpectator(Player p){
		p.setAllowFlight(true);
		p.setFlying(true);
		p.setCanPickupItems(false);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setSaturation(20);
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setItem(0, getCompass());
		p.setCompassTarget(getNearestPlayer(p).getLocation());
	}
	
	public static ItemStack getCompass(){
		ItemStack is = new ItemStack(Material.COMPASS);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Nearest Player");
		ArrayList<String> lol = new ArrayList<String>();
		lol.add("Right click to get details of the");
		lol.add("location of nearest player!");
		im.setLore(lol);
		is.setItemMeta(im);
		return is;
	}
	
	//Get Nearest Player
	public static Player getNearestPlayer (Player player){
        Player nearest = Main.playerList.get(0);
        double dist = player.getLocation().distance(nearest.getLocation());
        for (int i = 0; i < Main.playerList.size(); i++){
        	Player inGamePlayer = Main.playerList.get(i);
        	if (!player.getWorld().equals(inGamePlayer.getWorld())) continue;
        	if (player.getLocation().distance(inGamePlayer.getLocation()) < dist){
        		nearest = inGamePlayer;
        		dist = player.getLocation().distance(inGamePlayer.getLocation());
        	}
        }
        /*for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
            if (!player.getWorld().equals(onlinePlayer.getWorld())) continue; // Check only players in player's world
            if ((nearest!=null)&&(player.getLocation().distance(nearest.getLocation())<player.getLocation().distance(onlinePlayer.getLocation()))) continue;
            nearest = onlinePlayer;
        }*/
        return nearest;
    }
	
	public static void spectator(Player p){
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setSaturation(20);
		p.setCompassTarget(getNearestPlayer(p).getLocation());
	}
	
	public static void spectatorMsg(Player p){
		Player nearest = getNearestPlayer(p);
		//String msg = ChatColor.DARK_RED + "FEATURE COMING SOON!";
		String[] msg = {"&b[SpeedChallenge] &aNearest Player: &6" + nearest.getDisplayName(), 
				"&b[SpeedChallenge] &aDistance Away: &c" + (Math.round(p.getLocation().distance(nearest.getLocation())*100.0)/100.0) + " block(s)",
				"&b[SpeedChallenge] &aLocation: &5X:&c " + (Math.round(nearest.getLocation().getX()*100.0)/100.0) + " &5Y:&c " + (Math.round(nearest.getLocation().getY()*100.0)/100.0) + " &5Z:&c " + (Math.round(nearest.getLocation().getZ()*100.0)/100.0),
				"&b[SpeedChallenge] &aDo &6/spectate <playername> &ato teleport to a ingame player"};
		for (String msges : msg){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', msges));
		}
	}

}
