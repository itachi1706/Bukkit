package io.github.itachi1706.SpeedChallenge;

import io.github.itachi1706.SpeedChallenge.Utilities.InventoriesPreGame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Spec {
	
	public static Inventory spectatePlayerList = Bukkit.createInventory(null, 27, ChatColor.RED + "Teleport to a Player!");
	
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
		lol.add("Left click to select ");
		lol.add("player to teleport to!");
		im.setLore(lol);
		is.setItemMeta(im);
		return is;
	}
	
	public static void updateSpectatorList(){
		spectatePlayerList.clear();
		for (int i = 0; i < Main.playerList.size(); i++){
			Player p = Main.playerList.get(i);
			List<String> lore = new ArrayList<String>();
			lore.add("Click here to teleport");
			lore.add("to the player!");
			spectatePlayerList.addItem(InventoriesPreGame.makeItem(Material.SKULL_ITEM,1 ,3, p.getDisplayName(), lore));
		}
	}
	
	public static void selectPlayer(Player p, int clickslot){
		ItemStack i = spectatePlayerList.getItem(clickslot);
		ItemMeta im = i.getItemMeta();
		String displayName = im.getDisplayName();
		Player target = null;
		for (Player online : Bukkit.getServer().getOnlinePlayers()){
			if (online.getDisplayName().equals(displayName)){
				target = online;
				break;
			}
		}
		if (target == null){
			p.sendMessage(ChatColor.DARK_RED + "AN ERROR OCCURED");
			return;
		}
		if (p.getWorld().equals(target.getWorld())){
			if (checkPlayerInGame(p)){
				if (!p.hasPermission("sc.override")){
					p.sendMessage(ChatColor.RED + "You are currently a player ingame and cannot use this command");
					return;
				} else {
					p.sendMessage(ChatColor.GOLD + "OVERRIDING INABILITY TO TELEPORT - " + ChatColor.DARK_RED + "CURRENTLY IN GAME");
					p.sendMessage(ChatColor.GOLD + "You are a player ingame but can teleport due to being a player with permission. Do not abuse it");
				}
			}
			p.sendMessage(ChatColor.LIGHT_PURPLE + "Teleported to " + target.getDisplayName());
			p.teleport(target.getLocation());
			return;
		} else {
			if (p.hasPermission("sc.override")){
				p.sendMessage(ChatColor.GOLD + "OVERRIDING INABILITY TO TELEPORT - " + ChatColor.DARK_RED + "DIFFERENT WORLD");
				p.sendMessage(ChatColor.GOLD + "You are not in the same world as " + target.getDisplayName() + ChatColor.GOLD + ". If you just joined, do /spectate or you will not be hidden");
				p.sendMessage(ChatColor.LIGHT_PURPLE + "Teleported to " + target.getDisplayName());
				(p).teleport(target.getLocation());
				return;
			}
				p.sendMessage(ChatColor.RED + "You are not in the same world as the player. Please do /spectate to join the game world first");
				return;
		}
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
				"&b[SpeedChallenge] &aDo &6/spectate <playername> &ato teleport to a ingame player or left click the compass!"};
		for (String msges : msg){
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', msges));
		}
	}
	
	public static boolean checkPlayerInGame(Player p){
		for (int i = 0; i < Main.playerList.size(); i++){
			Player game = Main.playerList.get(i);
			if (p.getName().equals(game.getName())){
				return true;
			}
		}
		return false;
	}

}
