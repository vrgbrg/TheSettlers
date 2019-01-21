package flow;

public class Player {

    private final String name;
    private int roundPoint;
    private int gold;
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

    public int changeRoundPoint(int point) {
        return roundPoint -= point;
    }

    public double changeGold(double minus) {
        return gold -= minus;
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
