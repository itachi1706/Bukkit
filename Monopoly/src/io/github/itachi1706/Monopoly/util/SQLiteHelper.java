package io.github.itachi1706.Monopoly.util;

import java.sql.SQLException;

import io.github.itachi1706.Monopoly.Monopoly;
import lib.PatPeter.SQLibrary.Database;

public class SQLiteHelper {
	
	@SuppressWarnings("deprecation")
	public static void checkTableExist() {
		Database sqlite = Monopoly.sql;
		if (sqlite.checkTable("Properties")){
			
		}else{
			try {
				sqlite.query("CREATE TABLE Properties (id INT PRIMARY KEY, name VARCHAR(50), type VARCHAR(15), cost INT, initRent INT, oneHou INT, twoHou INT, threeHou INT, fourHou INT, hotel INT, houseCost INT, mortgage INT, buyback INT);");
				initDefaultProperties(sqlite);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (sqlite.checkTable("Chance")){
		} else {
			try {
				sqlite.query("CREATE TABLE Chance (id INT PRIMARY KEY, name VARCHAR(200));");
				initChance(sqlite);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (sqlite.checkTable("CommunityChest")){
		} else {
			try {
				sqlite.query("CREATE TABLE CommunityChest (id INT PRIMARY KEY, name VARCHAR(200));");
				initCommunityChest(sqlite);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public static void initDefaultProperties(Database sqlite){
		try {
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('0', 'Pass Go', 'Null', '200', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Pass Go (Slot 0)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('1', 'Mediterranean Avenue', 'Property', '60', '2', '10', '30', '90', '160', '250', '50', '30', '33');");	//Brown 1 (Slot 1)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('2', 'Community Chest', 'CC', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//CC (Slot 2)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('3', 'Baltic Avenue', 'Property', '60', '4', '20', '60', '180', '320', '450', '50', '30', '33');");	//Brown 2 (Slot 3)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('4', 'Income Tax', 'Null', '100', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Income Tax (Slot 4)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('5', 'Reading Railroad', 'Train', '200', '25', '50', '100', '200', '0', '0', '0', '100', '110');");	//Train 1 (Slot 5)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('6', 'Oriental Avenue', 'Property', '100', '6', '30', '90', '270', '400', '550', '50', '50', '55');");	//Aqua 1 (Slot 6)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('7', 'Chance', 'Chance', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//CHANCE (Slot 7)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('8', 'Vermont Avenue', 'Property', '100', '6', '30', '90', '270', '400', '550', '50', '50', '55');");	//Aqua 2 (Slot 8)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('9', 'Connecticut Avenue', 'Property', '120', '8', '40', '100', '300', '450', '600', '50', '60', '66');");	//Aqua 3 (Slot 9)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('10', 'Just Visiting/Jail', 'Null', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Jail (Slot 10)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('11', 'St Charles Place', 'Property', '140', '10', '50', '150', '450', '625', '750', '100', '70', '77');");	//Pink 1 (Slot 11)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('12', 'Electric Company', 'Utility', '150', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Utility 1 (Slot 12)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('13', 'States Avenue', 'Property', '140', '10', '50', '150', '450', '625', '750', '100', '70', '77');");	//Pink 2 (Slot 13)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('14', 'Virginia Avenue', 'Property', '160', '12', '60', '180', '500', '700', '900', '100', '80', '88');");	//Pink 3 (Slot 14)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('15', 'Pennsylvania Railroad', 'Train', '200', '25', '50', '100', '200', '0', '0', '0', '100', '110');");	//Train 2 (Slot 15)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('16', 'St James Place', 'Property', '180', '14', '70', '200', '550', '750', '950', '100', '90', '99');");	//Orange 1 (Slot 16)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('17', 'Community Chest', 'CC', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//CC (Slot 17)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('18', 'Tennessee Avenue', 'Property', '180', '14', '70', '200', '550', '750', '950', '100', '90', '99');");	//Orange 2 (Slot 18)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('19', 'New York Avenue', 'Property', '200', '16', '80', '220', '600', '800', '1000', '100', '100', '110');");	//Orange 3 (Slot 19)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('20', 'Free Parking', 'Null', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Free Parking (Slot 20)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('21', 'Kentucky Avenue', 'Property', '220', '18', '90', '250', '700', '875', '1050', '150', '110', '111');");	//Red 1 (Slot 21)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('22', 'Chance', 'Chance', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//CHANCE (Slot 22)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('23', 'Indiana Avenue ', 'Property', '220', '18', '90', '250', '700', '875', '1050', '150', '110', '111');");	//Red 2 (Slot 23)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('24', 'Illinois Avenue', 'Property', '240', '20', '100', '300', '750', '925', '1100', '150', '120', '112');");	//Red 3 (Slot 24)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('25', 'B & O Railroad', 'Train', '200', '25', '50', '100', '200', '0', '0', '0', '100', '110');");	//Train 3 (Slot 25)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('26', 'Atlantic Avenue', 'Property', '260', '22', '110', '330', '800', '975', '1150', '150', '130', '113');");	//Yellow 1 (Slot 26)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('27', 'Ventnor Avenue', 'Property', '260', '22', '110', '330', '800', '975', '1150', '150', '130', '113');");	//Yellow 2 (Slot 27)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('28', 'Water Works', 'Utility', '150', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Utility 2 (Slot 28)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('29', 'Marvin Gardens', 'Property', '280', '24', '120', '360', '850', '1025', '1200', '150', '140', '114');");	//Yellow 3 (Slot 29)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('30', 'Go To Jail', 'Null', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Go To Jail (Slot 30)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('31', 'Pacific Avenue', 'Property', '300', '26', '130', '390', '900', '1100', '1275', '200', '150', '115');");	//Green 1 (Slot 31)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('32', 'North Carolina', 'Property', '300', '26', '130', '390', '900', '1100', '1275', '200', '150', '115');");	//Green 2 (Slot 32)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('33', 'Community Chest', 'CC', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//CC (Slot 33)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('34', 'Pennsylvania Avenue', 'Property', '320', '28', '150', '450', '1000', '1200', '1400', '200', '160', '116');");	//Green 3 (Slot 34)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('35', 'Short Line', 'Train', '200', '25', '50', '100', '200', '0', '0', '0', '100', '110');");	//Train 4 (Slot 35)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('36', 'Chance', 'Chance', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//CHANCE (Slot 36)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('37', 'Park Place', 'Property', '350', '35', '175', '500', '1100', '1300', '1500', '200', '175', '192');");	//Dark Blue 1 (Slot 37)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('38', 'Super Tax', 'Null', '75', '0', '0', '0', '0', '0', '0', '0', '0', '0');");	//Super Tax (Slot 38)
			sqlite.query("INSERT INTO Properties(id, name, type, cost, initRent, oneHou, twoHou, threeHou, fourHou, hotel, houseCost, mortgage, buyback) VALUES('39', 'Boardwalk', 'Property', '400', '50', '200', '600', '1400', '1700', '2000', '200', '200', '220');");	//Dark Blue 2 (Slot 39)
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void initChance(Database sqlite){
		try {
			sqlite.query("INSERT INTO Chance(id, name) VALUES('0','Advance to Go (Collect $200)');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('1','Advance to Illinois Ave. - If you pass Go, collect $200');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('2','Advance to St. Charles Place – If you pass Go, collect $200');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('3','Bank pays you dividend of $50');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('4','Get out of Jail Free – This card may be kept until needed, or traded/sold');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('5','Go Back 3 Spaces');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('6','Go to Jail – Go directly to Jail – Do not pass Go, do not collect $200');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('7','Make general repairs on all your property – For each house pay $25 – For each hotel $100');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('8','Pay poor tax of $15');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('9','Take a trip to Reading Railroad – If you pass Go, collect $200');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('10','Take a walk on the Boardwalk – Advance token to Boardwalk');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('11','Your building loan matures – Collect $150');");
			sqlite.query("INSERT INTO Chance(id, name) VALUES('12','You have won a crossword competition - Collect $100');");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void initCommunityChest(Database sqlite){
		try{
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('0','Advance to Go (Collect $200)');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('1','Bank error in your favor – Collect $200');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('2','Doctor fees – Pay $50');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('3','From sale of stock you get $50');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('4','Get out of Jail Free – This card may be kept until needed, or traded/sold');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('5','Go to Jail – Go directly to jail – Do not pass Go – Do not collect $200');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('6','Holiday Fund matures - Receive $100');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('7','Income tax refund – Collect $20');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('8','Life insurance matures – Collect $100');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('9','Pay hospital fees of $100');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('10','Pay school fees of $150');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('11','Receive $25 consultancy fee');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('12','You are assessed for street repairs – $40 per house – $115 per hotel');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('13','You have won second prize in a beauty contest – Collect $10');");
			sqlite.query("INSERT INTO CommunityChest(id, name) VALUES('14','You inherit $100');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
