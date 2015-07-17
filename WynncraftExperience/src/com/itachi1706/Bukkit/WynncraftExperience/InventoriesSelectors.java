package com.itachi1706.Bukkit.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenneth on 17/7/2015.
 * for WynncraftExperience in com.itachi1706.Bukkit.WynncraftExperience
 */
public class InventoriesSelectors {

    //Back button at max - 5
    public static Inventory mainSelector = Bukkit.createInventory(null, 9, ChatColor.RED + "Teleport Selector");
    public static Inventory townSelector = Bukkit.createInventory(null, 45, ChatColor.RED + "Town Selector");
    public static Inventory dungeonSelector = Bukkit.createInventory(null, 45, ChatColor.RED + "Dungeon Selector");
    public static Inventory miscSelector = Bukkit.createInventory(null, 45, ChatColor.RED + "Miscellaneous Selector");
    public static Inventory otherSelector = Bukkit.createInventory(null, 45, ChatColor.RED + "Uncatergorized Selector");

    static {
        //Fixed Lores
        List<String> townSel = new ArrayList<String>();
        List<String> dungeonSel = new ArrayList<String>();
        List<String> miscSel = new ArrayList<String>();
        List<String> otherSel = new ArrayList<String>();
        townSel.add("Click here to select");
        townSel.add("a town to TP to!");
        dungeonSel.add("Click here to select");
        dungeonSel.add("a dungeon to TP to!");
        miscSel.add("Click here to select");
        miscSel.add("a Misc Area to TP to!");
        otherSel.add("Click here to select an");
        otherSel.add("Uncatergorized Area to TP to!");

        //Gamemode Options
        mainSelector.setItem(1, makeItem(Material.EMERALD_BLOCK, ChatColor.GOLD + "Towns", townSel));
        mainSelector.setItem(3, makeItem(Material.WEB, ChatColor.GOLD + "Dungeons", dungeonSel));
        mainSelector.setItem(5, makeItem(Material.STRING, ChatColor.GOLD + "Misc Areas", miscSel));
        mainSelector.setItem(7, makeItem(Material.ROTTEN_FLESH, ChatColor.GOLD + "Uncatergorized", otherSel));
    }

    private static ItemStack makeItem(Material material, String displayName, List<String> lore){
        ItemStack i = new ItemStack(material);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(displayName);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public static void updateTownSel(){
        townSelector.clear();
        townSelector.setItem((townSelector.getSize() - 5), makeItem(Material.ARROW, ChatColor.GOLD + "Go Back", null));

        for (int i = 0; i < Main.locations.size(); i++){
            WynnLocations wl = Main.locations.get(i);
            if (wl.getType().equalsIgnoreCase("town")){
                List<String> townLore = new ArrayList<String>();
                townLore.add("Details");
                townLore.add(ChatColor.AQUA + "X: " + ChatColor.WHITE + wl.getX());
                townLore.add(ChatColor.AQUA + "Y: " + ChatColor.WHITE + wl.getY());
                townLore.add(ChatColor.AQUA + "Z: " + ChatColor.WHITE + wl.getZ());
                townLore.add("");
                townLore.add("Click here to teleport");
                townLore.add("to " + wl.getName());
                townSelector.addItem(makeItem(Material.NETHER_STAR, wl.getName(), townLore));
            }
        }
    }

    public static void updateDungeonSel(){
        dungeonSelector.clear();
        dungeonSelector.setItem((dungeonSelector.getSize() - 5), makeItem(Material.ARROW, ChatColor.GOLD + "Go Back", null));

        for (int i = 0; i < Main.locations.size(); i++){
            WynnLocations wl = Main.locations.get(i);
            if (wl.getType().equalsIgnoreCase("dungeon")){
                List<String> townLore = new ArrayList<String>();
                townLore.add("Details");
                townLore.add(ChatColor.AQUA + "X: " + ChatColor.WHITE + wl.getX());
                townLore.add(ChatColor.AQUA + "Y: " + ChatColor.WHITE + wl.getY());
                townLore.add(ChatColor.AQUA + "Z: " + ChatColor.WHITE + wl.getZ());
                townLore.add("");
                townLore.add("Click here to teleport");
                townLore.add("to " + wl.getName());
                dungeonSelector.addItem(makeItem(Material.NETHER_STAR, wl.getName(), townLore));
            }
        }
    }

    public static void updateMiscSel(){
        miscSelector.clear();
        miscSelector.setItem((miscSelector.getSize() - 5), makeItem(Material.ARROW, ChatColor.GOLD + "Go Back", null));

        for (int i = 0; i < Main.locations.size(); i++){
            WynnLocations wl = Main.locations.get(i);
            if (wl.getType().equalsIgnoreCase("misc")){
                List<String> townLore = new ArrayList<String>();
                townLore.add("Details");
                townLore.add(ChatColor.AQUA + "X: " + ChatColor.WHITE + wl.getX());
                townLore.add(ChatColor.AQUA + "Y: " + ChatColor.WHITE + wl.getY());
                townLore.add(ChatColor.AQUA + "Z: " + ChatColor.WHITE + wl.getZ());
                townLore.add("");
                townLore.add("Click here to teleport");
                townLore.add("to " + wl.getName());
                miscSelector.addItem(makeItem(Material.NETHER_STAR, wl.getName(), townLore));
            }
        }
    }

    public static void updateNoneSel(){
        otherSelector.clear();
        otherSelector.setItem((otherSelector.getSize() - 5), makeItem(Material.ARROW, ChatColor.GOLD + "Go Back", null));
        for (int i = 0; i < Main.locations.size(); i++){
            WynnLocations wl = Main.locations.get(i);
            if (wl.getType().equalsIgnoreCase("")){
                List<String> townLore = new ArrayList<String>();
                townLore.add("Details");
                townLore.add(ChatColor.AQUA + "X: " + ChatColor.WHITE + wl.getX());
                townLore.add(ChatColor.AQUA + "Y: " + ChatColor.WHITE + wl.getY());
                townLore.add(ChatColor.AQUA + "Z: " + ChatColor.WHITE + wl.getZ());
                townLore.add("");
                townLore.add("Click here to teleport");
                townLore.add("to " + wl.getName());
                otherSelector.addItem(makeItem(Material.NETHER_STAR, wl.getName(), townLore));
            }
        }
    }

    public static void selectMainItem(Player p, int clickslot){
        switch(clickslot){
            case 1: updateTownSel();
                p.closeInventory();
                p.openInventory(townSelector);
                break;
            case 3: updateDungeonSel();
                p.closeInventory();
                p.openInventory(dungeonSelector);
                break;
            case 5: updateMiscSel();
                p.closeInventory();
                p.openInventory(miscSelector);
                break;
            case 7: updateNoneSel();
                p.closeInventory();
                p.openInventory(otherSelector);
                break;
        }
    }

    public static void teleportToArea(Player p, int clickslot, Inventory inv){
        try {
            if (inv.getName().equals(dungeonSelector.getName())){
                ItemStack is = dungeonSelector.getItem(clickslot);
                if (clickslot == (inv.getSize() - 5)) {
                    p.closeInventory();
                    p.openInventory(mainSelector);
                    return;
                }
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tpto " + p.getName() + " " + is.getItemMeta().getDisplayName());
            } else if (inv.getName().equals(townSelector.getName())){
                ItemStack is = townSelector.getItem(clickslot);
                if (clickslot == (inv.getSize() - 5)){
                    p.closeInventory();
                    p.openInventory(mainSelector);
                    return;
                }
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tpto " + p.getName() + " " + is.getItemMeta().getDisplayName());
            } else if (inv.getName().equals(miscSelector.getName())){
                ItemStack is = miscSelector.getItem(clickslot);
                if (clickslot == (inv.getSize() - 5)){
                    p.closeInventory();
                    p.openInventory(mainSelector);
                    return;
                }
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tpto " + p.getName() + " " + is.getItemMeta().getDisplayName());
            } else if (inv.getName().equals(otherSelector.getName())){
                ItemStack is = otherSelector.getItem(clickslot);
                if (clickslot == (inv.getSize() - 5)){
                    p.closeInventory();
                    p.openInventory(mainSelector);
                    return;
                }
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tpto " + p.getName() + " " + is.getItemMeta().getDisplayName());
            }
        } catch (NullPointerException ex){

        }
    }

}
