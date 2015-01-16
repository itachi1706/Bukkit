package io.github.itachi1706.OpenInventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class SilentChest implements CommandExecutor,Listener {
	
	private String permissionString = "oi.debug";
	
	//START OF COMMANDS

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("silentchest")){
			if (!sender.hasPermission(permissionString)){
				Main.invalidPerms(sender);
				return true;
			}
			if (args.length < 0 || args.length > 2){
				Main.invalidCommandNotice(sender);
				return true;
			}
			if (args.length == 0) {
				//Do on yourself
				if (!(sender instanceof Player)){
					Main.consoleUser(sender);
					return true;
				}
				return updateConfig(sender, null);
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("check")){
				Main.invalidUser(sender);
				return true;
			}
			if (args.length == 2 && args[0].equalsIgnoreCase("check")){
				String target = args[1];
				boolean stat = checkPlayerStat(target);
				if (stat){
					sender.sendMessage(ChatColor.YELLOW + target + " has Silent Chest Mode toggled " + ChatColor.GREEN + "On");
					return true;
				}
				sender.sendMessage(ChatColor.YELLOW + target + " has Silent Chest Mode toggled " + ChatColor.RED + "Off");
				return true;
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("force")){
				Main.invalidUser(sender);
				return true;
			}
			if (args.length == 2 && args[0].equalsIgnoreCase("force")){
				sender.sendMessage(ChatColor.RED + "Force Toggling Silent Chest Mode for " + args[1]);
				return updateConfig(sender, args[1]);
			}
			String target = args[0];
			OfflinePlayer player = Bukkit.getOfflinePlayer(target);
			if (player.hasPlayedBefore()){
				return updateConfig(sender, player.getName());
			}
			sender.sendMessage(ChatColor.RED + target + " has never played on this server before!");
			sender.sendMessage(ChatColor.RED + "To force toggle silent chest, just run /silentchest force " + target);
			return true;
		}
		return false;
	}
	
	private boolean updateConfig(CommandSender sender, String target){
		if (target == null){
			//For sender
			//Check if plugin config has player
			if (!Main.playerConfig.contains(sender.getName() + ".silentChest")){
				//Creates player Config
				Main.playerConfig.set(sender.getName() + ".silentChest", false);
				Main.savePlayer();
			}
			//Check if its true or false
			boolean status = checkPlayerStat(sender.getName());
			if (status){
				//Disable
				Main.playerConfig.set(sender.getName() + ".silentChest", false);
				Main.savePlayer();
				sender.sendMessage("Silent Chest Opening Mode: " + ChatColor.RED + "Disabled");
				return true;
			}
			//Enable
			Main.playerConfig.set(sender.getName() + ".silentChest", true);
			Main.savePlayer();
			sender.sendMessage("Silent Chest Opening Mode: " + ChatColor.GREEN + "Enabled");
			return true;
		} else {
			//For target
			//Check if plugin config has player
			if (!Main.playerConfig.contains(target + ".silentChest")){
				//Creates player Config
				Main.playerConfig.set(target + ".silentChest", false);
				Main.savePlayer();
			}
			//Check if its true or false
			boolean status = checkPlayerStat(target);
			if (status){
				//Disable
				Main.playerConfig.set(target + ".silentChest", false);
				Main.savePlayer();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Toggled Silent Chest &cOff&r for &6" + target));
				return true;
			}
			//Enable
			Main.playerConfig.set(target + ".silentChest", true);
			Main.savePlayer();
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Toggled Silent Chest &aOff&r for &6" + target));
			return true;
		}
	}
	
	private boolean checkPlayerStat(String target){
		//Check if plugin config has player
		if (!Main.playerConfig.contains(target + ".silentChest")){
			return false;
		}
		return Main.playerConfig.getBoolean(target + ".silentChest");
	}
	
	//END OF COMMANDS
	
	//START OF LISTENERS
	
	@EventHandler
	private void onChestOpen(PlayerInteractEvent e){
		if (e.getClickedBlock().getType().equals(Material.CHEST)){
			if (checkPlayerStat(e.getPlayer().getName())){
				//Open Chest Silently
				Inventory inv;
				Chest c = (Chest) e.getClickedBlock().getState();
				inv = c.getBlockInventory();
				Bukkit.getLogger().info("not double");
				if (isDoubleChest(e.getClickedBlock().getState()) == 1)
					
				} else {
					DoubleChest c = (DoubleChest) e.getClickedBlock().getState();
					inv = c.getInventory();
					Bukkit.getLogger().info("Is double");
				}
				
				ItemStack[] inventoryItems = inv.getContents();
				String invName = inv.getName();
				int invSize = inv.getSize();
				Player p = (Player) e.getPlayer();
				Inventory silentInv = Bukkit.createInventory(null, invSize, invName);
				//silentInv.setItem(0, new ItemStack(Material.DEAD_BUSH));
				silentInv.setContents(inventoryItems);
				p.openInventory(silentInv);
				e.setCancelled(true);
			}
		}
	}
	
	private int isDoubleChest(BlockState s){
		int x = s.getY();
		int z = s.getZ();
		Location sLoc = s.getLocation();
		sLoc.setX(x + 1);
		Location bLoc1 = sLoc;
		sLoc.setX(x - 1);
		Location bLoc2 = sLoc;
		sLoc.setX(x);
		sLoc.setZ(z + 1);
		Location bLoc3 = sLoc;
		sLoc.setZ(z - 1);
		Location bLoc4 = sLoc;
		if (bLoc1.getBlock().getState() instanceof Chest){
			return 1;
		}
		if (bLoc2.getBlock().getState() instanceof Chest){
			return 2;
		}
		if (bLoc3.getBlock().getState() instanceof Chest){
			return 3;
		}
		if (bLoc4.getBlock().getState() instanceof Chest){
			return 4;
		}
		return 0;
	}
	
	//END OF LISTENERS

}
