package com.itachi1706.Bukkit.Monopoly.util;

import com.itachi1706.Bukkit.Monopoly.Objects.GameProperties;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.util
 */
public class Book implements Listener {

    @EventHandler
    public void checkForBook(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (p.getInventory().contains(Material.WRITTEN_BOOK)){
            p.sendMessage(ChatColor.RED + "If you do not have 2 books \"Rules of Monopoly\" and \"Instructions\", clear your inventory of written books and relog");
            p.sendMessage(ChatColor.RED + "Spawn a chest to place your properties in if you need to do so.");
        } else {
            giveRules(p);
            giveInstructions(p);
        }
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        try{
            Objective o = board.getObjective("game_isPlaying");
            Score sc = o.getScore(p);
            if (sc.getScore() == 0){
                p.setGameMode(GameMode.ADVENTURE);
            }
        } catch (NullPointerException ex){
            Bukkit.getLogger().info("Invalid scoreboard. Please reinitialize scoreboard");
        }

    }

    public void giveRules(Player p){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        String page1 = ChatColor.BOLD + "RULES OF MONOPOLY\n" + ChatColor.ITALIC + "Table Of Contents\n" + ChatColor.RESET + "2. Roll Dice\n5. Pass GO\n6. Buying Property\n9. Paying Rent\n13. Jail";
        String page2 = ChatColor.BOLD + "Roll Dice\n" + ChatColor.RESET + "Place your token on the corner marked \"GO\", then throw the dices and move your token (in the direction of the arrow) the number of spaces indicated by the dice. Depending on the space your token reaches,";
        String page3 = "you may be entitled to buy real estate or other properties, or be obliged to pay rent, pay taxes, draw a Chance or Community Chest card, Go To Jail, or etc... If you throw doubles, you move your token as usual, and are subject to any privileges";
        String page4 = "or penalties pertaining to the space on which you land. Retaining the dice, throw again and move your token as before. If you throw doubles three times in succession, move your token immediately to the space marked \"In Jail\".";
        String page5 = ChatColor.BOLD + "Pass Go\n" + ChatColor.RESET + "Each time a player's token lands on or passes over GO, whether by throwing the dice or drawing a card, that player gets a $200 salary.";
        String page6 = ChatColor.BOLD + "Buying Property\n" + ChatColor.RESET + "Whenever you land on an unowned property you may buy that property from the Bank at its printed price. You receive the Title Deed card showing ownership. Place the title deed card face up in front of you.";
        String page7 = "If you do not wish to buy the property, the Bank sells it at through an auction to the highest bidder. The high bidder pays the Bank the amount of the bid in cash and receives the Title Deed card for that property. Any player,";
        String page8 = " including the one who declined the option to buy it at the printed price, may bid. Bidding may start at any price.";
        String page9 = ChatColor.BOLD + "Paying Rent\n" + ChatColor.RESET + "When you land on a property that is owned by another player, the owner collects rent from you in accordance with the list printed on its Title Deed card. If the property is mortgaged, no rent can be collected. ";
        String page10 = "It is an advantage to hold all the Title Deed cards in a color-group (i.e., Boardwalk and Park Place, or Connecticut, Vermont and Oriental Avenues) because the owner may then charge double rent for unimproved properties in that colour-group.";
        String page11 = "This rule applies to unmortgaged properties even if another property in that colour-group is mortgaged. It is even more advantageous to have houses or hotels on properties because rents are much higher than for unimproved properties.";
        String page12 = "The owner may not collect the rent if they fail to ask for it before the second player following throws the dice.";
        String page13 = ChatColor.BOLD + "Jail\n" + ChatColor.RESET + "You land in Jail when...\n(1) Your token lands on the space marked \"Go to Jail\",\n(2) You draw a card marked \"Go to Jail\" or\n(3) You throw doubles three times in succession.";
        String page14 = "When you are sent to Jail you cannot collect your $200 salary in that move since, regardless of where your token is on the board, you must move directly into Jail. Your turn ends when you are sent to Jail.";
        String page15 = "If you are not \"sent to jail\" but in the ordinary course of play lands on that space, you are \"Just Visiting\", you incur no penalty, and you move ahead in the usual manner on your next turn.";
        String page16 = "You still are able to collect rent on your properties because you are \"Just Visiting\".";
        String page17 = "A player gets out of Jail by...\n (1) Throwing doubles on any of your next three turns, if you succeed in doing this you immediately move forward the number of spaces shown by your doubles throw.";
        String page18 = "Even though you had thrown doubles, you do not take another turn.\n(2) Using the \"Get Out of Jail Free Card\"\n(3) Purchasing the \"Get Out of Jail Free Card\" from another player and playing it.";
        String page19 = "(4) Paying a fine of $50 before you roll the dice on either of your next two turns. If you do not throw doubles by your third turn, you must pay the $50 fine. You then get out of Jail and immediately move forward the number of spaces shown by your throw.";
        String page20 = "Even though you are in Jail, you may buy and sell property, buy and sell houses and hotels and collect rents.";
        String page21 = "For more information, go to http://richard_wilding.tripod.com/monorules.htm";
        bm.addPage(page1,page2,page3,page4,page5,page6,page7,page8,page9,page10,page11,page12,page13,page14,page15,page16,page17,page18,page19,page20,page21);
        bm.setAuthor(ChatColor.GOLD + "Monopoly");
        bm.setDisplayName("Rules of Monopoly");
        bm.setTitle("Rules of Monopoly");
        book.setItemMeta(bm);
        p.getInventory().setItem(7, book);
        p.sendMessage(ChatColor.GOLD + "You have been given a book on the rules of Monopoly!");
    }

    public void giveInstructions(Player p){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bm = (BookMeta) book.getItemMeta();
        String page1 = "1. To join the game, do /monopoly join\n\n2. To leave the game, do /monopoly leave\n\nTo start the game, host will do /monopoly start\n\nUse /monopoly dice to see what is the current dice row";
        String page2 = "To define regions, use /monopoly region <id>. where id = 0-39, with 0 being Go and 39 being final dark blue\n\nuse '/monopoly chatcmd' to see what commands there are to play the game";
        String page3 = "Figure out where you are supposed to be with /monopoly where\n If theres any error, inform the admin of the game";
        String page4 = "To buy houses/hotels, inform the admin who will do it for you.";
        String page5 = ChatColor.DARK_RED + "NOTE TO ADMINS:\n" + ChatColor.RED + "Full set is not implemented, if rent involves full set, manually edit scoreboard to do rent";
        String page6 = ChatColor.RED + "If theres an error in property awarding etc, inform the plugin dev. If it involves money, override with scoreboard and the objective 'game_money'";
        String page7 = ChatColor.RED + "How to buy house/hotel: Do the '/monopoly update' command Please update the" + ChatColor.GREEN + " playerProperties.yml " + ChatColor.RED + "file with it. Based on which property is being purchased, edit the 'Houses' and 'Hotel' config accordingly. (cont.)";
        String page8 = ChatColor.RED + "Then do a scoreboard edit of the respective house/hotel score to reflect the total house/hotel of the player. (if hotel, remove the houses from the playerProperties.yml file and from the total count in the scoreboard accordingly). (cont.)";
        String page9 = ChatColor.RED + "When you are done, do the '/monopoly complete' command to update the yml file into the game. File can be found in the Monopoly plugin folder.";
        bm.addPage(page1,page2,page3,page4,page5,page6,page7,page8,page9);
        bm.setAuthor(ChatColor.DARK_GREEN + "Ze. Developer!");
        bm.setDisplayName("Instructions");
        bm.setTitle("Instructions");
        book.setItemMeta(bm);
        p.getInventory().setItem(8, book);
        p.sendMessage(ChatColor.GOLD + "You have been given an instructions book for the game!");
    }

    public static void getProperty(Player p, GameProperties gp){
        if (gp.getType().contains("Property")){
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bm = (BookMeta) book.getItemMeta();
            String page1 = ChatColor.BOLD + gp.getName() + "\n" + ChatColor.ITALIC + "RENT\n" + ChatColor.RESET + "Empty:           " + gp.getInitRent() + "\nFull Set:         " + (gp.getInitRent() * 2) + "\n1 House:         " + gp.getOneHou() + "\n2 Houses:      " + gp.getTwoHou() + "\n3 Houses:      " + gp.getThreeHou() + "\n4 Houses:      " + gp.getFourHou() + "\nHotel:           " + gp.getHotel() +
                    "\n\nUpgrade Cost: " + gp.getHouseCost() + "\n\nMortgage Cost: " + gp.getMortgage();
            bm.addPage(page1);
            bm.setAuthor("Monopoly");
            bm.setDisplayName(gp.getName());
            bm.setTitle(gp.getName());
            book.setItemMeta(bm);
            p.getInventory().addItem(book);
        } else if (gp.getType().contains("Train")){
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bm = (BookMeta) book.getItemMeta();
            String page1 = ChatColor.BOLD + gp.getName() + "\n" + ChatColor.ITALIC + "RENT\n" + ChatColor.RESET + "1 Train:         " + gp.getInitRent() + "\n\n\n2 Trains:        " + gp.getOneHou() + "\n3 Trains:      " + gp.getTwoHou() + "\n4 Trains:      " + gp.getThreeHou() + "\n\n\n\n\n\n" +
                    "\n\nMortgage Cost: " + gp.getMortgage();
            bm.addPage(page1);
            bm.setAuthor("Monopoly");
            bm.setDisplayName(gp.getName());
            bm.setTitle(gp.getName());
            book.setItemMeta(bm);
            p.getInventory().addItem(book);
        } else {
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bm = (BookMeta) book.getItemMeta();
            String page1 = ChatColor.BOLD + gp.getName() + "\n" + ChatColor.ITALIC + "RENT\n" + ChatColor.RESET + "1 Utility:  4xDice\n2 Utility:  10xDice\n\n\n\n\n\n\n" +
                    "\n\nMortgage Cost: " + gp.getMortgage();
            bm.addPage(page1);
            bm.setAuthor("Monopoly");
            bm.setDisplayName(gp.getName());
            bm.setTitle(gp.getName());
            book.setItemMeta(bm);
            p.getInventory().addItem(book);
        }

    }

}
