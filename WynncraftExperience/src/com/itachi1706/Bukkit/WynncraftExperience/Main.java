package com.itachi1706.Bukkit.WynncraftExperience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kenneth on 17/7/2015.
 * for WynncraftExperience in com.itachi1706.Bukkit.WynncraftExperience
 */
public class Main extends JavaPlugin implements Listener {

    static double pluginVersion = 1.0;
    public static String pluginPrefix = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "WynnCraft Experience" + ChatColor.DARK_RED + "] " + ChatColor.WHITE;

    public static ArrayList<WynnLocations> locations = new ArrayList<WynnLocations>();

    public static boolean commandTpto, commandSpeed;

    @Override
    public void onEnable(){
        //Logic when plugin gets enabled
        getLogger().info("Enabling Plugin...");
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        this.saveConfig();
        initializeConfig();
        getLogger().info("Enabling Plugin listeners...");
        getCommand("wynncraftadmin").setExecutor(new AdminCmd(this));
        getCommand("help").setExecutor(new HelpCmd(this));
        getCommand("addtp").setExecutor(new AddTP(this));
        getCommand("deltp").setExecutor(new DelTP(this));
        getCommand("tplist").setExecutor(new TPList(this));
        getCommand("speedmode").setExecutor(new SpeedMode(this));
        getCommand("tpto").setExecutor(new TpTo(this));
        getCommand("resetpitch").setExecutor(new ResetDirection(this));
        getCommand("resetyaw").setExecutor(new ResetDirection(this));
        getCommand("settype").setExecutor(new SetType(this));
        getCommand("tpto").setTabCompleter(new TpToTab());
        getCommand("deltp").setTabCompleter(new DelTpTab());
        loadListeners();
    }

    @Override
    public void onDisable(){
        //Logic when plugin gets disabled
        getLogger().info("Disabling Plugin...");
        this.saveConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //Main Command
        if(cmd.getName().equalsIgnoreCase("wynncraft")){
            if (args.length < 1 || args.length > 1){
                displayHelp(sender);
                return true;
            }
            if (!sender.hasPermission("wynncraft.admin")){
                sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command");
                return true;
            }
            if (args[0].equalsIgnoreCase("version")){
                //Show Plugin Version
                sender.sendMessage(ChatColor.GOLD + "======================================");
                sender.sendMessage(ChatColor.BLUE + "Wynncraft Experience Plugin");
                sender.sendMessage(ChatColor.GOLD + "======================================");
                sender.sendMessage("Version: " + ChatColor.AQUA + pluginVersion);
                return true;
            } else if (args[0].equalsIgnoreCase("reload")){
                //Reload Plugin
                reloadCommand(this);
                sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Configuration reloaded");
                return true;
            } else if (args[0].equalsIgnoreCase("module")){
                listModules(sender);
                return true;
            } else {
                //Error
                displayHelp(sender);
                return true;
            }
        }
        return false;
    }

    public void displayHelp(CommandSender s){
        s.sendMessage(ChatColor.GOLD + "-----------WynnCraft Experience Commands-----------");
        s.sendMessage(ChatColor.GOLD + "/wynncraft version: " + ChatColor.WHITE +  "Check current plugin version");
        s.sendMessage(ChatColor.GOLD + "/wynncraft reload: " + ChatColor.WHITE +  "Reload Config");
        s.sendMessage(ChatColor.GOLD + "/wynncraft module: " + ChatColor.WHITE +  "Display Current Activated Modules");
        s.sendMessage(ChatColor.GOLD + "/help: " + ChatColor.WHITE +  "Displays Help");
        s.sendMessage(ChatColor.GOLD + "/wynncraftadmin <module>: " + ChatColor.WHITE +  "Administration Command");
        if (commandTpto){
            s.sendMessage(ChatColor.GOLD + "/tpto [town]: " + ChatColor.WHITE +  "Teleports to a town");
            s.sendMessage(ChatColor.GOLD + "/tplist: " + ChatColor.WHITE +  "List of places where you can teleport to");
        }
        if (commandSpeed){
            s.sendMessage(ChatColor.GOLD + "/speedmode: " + ChatColor.WHITE +  "Toggles speedmode");
        }
        if (s.hasPermission("wynncraft.admin")){
            s.sendMessage(ChatColor.GOLD + "/addtp <name>: " + ChatColor.WHITE +  "Create a TP point where you stand");
            s.sendMessage(ChatColor.GOLD + "/deltp <name>: " + ChatColor.WHITE +  "Deletes a TP point");
            s.sendMessage(ChatColor.GOLD + "/resetpitch [name]: " + ChatColor.WHITE +  "Resets your Pitch (Facing)");
            s.sendMessage(ChatColor.GOLD + "/resetyaw [name]: " + ChatColor.WHITE +  "Resets your Yaw (Direction)");
            s.sendMessage(ChatColor.GOLD + "/settype <location> <town/dungeon/misc/reset>: " + ChatColor.WHITE +  "Sets Type");
        }
    }

    private void initializeConfig(){
        commandTpto = this.getConfig().getBoolean("modules.teleport");
        commandSpeed = this.getConfig().getBoolean("modules.speedmode");
        ConfigurationSection cs = this.getConfig().getConfigurationSection("warps");
        locations.clear();
        for (String s : cs.getKeys(false)){
            ConfigurationSection cs1 = cs.getConfigurationSection(s);
            try {
                if (cs1.getString("type") == null){
                    cs1.set("type", "");
                }
                WynnLocations wl = new WynnLocations(s, cs1.getDouble("x"), cs1.getDouble("y"), cs1.getDouble("z"), (float) cs1.getDouble("yaw"), (float) cs1.getDouble("pitch"), cs1.getString("type"));
                locations.add(wl);
            } catch (NullPointerException ex){
                getLogger().warning("Error attempting to add " + s + " as a TP location. Setting pitch and yaw to 0 by default");
                getLogger().warning("Please tp to this location and recreate this TP location with /addtp " + s);
                WynnLocations wl = new WynnLocations(s, cs1.getDouble("x"), cs1.getDouble("y"), cs1.getDouble("z"));
                locations.add(wl);
            }
        }
    }

    private void reloadCommand(Plugin plugin){
        this.reloadConfig();
        initializeConfig();
        HandlerList.unregisterAll(plugin);
        loadListeners();
    }

    private void loadListeners(){
        getServer().getPluginManager().registerEvents(this,this);
    }

    private void listModules(CommandSender s){
        s.sendMessage(ChatColor.GOLD + "-----------WynnCraft Experience Modules-----------");
        s.sendMessage(ChatColor.GOLD + "Teleport: " + ChatColor.WHITE + commandTpto);
        s.sendMessage(ChatColor.GOLD + "Speedmode: " + ChatColor.WHITE + commandSpeed);
    }

    public static void sendAdminMsg(String msg, CommandSender sender){
        for (Player p : Bukkit.getServer().getOnlinePlayers()){
            if (!(p.getName().equalsIgnoreCase(sender.getName()))){
                if (p.hasPermission("bukkit.broadcast.admin")){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }
            }
        }
    }

    @EventHandler
    private void entityDamage(EntityDamageEvent e){
        Entity p = e.getEntity();
        if (p instanceof Player) {
            PlayerInventory i = ((HumanEntity) p).getInventory();
            for(ItemStack ac : i.getArmorContents()){
                ac.setDurability((short) 0);
                Bukkit.getLogger().fine("Reset " + ((Player) p).getName() + "'s durability for " + ac.toString());
            }
        }
    }

    @EventHandler
    private void interactRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Material mat = p.getInventory().getItemInHand().getType();
        if (isDurable(mat)){
            p.getInventory().getItemInHand().setDurability((short) 0);
            Bukkit.getLogger().fine("Reset " + ((Player) p).getName() + "'s durability for " + p.getInventory().getItemInHand().toString());
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (p.getInventory().getItemInHand().isSimilar(getTPItem())){
                e.getPlayer().openInventory(InventoriesSelectors.mainSelector);
            }
        }
    }

    @EventHandler
    private void checkItemClicked(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        int clickSlot = e.getSlot();
        Inventory inventory = e.getInventory();
        try {
            if (inventory.getName().equals(InventoriesSelectors.mainSelector.getName())){
                //Main Selector
                InventoriesSelectors.selectMainItem(player, clickSlot);
                e.setCancelled(true);
            } else if (inventory.getName().equals(InventoriesSelectors.townSelector.getName())){
                //Town Selector
                InventoriesSelectors.teleportToArea(player, clickSlot, inventory);
                e.setCancelled(true);
            } else if (inventory.getName().equals(InventoriesSelectors.dungeonSelector.getName())){
                //Dungeon Selector
                InventoriesSelectors.teleportToArea(player, clickSlot, inventory);
                e.setCancelled(true);
            } else if (inventory.getName().equals(InventoriesSelectors.miscSelector.getName())){
                //Misc Selector
                InventoriesSelectors.teleportToArea(player, clickSlot, inventory);
                e.setCancelled(true);
            } else if (inventory.getName().equals(InventoriesSelectors.otherSelector.getName())){
                //Others Selector
                InventoriesSelectors.teleportToArea(player, clickSlot, inventory);
                e.setCancelled(true);
            }
        } catch (Exception ex) {
            String adminmsg = "&4[SERIOUS]&r An exception occured! Error Details are as follows (Check CONSOLE for more details):";
            String adminerrormsg = ex.toString();
            ex.printStackTrace();
            sendAdminMsg(adminmsg, getServer().getConsoleSender());
            sendAdminMsg(adminerrormsg, getServer().getConsoleSender());

        }
    }

    @EventHandler
    private void playerJoining(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!(p.getInventory().contains(getTPItem()))){
            p.getInventory().addItem(getTPItem());
            p.sendMessage(ChatColor.GOLD + "You have been given a clock item to teleport with. Check your inventory for it! Relog if you lose it");
        }
    }

    private ItemStack getTPItem(){
        ItemStack is = new ItemStack(Material.WATCH);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Teleporter");
        List<String> lore = new ArrayList<String>();
        lore.add("Click here to select");
        lore.add("a place to teleport to");
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    private boolean isDurable(Material m){
        //Diamond tools
        if (m == Material.DIAMOND_SPADE || m == Material.DIAMOND_AXE || m == Material.DIAMOND_SWORD || m == Material.DIAMOND_HOE || m == Material.DIAMOND_PICKAXE){
            return true;
        }
        //Gold tools
        if (m == Material.GOLD_SPADE || m == Material.GOLD_AXE || m == Material.GOLD_SWORD || m == Material.GOLD_HOE || m == Material.GOLD_PICKAXE){
            return true;
        }
        //Iron tools
        if (m == Material.IRON_SPADE || m == Material.IRON_AXE || m == Material.IRON_SWORD || m == Material.IRON_HOE || m == Material.IRON_PICKAXE){
            return true;
        }
        //Stone tools
        if (m == Material.STONE_SPADE || m == Material.STONE_AXE || m == Material.STONE_SWORD || m == Material.STONE_HOE || m == Material.STONE_PICKAXE){
            return true;
        }
        //Wood tools
        if (m == Material.WOOD_SPADE || m == Material.WOOD_AXE || m == Material.WOOD_SWORD || m == Material.WOOD_HOE || m == Material.WOOD_PICKAXE){
            return true;
        }
        //Misc
        if (m == Material.BOW || m == Material.FISHING_ROD || m == Material.FLINT_AND_STEEL || m == Material.CARROT_STICK || m == Material.SHEARS){
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> list = new ArrayList<String>();
        // Now, it's just like any other command.
        // Check if the sender is a player.
        if (sender instanceof Player) {
            // Check if the command is "something."
            if (cmd.getName().equalsIgnoreCase("wynncraft")){
                // If the player has not typed anything in
                if (args.length == 0) {
                    // Add a list of words that you'd like to show up
                    // when the player presses tab.
                    list.add("module");
                    list.add("reload");
                    list.add("version");
                    // Sort them alphabetically.
                    Collections.sort(list);
                    // return the list.
                    return list;
                    // If player has typed one word in.
                    // This > "/command hello " does not count as one
                    // argument because of the space after the hello.
                } else if (args.length == 1) {
                    list.add("module");
                    list.add("reload");
                    list.add("version");
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
                }
            }
        }
        return null;
    }

}
