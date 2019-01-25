package flow.cells;

import flow.Player;

import javax.swing.*;

public class LivingQuarters extends CellItem {
    private int price;
    public final int storageLivingQuarters;
    public LivingQuarters(Player owner) {
        super(owner, "Living Quarters", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/livingq.png"));
        this.price = 100;
        this.storageLivingQuarters = 2000;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean canMoveDiagonally() {
        return false;
    }

    @Override
    public int maxStep() {
        return 0;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int attackRange() {
        return 0;
    }

    @Override
    public String toString() {
        return "LivingQuarters{" +
                "price=" + price +
                '}';
    }
}
