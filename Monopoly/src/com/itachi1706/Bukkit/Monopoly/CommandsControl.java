package com.itachi1706.Bukkit.Monopoly;

import com.itachi1706.Bukkit.Monopoly.Logic.*;
import com.itachi1706.Bukkit.Monopoly.Objects.GameProperties;
import com.itachi1706.Bukkit.Monopoly.util.ScoreboardHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly
 */
public class CommandsControl implements CommandExecutor {

    @SuppressWarnings("unused")
    private Monopoly plugin;

    public CommandsControl(Monopoly plugin){
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("monopoly")){
            if (args.length > 4){
                displayMenu(sender);
                return true;
            }

            if (args.length == 0){
                displayMenu(sender);
                return true;
            }

            if (args.length == 1){
                if (args[0].equalsIgnoreCase("start")){
                    //Do /monopoly start command
                    MainGameLogic.initGameLogic();
                    Monopoly.configGame.set("pause", false);
                    return true;
                } else if (args[0].equalsIgnoreCase("pause")){
                    //Do /monopoly pause command
                    return false;
                } else if (args[0].equalsIgnoreCase("reset")){
                    //Do /monopoly reset command
                    MainGameLogic.endGame();
                    return true;
                } else if (args[0].equalsIgnoreCase("join")){
                    //Do /monopoly join command
                    if (sender instanceof Player){
                        Player p = (Player) sender;
                        PlayerTeams.giveTeamChooseOption(p);
                        return true;
                    } else {
                        sender.sendMessage("You must be a player in-game to use this command");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("leave")){
                    //Do /monopoly leave command
                    if (sender instanceof Player){
                        Player p = (Player) sender;
                        PlayerTeams.leaveGame(p);
                        return true;
                    } else {
                        sender.sendMessage("You must be a player in-game to use this command");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("give")){
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("take")){
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")){
                    //Do /monopoly reload command
                    Monopoly.loadYamls();
                    Monopoly.configGame.set("pause", false);
                    Monopoly.configGame.set("start", false);
                    Monopoly.configGame.set("players", 0);
                    ScoreboardHelper.resetScoreboard();
                    Monopoly.initPropertiesFile();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded. Game has been reset!");
                    return true;
                } else if (args[0].equalsIgnoreCase("dice")){
                    //Do /monopoly dice command
                    sender.sendMessage(ChatColor.GREEN + "Last dice roll value is " + ChatColor.AQUA + Dice.diceRoll());
                    return true;
                } else if (args[0].equalsIgnoreCase("where")){
                    //Do /monopoly where command
                    if (sender instanceof Player){
                        int l = PlayerLocation.getLocation((Player) sender);
                        GameProperties gp = MainGameLogic.propertyList.get(l);
                        sender.sendMessage(ChatColor.BLUE + "Current Location: " + ChatColor.GOLD + gp.getName());
                        return true;
                    } else {
                        sender.sendMessage("You must be a player in-game to use this command");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("chatcmd")){
                    //Do /monopoly chatcmd command
                    displayChatCmd(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("region")){
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("skip")){
                    //Do /monopoly skip command
                    PlayerTurns.forceEndTurn();
                    return true;
                } else if (args[0].equalsIgnoreCase("jail")) {
                    sender.sendMessage(ChatColor.RED + "Please input a player's name");
                    return true;
                } else if (args[0].equalsIgnoreCase("update")){
                    //Do /monopoly update command
                    Monopoly.saveYamls();
                    Bukkit.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "An administrator is currently updating the YAML File. Please pause the game.");
                    return true;
                } else if (args[0].equalsIgnoreCase("complete")){
                    //Do /monopoly complete command
                    Monopoly.loadYamls();
                    Bukkit.getServer().broadcastMessage(ChatColor.DARK_PURPLE + "An administrator is done updating the YAML File. The game can now continue :)");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Usage");
                    displayMenu(sender);
                    return true;
                }
            }

            if (args.length == 2){
                if (args[0].equalsIgnoreCase("join")){
                    //Do /monopoly join <player> command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("leave")){
                    //Do /monopoly leave <player> command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("give")){
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("take")){
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("where")){
                    //Do /monopoly where <player> command
                    Player target = (Bukkit.getServer().getPlayer(args[1]));
                    if (target == null){
                        sender.sendMessage(ChatColor.BLUE + args[1] + " is not online!");
                    } else {
                        int l = PlayerLocation.getLocation(target);
                        if (l == 0){
                            sender.sendMessage(ChatColor.AQUA + target.getDisplayName() + ChatColor.BLUE + "is either at " + ChatColor.GOLD + "Pass Go" + ChatColor.BLUE + " or is not playing.");
                        } else {
                            GameProperties gp = MainGameLogic.propertyList.get(l);
                            sender.sendMessage(ChatColor.BLUE + "Current Location of " + ChatColor.AQUA + target.getDisplayName() +": " + ChatColor.GOLD + gp.getName());
                        }
                    }
                } else if (args[0].equalsIgnoreCase("region")){
                    //Do /monopoly region <id> command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                }  else if (args[0].equalsIgnoreCase("jail")) {
                    //Do /monopoly jail [player] command
                    Player target = (Bukkit.getServer().getPlayer(args[1]));
                    if (target == null){
                        sender.sendMessage(ChatColor.BLUE + args[1] + " is not online!");
                        return true;
                    } else {
                        Jailing.goToJail(target, args[1]);
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Usage");
                    displayMenu(sender);
                    return true;
                }
            }

            if (args.length == 3){
                sender.sendMessage(ChatColor.RED + "INVALID USAGE");
                return true;
            }

            if (args.length == 4){
                if (args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("property")){
                    //Do give property command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("take") && args[1].equalsIgnoreCase("property")){
                    //Do take property command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("take") && args[1].equalsIgnoreCase("gold")){
                    //Do take gold command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else if (args[0].equalsIgnoreCase("give") && args[1].equalsIgnoreCase("gold")){
                    //Do give gold command
                    sender.sendMessage(ChatColor.DARK_RED + "Not planned. Will be implemented in a future update.");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid Usage");
                    displayMenu(sender);
                    return true;
                }
            }
        }
        sender.sendMessage(ChatColor.DARK_RED + "NOT IMPLEMENTED YET");
        return true;
    }

    public void displayMenu(CommandSender sender){
        sender.sendMessage(ChatColor.GOLD + "-----------Monopoly Commands-----------");
        sender.sendMessage(ChatColor.GOLD + "/monopoly: " + ChatColor.WHITE + "Main plugin command");
        sender.sendMessage(ChatColor.GOLD + "/monopoly start: " + ChatColor.WHITE + "Starts (Resume) the game");
        sender.sendMessage(ChatColor.GOLD + "/monopoly pause: " + ChatColor.WHITE + "Pauses the game");
        sender.sendMessage(ChatColor.GOLD + "/monopoly reset: " + ChatColor.WHITE + "Resets the game");
        sender.sendMessage(ChatColor.GOLD + "/monopoly join: " + ChatColor.WHITE + "Joins the game");
        sender.sendMessage(ChatColor.GOLD + "/monopoly leave: " + ChatColor.WHITE + "Leaves the game");
        sender.sendMessage(ChatColor.GOLD + "/monopoly reload: " + ChatColor.WHITE + "Reloads the files (will cause game to reset automatically)");
        sender.sendMessage(ChatColor.GOLD + "/monopoly dice: " + ChatColor.WHITE + "See the current value of the dice (will get and save to config file)");
        sender.sendMessage(ChatColor.GOLD + "/monopoly where [player]: " + ChatColor.WHITE + "Finding out the current location on the gameboard for you/other players");
        sender.sendMessage(ChatColor.GOLD + "/monopoly chatcmd: " + ChatColor.WHITE + "Finds out what chat commands there are");
        sender.sendMessage(ChatColor.GOLD + "/monopoly skip: " + ChatColor.WHITE + "Skips current player turn");
        sender.sendMessage(ChatColor.GOLD + "/monopoly update: " + ChatColor.DARK_RED + "[ADMIN Command]" + ChatColor.WHITE + " Saves and allow admins to edit .yml files.");
        sender.sendMessage(ChatColor.GOLD + "/monopoly complete: " + ChatColor.DARK_RED + "[ADMIN Command]" + ChatColor.WHITE + " Loads admins edited .yml files.");
    }

    public void displayChatCmd(CommandSender sender){
        sender.sendMessage(ChatColor.GOLD + "-----------Chat Commands-----------");
        sender.sendMessage(ChatColor.GOLD + "roll dice: " + ChatColor.WHITE + "Rolls the dice (if player's turn)");
        sender.sendMessage(ChatColor.GOLD + "buy: " + ChatColor.WHITE + "buys property (If possible)");
        sender.sendMessage(ChatColor.GOLD + "dont buy: " + ChatColor.WHITE + "Don't buy property and auction (if enabled in config)");
        sender.sendMessage(ChatColor.GOLD + "list property: " + ChatColor.WHITE + "List properties you currently own");
        sender.sendMessage(ChatColor.GOLD + "list property [player]: " + ChatColor.WHITE + "List properties that other players own");
        sender.sendMessage(ChatColor.GOLD + "list rent <property name>: " + ChatColor.WHITE + "List current rental cost of property");
        sender.sendMessage(ChatColor.GOLD + "list cost <property name>: " + ChatColor.WHITE + "List current cost of property to buy");
        sender.sendMessage(ChatColor.GOLD + "send <player> gold <amount>: " + ChatColor.WHITE + "Sends a player gold");
        sender.sendMessage(ChatColor.GOLD + "send <player> property <property name>: " + ChatColor.WHITE + "Sends a player a property");
        sender.sendMessage(ChatColor.GOLD + "use jail card: " + ChatColor.WHITE + "Uses Get out of jail free card");
        sender.sendMessage(ChatColor.GOLD + "yes: " + ChatColor.WHITE + "Sends rent/Confirms action");
        sender.sendMessage(ChatColor.GOLD + "no: " + ChatColor.WHITE + "Cancels action");
        sender.sendMessage(ChatColor.GOLD + "end turn: " + ChatColor.WHITE + "Ends your turn");
        sender.sendMessage(ChatColor.GOLD + "mortgage <property name>: " + ChatColor.WHITE + "Mortgage a property owned (will have confirm action)");
        sender.sendMessage(ChatColor.GOLD + "buyback <property name>: " + ChatColor.WHITE + "Buyback property owned (will have confirm action)");
    }

    public void deprecratedCommands(CommandSender sender){
        sender.sendMessage(ChatColor.GOLD + "/monopoly region <id>: " + ChatColor.WHITE + "Defines a region (0-39)");
        sender.sendMessage(ChatColor.GOLD + "/monopoly join/leave <player>: " + ChatColor.WHITE + "Adds or Kicks a player from the game");
        sender.sendMessage(ChatColor.GOLD + "/monopoly give <property/gold> <player> <value/amount>: " + ChatColor.WHITE + "Gives a player a property (based on pos number) or gold");
        sender.sendMessage(ChatColor.GOLD + "/monopoly take <property/gold> <player> <value/amount>: " + ChatColor.WHITE + "Takes a property (based on pos number) or gold from player");
    }

}
