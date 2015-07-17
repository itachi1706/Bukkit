package com.itachi1706.Bukkit.Monopoly.Objects;

/**
 * Created by Kenneth on 17/7/2015.
 * for Monopoly in com.itachi1706.Bukkit.Monopoly.Objects
 */
public class GameProperties {

    private int id;
    private String name;
    private String type;
    private int cost;
    private int initRent;	//FullSet is just double of rent
    private int oneHou;
    private int twoHou;
    private int threeHou;
    private int fourHou;
    private int hotel;
    private int houseCost;
    private int mortgage;
    private int buyback;

    public GameProperties(){}
    public GameProperties(int i, String n, String t, int c, int ir, int oh, int th, int thh, int fh, int hot, int houc, int m, int bb){
        id = i;
        name = n;
        type = t;
        cost = c;
        initRent = ir;
        oneHou = oh;
        twoHou = th;
        threeHou = thh;
        fourHou = fh;
        hotel = hot;
        houseCost = houc;
        mortgage = m;
        buyback = bb;
    }
    public int getId(){return id;}
    public void setId(int i){id = i;}
    public String getName(){return name;}
    public void setName(String n){name = n;}
    public String getType(){return type;}
    public void setType(String t){type = t;}
    public int getCost(){return cost;}
    public void setCost(int c){cost = c;}
    public int getInitRent(){return initRent;}
    public void setInitRent(int ir){initRent = ir;}
    public int getOneHou(){return oneHou;}
    public void setOneHou(int oh){oneHou = oh;}
    public int getTwoHou(){return twoHou;}
    public void setTwoHou(int i){twoHou = i;}
    public int getThreeHou(){return threeHou;}
    public void setThreeHou(int i){threeHou = i;}
    public int getFourHou(){return fourHou;}
    public void setFourHou(int i){fourHou = i;}
    public int getHotel(){return hotel;}
    public void setHotel(int i){hotel = i;}
    public int getHouseCost(){return houseCost;}
    public void setHouseCost(int i){houseCost = i;}
    public int getMortgage(){return mortgage;}
    public void setMortgage(int i){mortgage = i;}
    public int getBuyback(){return buyback;}
    public void setBuyback(int i){buyback = i;}
    public int calculateRent(int house, int hotels){
        switch (house)
        {
            case 1: return oneHou;
            case 2: return twoHou;
            case 3: return threeHou;
            case 4: return fourHou;
            case 0: if (hotels == 1){
                return hotel;
            } else {
                return initRent;
            }
        }
        return -1;
    }
    public String toString()
    {
        return "Id: " + id + "\tName: " + name + "\tCost: " + cost + "\tInitial Rent: " + initRent + "\t1 House: " + oneHou + "\t2 Houses: " +
                twoHou + "\t3 Houses: " + threeHou + "\t4 Houses: " + fourHou + "\tHotel: " + hotel + "\tHouse Cost: " + houseCost + "\tMortgage: " + mortgage + "\tBuyback: " + buyback;
    }

}
