package flow.cells;

import flow.Player;

public class BarrackRoom extends CellItem{
    private int price;
    public BarrackRoom(Player owner) {
        super(owner, "Kaszárnya");
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
