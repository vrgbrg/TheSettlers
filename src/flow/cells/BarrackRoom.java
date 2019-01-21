package flow.cells;

import flow.Player;

import javax.swing.*;

public class BarrackRoom extends CellItem{
    private int price;
    public BarrackRoom(Player owner) {
        super(owner, "Kaszárnya", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/townhall.png"));
        this.price = 200;
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
        return true;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int attackRange() {
        return 0;
    }
}
