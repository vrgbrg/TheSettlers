package flow.cells;

import flow.Player;

import javax.swing.*;

public class BarrackRoom extends CellItem{
    private int price;
    public BarrackRoom(Player owner) {
        super(owner, "Barrack Room", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/barrackroom.png"));
        this.price = 200;
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
        return "BarrackRoom{" +
                "price=" + price +
                '}';
    }
}
