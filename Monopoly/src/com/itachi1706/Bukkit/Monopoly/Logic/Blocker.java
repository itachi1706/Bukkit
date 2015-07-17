package com.itachi1706.Bukkit.Monopoly.Logic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Logic
 */
public class Blocker implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void breakBlock(BlockBreakEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (p.hasPermission("monopoly.admin")){

        } else {
            if (b.getType().equals(Material.SKULL_ITEM)){

                if (b.getData() == 0 || b.getData() == 1 || b.getData() == 2 || b.getData() == 3 || b.getData() == 4){
                    e.setCancelled(false);
                } else {
                    p.sendMessage(ChatColor.DARK_GREEN + "Block breaking is not allowed except for tokens!");
                    e.setCancelled(true);
                }

            } else {
                p.sendMessage(ChatColor.DARK_GREEN + "Block breaking is not allowed except for tokens!");
                e.setCancelled(true);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void placeBlock(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        ItemStack i = new ItemStack(b.getType() , 1, b.getData());
        if (p.hasPermission("monopoly.admin")){

        } else {
            if (b.getType().equals(Material.SKULL_ITEM)){
                if (b.getData() == 0 || b.getData() == 1 || b.getData() == 2 || b.getData() == 3 || b.getData() == 4){
                    e.setCancelled(false);
                }
                p.getInventory().addItem(i);
                p.sendMessage(ChatColor.DARK_GREEN + "Block placement is not allowed except for tokens!");
                e.setCancelled(true);
            } else {
                p.getInventory().addItem(i);
                p.sendMessage(ChatColor.DARK_GREEN + "Block placement is not allowed except for tokens!");
                e.setCancelled(true);
            }
        }
    }

}
