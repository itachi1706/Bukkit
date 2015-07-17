package com.itachi1706.Bukkit.SpeedChallenge.Gamemodes;

import com.itachi1706.Bukkit.SpeedChallenge.Main;
import com.itachi1706.Bukkit.SpeedChallenge.Utilities.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Kenneth on 17/7/2015.
 * for SpeedChallenge in com.itachi1706.Bukkit.SpeedChallenge.Gamemodes
 */
public class AbbaRulesRetardStyle extends AbstractGamemode {

    private static String challengeTitle = "ABBA Rules (God Edition)";

    public static String getGMTitle(){
        return challengeTitle;
    }

    public static void gmInfo(){
        // List all information about the gamemode
        // Lists at the start
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                ABBA RULES (God Edition)"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&l                   MindCrack Style"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3This is based of the MindCrack ABBA Rules game invented by &6VintageBeef"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lObjectives"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bGet as much of the following items. The person with the highest points wins!"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bYou are given a &6&lSILK TOUCH DIAMOND PICKAXE &band &6&lGOD ITEMS&b for this challenge"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bShould you lose the items, you can do &6/reequip&b to regain it"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aNOTE:&b This Challenge requires you to mine the ores with silk touch. The ore ingots will not count"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lItems and its respective scores"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cLapis Lazulli Ore - &a1 point"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cRedstone Ore - &a1 point"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cGold Ore - &a3 points"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cDiamond Ore - &a5 points"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cEmerald Ore - &a7 points"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cName Tag - &a10 points"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cGolden Apple - &a10 points"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&cAny Horse Armor - &a10 points"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6=================================================="));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&aTo check your current objectives, do &6/listobjectives"));
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4&lBETA TEST GAMEMODE - SCORE AND TIME GIVEN SUBJECTED TO CHANGE"));
    }

    public static void checkInventory(){
        if (Main.gamePlayerList.size() == 0){
            Bukkit.getLogger().info("INVALID - NO PLAYERS");
            return;
        }
        for (int i = 0; i < Main.gamePlayerList.size(); i++){
            Player p = Main.gamePlayerList.get(i);
            @SuppressWarnings("unused")
            int check = checkObjective(p);
        }
    }

    public static int checkObjective(Player p){
        //Basic Score
        int score = 0;
        int tmpAmount = 0;
        //Grab the amount of each item of the player
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.LAPIS_ORE));
        score += (tmpAmount);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.REDSTONE_ORE));
        score += (tmpAmount);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_ORE));
        score += (tmpAmount*3);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_ORE));
        score += (tmpAmount*5);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.EMERALD_ORE));
        score += (tmpAmount*7);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.NAME_TAG));
        score += (tmpAmount*10);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLDEN_APPLE));
        score += (tmpAmount*10);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_BARDING));
        score += (tmpAmount*10);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.IRON_BARDING));
        score += (tmpAmount*10);
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_BARDING));
        score += (tmpAmount*10);
        //End of it add score to player
        ScoreboardHelper.setScoreOfPlayer(p, score);
        return score;
    }

    public static int amountInInventory(Inventory inventory, ItemStack item){
        int amount = 0;
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++){
            if (items[i] != null && items[i].getType() == item.getType() && items[i].getDurability() == item.getDurability()){
                amount += items[i].getAmount();
            }
        }
        return amount;
    }

    public static void initWeapon(){
        //Pickaxe
        ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("This is a pickaxe that is given at the start");
        lore.add("to allow you to play ABBA Rules (GOD)!");
        im.setDisplayName(ChatColor.AQUA + "ABBA Rules Pickaxe (GOD)");
        im.setLore(lore);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is.addUnsafeEnchantment(Enchantment.DIG_SPEED, 9999);
        is.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        //Shovel
        ItemStack is2 = new ItemStack(Material.DIAMOND_SPADE);
        ItemMeta im2 = is.getItemMeta();
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("This is a shovel that is given at the start");
        lore2.add("to allow you to play ABBA Rules (GOD)!");
        im2.setDisplayName(ChatColor.AQUA + "ABBA Rules Shovel (GOD)");
        im2.setLore(lore2);
        is2.setItemMeta(im2);
        is2.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is2.addUnsafeEnchantment(Enchantment.DIG_SPEED, 9999);
        //Sword
        ItemStack is3 = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta im3 = is.getItemMeta();
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("This is a sword that is given at the start");
        lore3.add("to allow you to play ABBA Rules (GOD)!");
        im3.setDisplayName(ChatColor.AQUA + "ABBA Rules Sword (GOD)");
        im3.setLore(lore3);
        is3.setItemMeta(im3);
        is3.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is3.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9999);
        //Helmet
        ItemStack is4 = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta im4 = is.getItemMeta();
        ArrayList<String> lore4 = new ArrayList<String>();
        lore4.add("This is a Diamond Helmet that is given at the start");
        lore4.add("to allow you to play ABBA Rules (GOD)!");
        im4.setDisplayName(ChatColor.AQUA + "ABBA Rules Helmet (GOD)");
        im4.setLore(lore4);
        is4.setItemMeta(im4);
        is4.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is4.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        //Chestpiece
        ItemStack is5 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta im5 = is.getItemMeta();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add("This is a Diamond Chestpiece that is given at the start");
        lore5.add("to allow you to play ABBA Rules (GOD)!");
        im5.setDisplayName(ChatColor.AQUA + "ABBA Rules Chestpiece (GOD)");
        im5.setLore(lore5);
        is5.setItemMeta(im5);
        is5.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is5.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        //Leggings
        ItemStack is6 = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta im6 = is.getItemMeta();
        ArrayList<String> lore6 = new ArrayList<String>();
        lore6.add("This is a Diamond Leggings that is given at the start");
        lore6.add("to allow you to play ABBA Rules (GOD)!");
        im6.setDisplayName(ChatColor.AQUA + "ABBA Rules Leggings (GOD)");
        im6.setLore(lore6);
        is6.setItemMeta(im6);
        is6.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is6.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        //Boots
        ItemStack is7 = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta im7 = is.getItemMeta();
        ArrayList<String> lore7 = new ArrayList<String>();
        lore7.add("This is a Diamond Boots that is given at the start");
        lore7.add("to allow you to play ABBA Rules (GOD)!");
        im7.setDisplayName(ChatColor.AQUA + "ABBA Rules Boots (GOD)");
        im7.setLore(lore7);
        is7.setItemMeta(im7);
        is7.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is7.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        is7.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 9999);
        for (int i = 0; i < Main.gamePlayerList.size(); i++){
            Player p = Main.gamePlayerList.get(i);
            p.getInventory().addItem(is);
            p.getInventory().addItem(is2);
            p.getInventory().addItem(is3);
            p.getInventory().setHelmet(is4);
            p.getInventory().setChestplate(is5);
            p.getInventory().setLeggings(is6);
            p.getInventory().setBoots(is7);
            p.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 64));
            p.getInventory().addItem(new ItemStack(Material.TORCH, 64));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You have been given a &aSilk Touch Diamond Pickaxe with Efficiency II and Unbreaking&6 and &aGOD ARMOR/TOOLS&6 and some food for this challenge!"));
        }
    }

    public static void checkWeapon(Player p){
        //Pickaxe
        ItemStack is = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta im = is.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("This is a pickaxe that is given at the start");
        lore.add("to allow you to play ABBA Rules (GOD)!");
        im.setDisplayName(ChatColor.AQUA + "ABBA Rules Pickaxe (GOD)");
        im.setLore(lore);
        is.setItemMeta(im);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is.addUnsafeEnchantment(Enchantment.DIG_SPEED, 9999);
        is.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
        //Shovel
        ItemStack is2 = new ItemStack(Material.DIAMOND_SPADE);
        ItemMeta im2 = is.getItemMeta();
        ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("This is a shovel that is given at the start");
        lore2.add("to allow you to play ABBA Rules (GOD)!");
        im2.setDisplayName(ChatColor.AQUA + "ABBA Rules Shovel (GOD)");
        im2.setLore(lore2);
        is2.setItemMeta(im2);
        is2.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is2.addUnsafeEnchantment(Enchantment.DIG_SPEED, 9999);
        //Sword
        ItemStack is3 = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta im3 = is.getItemMeta();
        ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("This is a sword that is given at the start");
        lore3.add("to allow you to play ABBA Rules (GOD)!");
        im3.setDisplayName(ChatColor.AQUA + "ABBA Rules Sword (GOD)");
        im3.setLore(lore3);
        is3.setItemMeta(im3);
        is3.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is3.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9999);
        //Helmet
        ItemStack is4 = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta im4 = is.getItemMeta();
        ArrayList<String> lore4 = new ArrayList<String>();
        lore4.add("This is a Diamond Helmet that is given at the start");
        lore4.add("to allow you to play ABBA Rules (GOD)!");
        im4.setDisplayName(ChatColor.AQUA + "ABBA Rules Helmet (GOD)");
        im4.setLore(lore4);
        is4.setItemMeta(im4);
        is4.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is4.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        //Chestpiece
        ItemStack is5 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta im5 = is.getItemMeta();
        ArrayList<String> lore5 = new ArrayList<String>();
        lore5.add("This is a Diamond Chestpiece that is given at the start");
        lore5.add("to allow you to play ABBA Rules (GOD)!");
        im5.setDisplayName(ChatColor.AQUA + "ABBA Rules Chestpiece (GOD)");
        im5.setLore(lore5);
        is5.setItemMeta(im5);
        is5.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is5.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        //Leggings
        ItemStack is6 = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta im6 = is.getItemMeta();
        ArrayList<String> lore6 = new ArrayList<String>();
        lore6.add("This is a Diamond Leggings that is given at the start");
        lore6.add("to allow you to play ABBA Rules (GOD)!");
        im6.setDisplayName(ChatColor.AQUA + "ABBA Rules Leggings (GOD)");
        im6.setLore(lore6);
        is6.setItemMeta(im6);
        is6.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is6.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        //Boots
        ItemStack is7 = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta im7 = is.getItemMeta();
        ArrayList<String> lore7 = new ArrayList<String>();
        lore7.add("This is a Diamond Boots that is given at the start");
        lore7.add("to allow you to play ABBA Rules (GOD)!");
        im7.setDisplayName(ChatColor.AQUA + "ABBA Rules Boots (GOD)");
        im7.setLore(lore7);
        is7.setItemMeta(im7);
        is7.addUnsafeEnchantment(Enchantment.DURABILITY, 9999);
        is7.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9999);
        is7.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 9999);
        p.getInventory().addItem(is);
        p.getInventory().addItem(is2);
        p.getInventory().addItem(is3);
        p.getInventory().setHelmet(is4);
        p.getInventory().setChestplate(is5);
        p.getInventory().setLeggings(is6);
        p.getInventory().setBoots(is7);
        p.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 64));
        p.getInventory().addItem(new ItemStack(Material.TORCH, 64));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6You have been regiven a &aSilk Touch Diamond Pickaxe with Efficiency II and Unbreaking&6 and &aGOD ARMOR/TOOLS&6 and some food! Dont lose it again!"));
        return;
    }

    //List all objectives for a player (for future command)
    public static void listObjectives(Player p){
        p.sendMessage(ChatColor.GOLD + "==================================================");
        p.sendMessage(ChatColor.GOLD + "OBJECTIVES CHECK");
        int tmpAmount = 0;
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.LAPIS_ORE));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Lapis Lazulli Ore - &a(1 point)&r: &b" + tmpAmount));
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.REDSTONE_ORE));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Redstone Ore - &a(1 point)&r: &b" + tmpAmount));
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_ORE));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Gold Ore - &a(3 points)&r: &b" + tmpAmount));
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_ORE));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Diamond Ore - &a(5 points)&r: &b" + tmpAmount));
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.EMERALD_ORE));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Emerald Ore - &a(7 points)&r: &b" + tmpAmount));
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.NAME_TAG));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Name Tag - &a(10 points)&r: &b" + tmpAmount));
        tmpAmount = amountInInventory(p.getInventory(), new ItemStack(Material.GOLDEN_APPLE));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Golden Apple - &a(10 points)&r: &b" + tmpAmount));
        int tmpAmount1 = amountInInventory(p.getInventory(), new ItemStack(Material.GOLD_BARDING));
        int tmpAmount2 = amountInInventory(p.getInventory(), new ItemStack(Material.IRON_BARDING));
        int tmpAmount3 = amountInInventory(p.getInventory(), new ItemStack(Material.DIAMOND_BARDING));
        tmpAmount = tmpAmount1 + tmpAmount2 + tmpAmount3;
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Horse Armor - &a(10 points)&r: &b" + tmpAmount));
        p.sendMessage(ChatColor.GOLD + "==================================================");

    }

}
