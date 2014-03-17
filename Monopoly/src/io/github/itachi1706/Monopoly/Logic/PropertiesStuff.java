package io.github.itachi1706.Monopoly.Logic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import io.github.itachi1706.Monopoly.Monopoly;
import io.github.itachi1706.Monopoly.Objects.GameProperties;
import io.github.itachi1706.Monopoly.util.Book;

public class PropertiesStuff {
	
	private static ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
	private static Scoreboard board = manager.getMainScoreboard();
	
	public static boolean checkPropertyPurchased(GameProperties gp){
		String name = gp.getName();
		if (Monopoly.playerProperties.getString(name + ".Owned").contains("Nobody")){
			return false;		//Not Purchased
		}
		return true;	//Purchased
	}
	
	public static boolean buyProperty(Player p){
		Objective o = board.getObjective("game_location");
		Score sc = o.getScore(p);
		GameProperties gp = MainGameLogic.propertyList.get(sc.getScore());
		if (MoneyHandling.checkIfMoney(gp.getCost(), p) == true){
			Bukkit.getServer().broadcastMessage(ChatColor.AQUA + p.getDisplayName() + ChatColor.BLUE + " purchased " + ChatColor.GOLD + gp.getName() + "!");
			MoneyHandling.deductsMoney(gp.getCost(), p);
			Objective o2 = board.getObjective("game_Properties");
			Score sc2 = o2.getScore(p);
			sc2.setScore(sc2.getScore() + 1);
			Monopoly.playerProperties.set(gp.getName() + ".Owned", p.getName());
			MainGameLogic.owned.set(gp.getId(), p.getName());
			Book.getProperty(p, gp);
			Monopoly.saveYamls();
			return true;
		} else {
			p.sendMessage(ChatColor.RED + "Insufficient money, please mortgage some properties off before trying to buy or do " + ChatColor.WHITE + "dont buy");
			return false;
		}
	}
	
	public static boolean checkMortgaged(GameProperties gp){
		if (Monopoly.playerProperties.getBoolean(gp.getName() + ".Mortgaged") == true){
			return true;
		} else {
			return false;
		}
	}
	
	public static void payPropertyRent(Player p, GameProperties gp){
		String tmp = Monopoly.playerProperties.getString(gp.getName() + ".Owned");
		Player target = Bukkit.getServer().getPlayer(tmp);
		if (target == null){
			Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "An error occured as " + ChatColor.GOLD + tmp + ChatColor.DARK_RED + " left the game. Restart the game to fix this");
		} else {
			if (checkMortgaged(gp) == false){
				int house = Monopoly.playerProperties.getInt(gp.getName() + ".House") + Monopoly.playerProperties.getInt(gp.getName() + ".Hotel");
				switch (house){
					case 0:
						if (MoneyHandling.checkIfMoney(gp.getInitRent(), p)){
							MoneyHandling.deductsMoney(gp.getInitRent(), p);
							MoneyHandling.addMoney(gp.getInitRent(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getInitRent() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getInitRent() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(gp.getInitRent(), p);
							MoneyHandling.addMoney(gp.getInitRent(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getInitRent() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getInitRent() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 1:
						if (MoneyHandling.checkIfMoney(gp.getOneHou(), p)){
							MoneyHandling.deductsMoney(gp.getOneHou(), p);
							MoneyHandling.addMoney(gp.getOneHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getOneHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getOneHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(gp.getOneHou(), p);
							MoneyHandling.addMoney(gp.getOneHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getOneHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getOneHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 2:
						if (MoneyHandling.checkIfMoney(gp.getTwoHou(), p)){
							MoneyHandling.deductsMoney(gp.getTwoHou(), p);
							MoneyHandling.addMoney(gp.getTwoHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getTwoHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getTwoHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(gp.getTwoHou(), p);
							MoneyHandling.addMoney(gp.getTwoHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getTwoHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getTwoHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 3:
						if (MoneyHandling.checkIfMoney(gp.getThreeHou(), p)){
							MoneyHandling.deductsMoney(gp.getThreeHou(), p);
							MoneyHandling.addMoney(gp.getThreeHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getThreeHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getThreeHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(gp.getThreeHou(), p);
							MoneyHandling.addMoney(gp.getThreeHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getThreeHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getThreeHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 4:
						if (MoneyHandling.checkIfMoney(gp.getFourHou(), p)){
							MoneyHandling.deductsMoney(gp.getFourHou(), p);
							MoneyHandling.addMoney(gp.getFourHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getFourHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getFourHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(gp.getFourHou(), p);
							MoneyHandling.addMoney(gp.getFourHou(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getFourHou() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getFourHou() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 5:
						if (MoneyHandling.checkIfMoney(gp.getHotel(), p)){
							MoneyHandling.deductsMoney(gp.getHotel(), p);
							MoneyHandling.addMoney(gp.getHotel(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getHotel() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getHotel() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(gp.getHotel(), p);
							MoneyHandling.addMoney(gp.getHotel(), target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + gp.getHotel() + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + gp.getHotel() + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
				}
				} else {
					p.sendMessage(ChatColor.BLUE + "Property was mortgaged, no rent is paid.");
				}
		}
	}
	
	public static void payTrainRent(Player p, GameProperties gp){
		String tmp = Monopoly.playerProperties.getString(gp.getName() + ".Owned");
		Player target = Bukkit.getServer().getPlayer(tmp);
		if (target == null){
			Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "An error occured as " + ChatColor.GOLD + tmp + ChatColor.DARK_RED + " left the game. Restart the game to fix this");
		} else {
			if (checkMortgaged(gp) == false){
				int set = checkFullSet(gp, target);
				switch (set){
					case 1:
						int diceOne = 1 + (int) (Math.random() * ((12-1)+1));
						int rent = diceOne * 4;
						if (MoneyHandling.checkIfMoney(rent, p)){
							MoneyHandling.deductsMoney(rent, p);
							MoneyHandling.addMoney(rent, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(rent, p);
							MoneyHandling.addMoney(rent, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 2:
						int diceTwo = 1 + (int) (Math.random() * ((12-1)+1));
						int rent2 = diceTwo * 4;
						if (MoneyHandling.checkIfMoney(rent2, p)){
							MoneyHandling.deductsMoney(rent2, p);
							MoneyHandling.addMoney(rent2, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent2 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent2 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(rent2, p);
							MoneyHandling.addMoney(rent2, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent2 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent2 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 3:
						int diceThree = 1 + (int) (Math.random() * ((12-1)+1));
						int rent3 = diceThree * 4;
						if (MoneyHandling.checkIfMoney(rent3, p)){
							MoneyHandling.deductsMoney(rent3, p);
							MoneyHandling.addMoney(rent3, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent3 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent3 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(rent3, p);
							MoneyHandling.addMoney(rent3, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent3 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent3 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 4:
						int diceFour = 1 + (int) (Math.random() * ((12-1)+1));
						int rent4 = diceFour * 4;
						if (MoneyHandling.checkIfMoney(rent4, p)){
							MoneyHandling.deductsMoney(rent4, p);
							MoneyHandling.addMoney(rent4, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent4 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent4 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(rent4, p);
							MoneyHandling.addMoney(rent4, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent4 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent4 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 0:
						p.sendMessage(ChatColor.RED + "An error occured. Please tell the admin about it.");
						break;
				}
				} else {
					p.sendMessage(ChatColor.BLUE + "Property was mortgaged, no rent is paid.");
				}
		}
	}
	
	public static void payUtilityRent(Player p, GameProperties gp){
		String tmp = Monopoly.playerProperties.getString(gp.getName() + ".Owned");
		Player target = Bukkit.getServer().getPlayer(tmp);
		if (target == null){
			Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "An error occured as " + ChatColor.GOLD + tmp + ChatColor.DARK_RED + " left the game. Restart the game to fix this");
		} else {
			if (checkMortgaged(gp) == false){
				int set = checkFullSet(gp, target);
				switch (set){
					case 1:
						int diceOne = 1 + (int) (Math.random() * ((12-1)+1));
						int rent = diceOne * 4;
						if (MoneyHandling.checkIfMoney(rent, p)){
							MoneyHandling.deductsMoney(rent, p);
							MoneyHandling.addMoney(rent, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(rent, p);
							MoneyHandling.addMoney(rent, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 2:
						int diceTwo = 1 + (int) (Math.random() * ((12-1)+1));
						int rent2 = diceTwo * 10;
						if (MoneyHandling.checkIfMoney(rent2, p)){
							MoneyHandling.deductsMoney(rent2, p);
							MoneyHandling.addMoney(rent2, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent2 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent2 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
						} else {
							MoneyHandling.deductsMoney(rent2, p);
							MoneyHandling.addMoney(rent2, target);
							p.sendMessage(ChatColor.BLUE + "You paid " + ChatColor.AQUA + target.getDisplayName() + ChatColor.GOLD + " " + rent2 + ChatColor.BLUE + " in rent");
							target.sendMessage(ChatColor.BLUE + "You recieved " + ChatColor.GOLD + "" + rent2 + ChatColor.BLUE + " rent from " + ChatColor.AQUA + target.getDisplayName());
							if(MoneyHandling.checkProperties(p) == true){
								p.sendMessage(ChatColor.BLUE + "You have insufficient gold. Please mortgage off your properties etc to get back to positive value.");
							} else {
								MainGameLogic.surrender(p);
							}
						}
						break;
					case 0:
						p.sendMessage(ChatColor.RED + "An error occured. Please tell the admin about it.");
						break;
				}
				} else {
					p.sendMessage(ChatColor.BLUE + "Property was mortgaged, no rent is paid.");
				}
		}
	}
	
	public static int checkFullSet(GameProperties gp, Player p){
		String name = p.getName();
		if (gp.getType() == "Utility"){
			//Does utility check
			int end = 0;
			GameProperties util1 = MainGameLogic.propertyList.get(12);
			GameProperties util2 = MainGameLogic.propertyList.get(28);
			if (Monopoly.playerProperties.getString(util1.getName() + ".Owned").equalsIgnoreCase(name)){
				end = end + 1;
			}
			if (Monopoly.playerProperties.getString(util2.getName() + ".Owned").equalsIgnoreCase(name)){
				end = end + 1;
			}
			return end;
		} else if (gp.getType() == "Train"){
			//Does Train check
			int end = 0;
			GameProperties util1 = MainGameLogic.propertyList.get(5);
			GameProperties util2 = MainGameLogic.propertyList.get(15);
			GameProperties util3 = MainGameLogic.propertyList.get(25);
			GameProperties util4 = MainGameLogic.propertyList.get(35);
			if (Monopoly.playerProperties.getString(util1.getName() + ".Owned").equalsIgnoreCase(name)){
				end = end + 1;
			}
			if (Monopoly.playerProperties.getString(util2.getName() + ".Owned").equalsIgnoreCase(name)){
				end = end + 1;
			}
			if (Monopoly.playerProperties.getString(util3.getName() + ".Owned").equalsIgnoreCase(name)){
				end = end + 1;
			}
			if (Monopoly.playerProperties.getString(util4.getName() + ".Owned").equalsIgnoreCase(name)){
				end = end + 1;
			}
			return end;
		}
		return 1;
	}
	
	public static void listOwnProperty(Player p){
		int d = 0;
		p.sendMessage(ChatColor.GOLD + "-----Property Owned-----");
		for (int i = 0; i < MainGameLogic.owned.size(); i++){
			String own = MainGameLogic.owned.get(i);
			GameProperties gp = MainGameLogic.propertyList.get(i);
			if (own.equals(p.getName())){
				p.sendMessage(ChatColor.AQUA + gp.getName());
				d++;
			}
			
		}
		if (d == 0){
			p.sendMessage(ChatColor.RED + "You do not own any property.");
		}
	}
	
	public static void listProperty(Player p, String[] args){
		int d = 0;
		p.sendMessage(ChatColor.GOLD + "-----Property Owned-----");
		for (int i = 0; i < MainGameLogic.owned.size(); i++){
			String own = MainGameLogic.owned.get(i);
			GameProperties gp = MainGameLogic.propertyList.get(i);
			if (own.equals(args[2])){
				p.sendMessage(ChatColor.AQUA + gp.getName());
				d++;
			}
			
		}
		if (d == 0){
			p.sendMessage(ChatColor.RED + args[2] + " do not own any property.");
		}
	}
	
	public static void listRent(Player p, String[] args){
		int d = 0;
		p.sendMessage(ChatColor.GOLD + "-----Property Info-----");
		String building = "";
		int lol = args.length-1;
		for (int i = 2; i < args.length - 1; i++){
			building = building + args[i] + " ";			
		}
		building = building + args[lol];
		System.out.println(building);
		building = building.toLowerCase();
		for (int i = 0; i < MainGameLogic.propertyList.size(); i++){
			GameProperties gp = MainGameLogic.propertyList.get(i);
			String name = gp.getName().toLowerCase();
			if (name.contains(building)){
				p.sendMessage(ChatColor.RED + gp.toString());
				d++;
				break;
			}
		}
		if (d == 0){
			p.sendMessage(ChatColor.RED + "No properties found");
		}
	}

}
