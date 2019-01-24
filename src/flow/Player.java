package flow;

import flow.cells.*;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private int roundPoint;
    private int gold;
    private int actualPopulation;
    private CellItem[][] townTable;

    public Player(String name) {

        this.name = name;
        this.gold = 1000;
        this.roundPoint = 3;
        this.actualPopulation = random(500, 1000);
        this.townTable = new CellItem[11][11];

    }

    public String getName() {
        return name;
    }

    public int getRoundPoint() {
        return roundPoint;
    }

    public void setRoundPoint(int roundPoint) {
        this.roundPoint = roundPoint;
    }

    public int changeRoundPoint(int point) {
        return roundPoint -= point;
    }

    public int changeGold(int plus) {
        return gold += plus;
    }

    public int getGold() {
        return gold;
    }

    public int getActualPopulation() {
        return actualPopulation;
    }

    public int populationDecrease(int minus) {
        return actualPopulation -= minus;
    }

    public int populationIncrease(int plus) {
        return actualPopulation += plus;
    }

    private int random(int min, int max) {
        int random = (int)(Math.random() * (max-min)+1 + min);
        return random;
    }

    public CellItem[][] getTownTable() {
        return townTable;
    }

    public void addTownItem(Position position, CellItem cells) {
        townTable[position.x][position.y] = cells;
    }

    public void drawTownTable() {
        for(int i=0;i<townTable.length; i++) {
            for(int j=0;j<townTable[i].length;j++){
                if(townTable[i][j] == null) {
                    System.out.print("0");
                } else {
                    System.out.print(townTable[i][j].toString());
                }

            }
            System.out.println();
        }
        System.out.println("-------");
    }

    public CellItem getTownHallCellItem(Position position) {
        return townTable[position.x][position.y];
    }


    @Override
    public String toString() {
        return name;
    }
}
