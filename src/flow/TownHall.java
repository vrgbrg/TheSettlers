package flow;

public class TownHall extends Building {
    private int capacity = 2000;

    public TownHall(String value, String name, int[][] shape, int price, int capacity) {
        super(value, "Városháza", shape, price);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

