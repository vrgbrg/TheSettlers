package flow.cells;

import flow.Player;

import javax.swing.*;

public class SoldierLivingQuarters extends CellItem{
    private int price;
    public SoldierLivingQuarters(Player owner) {
        super(owner, "Katonaszállás", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/townhall.png"));
        this.price = 150;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public int maxStep() {
        return 0;
    }

    @Override
    public boolean canMoveDiagonally() {
        return false;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int attackRange() {
        return 0;
    }
}
