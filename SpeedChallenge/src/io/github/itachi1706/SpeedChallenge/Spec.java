package io.github.itachi1706.SpeedChallenge;

import java.util.ArrayList;

import org.bukkit.Bukkit;
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
		ItemStack is = new ItemStack(Material.COMPASS);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Nearest Player");
		ArrayList<String> lol = new ArrayList<String>();
		lol.add("Right click to get details of the");
		lol.add("location of nearest player!");
		im.setLore(lol);
		is.setItemMeta(im);
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setItem(0, is);
		p.setCompassTarget(getNearestPlayer(p).getLocation());
	}
	
	//Get Nearest Player
	public static Player getNearestPlayer (Player player){
        Player nearest = null;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
            if (!player.getWorld().equals(onlinePlayer.getWorld())) continue; // Check only players in player's world
            if ((nearest!=null)&&
       (player.getLocation().distance(nearest.getLocation())<player.getLocation().distance(onlinePlayer.getLocation()))) continue;
            nearest = onlinePlayer;
        }
        return nearest;
    }
	
	public static void spectator(Player p){
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setSaturation(20);
		p.setCompassTarget(getNearestPlayer(p).getLocation());
	}
	
	public static void spectatorMsg(Player p){
		//Player nearest = getNearestPlayer(p);
		String msg = ChatColor.DARK_RED + "FEATURE COMING SOON!";
		/*String[] msg = {"&b[SpeedChallenge] &aNearest Player: &6" + nearest.getDisplayName(), 
				"&b[SpeedChallenge] &aDistance Away: &c" + p.getLocation().distance(nearest.getLocation()) + " block(s)",
				"&b[SpeedChallenge] &a Location: &5X:&c " + p.getLocation().getX() + " &5Y:&c " + p.getLocation().getY() + " &5Z:&c " + p.getLocation().getZ()};*/
		p.sendMessage(msg);
	}

}
