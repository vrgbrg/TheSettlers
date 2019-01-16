package flow;

public class Player {

    private final String name;
    private int roundPoint;
    private double gold;
    private int actualPopulation;
    private int maxPopulation;

    public Player(String name) {

        this.name = name;
        this.gold = 1000;
        this.roundPoint = 3;
        this.actualPopulation = random(500, 1000);

    }

    public String getName() {
        return name;
    }

    public int getRoundPoint() {
        return roundPoint;
    }

    public void changeRoundPoint(int point) {
        roundPoint -= point;
    }

    public void changeGold(double minus) {
        gold -= minus;
    }

    public double getGold() {
        return gold;
    }

    public int getActualPopulation() {
        return actualPopulation;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    private int random(int min, int max) {
        int random = (int)(Math.random() * (max-min)+1 + min);
        return random;
    }

    @Override
    public String toString() {
        return name;
    }
}
