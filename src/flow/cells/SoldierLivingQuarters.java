package flow.cells;

import flow.Player;

import javax.swing.*;

public class SoldierLivingQuarters extends CellItem{
    private int price;
    public final int storageSoldier;
    public SoldierLivingQuarters(Player owner) {
        super(owner, "Soldier Living Quarters", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/soldierlivingq.png"));
        this.price = 150;
        this.storageSoldier = 40;
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
        return "SoldierLivingQuarters{" +
                "price=" + price +
                '}';
    }
}
